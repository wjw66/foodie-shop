package com.wjw;

import com.wjw.pojo.Category;
import com.wjw.pojo.vo.CategoryVO;
import com.wjw.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 0:03 2020/5/6
 * @description : 类目相关CRUD
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 查询根类目下的子类目
     * @param rootCatId 根类目Id
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页下方分类商品最新的6条数据
     * @param rootCatId 根类目Id
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
