package com.wjw.pojo.bo;

import lombok.Data;

/**
 * 用于创建订单的BO对象
 * @author asus
 */
@Data
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    /**
     * 支付方式
     */
    private Integer payMethod;
    /**
     * 买家备注
     */
    private String leftMsg;

}
