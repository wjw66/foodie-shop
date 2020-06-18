package com.wjw.pojo.vo;

import com.wjw.pojo.bo.ShopCartBO;
import lombok.Data;

import java.util.List;

/**
 * OrderVO
 * @author asus
 */
@Data
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopCartBO> toBeRemovedShopCartList;

    public OrderVO() {
    }

    public OrderVO(String orderId, MerchantOrdersVO merchantOrdersVO) {
        this.orderId = orderId;
        this.merchantOrdersVO = merchantOrdersVO;
    }

    public OrderVO(String orderId, MerchantOrdersVO merchantOrdersVO, List<ShopCartBO> toBeRemovedShopCartList) {
        this.orderId = orderId;
        this.merchantOrdersVO = merchantOrdersVO;
        this.toBeRemovedShopCartList = toBeRemovedShopCartList;
    }
}
