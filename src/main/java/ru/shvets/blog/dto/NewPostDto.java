package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shvets.blog.models.Tag;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDto {
    @JsonProperty("user_id")
    private Long userId;
    @Temporal(TemporalType.TIMESTAMP) //!
    @NotNull
    private long timestamp;

    @Min(0)
    @Max(1)
    private byte active;

    @NotBlank (message = "Заголовок не установлен")
    @Size(min = 3, message = "Наименование заголовка слишком короткое (не менее 3 символов)")
    private String title;

    //@NotEmpty(message = "Необходимо указать минимум  один тэг")
    String[] tags;

    @NotBlank (message = "Нет текста")
    @Size(min = 50, message = "Текст публикации слишком короткий (не менее 50 символов)")
    private String text;
}
