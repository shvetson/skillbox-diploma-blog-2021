package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginInDto {
    @NotBlank(message = "Убедитесь, что поле не пустое")
    @JsonProperty("e_mail")
    private String email;
    @NotBlank(message = "Убедитесь, что поле не пустое")
    private String password;
}
