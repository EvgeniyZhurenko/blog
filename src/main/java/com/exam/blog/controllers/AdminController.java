package com.exam.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/admin"})
public class AdminController {

    @GetMapping("")
    public String adminMenu(Model model){
        model.addAttribute("title", "Страница меню админа");
        return "admin-menu";
    }
}
