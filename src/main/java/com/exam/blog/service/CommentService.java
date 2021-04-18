package com.exam.blog.service;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Comment;
import com.exam.blog.models.User;
import com.exam.blog.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * author Zhurenko Evgeniy
 */

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private BlogService blogService;
    private UserRepoImpl userRepo;

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public CommentService() {
    }

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment) {

        comment.setBanComment(false);
        commentRepo.save(comment);

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

        Comment commentDB = commentRepo.getCommentById(id);
        Blog blogDB = blogService.getById(commentDB.getBlog().getId());
        User userDB = userRepo.getById(commentDB.getUser().getId());
        blogDB.getComments().remove(commentDB);
        userDB.getComments().remove(commentDB);
        commentRepo.deleteById(id);
    }

    public Comment getById(Long id) {
        return commentRepo.getCommentById(id);
    }

    public List<Comment> findAllCommentsBlog(Long idBlog){
        return commentRepo.findAll().stream()
                          .filter(comment -> comment.getBlog().getId()==(idBlog))
                          .collect(Collectors.toList());
    }
}
