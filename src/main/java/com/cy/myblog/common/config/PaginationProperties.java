package com.cy.myblog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "page.config")
public class PaginationProperties {

	/**
	 * 默认 每页 数据量
	 */
	final static private Integer DEFAULT_PAGESIZE = 10 ;  
	
	/**
	 * 每页多少条数据
	 */
	private Integer pageSize = DEFAULT_PAGESIZE ; 
	
	/**
	 * 工具:计算分页的startIndex
	 * @param pageCurrent 当前页码 1~n
	 * @return startIndex
	 */
	public Integer getStartIndex(Integer pageCurrent) {
		return (pageCurrent-1)*pageSize ; 
	}
	
}
