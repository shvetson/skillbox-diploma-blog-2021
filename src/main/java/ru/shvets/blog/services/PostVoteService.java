package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.dto.PostJustIdDto;
import ru.shvets.blog.models.Post;
import ru.shvets.blog.models.PostVote;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.PostRepository;
import ru.shvets.blog.repositories.PostVoteRepository;
import ru.shvets.blog.utils.MappingUtils;

@Service
@AllArgsConstructor
@Slf4j
public class PostVoteService {
    private final PostVoteRepository postVoteRepository;
    private final PostRepository postRepository;
    private final MappingUtils mappingUtils;

    //Лайк поста
    public ErrorResponse likePost(PostJustIdDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        User user = mappingUtils.getUserFromListSessions();
        if (post.getUser().getId() == user.getId()) {
            return ErrorResponse.of(false, null);
        }

        PostVote postVote = postVoteRepository.findPostVoteByUserIdAndPostId(user.getId(), dto.getPostId());
        if (postVote == null) {
            PostVote newPostVote = new PostVote();
            newPostVote.setUserId(user.getId());
            newPostVote.setPost(post);
            newPostVote.setValue((byte) 1);
            postVoteRepository.save(newPostVote);
        } else {
            if (postVote.getValue() == 1) {
                return ErrorResponse.of(false, null);
            }
            postVote.setValue((byte) 1);
            postVoteRepository.save(postVote);
        }
        return ErrorResponse.of(true, null);
    }

    //Дизлайк поста
    public ErrorResponse dislikePost(PostJustIdDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        User user = mappingUtils.getUserFromListSessions();
        if (post.getUser().getId() == user.getId()) {
            return ErrorResponse.of(false, null);
        }

        PostVote postVote = postVoteRepository.findPostVoteByUserIdAndPostId(user.getId(), dto.getPostId());
        if (postVote == null) {
            PostVote newPostVote = new PostVote();
            newPostVote.setUserId(user.getId());
            newPostVote.setPost(post);
            newPostVote.setValue((byte) -1);
            postVoteRepository.save(newPostVote);
        } else {
            if (postVote.getValue() == -1) {
                return ErrorResponse.of(false, null);
            }
            postVote.setValue((byte) -1);
            postVoteRepository.save(postVote);
        }
        return ErrorResponse.of(true, null);
    }

}
