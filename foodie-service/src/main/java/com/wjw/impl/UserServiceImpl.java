package com.wjw.impl;

import com.wjw.UserService;
import com.wjw.mapper.UsersMapper;
import com.wjw.pojo.Users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author : wjwjava01@163.com
 * @date : 20:37 2020/4/26
 * @description : 用户Service实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;
    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 用户已存在返回true,否则为false
     */
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users(username);
        int count = usersMapper.selectCount(user);
        return count > 0;
    }
}
