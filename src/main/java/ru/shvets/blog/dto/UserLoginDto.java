package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.shvets.blog.validators.IsEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserLoginDto {
    @NotBlank(message = "Убедитесь, что поле не пустое")
    //@IsEmail(message = "Этот e-mail уже зарегистрирован")
    @JsonProperty("e_mail")
    private String email;
    @NotBlank(message = "Убедитесь, что поле не пустое")
    //@Size(min = 6, message = "Пароль короче 6-ти символов")
    private String password;
}
