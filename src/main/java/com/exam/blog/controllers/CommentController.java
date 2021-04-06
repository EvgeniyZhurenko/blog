package com.exam.blog.controllers;

import com.exam.blog.models.Comment;
import com.exam.blog.repository.UserRepo;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = {"comment/"})
public class CommentController {

    private final BlogService blogService;
    private final CommentService commentService;
    private final UserRepo userRepo;

    @Autowired
    public CommentController(BlogService blogService, CommentService commentService, UserRepo userRepo) {
        this.blogService = blogService;
        this.commentService = commentService;
        this.userRepo = userRepo;
    }

    @PostMapping("creat/{id}")
    public String creatComment(@ModelAttribute Comment comment,
                               @PathVariable(value = "id", required = false) Long idBlog){
        comment.setId(null);
        comment.setDateCreateComment(LocalDateTime.now());
        comment.setBlog(blogService.getById(idBlog));
        comment.setBanComment(false);
        comment.setUser(userRepo.getUserById(1L));
        commentService.save(comment);

        return "redirect:/blog/" + idBlog;
    }
}
