package com.jdsw.distribute;


import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.apache.catalina.filters.CorsFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.jdsw.distribute.dao")
@EnableCaching
@EnableScheduling
public class DistributeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeApplication.class, args);
    }
    @Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        return executor;
    }

}

