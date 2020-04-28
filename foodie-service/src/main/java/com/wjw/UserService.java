package com.wjw;

import com.wjw.pojo.Users;
import com.wjw.pojo.bo.UserBO;

/**
 * @author : wjwjava01@163.com
 * @date : 20:35 2020/4/26
 * @description : 用户登录接口
 */
public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);
}
