package com.wjw;

import com.wjw.pojo.UserAddress;
import com.wjw.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 23:16 2020/5/12
 * @description :
 */
public interface AddressService {
    /**
     * 通过userId查询地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     * @return
     */
    int addNewUserAddress(AddressBO addressBO);
}
