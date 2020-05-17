package com.wjw;

import com.wjw.pojo.bo.SubmitOrderBO;

/**
 * @author : wjwjava01@163.com
 * @date : 21:58 2020/5/13
 * @description :
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return
     */
    String createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId id
     * @param orderStatus 状态枚举
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
