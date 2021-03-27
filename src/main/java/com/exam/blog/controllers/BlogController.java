package com.exam.blog.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/blog"})
public class BlogController {

    @GetMapping("")
    public String blogMain(Model model){
        model.addAttribute("title", "Блог");
        return "blog-main";
    }
}

