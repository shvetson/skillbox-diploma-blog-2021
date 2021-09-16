package ru.shvets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewUserDto implements Serializable {


    @Email
    @JsonProperty("e_mail")
    private String email;

    private String password;

    private String name;
    @JsonProperty("captcha")
    private String code;
    @JsonProperty("captcha_secret")
    private String secretCode;
}