package com.wjw.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 
 * @author asus
 * @Title: PagedGridResult.java
 * @Package com.wjw.utils
 * @Description: 用来返回分页Grid的数据格式
 */
@Data
public class PagedGridResult {
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

	private PagedGridResult(){};

	public static PagedGridResult pageUtils(List<?> list,Integer page){
		PageInfo<?> pageInfo = new PageInfo<>(list);
		PagedGridResult gridResult = new PagedGridResult();
		gridResult.setPage(page);
		gridResult.setRecords(pageInfo.getTotal());
		gridResult.setTotal(pageInfo.getPages());
		gridResult.setRows(list);
		return gridResult;
	}

}
