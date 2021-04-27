package com.exam.blog.config;


import com.exam.blog.security.CustomAuthenticationFailureHandler;
import com.exam.blog.security.RefererRedirectionAuthenticationSuccessHandler;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

/**
 @author Zhurenko Evgeniy
  * @noinspection ALL
 */

@Configuration
@Import({WebSecurityBeanConfig.class})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private PasswordEncoder passwordEncoder;

        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

        private AccessDeniedHandler accessDeniedHandler;

        private AuthenticationEntryPoint authenticationEntryPoint;

        private UserRepoImpl userRepo;

        @Autowired
        private DataSource dataSource;

        @Autowired
        public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
                this.passwordEncoder = passwordEncoder;
        }

        @Autowired
        public void setUserDetailsService(UserRepoImpl userRepo) {
                this.userRepo = userRepo;
        }

        @Autowired
        public void setCustomAuthenticationFailureHandler(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
                this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
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

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
                JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
                db.setDataSource(dataSource);
                return db;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
                web.debug(true);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

                http
                        .authorizeRequests().antMatchers("/registration").not().fullyAuthenticated();
                http
                        .authorizeRequests().antMatchers("/user/**").hasRole("USER");
                http
                        .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
                http
                        .authorizeRequests()
                        .antMatchers("/","/**","/**/*.jpg", "/**/*.css", "/**/*.js","/static/**", "/activate/*" ).permitAll();
                http
                        .authorizeRequests().anyRequest().authenticated();
                http
                        .authorizeRequests().and().exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler);
                http
                        .authorizeRequests()
                        .and()
                        .formLogin()
                        .failureHandler(customAuthenticationFailureHandler)
                        .loginPage("/sign_in")
                        .loginProcessingUrl("/authentication")
//                        .defaultSuccessUrl("/main", true)
                        .usernameParameter("username")
//                        .failureUrl("/sign_in")
//                        .failureForwardUrl("/authentication?error=true")
                        .successHandler(new RefererRedirectionAuthenticationSuccessHandler())

                        .and()
                        .logout(logout -> logout
                                        .logoutUrl("/leave/authentication")
                                        .deleteCookies("JSESSIONID")
                                        .logoutSuccessUrl("/main")
                        );
                http
                        .authorizeRequests().and() //
                        .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                        .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h


        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userRepo)
                        .passwordEncoder(passwordEncoder);
        }

}
