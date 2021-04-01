package com.exam.blog.controllers;

import com.exam.blog.models.User;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;

@Controller
public class SecurityController {

    private final UserRepoImpl userRepo;

    @Autowired
    public SecurityController(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String loginGet(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
            model.addAttribute("title", "Страница авторизации");
            return "redirect:/";
        }

        return "sign_in";
    }

    @PostMapping(value = {"/login"})
    public String loginPost(@RequestParam(name = "error", required = false) Boolean error,
                            Model model){

        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error" , true);
        }
        return "sign_in";

    }

    @GetMapping("/registration")
    public String registrationGet(Model model){
        model.addAttribute("title", "Страница регистрации");
        Map post = model.asMap();
        for(Object key : post.keySet()){
            if(key != "title") {
                model.addAttribute(key.toString(), post.get(key));
            }
        }

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute User user,
                                   RedirectAttributes redirectAttributes){

        if(Arrays.asList(userRepo.userRegistration(user)).contains(null)) {
            User userDB = userRepo.getUserByUserName(user.getUsername());
            if (userDB == null && user.getUsername() != null) {
                userRepo.saveBoolean(user);
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("account", true);
                redirectAttributes.addFlashAttribute("msg", "Такой аккаунт уже существует!\n Попробуйте еще раз");
                return "redirect:/registration";
            }
        } else {
            redirectAttributes.addFlashAttribute("bool", true);
            redirectAttributes.addFlashAttribute("msg", userRepo.userRegistration(user));
            return "redirect:/registration";
        }
    }
}
