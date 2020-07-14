package com.wjw.demo;

import com.wjw.EsApplicationTest;
import com.wjw.pojo.Stu;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import javax.annotation.Resource;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/14 13:58
 **/
@Slf4j
public class EsTest extends EsApplicationTest {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 创建索引Stu
     */
    @Test
    public void createIndexStu(){
        Stu stu = new Stu();
        stu.setStuId(1000L);
        stu.setName("mybatis");
        stu.setAge(12);

        //构建创建索引对象
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        //创建索引,如果索引已存在，更新数据（不重新创建索引）
        elasticsearchTemplate.index(indexQuery);
    }
    @Test
    public void delIndexStu(){
        elasticsearchTemplate.deleteIndex(Stu.class);
    }
}
