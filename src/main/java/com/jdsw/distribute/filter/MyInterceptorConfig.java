package com.jdsw.distribute.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，要声明拦截器对象和要拦截的请求
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**") //所有路径都被拦截
                .excludePathPatterns("/login");
    }
}