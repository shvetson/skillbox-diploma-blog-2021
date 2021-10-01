package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.api.responses.UserResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.dto.UserLoginDto;
import ru.shvets.blog.models.ModerationStatus;
import ru.shvets.blog.models.User;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.InitService;
import ru.shvets.blog.services.UserService;
import ru.shvets.blog.utils.MappingUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final InitService initService;
    private final CaptchaService captchaService;
    private final UserService userService;
    private final MappingUtils mappingUtils;
    private final HttpSession httpSession;

    @GetMapping("/check")
    public CheckResponse check() {
        return checkService.getUser(initService.authId());
    }

    @GetMapping("/check/users/{id}")
    public CheckResponse getUser(@PathVariable long id) {
        return checkService.getUser(id);
    }

    @GetMapping("/captcha")
    public CaptchaDto getCaptcha() {
        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<ErrorResponse> addUser(@Valid @RequestBody NewUserDto newUserDto) {
        ErrorResponse response = new ErrorResponse();
        userService.addUser(mappingUtils.mapToUser(newUserDto));
        response.setResult(true);
        response.setErrors(null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CheckResponse> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        Map<String, Long> mapSessions = new HashMap<>();

        UserResponse userResponse = new UserResponse();
        CheckResponse checkResponse = new CheckResponse();

        User user = userService.getUserByEmail(userLoginDto.getEmail());

        if (user != null) {
            mapSessions.put(httpSession.getId(), user.getId());



        } else {
            checkResponse.setResult(false);
            checkResponse.setUserResponse(null);
        }
        return ResponseEntity.ok(checkResponse);
    }
}