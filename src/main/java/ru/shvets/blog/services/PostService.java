package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.PostResponse;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.Post;
import ru.shvets.blog.models.User;
import ru.shvets.blog.repositories.PostRepository;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponse> prepare(List<Post> list) {
        List<PostResponse> response = new ArrayList<>();
        list.forEach(post -> {
            response.add(new PostResponse(
                    post.getId(),
                    post.getTime().getTime() / 1000,
                    new User(post.getUser().getId(), post.getUser().getName()),
                    post.getTitle(),
                    post.getText().substring(0, Math.min(post.getText().length(), 150)).concat(" ..."),
                    (int) post.getListVotes().stream().filter(a -> a.getValue() == 1).count(),
                    (int) post.getListVotes().stream().filter(a -> a.getValue() == -1).count(),
                    post.getListComments().size(),
                    post.getViewCount())
            );
        });
        return response;
    }

    public Sort sort(String mode) {
        switch (mode) {
            case "popular":
                return Sort.by("comments").descending();
            case "best":
                return Sort.by("votes").descending();
            case "early":
                return Sort.by("time");
            default:
                return Sort.by("time").descending();
        }
    }

    public Map<String, Object> response(Page<Post> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("posts", (page != null) ? prepare(page.toList()) : new PostResponse[]{});
        response.put("count", (page != null) ? page.getTotalElements() : 0);
        return response;
    }

    public Map<String, Object> getAllPosts(int offset, int limit, String mode) {
        if (mode.equals("popular")) {
            return response(postRepository.findAllIsActiveAndIsAcceptedAndComments(PageRequest.of(offset, limit, sort(mode))));
        } else if (mode.equals("best")) {
            return response(postRepository.findAllIsActiveAndIsAcceptedAndVotes(PageRequest.of(offset, limit, sort(mode))));
        } else {
            return response(postRepository.findAllIsActiveAndIsAccepted(PageRequest.of(offset, limit, sort(mode))));
        }
    }

    public Map<String, Object> getAllPostsByQuery(int offset, int limit, String query) {
        return response(postRepository.findByIsActiveAndModerationStatusAndTitleContaining((byte) 1,
                ModerationStatus.ACCEPTED,
                query,
                PageRequest.of(offset, limit, sort("recent"))));
    }

    public Map<String, Object> getAllPostsByYear(int year) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Integer> mapDatePostCount = new HashMap<>();

        LocalDate date = LocalDate.now();
        if (year == 0) {
            year = date.getYear();
        }

        int[] years = postRepository.findAllYears().stream().mapToInt(Number::intValue).toArray();
        List<Object> resultList = postRepository.countPostsByDate(formatDate.parse(year + "-01-01"), formatDate.parse((year + 1) + "-01-01"));

        for (Object item : resultList) {
            Object[] object = (Object[]) item;
            Date datePost = (Date) object[0];
            int countPosts = ((BigInteger) object[1]).intValue();
            mapDatePostCount.put(datePost.toString(), countPosts);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("years", years);
        response.put("posts", mapDatePostCount);

        return response;
    }

    public Map<String, Object> getAllPostsByDate(int offset, int limit, String date) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date byDate;

        if (date != null) {
            byDate = formatDate.parse(date);
        } else {
            byDate = new Date();
        }
        Date dateAfter = byDate;
        LocalDate localDateBefore = new java.sql.Date(byDate.getTime()).toLocalDate();
        Date dateBefore = java.sql.Date.valueOf(localDateBefore.plusDays(1));

        return response(postRepository.findByIsActiveAndModerationStatusAndDate(dateAfter, dateBefore, PageRequest.of(offset, limit, sort("recent"))));
    }

    public Map<String, Object> getAllPostsByTag(int offset, int limit, String tag) {
        return response(postRepository.findByIsActiveAndModerationStatusAndTag(tag, PageRequest.of(offset, limit, sort("recent"))));
    }
}