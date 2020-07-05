package com.wjw.config;

import com.wjw.interceptor.UserTokenInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : wjwjava01@163.com
 * @date : 20:55 2020/5/21
 * @description :
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 静态资源映射处理，如图片，js等
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                //swagger2的映射地址
                .addResourceLocations("classpath:/META-INF/resources/")
                //头像图片映射资源的路径
                .addResourceLocations("file:\\workspaces\\images\\");
    }

    /**
     * 将restTemplate构建成一个bean,http方式远程调用服务
     *
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    /**
     * 将UserTokenInterceptor注册成一个bean,相当于spring的xml中的bean标签
     * @return
     */
    @Bean
    public UserTokenInterceptor userTokenInterceptor(){
        return new UserTokenInterceptor();
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(userTokenInterceptor())
                //添加要拦截的路径
                .addPathPatterns("/hello");
        //将registry注册到mvc中
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
