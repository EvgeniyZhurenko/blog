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
        model.addAttribute("blogList", blogs);
        model.addAttribute("boolean", false);
        model.addAttribute("anonymous", true);
        model.addAttribute("blog", false);
        return "blog-list";
    }

    @GetMapping(value = {"/blog/{id_blog}"})
    public String blogSee(@PathVariable(name = "id_blog", required = false) Long idBlog,
                          Model model){
        Blog blog = blogService.getById(idBlog);
        Comment comment = new Comment();
        model.addAttribute("blog", blog);
        model.addAttribute("comment", comment);
        return "blog";
    }

//    @GetMapping("blog/range/{id_blog}/{raiting}")
//    public String blogShow(@PathVariable(value = "id_blog", required = false) Long id_blog,
//                           @PathVariable(value = "raiting", required = false) Long raiting){
//        Blog blogDB = blogService.getById(id_blog);
//        if(blogDB.getRating() == 0F){
//            blogDB.setRating(blogDB.getRating() + raiting);
//        } else {
//            blogDB.setRating((blogDB.getRating() + raiting) / 2);
//        }
//        blogService.update(blogDB);
//        return "redirect:/blog/" + id_blog;
//    }

}

