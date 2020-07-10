package com.wjw.center;

import com.wjw.pojo.OrderItems;
import com.wjw.pojo.bo.center.OrderItemsCommentBO;
import com.wjw.pojo.vo.MyCommentVO;
import com.wjw.utils.PageResult;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 23:41 2020/5/25
 * @description : 商品评论
 */
public interface MyCommentsService {
    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评论内容
     * @param orderId
     * @param userId
     * @param commentList
     * @return
     */
    void saveComments(String orderId,String userId,List<OrderItemsCommentBO> commentList);

    /**
     * 查询历史评论内容
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    List<MyCommentVO> queryMyComments(String userId, Integer page, Integer pageSize);
}
