package com.exam.blog.config;


import com.exam.blog.security.RefererRedirectionAuthenticationSuccessHandler;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 @author Zhurenko Evgeniy
  * @noinspection ALL
 */

@Configuration
@Import({WebSecurityBeanConfig.class})
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private PasswordEncoder passwordEncoder;

        private AuthenticationFailureHandler authenticationFailureHandler;

        private AccessDeniedHandler accessDeniedHandler;

        private AuthenticationEntryPoint authenticationEntryPoint;

        private UserRepoImpl userRepo;

        @Autowired
        public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Autowired
        public void setUserDetailsService(UserRepoImpl userRepo) {
                this.userRepo = userRepo;
        }

        @Autowired
        public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
                this.authenticationFailureHandler = authenticationFailureHandler;
        }

        @Autowired
        public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
                this.accessDeniedHandler = accessDeniedHandler;
        }

        @Autowired
        public void setUserRepo(UserRepoImpl userRepo) {
                this.userRepo = userRepo;
        }

        @Autowired
        public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
                this.authenticationEntryPoint = authenticationEntryPoint;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
                web.debug(true);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .authorizeRequests()
                        .antMatchers("/user/**").hasRole("USER")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/registration").anonymous()
                        .antMatchers("/login", "/","/metrics", "/blog/list",
                                                 "/about", "/contacts",
                                                 "/**/*.jpg", "/**/*.css", "/**/*.js" ).permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                        .and()
                        .formLogin()
                        .loginPage("/sign_in")
                        .loginProcessingUrl("/login/process")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/login")
                        .failureForwardUrl("/login?error = true")
                        .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler)
                        .and()
                        .logout(logout -> logout
                                        .logoutUrl("/leave/authentication")
                                        .deleteCookies("JSESSIONID")
                                        .logoutSuccessUrl("/")
                        );


        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userRepo).passwordEncoder(passwordEncoder);
        }

}
