package com.wjw.mapper;

import com.wjw.pojo.vo.CategoryVO;
import com.wjw.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义的类别mapper
 *
 * @author asus
 */
public interface CategoryMapperCustom {
    /**
     * 获取子类目
     * @param rootCatId 顶层父类id
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}