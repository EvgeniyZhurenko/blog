package com.exam.blog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 @author Zhurenko Evgeniy
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/", "classpath:/home/jelastic/upload/images/",
                "file:////var/lib/jelastic/PROJECTS/cooking-blog_cp_ROOT_202937/upload/images/");
    }
}
