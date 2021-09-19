package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.dto.NewUserDto;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.InitService;
import ru.shvets.blog.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final InitService initService;
    private final CaptchaService captchaService;
    private final UserService userService;

    @GetMapping("/check")
    public CheckResponse check(HttpSession session){
        session.setAttribute("user", 1);
        System.out.println(session.getAttribute("user"));

        return checkService.getUser(initService.authId());
    }
    
    @GetMapping("/check/users/{id}")
    public CheckResponse getUser(@PathVariable long id){
        return checkService.getUser(id);
    }

    @GetMapping("/captcha")
    public CaptchaDto getCaptcha(){
        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    public NewUserDto addUser(@RequestParam(name = "email") @Email(message = "Убедитесь, что адрес электронной почты действителен") String email,
                              @RequestParam(name = "password") @Min(value = 6, message = "Убедитесь, что количество символов пароля не меньше 6-ти") String password,
                              @RequestParam(name = "name") String name){
        return userService.addUser();
    }
}