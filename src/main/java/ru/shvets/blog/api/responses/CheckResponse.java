package ru.shvets.blog.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shvets.blog.dto.UserLoginOutDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckResponse {
    private boolean result;
    @JsonProperty("user")
    private UserLoginOutDto userLoginOutDto;
}
