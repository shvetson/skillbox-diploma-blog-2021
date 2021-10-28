package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shvets.blog.validators.IsPost;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewCommentDto {
    //TODO  добавить проверку?

    @JsonProperty("parent_id")
    private Long parentId;

    @IsPost(message = "Записи в базе данных нет")
    @JsonProperty("post_id")
    private Long postId;

    @NotBlank(message = "Нет текста")
    @Size(min = 50, message = "Текст публикации слишком короткий (не менее 50 символов)")
    private String text;
}
