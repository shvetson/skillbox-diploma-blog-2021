package ru.shvets.blog.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagInResponse {
    private String name;
    @JsonIgnore
    private int countPostsWithTag;
    @JsonIgnore
    private double countWeight;
    private double weight;
}
