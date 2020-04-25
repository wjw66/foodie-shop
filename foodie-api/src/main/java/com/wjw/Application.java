package com.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author : wjwjava01@163.com
 * @date : 2:42 2020/4/25
 * @description :
 */
@MapperScan(basePackages = "com.wjw.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
