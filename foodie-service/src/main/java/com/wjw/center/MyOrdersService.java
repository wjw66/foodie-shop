package com.wjw.center;

import com.wjw.pojo.Orders;
import com.wjw.pojo.vo.OrderStatusCountsVO;
import com.wjw.utils.PageResult;

/**
 * @author : wjwjava01@163.com
 * @date : 23:41 2020/5/25
 * @description : 查询自己的订单
 */
public interface MyOrdersService {
    /**
     * 分页查询我的订单列表
     * @param userId 用户id
     * @param orderStatus 订单状态
     * @param page 当前页
     * @param pageSize 每页条数
     * @return
     */
    PageResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);
    /**
     * 摸拟商家发货
     * @param orderId
     * @Description: 订单状态 --> 商家发货
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 -> 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 逻辑删除订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId,String orderId);

    /**
     * 获取我的订单各状态相关数量
     * @param userId
     * @return
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 分页查询订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PageResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
