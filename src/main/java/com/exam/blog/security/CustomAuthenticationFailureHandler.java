package com.exam.blog.security;


import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Zhurenko Evgeniy
 */

@Component
public class CustomAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {


    private UserRepoImpl userRepo;

    @Autowired
    public void setUserRepo(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException, AuthenticationException {
        String message = "";
        String username = request.getParameter("username");

        if(userRepo.findUserByUsernameException(username)){
            message = "Пользователя с таким логином не найдено";
        } else if(exception.getClass() == BadCredentialsException.class) {
            message = "Проверьте свой пароль";
        }

        request.getRequestDispatcher(String.format("/authentication?error=true&message=%s&un=%s", message,username))
                .forward(request, response);
    }


}