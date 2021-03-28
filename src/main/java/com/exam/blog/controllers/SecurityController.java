package com.exam.blog.controllers;

import com.exam.blog.models.User;
import com.exam.blog.service.UserRepoImpl;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecurityController {

    private final UserRepoImpl userRepo;

    @Autowired
    public SecurityController(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("title", "Страница авторизации");
        return "sign_in";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam(name = "error", required = false) Boolean error,
                            Model model){
        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error" , true);
        }
        return "sign_in";

    }

    @GetMapping("/registration")
    public String registraionGet(Model model){
        model.addAttribute("title", "Страница регистрации");
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute User user,
                                   Model model){
        if( userRepo.userRegistration(user).equalsIgnoreCase("Заполните поля :")) {
            User userDB = userRepo.getUserByUserName(user.getUsername());
            if (userDB == null) {
                userRepo.saveBoolean(user);
                return "redirect:/login";
            } else {
                model.addAttribute("bool", true);
                model.addAttribute("msg", "Аккаунт с таким " + user.getUsername() + " уже существует!");
                return "redirect:/registration";
            }
        } else {
            model.addAttribute("bool", true);
            model.addAttribute("msg", userRepo.userRegistration(user));
            return "redirect:/registration";
        }
    }
}
