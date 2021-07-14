package com.exam.blog.controllers;

import com.exam.blog.models.User;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 @author Zhurenko Evgeniy
 */

@Controller
public class SecurityController {

    private final UserRepoImpl userRepo;

    @Autowired
    public SecurityController(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    // Authentication process and redirect depending on role to the relation page
    @GetMapping(value = {"login", "/authentication"})
    public String loginGet(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("title", "Страница авторизации");
        if(auth == null){
            return "redirect:/main";
        } else if(!auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
            if(auth.getAuthorities().stream().anyMatch(a-> a.toString().equalsIgnoreCase("ROLE_USER"))) {
                User userDB = userRepo.getUserByUserName(auth.getName());
                userDB.setEnabled(true);
                userRepo.update(userDB,true);
                return "redirect:/user/main";
            }
            if(auth.getAuthorities().stream().anyMatch(a-> a.toString().equalsIgnoreCase("ROLE_ADMIN")))
                return "redirect:/admin/main";
        }
        return "sign_in";
    }

    // Redirect on login page if receive error
    @PostMapping(value = {"/authentication"})
    public String loginPost(@RequestParam(name = "error", required = false) Boolean error,
                            @RequestParam(name = "message", required = false) String message,
                            @RequestParam(name = "un", required = false) String username,
                            Model model){

        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error" , true);
            model.addAttribute("message", message);
            model.addAttribute("username", username);
        }
        return "sign_in";

    }

    // Open registration page
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

    // Registration process controller
    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute User user, Model model,
                                   RedirectAttributes redirectAttributes){

        String[] arrProps = userRepo.userRegistration(user);
        if(Arrays.stream(arrProps).filter(e -> e != null).collect(Collectors.toList()).size() == 0) {
            User userDB = userRepo.getUserByUserName(user.getUsername());
            if (userDB == null && user.getUsername() != null) {
                userRepo.saveBoolean(user);
                model.addAttribute("title", "Сообщение");
                model.addAttribute("message", "Перейдите в почтовый ящик, указанный при регистрации "
                        + user.getEmail());
                return "email-message";
            } else {
                redirectAttributes.addFlashAttribute("account", true);
                redirectAttributes.addFlashAttribute("msg", "Такой аккаунт уже существует!\nПопробуйте еще раз");
                return "redirect:/registration";
            }
        } else {
            redirectAttributes.addFlashAttribute("bool", true);
            redirectAttributes.addFlashAttribute("msg", arrProps);
            return "redirect:/registration";
        }
    }

    // Ending registration process when receive email and follow by email link
    @GetMapping("/activate/{code}")
    public ModelAndView activate(ModelAndView modelAndView,
                           @PathVariable(name = "code", required = false) String code){

        boolean isActivate = userRepo.activateUser(code);

        if(isActivate){
            modelAndView.addObject("error", "true");
            modelAndView.addObject("message", "Пользователь активирован успешно");
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject("title", "Error");
            modelAndView.addObject("message", "Код активации не найден");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
