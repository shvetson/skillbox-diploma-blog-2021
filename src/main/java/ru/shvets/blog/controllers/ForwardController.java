package ru.shvets.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping(value = "/**/{path:[^\\.]*}")
    public String redirect() {
        return "redirect:/";
    }
}

//add this controller : perfect solution(from jhipster)
// https://spring.io/blog/2015/05/13/modularizing-the-client-angular-js-and-spring-security-part-vii
//@Controller
//public class ClientForwardController {
//    @GetMapping(value = "/**/{path:[^\\.]*}")
//    public String forward() {
//        return "forward:/";
//    }
//}