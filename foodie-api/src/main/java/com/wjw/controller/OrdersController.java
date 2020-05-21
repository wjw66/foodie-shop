package com.wjw.controller;

import com.wjw.OrderService;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.MerchantOrdersVO;
import com.wjw.pojo.vo.OrderVO;
import com.wjw.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wjw.enums.PayMethod.ALIPAY;
import static com.wjw.enums.PayMethod.WEIXIN;

/**
 * @author : wjwjava01@163.com
 * @date : 20:59 2020/5/13
 * @description :
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {

        Integer payMethod = submitOrderBO.getPayMethod();
        if (!ALIPAY.type.equals(payMethod) && !WEIXIN.type.equals(payMethod)) {
            return JSONResult.errorMsg("暂不支持此支付方式！");
        }
        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 -> 用户购买
         * 3003 -> 用户购买
         * 4004
         */
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据

        return JSONResult.ok(orderId);
    }

    /**
     * 支付回调信息接口
     * @param merchantOrderId 订单主表id
     * @return
     */
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

}