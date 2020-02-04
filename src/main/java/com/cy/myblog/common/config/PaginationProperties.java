package com.cy.myblog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "page.config")
public class PaginationProperties {

	final static private Integer DEFAULT_PAGESIZE = 10 ;  
	
	private Integer pageSize = DEFAULT_PAGESIZE ; 
	
	public Integer getStartIndex(Integer pageCurrent) {
		return (pageCurrent-1)*pageSize ; 
	}
	
}
