package com.shopme.admin.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-image", registry);
        exposeDirectory("category-image", registry);
    }

    private void exposeDirectory(String s, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(s);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + s + "/**").addResourceLocations("file:" + uploadPath + "/");
    }

}
