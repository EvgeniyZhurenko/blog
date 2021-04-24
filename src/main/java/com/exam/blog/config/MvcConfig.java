package com.exam.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.bg.path}")
    private String uploadBgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("../images/**").addResourceLocations("file://" + uploadPicturePath);
        registry.addResourceHandler("../images/profiles_images**").addResourceLocations("file://" + uploadPath);
//        registry.addResourceHandler("../images/**").addResourceLocations("file://" + uploadBgPath);
    }
}
