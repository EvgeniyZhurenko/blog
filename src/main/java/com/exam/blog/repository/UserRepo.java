package com.exam.blog.repository;

import com.exam.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 @author Zhurenko Evgeniy
 */


public interface UserRepo extends JpaRepository<User, Long> {

        User getUserByUsername(String userName);

        User getUserById(Long id);
}
