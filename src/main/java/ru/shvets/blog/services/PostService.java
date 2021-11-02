package ru.shvets.blog.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shvets.blog.api.responses.CommentResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.dto.*;
import ru.shvets.blog.exceptions.NoSuchPostException;
import ru.shvets.blog.models.*;
import ru.shvets.blog.repositories.*;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;
    private final TagService tagService;
    private final MappingUtils mappingUtils;
    private final HttpSession httpSession;
    private final MapSessions mapSessions;

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

    public PostCountDto response(Page<Post> page) {
        PostCountDto dto = new PostCountDto();
        dto.setCount((page != null) ? page.getTotalElements() : 0);
        dto.setPosts((page != null) ? mappingUtils.mapToListPostDto(page.toList()) : List.of(new PostDto[]{}));
        return dto;
    }

    public PostCountDto getAllPosts(int offset, int limit, String mode) {
        switch (mode) {
            case "popular":
                return response(postRepository.
                        findAllIsActiveAndIsAcceptedAndComments(PageRequest.of(offset, limit, sort(mode))));
            case "best":
                return response(postRepository.
                        findAllIsActiveAndIsAcceptedAndVotes(PageRequest.of(offset, limit, sort(mode))));
            default:
                return response(postRepository.
                        findAllIsActiveAndIsAccepted(PageRequest.of(offset, limit, sort(mode))));
        }
    }

    public PostCountDto getAllPostsByQuery(int offset, int limit, String query) {
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

    public PostCountDto getAllPostsByDate(int offset, int limit, String date) throws ParseException {
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

    public PostCountDto getAllPostsByTag(int offset, int limit, String tag) {
        return response(postRepository.findByIsActiveAndModerationStatusAndTag(tag, PageRequest.of(offset, limit, sort("recent"))));
    }

    public PostCommentDto getPostById(Long postId) {
        Post post = postRepository.findPostByIdAndIsActiveAndModerationStatus(postId, (byte) 1, ModerationStatus.ACCEPTED);

        if (post == null) {
            throw new NoSuchPostException("Записи с id=" + postId + " в базе данных нет.");
        }

        String sessionId = httpSession.getId();
        Long userId = mapSessions.getUserId(sessionId);

        if (userId != null) {
            User user = userRepository.findUserById(userId);

            if (user.getIsModerator() != 1 & post.getUser().getId() != userId) {
                increaseViewCount(post);
            }
        }
        return mappingUtils.mapToPostCommentDto(post);
    }

    public void increaseViewCount(Post post) {
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);
        }
    }

    public PostCountDto getAllPostByModerationStatus(int offset, int limit, String status) {
        switch (status) {
            case "new":
                return response(postRepository.findAllByIsActiveAndModerationStatusAndModeratorId((byte) 1,
                        ModerationStatus.NEW,
                        null,
                        PageRequest.of(offset, limit, sort("recent"))));
            case "accepted":
                return response(postRepository.findAllByIsActiveAndModerationStatusAndModeratorId((byte) 1,
                        ModerationStatus.ACCEPTED,
                        mappingUtils.getUserFromListSessions().getId(),
                        PageRequest.of(offset, limit, sort("recent"))));
            default:
                return response(postRepository.findAllByIsActiveAndModerationStatusAndModeratorId((byte) 1,
                        ModerationStatus.DECLINED,
                        mappingUtils.getUserFromListSessions().getId(),
                        PageRequest.of(offset, limit, sort("recent"))));
        }
    }

    public PostCountDto getAllPostByStatus(int offset, int limit, String status) {
        String sessionId = httpSession.getId();
        Long userId = mapSessions.getUserId(sessionId);

        switch (status) {
            case "pending":
                return response(postRepository.findAllByIsActiveAndModerationStatusAndUserId((byte) 1,
                        ModerationStatus.NEW,
                        userId,
                        PageRequest.of(offset, limit, sort("recent"))));
            case "declined":
                return response(postRepository.findAllByIsActiveAndModerationStatusAndUserId((byte) 1,
                        ModerationStatus.DECLINED,
                        userId,
                        PageRequest.of(offset, limit, sort("recent"))));
            case "published":
                return response(postRepository.findAllByIsActiveAndModerationStatusAndUserId((byte) 1,
                        ModerationStatus.ACCEPTED,
                        userId,
                        PageRequest.of(offset, limit, sort("recent"))));
            default:
                return response(postRepository.findAllByIsActiveAndModerationStatusAndUserId((byte) 0,
                        null,
                        userId,
                        PageRequest.of(offset, limit, sort("recent"))));
        }
    }

    //Добавление поста (с проверкой на ввод)
    public ErrorResponse addPost(NewPostDto newPostDto) {
        Post post = mappingUtils.mapNewPostDtoToPost(newPostDto);
        long postId = postRepository.save(post).getId();
        log.info("Добавлен новый пост");

        for (String itemTeg : Arrays.stream(newPostDto.getTags()).collect(Collectors.toList())) {
            Tag tag = tagService.findTagByName(itemTeg);

            //Добавление нового тэга в словарь, если его нет
            if (tag == null) {
                tag = new Tag();
                tag.setName(itemTeg);
                long tagId = tagService.save(tag).getId();
                log.info("Добавлен новый тэг - " + itemTeg);
            }

            //Добавление данных в связывающую таблицу Tag2Post
            Tag2Post tag2Post = new Tag2Post();
            tag2Post.setPostId(postId);
            tag2Post.setTagId(tag.getId());
            tagService.save(tag2Post);
        }

        ErrorResponse response = new ErrorResponse();
        response.setResult(true);
        response.setErrors(null);
        return response;
    }

    public ErrorResponse updatePost(Long postId, NewPostDto newPostDto) {
        Post post = mappingUtils.mapPostDtoUpdateToPost(postId, newPostDto);
        postRepository.save(post);
        ErrorResponse response = new ErrorResponse();
        response.setResult(true);
        response.setErrors(null);
        return response;
    }

    public CommentResponse addComment(NewCommentDto newCommentDto) throws Exception {
        CommentResponse response = new CommentResponse();
        response.setCommentID(postCommentRepository.save(mappingUtils.mapNewCommentDtoToPostComment(newCommentDto)).getId());
        log.info("Добавлены комментарии к  посту");
        return response;
    }
    //При добавлении комментария возможно надо включить проверку - если автор поста, то комментарии не может оставлять, только комментарии на комментарии других (в ТЗ нет)

    public ErrorResponse updateStatusPost(PostStatusDto postStatusDto) {
        ErrorResponse response = new ErrorResponse();
        if (mappingUtils.getUserFromListSessions().getIsModerator() == 1) {
            Post post = mappingUtils.mapPostStatusDtoToPost(postStatusDto);
            postRepository.save(post);
            response.setResult(true);
            response.setErrors(null);
            log.info("Обновлен статус поста");
        } else {
            response.setResult(false);
            response.setErrors(null);
        }
        return response;
    }

    //Моя статистика
    public StatDto getStatistics() {
        Integer dislikesCount;
        Integer likesCount;
        StatDto dto = new StatDto();

        User user = mappingUtils.getUserFromListSessions();
        List<Object[]> resultList = postRepository.countAndSumAndMinDateInPostsByUser(user.getId());
        mapDataFromQueryToDto(resultList, dto);
        likesCount = postVoteRepository.countPostVotesByUserIdAndValue(user.getId(), (byte) 1);
        dto.setLikesCount(likesCount != null ? likesCount : 0);
        dislikesCount = postVoteRepository.countPostVotesByUserIdAndValue(user.getId(), (byte) -1);
        dto.setDislikesCount(dislikesCount != null ? dislikesCount : 0);
        return dto;
    }

    //Статистика по всему блогу
    public StatDto getAllStatistics() {
        Integer dislikesCount;
        Integer likesCount;
        StatDto dto = new StatDto();
        boolean moderator = mappingUtils.getUserFromListSessions().getIsModerator() == 1;
        boolean statPublic = settingsRepository.getValueByCode().equals("true");

        List<Object[]> resultList = postRepository.countAndSumAndMinDateInPosts();
        mapDataFromQueryToDto(resultList, dto);
        likesCount = postVoteRepository.countPostVotesByValueEquals((byte) 1);
        dto.setLikesCount(likesCount != null ? likesCount : 0);
        dislikesCount = postVoteRepository.countPostVotesByValueEquals((byte) -1);
        dto.setDislikesCount(dislikesCount != null ? dislikesCount : 0);
        return dto;
    }

    //Обработка запроса агрегированных данных для формирования статистики
    private void mapDataFromQueryToDto(List<Object[]> resultList, StatDto dto) {
        for (Object[] item : resultList) {
            dto.setPostsCount(((BigInteger) item[0]).intValue());
            dto.setViewsCount(item[1] != null ? ((BigDecimal) item[1]).intValue() : 0);
            dto.setFirstPublication(item[2] != null ? ((Date) item[2]).getTime() / 1000 : 0);
        }
    }
}