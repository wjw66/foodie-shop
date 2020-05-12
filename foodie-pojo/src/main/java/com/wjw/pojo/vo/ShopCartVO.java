package com.wjw.pojo.vo;

import lombok.Data;

/**
 * @author asus
 */
@Data
public class ShopCartVO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;

}
