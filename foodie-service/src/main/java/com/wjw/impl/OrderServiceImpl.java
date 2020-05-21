package com.wjw.impl;

import com.wjw.AddressService;
import com.wjw.ItemService;
import com.wjw.OrderService;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.enums.YesOrNo;
import com.wjw.mapper.ItemsSpecMapperCustom;
import com.wjw.mapper.OrderItemsMapperCustom;
import com.wjw.mapper.OrderStatusMapper;
import com.wjw.mapper.OrdersMapper;
import com.wjw.pojo.OrderItems;
import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.Orders;
import com.wjw.pojo.UserAddress;
import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.ItemOrderVO;
import com.wjw.pojo.vo.MerchantOrdersVO;
import com.wjw.pojo.vo.OrderVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : wjwjava01@163.com
 * @date : 22:01 2020/5/13
 * @description :
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private Sid sid;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private AddressService addressService;
    @Resource
    private ItemService itemService;
    @Resource
    private ItemsSpecMapperCustom itemsSpecMapperCustom;
    @Resource
    private OrderItemsMapperCustom orderItemsMapperCustom;
    @Resource
    private OrderStatusMapper orderStatusMapper;


    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBO 前端提交的订单信息
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        int postAmount = 0;
        //生成订单id
        String orderId = sid.nextShort();

        UserAddress address = addressService.queryUserAddress(userId, addressId);

        // 1. 新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        //订单地址
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail());

//        newOrder.setTotalAmount();//总金额
//        newOrder.setRealPayAmount();//支付金额

        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        //商品原价累计
        AtomicInteger totalAmount = new AtomicInteger(0);
        //商品实付价累计
        AtomicInteger realPayAmount = new AtomicInteger(0);

        // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
        int buyCounts = 1;
        List<OrderItems> orderItems = new ArrayList<>();
        //2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        List<ItemOrderVO> itemsSpecs = itemsSpecMapperCustom.querySpecSpecIds(Arrays.asList(itemSpecIdArr));
        itemsSpecs.forEach(itemsSpec -> {
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            totalAmount.updateAndGet(v -> v + itemsSpec.getPriceNormal() * buyCounts);
            realPayAmount.updateAndGet(v -> v + itemsSpec.getPriceDiscount() * buyCounts);

            // 2.2 构建商品信息以及商品图片参数
            String subOrderId = sid.nextShort();
            OrderItems subOrderItems = new OrderItems();
            subOrderItems.setId(subOrderId);
            subOrderItems.setOrderId(orderId);
            subOrderItems.setItemId(itemsSpec.getItemId());
            subOrderItems.setItemName(itemsSpec.getItemName());
            subOrderItems.setPrice(itemsSpec.getPriceDiscount());
            subOrderItems.setItemSpecName(itemsSpec.getName());
            subOrderItems.setItemSpecId(itemsSpec.getId());
            subOrderItems.setItemImg(itemsSpec.getImgUrl());
            subOrderItems.setBuyCounts(buyCounts);
            orderItems.add(subOrderItems);
            //2.3 扣库存
            itemService.decreaseItemSpecStock(itemsSpec.getId(), buyCounts);
        });
        //保存子订单数据到数据库
        orderItemsMapperCustom.insertOrderItemsByList(orderItems);
        newOrder.setTotalAmount(Integer.valueOf(totalAmount.toString()));
        newOrder.setRealPayAmount(Integer.valueOf(realPayAmount.toString()));
        ordersMapper.insertSelective(newOrder);

        //3. 保存订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insertSelective(orderStatus);

        //4. 构建商户订单,用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(Integer.parseInt(realPayAmount.toString()) + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        return new OrderVO(orderId,merchantOrdersVO);
    }

    /**
     * 修改订单状态
     *
     * @param orderId     id
     * @param orderStatus 状态枚举
     */
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }
}
