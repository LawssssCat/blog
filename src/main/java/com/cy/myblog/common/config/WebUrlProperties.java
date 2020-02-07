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
	private String login  = "login"; //登录
	/**登出*/
	private String logout = "logout" ; // 登出
	
	/**
	 * 下列 url:/xxx
	 * 转跳到 
	 * /system/xxx
	 */
	private String[] systems = {"user" , "userState"} ;
	
	
	
	/*-----------------------------------------------------*/
	/*----                                             ----*/
	/*----          物理 url                             ----*/
	/*----                                             ----*/
	/*-----------------------------------------------------*/
	private String systemPrefix = "/system" ; 
}
