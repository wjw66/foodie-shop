package com.wjw.center.impl;

import com.wjw.center.CenterUserService;
import com.wjw.mapper.UsersMapper;
import com.wjw.pojo.Users;
import com.wjw.pojo.bo.center.CenterUserBO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : wjwjava01@163.com
 * @date : 23:41 2020/5/25
 * @description :
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Resource
    private UsersMapper usersMapper;
    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Users queryByUserId(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param centerUserBO
     */
    @Override
    public void updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO,users);
        users.setId(userId);
        users.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(users);
    }
}
