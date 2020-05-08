package com.wjw.enums;

import lombok.Getter;

/**
 * @author asus
 * @Desc: 商品评价等级 枚举
 */
@Getter
public enum CommentLevel {
    //好中差评价枚举
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评"),
    ;

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
