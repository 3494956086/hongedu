package com.hong.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient  //微服务
@SpringBootApplication
@ComponentScan(basePackages = {"com.hong"})
@MapperScan("com.hong.eduservice.mapper")
public class EduServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(EduServiceApplication.class,args);
    }
}
