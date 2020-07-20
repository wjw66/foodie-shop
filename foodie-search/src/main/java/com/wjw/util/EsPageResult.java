package com.wjw.util;

import lombok.Data;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 21:39 2020/7/19
 * @description :
 */
@Data
public class EsPageResult {
    /**
     * 当前页数
     */
    private int page;
    /**
     * 总页数
     */
    private int total;
    /**
     * 总记录数
     */
    private long records;
    /**
     * 每行显示的内容
     */
    private List<?> rows;

    private EsPageResult() {
    }

    public static EsPageResult pageUtils(AggregatedPage aggregatedPage, Integer page) {

        EsPageResult pageResult = new EsPageResult();
        pageResult.setPage(page + 1);
        pageResult.setTotal(aggregatedPage.getTotalPages());
        pageResult.setRecords(aggregatedPage.getTotalElements());
        pageResult.setRows(aggregatedPage.getContent());

        return pageResult;
    }
}
