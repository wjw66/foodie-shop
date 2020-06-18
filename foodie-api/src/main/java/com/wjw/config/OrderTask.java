package com.wjw.config;

import com.wjw.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : wjwjava01@163.com
 * @date : 22:04 2020/5/25
 * @description :
 */
@Component
public class OrderTask {
    @Autowired
    private OrderService orderService;
    /**
     * 关闭支付超时订单,3秒执行一次
     */
    @Scheduled(cron = "0/59 * * * * ? ")
    public void autoCloseOrder(){
        orderService.closeOrder();
    }
}
