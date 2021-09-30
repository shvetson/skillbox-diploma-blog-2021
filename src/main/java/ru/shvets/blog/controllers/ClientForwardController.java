package ru.shvets.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//add this controller : perfect solution(from jhipster)
@Controller
public class ClientForwardController {
//    @RequestMapping(value = "/**/{path:[^\\.]*}")
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "redirect:/";
    }
}