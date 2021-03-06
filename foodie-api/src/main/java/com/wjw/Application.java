package com.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author : wjwjava01@163.com
 * @date : 2:42 2020/4/25
 * @description :
 */
//@EnableRedisHttpSession //开启redis作为spring session
@EnableScheduling //开启定时任务
@MapperScan(basePackages = "com.wjw.mapper")
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class}) //排除springSecurity的自动装配
@ComponentScan(basePackages = {"com.wjw","org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
