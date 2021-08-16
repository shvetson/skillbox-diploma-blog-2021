package ru.shvets.blog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class ApiPostController {

    @GetMapping("/index")
    public String show(){
        return null;
    }
}
