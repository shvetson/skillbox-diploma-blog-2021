package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shvets.blog.validators.IsEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewUserDto {
    @NotBlank(message = "Убедитесь, что поле не пустое")
    @Email(message = "Убедитесь, что адрес электронной почты действителен")
    @IsEmail(message = "Этот e-mail уже зарегистрирован")
    @JsonProperty("e_mail")
    private String email;

    @NotBlank(message = "Убедитесь, что поле не пустое")
    @Size(min = 6, message = "Пароль короче 6-ти символов")
    private String password;

    @NotBlank(message = "Имя указано неверно (убедитесь, что поле не пустое)")
    private String name;

    @NotBlank(message = "Убедитесь, что поле не пустое")
    @JsonProperty("captcha")
    private String code;

    @NotBlank(message = "Убедитесь, что поле не пустое")
    @JsonProperty("captcha_secret")
    private String secretCode;
}