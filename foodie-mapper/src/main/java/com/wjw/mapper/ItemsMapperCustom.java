package com.wjw.mapper;

import com.wjw.pojo.vo.ItemCommentVO;
import com.wjw.pojo.vo.SearchItemsVO;
import com.wjw.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义商品mapper
 * @author asus
 */
public interface ItemsMapperCustom {
    /**
     * 根据商品id查询商品的评价（分页）
     * @param map
     * @return
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 通过搜索框模糊查询商品
     * @param map 搜索的参数
     * @return
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 通过分类id搜索商品
     * @param map
     * @return
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 通过前端传递来的规格id集合查询购物车中的商品
     * @param specIdsList list
     * @return
     */
    List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    int decreaseItemSpecStock(@Param("specId") String specId,
                              @Param("pendingCounts") int pendingCounts);
}