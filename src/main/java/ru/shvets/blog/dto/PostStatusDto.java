package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shvets.blog.validators.IsPost;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostStatusDto {
    @JsonProperty("post_id")
    @IsPost(message = "Записи в базе данных нет")
    private Long postId;
    private String decision;
}