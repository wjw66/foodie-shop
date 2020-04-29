package com.wjw.controller;

import com.wjw.UserService;
import com.wjw.pojo.Users;
import com.wjw.pojo.bo.UserBO;
import com.wjw.utils.JSONResult;
import com.wjw.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : wjwjava01@163.com
 * @date : 20:42 2020/4/26
 * @description : 用户通行证Controller
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "用户是否存在")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空！");
        }
        //判断用户名是否存在,已存在返回500
        if (userService.queryUsernameIsExist(username)) {
            return JSONResult.errorMsg("用户名已存在！");
        }
        return JSONResult.ok();
    }
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBO userBO) {
        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(userBO.getUsername()) || StringUtils.isBlank(userBO.getPassword())
                || StringUtils.isBlank(userBO.getConfirmPassword())) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        //1.查询用户名是否存在
        if (userService.queryUsernameIsExist(userBO.getUsername())) {
            return JSONResult.errorMsg("用户名已存在！");
        }
        //2.判断两次密码是否一致
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return JSONResult.errorMsg("两次输入的密码不一致！");
        }
        //3.密码长度不能少于6位
        if (userBO.getPassword().length() < 6) {
            return JSONResult.errorMsg("密码长度不能少于6位！");
        }

        //4.实现注册
        Users user = userService.createUser(userBO);
        return JSONResult.ok(user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }

//        userResult = setNullProperty(userResult);
//
//        CookieUtils.setCookie(request, response, "user",
//                JsonUtils.objectToJson(userResult), true);


        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return JSONResult.ok(userResult);
    }
}
