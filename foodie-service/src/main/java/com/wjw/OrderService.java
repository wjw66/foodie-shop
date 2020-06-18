package com.wjw;

import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.bo.ShopCartBO;
import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 21:58 2020/5/13
 * @description :
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @param shopCartBOList
     * @return
     */
    OrderVO createOrder(List<ShopCartBO> shopCartBOList, SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId id
     * @param orderStatus 状态枚举
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付的订单
     */
    void closeOrder();
}
