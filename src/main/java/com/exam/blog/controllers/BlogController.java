package com.exam.blog.controllers;


import com.exam.blog.models.Blog;
import com.exam.blog.models.User;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BlogController {


    private final BlogService blogService;
    private final UserRepoImpl userRepo;

    @Autowired
    public BlogController(BlogService blogService, UserRepoImpl userRepo) {
        this.blogService = blogService;
        this.userRepo = userRepo;
    }

    @GetMapping("/blog/list")
    public String blogMain(Model model){
        List<Blog> blogs = blogService.getSortListBlogByRating();
        if(blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("blogList", blogs);
        model.addAttribute("boolean", false);
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "blog-list";
    }

    @GetMapping("/blog/{id}")
    public String blogSee(@PathVariable(name = "id", required = false) Long idBlog,
                          Model model){
        Blog blog = blogService.getById(idBlog);
        model.addAttribute("blog", blog);
        return "blog";
    }

}

