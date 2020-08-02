package com.wjw.service.impl;

import com.wjw.pojo.Items;
import com.wjw.service.ItemsEsService;
import com.wjw.util.EsPageResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : wjwjava01@163.com
 * @date : 20:28 2020/7/19
 * @description :
 */
@Service
public class ItemsEsServiceImpl implements ItemsEsService {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public EsPageResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
//        String preTag = "<font color = 'red'>";
//        String postTag = "</font>";
        String itemNameFiled = "itemName";

        //SpringData分页
        Pageable pageable = PageRequest.of(page, pageSize);

        //排序规则
        SortBuilder sortBuilder;
        String sortBySellCounts = "c";
        String sortByPrice = "p";

        if (Objects.equals(sortBySellCounts, sort)) {
            sortBuilder = new FieldSortBuilder("sellCounts").order(SortOrder.DESC);
        } else if (Objects.equals(sortByPrice, sort)) {
            sortBuilder = new FieldSortBuilder("price").order(SortOrder.ASC);
        } else {
            //如果text类型要排序，需要使用keyword的排序规则
            sortBuilder = new FieldSortBuilder("itemName.keyword").order(SortOrder.ASC);
        }


        //构建查询对象
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(itemNameFiled, keywords))
                .withHighlightFields(new HighlightBuilder.Field(itemNameFiled)
//                        .preTags(preTag).postTags(postTag)
                )
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();

        //分页查询ES的数据，并返回分页信息
        AggregatedPage<Items> itemsAggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, Items.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Items> itemsList = new ArrayList<>(10);

                SearchHits hits = searchResponse.getHits();
                //循环获取高亮数据
                hits.forEach(hit -> itemsList.add(highlightItems(hit, itemNameFiled)));
                return new AggregatedPageImpl<T>((List<T>) itemsList, pageable, searchResponse.getHits().totalHits);
            }
        });
        return EsPageResult.pageUtils(itemsAggregatedPage, page);
    }

    private Items highlightItems(SearchHit hit, String itemNameFiled) {
        //获取高亮列的内容
        HighlightField highlightField = hit.getHighlightFields().get(itemNameFiled);
        String itemName = highlightField.getFragments()[0].toString();

        String itemId = String.valueOf(hit.getSourceAsMap().get("itemId"));
        String imgUrl = String.valueOf(hit.getSourceAsMap().get("imgUrl"));
        Integer price = (Integer) hit.getSourceAsMap().get("price");
        Integer sellCounts = (Integer) hit.getSourceAsMap().get("sellCounts");

        Items items = new Items();
        items.setItemName(itemName);
        items.setItemId(itemId);
        items.setImgUrl(imgUrl);
        items.setPrice(price);
        items.setSellCounts(sellCounts);
        return items;
    }

}
