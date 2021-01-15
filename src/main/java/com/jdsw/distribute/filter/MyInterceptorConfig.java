package com.jdsw.distribute.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，要声明拦截器对象和要拦截的请求
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**") //所有路径都被拦截
                .excludePathPatterns("/login")
                .excludePathPatterns("/distribute/excelNetwork")
                .excludePathPatterns("/telemark/excelNetwork")
                .excludePathPatterns("/distribute/uploadImg")
                .excludePathPatterns("/telemark/uploadImg")
                .excludePathPatterns("/distribute/uploadFile")
                .excludePathPatterns("/telemark/uploadFile")
                .excludePathPatterns("classpath:/META-INF/resources/")
        ;
    }
}