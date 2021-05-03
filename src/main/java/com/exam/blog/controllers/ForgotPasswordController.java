package com.exam.blog.controllers;

import com.exam.blog.models.User;
import com.exam.blog.service.MailSender;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 @author Zhurenko Evgeniy
 */


@Controller
public class ForgotPasswordController {

    private UserRepoImpl userRepo;

    private MailSender mailSender;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }


    // Redirect to the forgot password page
    @GetMapping(value="/forgot-password")
    public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("forgot-password");
        return modelAndView;
    }

    // Receive the address and send an email
    @PostMapping(value="/forgot-password")
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user) {
        User existingUser = userRepo.findUserByEmail(user.getEmail());
        if (existingUser != null) {

            existingUser.setActivationCode(UUID.randomUUID().toString());

            userRepo.update(existingUser,true);

            String message = String.format(
                    "Hello, %s! \n" +
                            "Добро пожаловать на blog.com. Для завершения сброса пароля перейдите по следующей ссылке: " +
                            "http://localhost:8080/confirm-reset/%s",
                    existingUser.getUsername(),
                    existingUser.getActivationCode()
            );

            // Send the email
            mailSender.send(existingUser.getEmail(), "Activation code", message);

            modelAndView.addObject("message", "Получен запрос на сброс пароля. Проверьте свой почтовый ящик на наличие ссылки для сброса.");
            modelAndView.addObject("user",existingUser);
            modelAndView.addObject("bool",true);
            modelAndView.addObject("title", "Страница восстановления пароля");
            modelAndView.setViewName("success-forgot-password");

        } else {
            modelAndView.addObject("title", "Страница восстановления пароля");
            modelAndView.addObject("message", "Такого почтового адреса не существует!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // Receive the token from email and redirect to the reset password page
    @GetMapping(value="/confirm-reset/{token}")
    public ModelAndView validateResetToken(ModelAndView modelAndView,
                                           @PathVariable("token")String confirmationToken) {

        if (confirmationToken != null) {
            User user = userRepo.findUserByActivationCode(confirmationToken);

            modelAndView.addObject("user", user);
            modelAndView.addObject("email", user.getEmail());
            modelAndView.addObject("title", "Новый пароль");
            modelAndView.setViewName("reset-password");
        } else {
            modelAndView.addObject("title", "Новый пароль");
            modelAndView.addObject("message", "Ссылка недействительна или битая!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // Endpoint to update a user's password
    @PostMapping(value = "/reset-password")
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        if (user.getEmail() != null) {
            // Use email to find user
            User tokenUser = userRepo.findUserByEmail(user.getEmail());
            tokenUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.updatePassword(tokenUser);
            modelAndView.addObject("title", "Страница авторизации");
            modelAndView.addObject("error", "true");
            modelAndView.addObject("message", "Пароль успешно перезаписан. " +
                    "Вы можете теперь логиниться с новым паролем.");
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject("user", user);
            modelAndView.addObject("idUser", user.getId());
            modelAndView.addObject("message","Ссылка недействительна или битая!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
