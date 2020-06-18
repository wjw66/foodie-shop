package com.wjw.controller;

import com.wjw.exception.AppException;
import com.wjw.pojo.bo.ShopCartBO;
import com.wjw.utils.JSONResult;
import com.wjw.utils.JsonUtils;
import com.wjw.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : wjwjava01@163.com
 * @date : 23:20 2020/5/10
 * @description : 购物车Api接口
 */
@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RequestMapping("shopcart")
@RestController
public class ShopCatController extends BaseController {
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId, @RequestBody ShopCartBO shopcartBO,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        String shopCartKey = FOODIE_SHOPCART + ":" + userId;
        //前端用户在登录的情况后，添加商品到购物车，会同时在后端同步购物车到redis缓存
        //需要判断购物车中已存在这个商品,则累加购买数量
        String shopCartJson = redisOperator.get(shopCartKey);
        List<ShopCartBO> shopCartBOList;
        if (StringUtils.isNotBlank(shopCartJson)) {
            shopCartBOList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);

            if (Objects.isNull(shopCartBOList)) {
                throw new AppException("购物车JSON转换异常", userId);
            }
            // 判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShopCartBO shopCart : shopCartBOList) {
                String specId = shopCart.getSpecId();
                //如果有这个商品,累加数量
                if (shopcartBO.getSpecId().equals(specId)) {
                    shopCart.setBuyCounts(shopCart.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            //如果购物车在没有这个商品
            if (!isHaving) {
                shopCartBOList.add(shopcartBO);
            }
        } else {
            //redis中没有购物车
            shopCartBOList = new ArrayList<>();
            //添加购物车
            shopCartBOList.add(shopcartBO);
        }
        //覆盖redis的购物车内容
        redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartBOList));

        return JSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId, @RequestParam String itemSpecId,
                          HttpServletRequest request, HttpServletResponse response) {


        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }

        //用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        String shopCartKey = FOODIE_SHOPCART + ":" + userId;
        String shopCartJson = redisOperator.get(shopCartKey);
        if (StringUtils.isNotBlank(shopCartJson)) {
            List<ShopCartBO> shopCartBOList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);
            //判断购物车中是否有商品,如果有则删除
            assert shopCartBOList != null;
            shopCartBOList = shopCartBOList.stream()
                    .filter(shopCartBO -> !shopCartBO.getSpecId().equals(itemSpecId))
                    .collect(Collectors.toList());
            //覆盖原来的购物车
            redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartBOList));
        }

        return JSONResult.ok();
    }
}
