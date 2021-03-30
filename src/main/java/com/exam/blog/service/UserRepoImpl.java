package com.exam.blog.service;

import com.exam.blog.models.Role;
import com.exam.blog.models.User;
import com.exam.blog.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 @author Zhurenko Evgeniy
 */


@Service
public class UserRepoImpl implements UserDetailsService {

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveBoolean(User user) {
        User userFromDB = userRepo.getUserByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(User user) {
        User userFromDB = userRepo.getUserById(user.getId());
        if (userFromDB != null) {
            userRepo.saveAndFlush(user);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        User userDelete = userRepo.getUserById(id);
        userRepo.delete(userDelete);
    }

    public User getById(Long id) {
        User user = userRepo.getUserById(id);
        return user;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with " + userName + " not found");
        }
        return user;
    }

    public User getUserByUserName(String userName) {
        User user = userRepo.getUserByUsername(userName);
        return user;
    }

    public String[] userRegistration(User user) {
        String[] attr = new String[6];

        if (user.getFirst_name().equals(""))
            attr[0] = "Заполните поле Ваша имя!";
        if (user.getLast_name().equals(""))
            attr[1] = "Заполните поле Ваша фамилия!";
        if (user.getEmail().equals(""))
            attr[2] = "Заполните поле Ваш e-mail!";
        if (user.getPhone().equals(""))
            attr[3] = "Заполните поле Ваш телефон!";
        if (user.getUsername().equals(""))
            attr[4] = "Заполните поле Ваше логин!";
        if (user.getPassword().equals(""))
            attr[5] = "Заполните поле Ваш пароль!";

        return attr;
    }



}
