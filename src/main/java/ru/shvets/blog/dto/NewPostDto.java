package ru.shvets.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPostDto {
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private long timestamp;

    @Min(0)
    @Max(1)
    private byte active;

    @NotBlank (message = "Заголовок не установлен")
    @Size(min = 3, message = "Наименование заголовка слишком короткое (не менее 3 символов)")
    private String title;

    @NotEmpty(message = "Необходимо указать минимум  один тэг")
    String[] tags;

    @NotBlank (message = "Нет текста")
    @Size(min = 50, message = "Текст публикации слишком короткий (не менее 50 символов)")
    private String text;
}
