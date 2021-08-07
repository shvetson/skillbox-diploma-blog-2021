package ru.shvets.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {

    @GetMapping("/index")
    public String auth(Model model){
        return null;
    }
}
