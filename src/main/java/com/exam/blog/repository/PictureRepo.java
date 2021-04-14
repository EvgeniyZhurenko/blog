package com.exam.blog.repository;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 @author Zhurenko Evgeniy
 */

public interface PictureRepo extends JpaRepository<Picture, Long> {

    Picture getPictureById(Long id);

}
