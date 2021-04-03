package com.exam.blog.repository;

import com.exam.blog.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 @author Zhurenko Evgeniy
 */

public interface PictureRepo extends JpaRepository<Picture, Long> {

    Picture getPictureById(Long id);

    Picture getPictureByName(String name_picture);
}
