package com.exam.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {


    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("title", "Страница авторизации");
        return "sign_in";
    }

    @GetMapping("/registration")
    public String registraionGet(Model model){
        model.addAttribute("title", "Страница регистрации");
        return "registration";
    }
}
