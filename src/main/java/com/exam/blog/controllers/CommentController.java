package com.exam.blog.controllers;

import com.exam.blog.models.Comment;
import com.exam.blog.repository.UserRepo;
import com.exam.blog.service.BlogService;
import com.exam.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 @author Zhurenko Evgeniy
 */

@Controller
@RequestMapping(value = {"comment/", "/user/"})
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

    // to add comment to blog bu guest
    @PostMapping("creat/{id}")
    public String creatComment(@ModelAttribute Comment comment,
                               @PathVariable(value = "id", required = false) Long idBlog){
        comment.setId(null);
        comment.setDateCreateComment(LocalDateTime.now());
        comment.setBlog(blogService.getById(idBlog));
        comment.setBanComment(false);
        comment.setUser(userRepo.getUserById(1L));
        commentService.save(comment);

        return "redirect:/blog/" + idBlog + "/true";
    }

    // to add comment to blog bu user
    @PostMapping("comment/creat/{id_user}/{id_blog}/{id_user_blog}")
    public String creatUserComment(@ModelAttribute Comment comment,
                                   @PathVariable(value = "id_user", required = false) Long id_user,
                                   @PathVariable(value = "id_blog", required = false) Long idBlog,
                                   @PathVariable(value = "id_user_blog", required = false) Long id_user_blog){
        comment.setId(null);
        comment.setDateCreateComment(LocalDateTime.now());
        comment.setBlog(blogService.getById(idBlog));
        comment.setBanComment(false);
        comment.setUser(userRepo.getUserById(id_user));
        commentService.save(comment);

        return "redirect:/user/blog/" + id_user + "/" + idBlog + "/" + id_user_blog + "/true";
    }
}
