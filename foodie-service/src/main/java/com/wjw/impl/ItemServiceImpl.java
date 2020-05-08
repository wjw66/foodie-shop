package com.wjw.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjw.ItemService;
import com.wjw.enums.CommentLevel;
import com.wjw.mapper.*;
import com.wjw.pojo.*;
import com.wjw.pojo.vo.CommentLevelCountsVO;
import com.wjw.pojo.vo.ItemCommentVO;
import com.wjw.utils.DesensitizationUtil;
import com.wjw.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

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

        return setterPagedGrid(list, page);
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
     * 封装分页对象
     *
     * @param list list集合
     * @param page 当前第几页
     * @return
     */
    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}