package com.wjw.impl;

import java.math.BigDecimal;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/25 16:06
 **/
public class DecimalTest {
    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("-3");
        BigDecimal onhandle = new BigDecimal("100");
        System.out.println(onhandle.add(decimal));
    }
}
