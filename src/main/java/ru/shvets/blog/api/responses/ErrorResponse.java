package ru.shvets.blog.api.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponse {
    private String email;
    private String name;
    private String password;
    private String captcha;
}
