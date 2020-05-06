package com.wjw.impl;

import com.wjw.CategoryService;
import com.wjw.mapper.CategoryMapper;
import com.wjw.mapper.CategoryMapperCustom;
import com.wjw.pojo.Category;
import com.wjw.pojo.vo.CategoryVO;
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
    @Resource
    private CategoryMapperCustom categoryMapperCustom;

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

    /**
     * 查询根类目下的子类目
     *
     * @param rootCatId 根类目Id
     * @return
     */
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

}
