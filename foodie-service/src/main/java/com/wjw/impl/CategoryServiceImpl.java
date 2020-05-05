package com.wjw.impl;

import com.wjw.CategoryService;
import com.wjw.mapper.CategoryMapper;
import com.wjw.pojo.Category;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 0:14 2020/5/6
 * @description :
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有一级分类
     *
     * @return
     */
    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        return categoryMapper.selectByExample(example);
    }

}
