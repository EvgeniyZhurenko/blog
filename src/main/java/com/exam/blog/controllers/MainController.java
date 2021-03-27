package com.exam.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage( Model model) {
        model.addAttribute("title", "Главная страница");
        return "main";
    }

    @GetMapping("/about")
    public String aboutPage( Model model) {
        model.addAttribute("title", "Страница о сайте");
        return "about";
    }

    @GetMapping("/support")
    public String supportPage( Model model) {
        model.addAttribute("title", "Страница технической поддержки");
        return "support";
    }

    @GetMapping("/contacts")
    public String contactsPage( Model model) {
        model.addAttribute("title", "Котнакты");
        return "contacts";
    }

}
