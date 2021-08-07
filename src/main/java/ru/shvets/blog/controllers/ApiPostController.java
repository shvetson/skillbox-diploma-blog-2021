package ru.shvets.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/posts")
public class ApiPostController {

    @GetMapping("/index")
    public String post(Model model){
        return null;
    }
}
