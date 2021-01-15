/*
package com.jdsw.distribute.config;

import com.jdsw.distribute.util.PathUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigure implements WebMvcConfigurer {
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

}
*/
