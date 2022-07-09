package com.simplecloudnote.config;

import com.simplecloudnote.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 配置类
*/
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/a/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
