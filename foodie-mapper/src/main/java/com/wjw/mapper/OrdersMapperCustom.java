package com.wjw.mapper;

import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public interface OrdersMapperCustom {
    /**
     * 查询个人的所有订单信息
     * @param map
     * @return
     */
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);

    /**
     * 获取我的订单个状态相关数量
     * @param map
     * @return
     */
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询用户订单动向
     * @param map
     * @return
     */
    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);


}
