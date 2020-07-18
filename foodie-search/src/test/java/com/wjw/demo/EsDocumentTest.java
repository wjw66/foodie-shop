package com.wjw.demo;

import com.wjw.EsApplicationTest;
import com.wjw.pojo.Stu;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/7/14 17:28
 * @Description: 文档数据的操作
 **/
@Slf4j
public class EsDocumentTest extends EsApplicationTest {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 更新文档数据
     * es语句翻译
     * update Stu set desc = '吃饭了没？起来干活！' where docId = 1000;
     */
    @Test
    public void updateDoc() {
        Map<String, Object> map = new HashMap<>();
        map.put("desc", "吃饭了没？起来干活！");
        IndexRequest request = new IndexRequest().source(map);
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                //索引的实体类（数据库表名）
                .withClass(Stu.class)
                //_id属性
                .withId("1000")
                //更新的数据请求
                .withIndexRequest(request)
                .build();
        elasticsearchTemplate.update(updateQuery);
    }

    /**
     * 查询文档数据（根据id）
     * select * from stu where id = 1000;
     */
    @Test
    public void queryDoc() {

        GetQuery query = new GetQuery();
        query.setId("1000");
        Stu stu = elasticsearchTemplate.queryForObject(query, Stu.class);
        log.info("stu = {}", stu);
    }

    /**
     * 删除文档数据
     * delete from stu where id = 1000;
     */
    @Test
    public void deleteDoc() {

        elasticsearchTemplate.delete(Stu.class, "1000");
    }

    /**
     * 全文检索，分页查询
     */
    @Test
    public void searchForPageDoc() {
        Pageable pageable = PageRequest.of(0, 5);
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                //name:查询的字段名 text:用户输入的字
                .withQuery(QueryBuilders.matchQuery("desc", "蚊子"))
                //分页参数
                .withPageable(pageable)
                .build();
        //es查询后返回的结果，携带分页参数
        AggregatedPage<Stu> esStuInfo = elasticsearchTemplate.queryForPage(queryBuilder, Stu.class);
        log.info("总页数 = {}", esStuInfo.getTotalPages());
        List<Stu> stuList = esStuInfo.getContent();
        stuList.forEach(stu -> System.out.println("stu = " + stu));
    }

    /**
     * 全文检索及高亮显示
     */
    @Test
    public void searchHighlightDoc() {
        String preTag = "<font color = 'red'>";
        String postTag = "</font>";

        Pageable pageable = PageRequest.of(0, 5);
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                //name:查询的字段名 text:用户输入的字
                .withQuery(QueryBuilders.matchQuery("desc", "蚊子"))
                //根据查询的字段高亮显示，可传多个值
                .withHighlightFields(new HighlightBuilder.Field("desc").preTags(preTag).postTags(postTag))
                //分页参数
                .withPageable(pageable)
                .build();
        //es查询后返回的结果，携带分页参数
        AggregatedPage<Stu> esStuInfo = elasticsearchTemplate.queryForPage(queryBuilder, Stu.class);
        log.info("总页数 = {}", esStuInfo.getTotalPages());
        List<Stu> stuList = esStuInfo.getContent();
        stuList.forEach(stu -> System.out.println("stu = " + stu));
    }

    /**
     * 全文检索排序
     */
    @Test
    public void searchSortDoc() {

        //通过字段从小到大排序
        FieldSortBuilder sortId = new FieldSortBuilder("stuId").order(SortOrder.ASC);
        FieldSortBuilder sortAge = new FieldSortBuilder("stuId").order(SortOrder.ASC);

        Pageable pageable = PageRequest.of(0, 10);
        SearchQuery queryBuilder = new NativeSearchQueryBuilder()
                //name:查询的字段名 text:用户输入的字
                .withQuery(QueryBuilders.matchQuery("desc", "蚊子"))
                //排序（支持多个排序，以第一个为主）
                .withSort(sortId)
                .withSort(sortAge)
                //分页参数
                .withPageable(pageable)
                .build();
        //es查询后返回的结果，携带分页参数
        AggregatedPage<Stu> esStuInfo = elasticsearchTemplate.queryForPage(queryBuilder, Stu.class);
        log.info("总页数 = {}", esStuInfo.getTotalPages());
        List<Stu> stuList = esStuInfo.getContent();
        stuList.forEach(stu -> System.out.println("stu = " + stu));
    }
}
