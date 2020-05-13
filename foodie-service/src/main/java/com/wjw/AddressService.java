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

    /**
     * 用户修改地址
     * @param addressBO
     * @return
     */
    int updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 更新地址为默认地址
     * @param userId
     * @param addressId
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
