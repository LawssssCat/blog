package com.cy.myblog.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.cy.myblog.dao.plugin.MyFirstPlugin;
import com.github.pagehelper.PageInterceptor;

@Configuration
public class MyBatisPlusConfig {

	
	/**
	 * 分页插件 （pageHelper 代替）
	 */
//	@Bean
//	public PaginationInterceptor paginationInterceptor() {
//		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//		return paginationInterceptor ; 
//	}
	public PageInterceptor pageInterceptor() {
		PageInterceptor interceptor = new PageInterceptor();
		return interceptor  ; 
	}
	
	/**
	 * 自定义 插件（测试用）
	 */
	@Bean
	public MyFirstPlugin myFirstPlugin() {
		return new MyFirstPlugin() ; 
	}
}
