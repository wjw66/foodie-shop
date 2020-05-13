package com.wjw;

import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.OrderVO;

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
    OrderVO createOrder(SubmitOrderBO submitOrderBO);
}
