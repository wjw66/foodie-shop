package com.wjw.impl;

import com.wjw.AddressService;
import com.wjw.enums.YesOrNo;
import com.wjw.mapper.UserAddressMapper;
import com.wjw.pojo.UserAddress;
import com.wjw.pojo.bo.AddressBO;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : wjwjava01@163.com
 * @date : 23:16 2020/5/12
 * @description :
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private Sid sid;

    /**
     * 通过userId查询地址列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        return userAddressMapper.select(address);
    }

    /**
     * 用户新增地址
     *
     * @param addressBO
     * @return
     */
    @Override
    public int addNewUserAddress(AddressBO addressBO) {

        //1.判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList.isEmpty()) {
            isDefault = 1;
        }

        //2.保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        return userAddressMapper.insert(userAddress);
    }

    @Override
    public int updateUserAddress(AddressBO addressBO) {

        //保存更新地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());

        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.delete(address);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        //1.查出默认地址，设置为普通地址
        userAddress.setIsDefault(YesOrNo.YES.type);
        UserAddress defaultAddress = userAddressMapper.selectOne(userAddress);
        //如果为空或当前地址已经为默认地址
        if (Objects.nonNull(defaultAddress) && addressId.equals(defaultAddress.getId())) {
            return;
        }
        userAddress.setIsDefault(YesOrNo.NO.type);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

        //2.将选中的地址设置为默认地址
        userAddress.setId(addressId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     *
     * @param userId
     * @param addressId
     * @return
     */
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        return userAddressMapper.selectOne(userAddress);
    }
}
