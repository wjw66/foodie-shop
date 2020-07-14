package com.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author : wjwjava01@163.com
 * @date : 23:54 2020/7/10
 * @description :
 */
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class}) //排除springSecurity的自动装配
@MapperScan(basePackages = "com.wjw.mapper")
@ComponentScan(basePackages = {"com.wjw","org.n3r.idworker"})
public class CasApplication {
    public static void main(String[] args) {
        SpringApplication.run(CasApplication.class,args);
    }
}
