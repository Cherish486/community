package com.nowcoder.community.config;

import com.nowcoder.community.controller.interceptor.LoginRequiredInterceptor;
import com.nowcoder.community.controller.interceptor.LoginTicketIntercept;
import com.nowcoder.community.controller.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    // @Autowired
    // private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketIntercept loginTicketIntercept;

    @Autowired
    private MessageInterceptor messageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // registry.addInterceptor(alphaInterceptor)
        //         .excludePathPatterns("/**/*.css","/**/*.js","/**/*,png","/**/*.jpg","/**/*.jpeg")
        //         .addPathPatterns("/register","/login");

        registry.addInterceptor(loginTicketIntercept)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*,png","/**/*.jpg","/**/*.jpeg");

        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*,png","/**/*.jpg","/**/*.jpeg");

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*,png","/**/*.jpg","/**/*.jpeg");
    }
}
/*
* 拦截器的应用：
* 1. 在请求开始时查询登录用户
* 2. 在本次请求中持有用户数据
* 3. 在模板仕途上显示用户数据
* 4. 在请求结束时清理用户数据
* */