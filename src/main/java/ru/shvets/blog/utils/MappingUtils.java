package ru.shvets.blog.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shvets.blog.components.MapSessions;
import ru.shvets.blog.dto.*;
import ru.shvets.blog.models.*;
import ru.shvets.blog.repositories.PostRepository;
import ru.shvets.blog.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MappingUtils {
    private final TimeUtils timeUtils;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final MapSessions mapSessions;

    public SettingsDto mapToSettingsDto(List<GlobalSettings> list) {
        SettingsDto dto = new SettingsDto();
        dto.setMultiUserMode(FindItemFromSettings(list, Settings.MULTIUSER_MODE));
        dto.setPostPreModeration(FindItemFromSettings(list, Settings.POST_PREMODERATION));
        dto.setStatisticsIsPublic(FindItemFromSettings(list, Settings.STATISTICS_IS_PUBLIC));
        return dto;
    }

    public boolean FindItemFromSettings(List<GlobalSettings> list, Settings settings) {
        return list.stream().
                filter(item -> item.getCode().equals(settings.toString())).
                findFirst().get().
                getValue().
                equals("true");
    }

    public PostCommentDto mapToPostCommentDto(Post post) {
        PostCommentDto dto = new PostCommentDto();
        dto.setId(post.getId());
        dto.setTimestamp(post.getTime().getTime() / 1000 - timeUtils.getSecondsOffSet());
        dto.setActive(post.getIsActive() == 1);
        dto.setUser(mapToUserShortDto(post.getUser()));
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        dto.setLikeCount((int) post.getListVotes().stream().filter(item -> item.getValue() == 1).count());
        dto.setDislikeCount((int) post.getListVotes().stream().filter(item -> item.getValue() == -1).count());
        dto.setViewCount(post.getViewCount());
        dto.setComments(mapToListCommentDto(post.getListComments()));
        dto.setTags(post.getTagList().stream().map(Tag::getName).toArray(String[]::new));
        return dto;
    }

    public PostDto mapToPostDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTimestamp(post.getTime().getTime() / 1000 - timeUtils.getSecondsOffSet());
        dto.setUser(mapToUserShortDto(post.getUser()));
        dto.setTitle(post.getTitle());
        dto.setAnnounce(post.getText().substring(0, Math.min(post.getText().length(), 150)).concat(" ..."));
        dto.setLikeCount((int) post.getListVotes().stream().filter(a -> a.getValue() == 1).count());
        dto.setDislikeCount((int) post.getListVotes().stream().filter(a -> a.getValue() == -1).count());
        dto.setCommentCount(post.getListComments().size());
        dto.setViewCount(post.getViewCount());
        return dto;
    }

    public List<PostDto> mapToListPostDto(List<Post> list) {
        return list.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }

    public CommentDto mapToCommentDto(PostComment postComment) {
        CommentDto dto = new CommentDto();
        dto.setId(postComment.getId());
        dto.setTimestamp(postComment.getTime().getTime() / 1000);
        dto.setText(postComment.getText());
        dto.setUser(mapToUserDto(postComment.getUser()));
        return dto;
    }

    public List<CommentDto> mapToListCommentDto(List<PostComment> list) {
        return list.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    public UserShortDto mapToUserShortDto(User user) {
        UserShortDto dto = new UserShortDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }

    public UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhoto(user.getPhoto());
        return dto;
    }

    // регистрация нового пользователя
    public User mapToUser(NewUserDto newUserDto) {
        User user = new User();
        user.setRegTime(new Date());
        user.setEmail(newUserDto.getEmail());
        user.setPassword(newUserDto.getPassword());
        user.setName(newUserDto.getName());
        user.setCode(newUserDto.getSecretCode());
        return user;
    }

    //Авторизация пользователя (вход)
    public UserLoginOutDto mapToUserLoginOutDto(User user) {
        UserLoginOutDto dto = new UserLoginOutDto();
        int moderationCount = postRepository.findByModerationStatus(ModerationStatus.NEW).size();
        boolean userIsModeration = user.getIsModerator() == 1;

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhoto(user.getPhoto());
        dto.setEmail(user.getEmail());
        dto.setModeration(userIsModeration);
        dto.setModerationCount(userIsModeration ? moderationCount : 0);
        dto.setSettings(userIsModeration);
        return dto;
    }

    public Post mapNewPostDtoToPost(NewPostDto newPostDto) {
        Post post = new Post();
        post.setUser(getUserFromListSessions());
        post.setTime(timeUtils.checkAndCorrectTimestamp(new Timestamp(newPostDto.getTimestamp())));
        post.setIsActive(newPostDto.getActive());
        post.setTitle(newPostDto.getTitle());
        post.setModerationStatus(ModerationStatus.NEW);
        post.setText(newPostDto.getText());
        return post;
    }

    public Post mapPostDtoUpdateToPost(Long postId, NewPostDto newPostDto){
        Post post = postRepository.getPostById(postId);
        User user = getUserFromListSessions();

        post.setTime(timeUtils.checkAndCorrectTimestamp(new Timestamp(newPostDto.getTimestamp())));
        post.setIsActive(newPostDto.getActive());
        post.setTitle(newPostDto.getTitle());
        post.setText(newPostDto.getText());

        if (user.getIsModerator() == 0) {
            post.setModerationStatus(ModerationStatus.NEW);
        }
        return post;
    }

    public User getUserFromListSessions(){
        String sessionId = httpSession.getId();
        Long userId = mapSessions.getUserId(sessionId);

        if (userId == null) {
            throw new IllegalArgumentException("Пользователя с id=" + userId + " нет в листе сессий");
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Пользователя с id=" + userId + " нет");
        }
        return user;
    }
}