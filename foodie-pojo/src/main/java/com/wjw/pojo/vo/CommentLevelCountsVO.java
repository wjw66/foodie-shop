package com.wjw.pojo.vo;

import lombok.Data;

/**
 * 用于展示商品评价数量的vo
 * @author asus
 */
@Data
public class CommentLevelCountsVO {
    /**
     * 所有的
     */
    public Integer totalCounts;
    /**
     * 好评
     */
    public Integer goodCounts;
    /**
     * 中评
     */
    public Integer normalCounts;
    /**
     * 差评
     */
    public Integer badCounts;

}
