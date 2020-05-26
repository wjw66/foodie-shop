package com.wjw.pojo.bo.center;

import lombok.Data;

import java.util.Date;

/**
 * @author : wjwjava01@163.com
 * @date : 23:17 2020/4/27
 * @description :
 */
@Data
public class CenterUserBO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private Date birthday;

}
