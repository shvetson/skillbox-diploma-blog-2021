package ru.shvets.blog.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {
    private int count;
    @JsonProperty("posts")
    private List<PostInResponse> postList;
}

