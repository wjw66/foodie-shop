package com.wjw.controller;

import com.wjw.UserService;
import com.wjw.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : wjwjava01@163.com
 * @date : 20:42 2020/4/26
 * @description : 用户通行证Controller
 */
@RestController
@RequestMapping("passport")
public class PassportController {
    @Resource
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username){
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空！");
        }
        //判断用户名是否存在,已存在返回500
        if (userService.queryUsernameIsExist(username)) {
            return JSONResult.errorMsg("用户名已存在！");
        }
        return JSONResult.ok();
    }}
