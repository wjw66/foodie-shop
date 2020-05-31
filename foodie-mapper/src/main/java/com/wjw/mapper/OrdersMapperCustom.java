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

    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);

}
