package com.exam.blog.config;

import com.exam.blog.security.CustomAccessDeniedHandler;
import com.exam.blog.security.CustomAuthenticationFailureHandler;
import com.exam.blog.security.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import java.util.LinkedHashMap;


/**
 @author Zhurenko Evgeniy
 */


@Configuration
public class WebSecurityBeanConfig {

    //настройка кодировки пароля
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new ForwardAuthenticationSuccessHandler("/status/200");
//    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
        authenticationEntryPoint.setUseForward(true);
        return authenticationEntryPoint;
    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> errorHandlers = new LinkedHashMap<>();
//
//        // неверный обработчик ошибок аутентификатора csrf
//        AccessDeniedHandlerImpl invalidCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
//        invalidCsrfTokenErrorHandler.setErrorPage("/status/403");
//        errorHandlers.put(InvalidCsrfTokenException.class, invalidCsrfTokenErrorHandler);
//
//        // пропуск обработчика ошибок аутентификатора csrf
//        AccessDeniedHandlerImpl missingCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
//        missingCsrfTokenErrorHandler.setErrorPage("/status/403");
//        errorHandlers.put(MissingCsrfTokenException.class, missingCsrfTokenErrorHandler);
//
//        // обработчик ошибок по умолчанию
//        AccessDeniedHandlerImpl defaultErrorHandler = new AccessDeniedHandlerImpl();
//        defaultErrorHandler.setErrorPage("/status/403");
//
//        return new DelegatingAccessDeniedHandler(errorHandlers, defaultErrorHandler);
//    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> authenticationFailureHandlers = new LinkedHashMap<>();
//
//
//        // обработчик ошибок аутентификатора
//        ForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler = new ForwardAuthenticationFailureHandler("/login");
//        authenticationFailureHandlers.put(AuthenticationException.class, forwardAuthenticationFailureHandler);
//
//        // обработчик ошибок по умолчанию
//        AuthenticationFailureHandler defaultAuthenticationFailureHandler = new ForwardAuthenticationFailureHandler("/authentication?error=true");
//
//
//        return new DelegatingAuthenticationFailureHandler(authenticationFailureHandlers, defaultAuthenticationFailureHandler);
//    }

    @Bean
    CustomAuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    CustomAccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
