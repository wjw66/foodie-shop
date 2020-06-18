package com.wjw.mapper;

import com.wjw.my.mapper.MyMapper;
import com.wjw.pojo.ItemsComments;
import com.wjw.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author asus
 */
public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {
    /**
     * 保存评论内容
     * @param map
     */
    void saveComments(Map<String, Object> map);

    /**
     * 查询订单历史记录
     * @param map
     * @return
     */
    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}