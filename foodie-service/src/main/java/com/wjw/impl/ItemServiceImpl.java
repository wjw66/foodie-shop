package com.wjw.impl;

import com.github.pagehelper.PageHelper;
import com.wjw.ItemService;
import com.wjw.enums.CommentLevel;
import com.wjw.mapper.*;
import com.wjw.pojo.*;
import com.wjw.pojo.vo.CommentLevelCountsVO;
import com.wjw.pojo.vo.ItemCommentVO;
import com.wjw.pojo.vo.SearchItemsVO;
import com.wjw.pojo.vo.ShopCartVO;
import com.wjw.utils.DesensitizationUtil;
import com.wjw.utils.PageResult;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : wjwjava01@163.com
 * @date : 20:43 2020/5/7
 * @description :
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemsMapper itemsMapper;
    @Resource
    private ItemsImgMapper itemsImgMapper;
    @Resource
    private ItemsSpecMapper itemsSpecMapper;
    @Resource
    private ItemsParamMapper itemsParamMapper;
    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;
    @Resource
    private ItemsMapperCustom itemsMapperCustom;


    /**
     * 根据商品ID查询详情
     *
     * @param itemId
     * @return
     */
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId
     * @return
     */
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsParamMapper.selectOneByExample(example);
    }

    /**
     * 根据商品id查询商品的评价等级数量
     *
     * @param itemId
     * @return CommentLevelCountsVO
     */
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

    /**
     * 根据商品id查询商品的评价（分页）
     *
     * @param itemId
     * @param level    评价等级，可为空
     * @param page     当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    @Override
    public PageResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>(16);
        map.put("itemId", itemId);
        map.put("level", level);
        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);

        //用户名脱敏
        list.forEach(itemCommentVO -> itemCommentVO.setNickname
                (DesensitizationUtil.commonDisplay(itemCommentVO.getNickname()))
        );

        return PageResult.pageUtils(list,page);
    }

    /**
     * 搜索商品列表
     *
     * @param keywords 商品模糊查询关键词
     * @param sort     排名方式
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        return PageResult.pageUtils(list,page);
    }

    /**
     * 根据分类id搜索商品列表
     *
     * @param catId    分类id
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return PageResult.pageUtils(list,page);
    }

    /**
     * 根据规格ids查询最新的购物车中商品数据(用于刷新渲染购物车中的商品数据)
     *
     * @param specIds
     * @return
     */
    @Override
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        String[] spec = specIds.split(",");
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,spec);

        return itemsMapperCustom.queryItemsBySpecIds(list);
    }

    private Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);

        if (level != null) {
            itemsComments.setCommentLevel(level);
        }

        return itemsCommentsMapper.selectCount(itemsComments);
    }


    /**
     * 乐观锁扣库存
     *
     * @param specId  商品规格id
     * @param buyCounts 购买件数
     * @return
     */
    @Override
    public int decreaseItemSpecStock(String specId, int buyCounts) {
        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }
        return result;
    }
}
