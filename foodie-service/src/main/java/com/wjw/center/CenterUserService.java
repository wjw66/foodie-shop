package com.wjw.center;

import com.wjw.pojo.Users;
import com.wjw.pojo.bo.center.CenterUserBO;

/**
 * @author : wjwjava01@163.com
 * @date : 23:41 2020/5/25
 * @description :
 */
public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    Users queryByUserId(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    void updateUserInfo(String userId, CenterUserBO centerUserBO);
}
