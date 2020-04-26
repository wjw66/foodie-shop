package com.wjw;

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
}
