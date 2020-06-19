package com.wjw.pojo.vo;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author : wjwjava01@163.com
 * @date : 12:15 2020/5/2
 * @description : 登录的VO对象
 */
@Data
public class UsersVO {
    /**
     * 主键id 用户id
     */
    @Id
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 用户会话Token
     */
    private String userUniqueToken;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private Date birthday;

}
