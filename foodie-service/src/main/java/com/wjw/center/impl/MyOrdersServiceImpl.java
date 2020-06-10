package com.wjw.center.impl;

import com.github.pagehelper.PageHelper;
import com.wjw.center.MyOrdersService;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.YesOrNo;
import com.wjw.mapper.OrderStatusMapper;
import com.wjw.mapper.OrdersMapper;
import com.wjw.mapper.OrdersMapperCustom;
import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.Orders;
import com.wjw.pojo.vo.MyOrdersVO;
import com.wjw.pojo.vo.OrderStatusCountsVO;
import com.wjw.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : wjwjava01@163.com
 * @date : 10:34 2020/5/31
 * @description :
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {
    @Resource
    private OrdersMapperCustom ordersMapperCustom;
    @Resource
    private OrderStatusMapper orderStatusMapper;
    @Resource
    private OrdersMapper ordersMapper;

    /**
     * 分页查询我的订单列表
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        当前页
     * @param pageSize    每页条数
     * @return
     */
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);
        if (Objects.nonNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> myOrdersVOS = ordersMapperCustom.queryMyOrders(map);

        return PagedGridResult.pageUtils(myOrdersVOS, page);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)

    @Override
    public Orders queryMyOrder(String userId, String orderId) {

        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(orders);
    }

    /**
     * 更新订单状态 -> 确认收货
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int result = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return result == 1;
    }

    /**
     * 逻辑删除订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(updateOrder, example);

        return result == 1;
    }

    /**
     * 获取我的订单各状态相关数量
     *
     * @param userId
     * @return
     */
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVO orderStatusCountsVO = new OrderStatusCountsVO(
                waitPayCounts, waitDeliverCounts
                , waitReceiveCounts, waitCommentCounts);
        return orderStatusCountsVO;
    }

    /**
     * 分页查询订单动向
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("userId",userId);

        PageHelper.startPage(page,pageSize);
        List<OrderStatus> myOrderTrend = ordersMapperCustom.getMyOrderTrend(map);

        return PagedGridResult.pageUtils(myOrderTrend,page);
    }
}
