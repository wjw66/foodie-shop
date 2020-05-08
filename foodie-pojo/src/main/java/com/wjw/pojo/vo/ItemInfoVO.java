package com.wjw.pojo.vo;

import com.wjw.pojo.Items;
import com.wjw.pojo.ItemsImg;
import com.wjw.pojo.ItemsParam;
import com.wjw.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * 商品详情VO
 */
@Data
public class ItemInfoVO {
    /**
     * 商品信息
     */
    private Items item;
    /**
     * 商品图片列表
     */
    private List<ItemsImg> itemImgList;
    /**
     * 商品规格列表
     */
    private List<ItemsSpec> itemSpecList;
    /**
     * 商品参数信息
     */
    private ItemsParam itemParams;


}
