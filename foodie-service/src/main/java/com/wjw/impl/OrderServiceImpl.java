package com.wjw.impl;

import com.wjw.AddressService;
import com.wjw.ItemService;
import com.wjw.OrderService;
import com.wjw.enums.YesOrNo;
import com.wjw.mapper.OrdersMapper;
import com.wjw.pojo.Orders;
import com.wjw.pojo.UserAddress;
import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.OrderVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : wjwjava01@163.com
 * @date : 22:01 2020/5/13
 * @description :
 */
public class OrderServiceImpl implements OrderService {
    @Resource
    private Sid sid;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBO 前端提交的订单信息
     * @return
     */
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount = 0;
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
        return null;
    }
}
