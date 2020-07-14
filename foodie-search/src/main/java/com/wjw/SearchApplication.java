package com.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : wjwjava01@163.com
 * @date : 2:42 2020/4/25
 * @description :
 */
@SpringBootApplication//排除springSecurity的自动装配
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }
}
