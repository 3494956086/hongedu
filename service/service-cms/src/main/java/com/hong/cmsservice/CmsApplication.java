package com.hong.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient  //开启微服务
@SpringBootApplication
@ComponentScan(basePackages = {"com.hong"})
@MapperScan("com.hong.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args){
        SpringApplication.run(CmsApplication.class,args);
    }
}
