package com.exam.blog.controllers;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 @author Zhurenko Evgeniy
 */

@Controller
public class BlogController {


    private final BlogService blogService;


    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // view all blogs
    @GetMapping(value = {"/blog/list"})
    public String blogMain(@RequestParam(name = "data", required = false) String data,
                           Model model){
        List<Blog> blogs = blogService.getSortListBlogByRating();
        if(blogs.size() == 0) {
            model.addAttribute("list", false);
            model.addAttribute("msg", "На данном ресурсе пока что нет блогов!");
        }
        model.addAttribute("title", "Все блоги");
        model.addAttribute("blogList", blogs);
        model.addAttribute("blog", false);
        if(data.equals("rating")) {
            model.addAttribute("blogList", blogService.getSortListBlogByRating());
            model.addAttribute("sort", data);
        }
        if(data.equals("alphabet")){
            model.addAttribute("blogList", blogService.getSortListBlogByAlphabet());
            model.addAttribute("sort", data);
        }
        if(data.equals("date")){
            model.addAttribute("blogList", blogService.getSortListBlogByDate());
            model.addAttribute("sort", data);
        }
        return "blog-list";
    }

    // view details of blog
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

