package com.exam.blog.controllers;


import com.exam.blog.models.Blog;
import com.exam.blog.repository.BlogRepo;
import com.exam.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collector;

@Controller
@RequestMapping(value = {"/blog"})
public class BlogController {


    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    public String blogMain(Model model){
        List<Blog> blogs = blogService.getSortListBlogByRating();
        model.addAttribute("title", "Блог");
        model.addAttribute("blogList", blogs);
        return "blog-main";
    }

}

