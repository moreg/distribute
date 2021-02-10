package com.jdsw.distribute.filter;

import com.jdsw.distribute.util.PathUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer{

    @Resource
    private MyInterceptor myInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath= PathUtil.getBasePath();
        String fileBasePath="file:"+basePath+"/";

        registry.addResourceHandler("/**").
                //保证其他资源不会被屏蔽
                        addResourceLocations("classpath:/META-INF/resources/").
                //设置入我们的基础路径
                        addResourceLocations(fileBasePath);
    }


   @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，要声明拦截器对象和要拦截的请求
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**") //所有路径都被拦截
                .excludePathPatterns("/login");
    }


}

