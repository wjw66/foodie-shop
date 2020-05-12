package com.wjw.pojo.bo;

import lombok.Data;

/**
 * 用户新增或修改地址的BO
 * @author asus
 */
@Data
public class AddressBO {
    /**
     * 地址主键id
     */
    private String addressId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 接收人姓名
     */
    private String receiver;
    /**
     * 接收人手机号
     */
    private String mobile;
    /**
     * 省
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String district;
    /**
     * 详细地址
     */
    private String detail;

}
