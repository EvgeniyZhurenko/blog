package com.exam.blog.repository;

import com.exam.blog.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


/**
 @author Zhurenko Evgeniy
 */


public interface UserRepo extends JpaRepository<User, Long> {

        User getUserByUsername(String userName);

        User getUserById(Long id);

        Optional<User> findById(Long id);
}
