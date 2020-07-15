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
 * @Description: 不建议使用ElasticsearchTemplate对索引进行管理（创建索引，更新映射，删除索引）索引即数据库表
 *
 * 1.属性（FieldType）类型不灵活
 * 2.主分片与副本分片数无法设置
 * 3.通过es创建索引（数据库表）不合理
 **/
@Slf4j
public class EsIndexTest extends EsApplicationTest {
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
