package com.wjw;

import com.wjw.pojo.Category;

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
}
