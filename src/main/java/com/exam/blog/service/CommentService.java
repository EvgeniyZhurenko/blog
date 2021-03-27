package com.exam.blog.service;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.User;
import com.exam.blog.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * author Zhurenko Evgeniy
 */

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private UserRepoImpl userRepo;
    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public CommentService() {
    }

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment, Long idUser, Long idBlog) {

        commentRepo.save(comment);

        User user = userRepo.getById(idUser);
        user.getComments().add(comment);
        userRepo.update(user);

        Blog blog = blogService.getById(idBlog);
        blog.getComments().add(comment);
        blogService.update(blog);

    }

    public boolean update(Comment comment) {
        Comment commentDB = commentRepo.getCommentById(comment.getId());
        if(commentDB != null) {
            commentRepo.saveAndFlush(comment);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        commentRepo.deleteById(id);
    }

    public Comment getById(Long id) {
        return commentRepo.getCommentById(id);
    }
}
