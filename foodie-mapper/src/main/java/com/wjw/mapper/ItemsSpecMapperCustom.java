package com.wjw.mapper;


import com.wjw.pojo.vo.ItemOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品规格自定义mapper
 * @author codespark
 */
@Repository
public interface ItemsSpecMapperCustom {
    /**
     * 通过规格id查询商品列表
     * @param paramsList
     * @return
     */
    List<ItemOrderVO> querySpecSpecIds(@Param("paramsList") List<String> paramsList);
}
