package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewUserDto {
    @NotNull
    @Email(message = "Убедитесь, что адрес электронной почты действителен")
    @JsonProperty("e_mail")
    private String email;

    @NotNull
    @Min(value = 6, message = "Убедитесь, что количество символов пароля не меньше 6-ти")
    private String password;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty("captcha")
    private String code;

    @NotNull
    @JsonProperty("captcha_secret")
    private String secretCode;
}