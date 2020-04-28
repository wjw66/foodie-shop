package com.wjw.config;

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

/**
 * @author : wjwjava01@163.com
 * @date : 0:04 2020/4/29
 * @description : 配置swagger2
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * http://localhost:8088/swagger-ui.html 原路径
     * http://localhost:8088/doc.html 皮肤路径
     * 配置swagger2的核心配置
     */
    @Bean
    public Docket createRestApi() {

        return new Docket(
                //指定文档为SWAGGER2
                DocumentationType.SWAGGER_2)
                //定义基础信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wjw.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger2信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档Api")
                .contact(new Contact("wjw",
                        "http://cdn.codespark.vip",
                        "wjw@163.com"))
                .description("接口文档Api")
                .version("1.0.1")
                .termsOfServiceUrl("127.0.0.1")
                .build();
    }
}
