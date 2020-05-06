package com.wjw.pojo.vo;

import lombok.Data;

/**
 * 三级分类vo
 * @author asus
 */
@Data
public class SubCategoryVO {

    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
