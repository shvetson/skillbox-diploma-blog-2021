package ru.shvets.blog.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private  boolean result;
    private Map<String, Object> errors;
}
