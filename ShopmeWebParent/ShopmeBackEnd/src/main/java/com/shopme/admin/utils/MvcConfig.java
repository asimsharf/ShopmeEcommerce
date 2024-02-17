package com.shopme.admin.utils;

import com.shopme.admin.paging.PagingAndSortingArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        exposeDirectory("user-image", registry);
        exposeDirectory("category-image", registry);
        exposeDirectory("brand-image", registry);
        exposeDirectory("product-images", registry);
    }

    private void exposeDirectory(String s, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(s);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + s + "/**").addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagingAndSortingArgumentResolver());
    }

}
