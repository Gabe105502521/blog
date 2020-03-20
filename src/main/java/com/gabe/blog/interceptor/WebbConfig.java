package com.gabe.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebbConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/register");
    }

    //上传到static的静态资源，是要重新build项目才能访问到的。可以通过自己指定静态资源访问路径来解决。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/spring-boot/blog/src/main/resources/static/upload/");
    }
}
