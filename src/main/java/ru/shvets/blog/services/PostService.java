package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.PostInResponse;
import ru.shvets.blog.api.responses.PostResponse;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.PostCommentRepository;
import ru.shvets.blog.repositories.PostRepository;
import ru.shvets.blog.repositories.PostVoteRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;
    private final PostCommentRepository postCommentRepository;

    public int count() {
        return postRepository.countFindByIsActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED);
    }

    public List<PostInResponse> getAll(int offset, int limit, String mode) {
        List<PostInResponse> postInResponse = new ArrayList<>();
        postRepository.findByIsActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED).
                forEach(post -> {
                    postInResponse.add(new PostInResponse(post.getId(),
                            post.getTime().getTime() / 1000,
                            new User(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(),
                            post.getText().substring(0, Math.min(post.getText().length(), 150)).concat(" ..."),
                            postVoteRepository.countFindByPostIdAndValue(post.getId(), (byte) 1),
                            postVoteRepository.countFindByPostIdAndValue(post.getId(), (byte) -1),
                            postCommentRepository.countFindByPostId(post.getId()),
                            post.getViewCount())
                    );
                });

        switch (mode) {
            case "popular":
                return postInResponse.stream().
                        skip(offset).limit(limit).
                        sorted(Comparator.comparingInt(PostInResponse::getCommentCount).reversed()).
                        collect(Collectors.toList());
            case "best":
                return postInResponse.stream().
                        skip(offset).limit(limit).
                        sorted(Comparator.comparingInt(PostInResponse::getLikeCount).reversed()).
                        collect(Collectors.toList());
            case "early":
                return postInResponse.stream().
                        skip(offset).limit(limit).
                        sorted(Comparator.comparingLong(PostInResponse::getTimestamp)).
                        collect(Collectors.toList());
            default:
                return postInResponse.stream().
                        skip(offset).limit(limit).
                        sorted(Comparator.comparingLong(PostInResponse::getTimestamp).reversed()).
                        collect(Collectors.toList());
        }
    }

    public PostResponse getAllPosts(int offset, int limit, String mode) {
        return new PostResponse(count(), getAll(offset, limit, mode));
    }
}