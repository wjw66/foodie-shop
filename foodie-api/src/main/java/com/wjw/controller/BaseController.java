package com.wjw.controller;

import com.wjw.center.MyOrdersService;
import com.wjw.pojo.Orders;
import com.wjw.pojo.Users;
import com.wjw.pojo.vo.UsersVO;
import com.wjw.utils.JSONResult;
import com.wjw.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

/**
 * 基础的Controller类
 *
 * @author asus
 */
@Controller
public class BaseController {
    @Autowired
    public MyOrdersService myOrdersService;
    @Autowired
    private RedisOperator redisOperator;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 支付中心的调用地址
     */
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                       |-> 回调通知的url
    String payReturnUrl = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";

    /**
     * 用户上传头像的位置
     */
    public static final String IMAGE_USER_FACE_LOCATION =
            File.separator + "workspaces"
                    + File.separator + "images"
                    + File.separator + "foodie"
                    + File.separator + "faces";
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie/faces";


    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public JSONResult checkUserOrder(String userId, String orderId) {

        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return JSONResult.errorMsg("订单不存在！");
        }
        return JSONResult.ok(order);
    }
    /**
     * 将用户回话存入redis并返回VO存到cookie中
     *
     * @param userResult
     * @return
     */
    public UsersVO getUsersVO(Users userResult) {
        String redisUserToken = REDIS_USER_TOKEN + ":" + userResult.getId();
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(redisUserToken, uniqueToken);
        //token存入到cookie中
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
