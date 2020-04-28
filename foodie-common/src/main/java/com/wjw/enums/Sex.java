package com.wjw.enums;

import lombok.Getter;

/**
 * @author : wjwjava01@163.com
 * @date : 23:58 2020/4/27
 * @description : 性别枚举
 */
@Getter
public enum Sex {
    //性别 1:男  0:女  2:保密

    MAN(1,"男"),
    WOMAN(0,"女"),
    SECRET(3,"保密"),
    ;
    private final Integer type;
    private final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
