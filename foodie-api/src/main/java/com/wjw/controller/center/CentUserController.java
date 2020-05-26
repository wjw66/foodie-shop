package com.wjw.controller.center;

import com.wjw.center.CenterUserService;
import com.wjw.pojo.Users;
import com.wjw.pojo.bo.center.CenterUserBO;
import com.wjw.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : wjwjava01@163.com
 * @date : 23:39 2020/5/25
 * @description :
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CentUserController {
    @Autowired
    private CenterUserService centerUserService;
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody CenterUserBO centUserBO) {

        Users user = centerUserService.queryByUserId(userId);
        return JSONResult.ok(user);
    }
}
