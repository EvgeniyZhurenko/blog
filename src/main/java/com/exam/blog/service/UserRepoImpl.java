package com.exam.blog.service;

import com.exam.blog.models.Blog;
import com.exam.blog.models.Role;
import com.exam.blog.models.User;
import com.exam.blog.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        User userFromDB = userRepo.getOne(user.getId());
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

    public Float countRating(User user){

        List<Float> ratingAllBlogsList = user.getBlogs().stream().map(Blog::getRating).collect(Collectors.toList());
        Float avarageRating =ratingAllBlogsList.stream().reduce((n1, n2)-> n1+n2).orElse((float) 0).floatValue() /ratingAllBlogsList.size();
        return avarageRating;
    }


    public void writeUser(User userForm, User userDB){
//        if(userDB.getPassword() != userForm.getPassword() && (userForm.getPassword() != ""
//        || userForm.getPassword() != null))
//            userDB.setPassword(userForm.getPassword());
//        if(userDB.getUsername() != userForm.getUsername() && (userForm.getUsername() != ""
//        || userForm.getUsername() != null))
//            userDB.setUsername(userForm.getUsername());
//        if(userDB.getFirst_name() != userForm.getFirst_name())
//            userDB.setFirst_name(userForm.getFirst_name());
//        if(userDB.getLast_name() != userForm.getLast_name())
//            userDB.setLast_name(userForm.getLast_name());
//        if(userDB.getEmail() != userForm.getEmail())
//            userDB.setEmail(userForm.getEmail());
//        if(userDB.getBorn() != userForm.getBorn())
//            userDB.setBorn(userForm.getBorn());
//        if(userDB.getCity() != userForm.getCity())
//            userDB.setCity(userForm.getCity());
//        if(userDB.getCountry() != userForm.getCountry())
//            userDB.setCountry(userForm.getCountry());
//        if(userDB.getFoto() != userForm.getFoto())
//            userDB.setFoto(userForm.getFoto());
//        if(userDB.getGit_hub() != userForm.getGit_hub())
//            userDB.setGit_hub(userForm.getGit_hub());
//        if(userDB.getFacebook() != userForm.getFacebook())
//            userDB.setFacebook(userForm.getFacebook());
//        if(userDB.getInstagram() != userForm.getInstagram())
//            userDB.setInstagram(userForm.getInstagram());
//        if(userDB.getTwiter() != userForm.getTwiter())
//            userDB.setTwiter(userForm.getTwiter());
//        if(userDB.getPhone() != userForm.getPhone())
//            userDB.setPhone(userForm.getPhone());
//
//        if(!userDB.getRoles().equals(userForm.getRoles()))
//            userDB.setRoles();
//        if(userDB.getInstagram() != userForm.getInstagram())
//            userDB.setInstagram(userForm.getInstagram());

    }
}
