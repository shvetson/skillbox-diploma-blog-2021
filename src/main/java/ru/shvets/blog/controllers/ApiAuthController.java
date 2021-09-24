package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.models.User;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.InitService;
import ru.shvets.blog.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final InitService initService;
    private final CaptchaService captchaService;
    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/check")
    public CheckResponse check() {
        long userId = initService.authId();
        httpSession.setAttribute("user", userId);
        return checkService.getUser(userId);
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
    public ResponseEntity<?> addUser(@Valid @RequestBody NewUserDto newUserDto) {

        User user = new User();
        user.setEmail(newUserDto.getEmail());
        user.setPassword(newUserDto.getPassword());
        user.setName(newUserDto.getName());
        user.setCode(newUserDto.getSecretCode());

        userService.addUser(user);
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("result", true);
        response.put("errors", null);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}