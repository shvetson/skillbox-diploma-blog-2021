package ru.shvets.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private long id;
    @Temporal(TemporalType.TIMESTAMP) //!
    private long timestamp;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
    private UserShortDto user;
}
