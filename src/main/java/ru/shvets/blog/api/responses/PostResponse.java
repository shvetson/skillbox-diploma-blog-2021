package ru.shvets.blog.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shvets.blog.dto.UserShortDto;
import ru.shvets.blog.models.User;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostResponse {
    private long id;
    private long timestamp;
    private User user;
//    private UserShortDto user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
