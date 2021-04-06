package com.exam.blog.controllers;


import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.CommentService;
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
    private final CommentService commentService;

    @Autowired
    public BlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
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
        Comment comment = new Comment();
        model.addAttribute("blog", blog);
        model.addAttribute("comment", comment);
        return "blog";
    }

    @GetMapping("blog/range/{id_blog}/{raiting}")
    public String blogShow(@PathVariable(value = "id_blog", required = false) Long id_blog,
                           @PathVariable(value = "raiting", required = false) Long raiting,
                           Model model){
        Blog blogDB = blogService.getById(id_blog);
        if(blogDB.getRating() == 0F){
            blogDB.setRating(blogDB.getRating() + raiting);
        } else {
            blogDB.setRating((blogDB.getRating() + raiting) / 2);
        }
        blogService.update(blogDB);
        return "redirect:/blog/" + id_blog;
    }

}

