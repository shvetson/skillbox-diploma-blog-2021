package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.InitService;
import ru.shvets.blog.services.UserService;
import ru.shvets.blog.utils.MappingUtils;

import javax.validation.Valid;

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
}