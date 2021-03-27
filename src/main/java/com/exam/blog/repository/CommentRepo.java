package com.exam.blog.repository;


import com.exam.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author Zhurenko Evgeniy
 */

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Comment getCommentById(Long id);
}
