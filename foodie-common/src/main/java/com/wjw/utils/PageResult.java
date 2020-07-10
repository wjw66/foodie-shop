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
public class PageResult {
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

	private PageResult(){};

	public static PageResult pageUtils(List<?> list, Integer page){
		PageInfo<?> pageInfo = new PageInfo<>(list);
		PageResult gridResult = new PageResult();
		gridResult.setPage(page);
		gridResult.setRecords(pageInfo.getTotal());
		gridResult.setTotal(pageInfo.getPages());
		gridResult.setRows(list);
		return gridResult;
	}

	public static PageResult pageUtils(List<?> list){
		PageInfo<?> pageInfo = new PageInfo<>(list);
		PageResult gridResult = new PageResult();
		gridResult.setPage(pageInfo.getPageNum());
		gridResult.setRecords(pageInfo.getTotal());
		gridResult.setTotal(pageInfo.getPages());
		gridResult.setRows(list);
		return gridResult;
	}

}
