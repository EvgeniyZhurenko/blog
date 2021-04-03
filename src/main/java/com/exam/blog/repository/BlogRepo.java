package com.exam.blog.repository;

import com.exam.blog.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 @author Zhurenko Evgeniy
 */

public interface BlogRepo extends JpaRepository<Blog, Long> {

    Blog getBlogById(Long id);

    Blog getBlogByTitle(String title);

}

