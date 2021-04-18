package com.exam.blog.controllers;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BlogController {


    private final BlogService blogService;


    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(value = {"/blog/list"})
    public String blogMain(Model model){
        List<Blog> blogs = blogService.getSortListBlogByRating();
        if(blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("title", "Все блоги");
        model.addAttribute("blogList", blogs);
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "blog-list";
    }

    @GetMapping(value = {"/blog/{id_blog}/{bool}"})
    public String blogSee(@PathVariable(name = "id_blog", required = false) Long idBlog,
                          @PathVariable(name = "bool", required = false) Boolean bool,
                          Model model){
        Blog blog = blogService.getById(idBlog);
        Comment comment = new Comment();
        model.addAttribute("blog", blog);
        model.addAttribute("comment", comment);
        model.addAttribute("state", bool);
        return "blog";
    }

}

