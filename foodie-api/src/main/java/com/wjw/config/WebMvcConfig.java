package com.wjw.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : wjwjava01@163.com
 * @date : 20:55 2020/5/21
 * @description :
 */
@Configuration
public class WebMvcConfig {
    /**
     * 将restTemplate构建成一个bean,
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }
}
