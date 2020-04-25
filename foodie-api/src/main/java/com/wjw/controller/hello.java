package com.wjw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wjwjava01@163.com
 * @date : 2:44 2020/4/25
 * @description :
 */
@RestController
public class hello {
    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }
}
