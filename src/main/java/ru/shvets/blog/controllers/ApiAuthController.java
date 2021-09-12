package ru.shvets.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shvets.blog.api.responses.CheckResponse;
import ru.shvets.blog.dto.CaptchaDto;
import ru.shvets.blog.models.CaptchaCode;
import ru.shvets.blog.repositories.CaptchaRepository;
import ru.shvets.blog.services.CaptchaService;
import ru.shvets.blog.services.CheckService;
import ru.shvets.blog.services.InitService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final InitService initService;
    private final CaptchaService captchaService;

    @GetMapping("/check")
    public CheckResponse check(){
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
}