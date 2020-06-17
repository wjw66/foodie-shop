package com.wjw.controller;

import com.wjw.OrderService;
import com.wjw.enums.OrderStatusEnum;
import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.bo.ShopCartBO;
import com.wjw.pojo.bo.SubmitOrderBO;
import com.wjw.pojo.vo.MerchantOrdersVO;
import com.wjw.pojo.vo.OrderVO;
import com.wjw.utils.CookieUtils;
import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.wjw.enums.PayMethod.ALIPAY;
import static com.wjw.enums.PayMethod.WEIXIN;

/**
 * @author : wjwjava01@163.com
 * @date : 20:59 2020/5/13
 * @description :
 */
@Slf4j
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {

        Integer payMethod = submitOrderBO.getPayMethod();
        if (!ALIPAY.type.equals(payMethod) && !WEIXIN.type.equals(payMethod)) {
            return JSONResult.errorMsg("暂不支持此支付方式！");
        }

        //整合redis,判断购物车情况
        String shopCartKey = FOODIE_SHOPCART + ":" + submitOrderBO.getUserId();
        String shopCartJson = redisOperator.get(shopCartKey);
        if (StringUtils.isBlank(shopCartJson)) {
            return JSONResult.errorMsg("购物车数据不正确");
        }
        List<ShopCartBO> shopCartBOList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);

        //1.创建订单
        OrderVO orderVO = orderService.createOrder(shopCartBOList, submitOrderBO);
        String orderId = orderVO.getOrderId();

        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        //设置所有金额为1分钱
        merchantOrdersVO.setAmount(1);
        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 -> 用户购买
         * 3003 -> 用户购买
         * 4004
         */
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        shopCartBOList.removeAll(orderVO.getToBeRemovedShopCartList());
        redisOperator.set(shopCartKey,JsonUtils.objectToJson(shopCartBOList));
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartBOList), true);

        //3.向支付中心发送当前订单，用于保存支付中心的订单数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "7535150-1214455149");
        headers.add("password", "98ir-0r3o-r0pv-vj2h");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);

        JSONResult jsonResult = responseEntity.getBody();
        if (Objects.isNull(jsonResult) || HttpStatus.OK.value() != jsonResult.getStatus()) {
            log.error("支付中心创建订单失败,orderId = {}", orderId);
            return JSONResult.errorMsg("支付中心异常，订单创建失败，请联系管理员！");
        }

        return JSONResult.ok(orderId);
    }

    /**
     * 支付回调信息接口
     *
     * @param merchantOrderId 订单主表id
     * @return
     */
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }

}
