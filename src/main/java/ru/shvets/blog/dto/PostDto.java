package ru.shvets.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private long id;
    private long timestamp;
    private UserShortDto user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
