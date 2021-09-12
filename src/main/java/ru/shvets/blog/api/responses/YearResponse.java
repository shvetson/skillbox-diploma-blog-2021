package ru.shvets.blog.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shvets.blog.models.Post;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class YearResponse {
    private int[] years;
    private List<?> posts;
}
