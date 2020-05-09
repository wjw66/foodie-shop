package com.wjw.pojo.vo;

import lombok.Data;

/**
 * 用于展示商品搜索列表结果的VO
 * @author asus
 */
@Data
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;

}
