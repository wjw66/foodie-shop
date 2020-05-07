package com.wjw.pojo.vo;

import lombok.Data;

/**
 * 6个最新商品的简单数据类型
 */
@Data
public class SimpleItemVO {
    /**
     * 商品id
     */
    private String itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品图片url
     */
    private String itemUrl;

}
