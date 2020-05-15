package com.wjw.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单入库dto
 * @author codespark
 */
@Data
public class ItemOrderVO {
    /**
     * 商品规格id
     */
    private String id;

    /**
     * 商品外键id
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品主图
     */
    private String imgUrl;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 折扣力度
     */
    private BigDecimal discounts;

    /**
     * 优惠价
     */
    private Integer priceDiscount;

    /**
     * 原价
     */
    private Integer priceNormal;

    public ItemOrderVO() {
    }
}
