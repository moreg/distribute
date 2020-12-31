package com.jdsw.distribute;


import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.jdsw.distribute.dao")
@EnableCaching
public class DistributeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeApplication.class, args);
    }
}
