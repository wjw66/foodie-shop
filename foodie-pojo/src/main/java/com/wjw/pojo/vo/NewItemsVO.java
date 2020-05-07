package com.wjw.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 最新商品VO
 * @author asus
 */
@Data
public class NewItemsVO {
    /**
     * 商品根分类id
     */
    private Integer rootCatId;
    /**
     * 商品分类名称
     */
    private String rootCatName;
    /**
     * 口号
     */
    private String slogan;
    /**
     * 商品所在分类图片
     */
    private String catImage;
    /**
     * 背景色
     */
    private String bgColor;
    /**
     * 六个商品的信息
     */
    private List<SimpleItemVO> simpleItemList;
}
