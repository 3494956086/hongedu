package com.hong.servicebase.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
@Bean
public Docket createRestApi(){
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("webApi")
            .apiInfo(webApiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            //.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
            //.paths(Predicates.not(PathSelectors.regex("/error.*")))
            .build();
}

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("hongedu在线教育API文档")
                .description("本文档描述了微服务各个接口")
                .version("1.0")
                .contact(new Contact("hong","http://hongpage.com","3494956086@qq.com"))
                .build();

    }
}
