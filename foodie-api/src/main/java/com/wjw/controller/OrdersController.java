package com.wjw.controller;

import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class OrdersController {

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {

        Integer payMethod = submitOrderBO.getPayMethod();
        if (!ALIPAY.type.equals(payMethod) && !WEIXIN.type.equals(payMethod)){
            return JSONResult.errorMsg("暂不支持此支付方式！");
        }
        //1.创建订单

        //2.创建后，移除购物车中已结算的商品

        //3.向支付中心发送当前订单，用于保存支付中心的订单数据

        return JSONResult.ok();
    }

}
