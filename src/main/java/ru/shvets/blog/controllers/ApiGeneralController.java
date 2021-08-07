package ru.shvets.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ApiGeneralController{

    @GetMapping("/init")
    public ResponseEntity<?> init(){
        return null;
    }
}
