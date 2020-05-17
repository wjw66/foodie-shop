package com.wjw.mapper;

import com.wjw.pojo.OrderItems;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderItemsMapperCustom
 * @author asus
 */
@Repository
public interface OrderItemsMapperCustom{
    /**
     * 批量插入下单数据
     * @param orderItems
     * @return
     */
    int insertOrderItemsByList(List<OrderItems> orderItems);
}