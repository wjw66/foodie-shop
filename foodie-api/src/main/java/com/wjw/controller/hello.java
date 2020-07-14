package com.wjw.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wjwjava01@163.com
 * @date : 2:44 2020/4/25
 * @description :
 */
@Controller
public class hello {
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }

    @GetMapping("/demo")
    public String index(){

        return "demo";
    }
}
