package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;

@AllArgsConstructor()
@NoArgsConstructor
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdatedDto implements Serializable {
    @NotBlank(message = "Имя указано неверно (убедитесь, что поле не пустое)")
    @NotEmpty(message = "Введите имя")
    @Size(min = 2, max = 25, message = "Имя должно быть не менее 2 символов и не больше 25")
    private String name;

    @NotBlank(message = "Убедитесь, что поле не пустое")
    @Email(message = "Убедитесь, что адрес электронной почты действителен")
    //@IsEmail(message = "Этот e-mail уже зарегистрирован")
    private String email;

    @Size(min = 6, message = "Пароль короче 6-ти символов")
    private String password;

    private MultipartFile photo;
    private byte removePhoto;
}