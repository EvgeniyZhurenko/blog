package com.exam.blog.config;


import com.exam.blog.security.RefererRedirectionAuthenticationSuccessHandler;
import com.exam.blog.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

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
                        .antMatchers("/","/**","/**/*.jpg", "/**/*.css", "/**/*.js" ).permitAll();
                http
                        .authorizeRequests().antMatchers("/registration").not().fullyAuthenticated();
                http
                        .authorizeRequests().antMatchers("/user/**","/main").hasRole("USER");
                http
                        .authorizeRequests().antMatchers("/admin/**","/main").hasRole("ADMIN");
                http
                        .authorizeRequests().anyRequest().authenticated();
                http.authorizeRequests().and().exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler);
                http
                        .authorizeRequests()
                        .and()
                        .formLogin()
                        .loginProcessingUrl("/authantication")
                        .loginPage("/sign_in")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/login?error = true")
//                        .failureForwardUrl("/login?error = true")
                        .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler)

                        .and()
//                        .logout()
//                        .logoutUrl("/leave/authentication")
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/main");
                        .logout(logout -> logout
                                        .logoutUrl("/leave/authentication")
                                        .deleteCookies("JSESSIONID")
                                        .logoutSuccessUrl("/main")
                                        .permitAll()
                        );
                http
                        .authorizeRequests().and() //
                        .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                        .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h


        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userRepo).passwordEncoder(passwordEncoder);
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
                JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
                db.setDataSource(dataSource);
                return db;
        }

}
