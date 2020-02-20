package com.cy.myblog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties("web.url")
@Configuration
public class WebUrlProperties {
	
	/*-----------------------------------------------------*/
	/*----                                             ----*/
	/*----          请求 uri                             ----*/
	/*----                                             ----*/
	/*-----------------------------------------------------*/
	
	
	/**登录*/
	private String login  = "/login"; //登录
	/**登出*/
	private String logout = "/logout" ; // 登出
	
	/**
	 * 要求 anon
	 */
	private String[] anons = {
		"/dist/**", // 静态资源
		"/*.ico" //图标
	};
	
	/**
	 * 要求 user 
	 */
	private String[] systems = {
			"/user" , // 用户 
			"/us" // 用户状态
			} ;
	
	
	
	/*-----------------------------------------------------*/
	/*----                                             ----*/
	/*----          物理 url                             ----*/
	/*----                                             ----*/
	/*-----------------------------------------------------*/
	private String systemPrefix = "/system" ; 
}
