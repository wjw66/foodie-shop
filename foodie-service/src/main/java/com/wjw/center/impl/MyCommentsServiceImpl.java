package com.wjw.center.impl;

import com.github.pagehelper.PageHelper;
import com.wjw.center.MyCommentsService;
import com.wjw.enums.YesOrNo;
import com.wjw.mapper.ItemsCommentsMapperCustom;
import com.wjw.mapper.OrderItemsMapper;
import com.wjw.mapper.OrderStatusMapper;
import com.wjw.mapper.OrdersMapper;
import com.wjw.pojo.OrderItems;
import com.wjw.pojo.OrderStatus;
import com.wjw.pojo.Orders;
import com.wjw.pojo.bo.center.OrderItemsCommentBO;
import com.wjw.pojo.vo.MyCommentVO;
import com.wjw.utils.PageResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wjwjava01@163.com
 * @date : 22:49 2020/5/31
 * @description :
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;

    /**
     * 根据订单id查询关联的商品
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    /**
     * 保存评论内容
     *
     * @param orderId
     * @param userId
     * @param commentList
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        //1.保存评价内容到items_comments
        //设置主键id
        commentList.forEach(comment -> comment.setCommentId(sid.nextShort()));
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        //2.修改订单表状态为已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);
        //3.修改订单状态表的留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 查询历史评论内容
     *
     * @param userId 查询条件
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @return
     */
    @Override
    public List<MyCommentVO> queryMyComments(String userId, Integer pageNum, Integer pageSize) {
        //查询参数
        Map<String,Object> map = new HashMap<>(16);
        map.put("userId",userId);

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        //返回list
        return list;
    }
}
