package com.wjw.pojo.vo;

import lombok.Data;

/**
 * OrderVO
 * @author asus
 */
@Data
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    public OrderVO() {
    }

    public OrderVO(String orderId, MerchantOrdersVO merchantOrdersVO) {
        this.orderId = orderId;
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
