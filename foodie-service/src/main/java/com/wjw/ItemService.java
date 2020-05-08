package com.wjw;

import com.wjw.pojo.Items;
import com.wjw.pojo.ItemsImg;
import com.wjw.pojo.ItemsParam;
import com.wjw.pojo.ItemsSpec;
import com.wjw.pojo.vo.CommentLevelCountsVO;
import com.wjw.utils.PagedGridResult;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 20:25 2020/5/7
 * @description : 商品信息相关接口
 */
public interface ItemService {
    /**
     * 根据商品ID查询详情
     *
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);
    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     * @return CommentLevelCountsVO
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价（分页）
     * @param itemId
     * @param level 评价等级，可为空
     * @param page 当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level,
                                       Integer page, Integer pageSize);
}