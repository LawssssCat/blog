package com.cy.myblog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties("web-service")
@Configuration
public class WebServerProperties {


	/**登录页面*/
	private String loginUrl ;
	/**登出转跳页面*/
	private String logoutUrl ; 
	
}
