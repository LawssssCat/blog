package com.cy.myblog.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageObject<T> implements Serializable {
	private static final long serialVersionUID = 7844855365725887051L;
	/**数据s*/
	private List<T> datas ;
	/**(分页前)总数据数*/
	private Integer totalDataCount = 0 ; 
	/**总页数*/
	private Integer pageTotal = 0 ; 
	/**当前页码*/
	private Integer pageCurrent = 1 ; 
	/**当前页数据量*/
	private Integer pageSize = 3;
	public PageObject(List<T> datas, Integer totalDataCount, Integer pageCurrent, Integer pageSize) {
		this.datas = datas;
		this.totalDataCount = totalDataCount;
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		
		this.pageTotal = (totalDataCount-1)/pageSize + 1;
	}
	
	
}
