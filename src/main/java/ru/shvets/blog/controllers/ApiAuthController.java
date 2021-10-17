package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.api.responses.ErrorResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.dto.UserLoginInDto;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.UserService;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final CaptchaService captchaService;
    private final UserService userService;

    @GetMapping("/check")
    public CheckResponse check() {
        return checkService.getUser();
    }

    @GetMapping("/captcha")
    public CaptchaDto getCaptcha() {
        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<ErrorResponse> addUser(@Valid @RequestBody NewUserDto newUserDto) {
        return ResponseEntity.ok(userService.addUser(newUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CheckResponse> login(@Valid @RequestBody UserLoginInDto userLoginInDto) {
        return ResponseEntity.ok(userService.getUserByLogin(userLoginInDto));
    }
}