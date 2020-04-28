package com.wjw.pojo.bo;

import lombok.Data;

/**
 * @author : wjwjava01@163.com
 * @date : 23:17 2020/4/27
 * @description :
 */
@Data
public class UserBO {
    private String username;
    private String password;
    private String confirmPassword;
}
