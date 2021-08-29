package ru.shvets.blog.api.responses;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;
}
