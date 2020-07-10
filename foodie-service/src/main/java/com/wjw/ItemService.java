package com.wjw;

import com.wjw.pojo.Items;
import com.wjw.pojo.ItemsImg;
import com.wjw.pojo.ItemsParam;
import com.wjw.pojo.ItemsSpec;
import com.wjw.pojo.vo.CommentLevelCountsVO;
import com.wjw.pojo.vo.ShopCartVO;
import com.wjw.utils.PageResult;

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
     *
     * @param itemId
     * @return CommentLevelCountsVO
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价（分页）
     *
     * @param itemId
     * @param level    评价等级，可为空
     * @param page     当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    PageResult queryPagedComments(String itemId, Integer level,
                                  Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     *
     * @param keywords 商品模糊查询关键词
     * @param sort     排名方式
     * @param page
     * @param pageSize
     * @return
     */
    PageResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     *
     * @param catId    分类id
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中商品数据(用于刷新渲染购物车中的商品数据)
     * @param specIds
     * @return
     */
    List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /**
     * 乐观锁扣库存
     * @param specId 商品规格id
     * @param buyCounts 购买件数
     * @return
     */
    int decreaseItemSpecStock(String specId,int buyCounts);
}
