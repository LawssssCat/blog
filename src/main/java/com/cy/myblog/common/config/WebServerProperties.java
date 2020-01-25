package com.cy.myblog.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties("web-service")
@Configuration
public class WebServerProperties {

	final static private String DEAFULT_LOGINURL = "/doLoginUI";  //登录url
	final static private String DEAFULT_LOGOUTURL = "/";//登出后,转跳到的url
	
	final static private String DEAFULT_SYSTEMPREFIX = "/system";  //管理系统的(项目名后)前缀
	final static private String DEAFULT_SYSTEMINDEX = "/blogs";  //管理系统的index页面名 ==> 加上前缀: (项目名)/system/blogs
	

	/**登录页面*/
	private String loginUrl  = DEAFULT_LOGINURL;
	/**登出转跳页面*/
	private String logoutUrl = DEAFULT_LOGOUTURL ;
	
	/**管理路径(路径后目录,均要登录,才可访问)*/
	private String systemPrefix = DEAFULT_SYSTEMPREFIX ; 
	private String systemIndex = DEAFULT_SYSTEMINDEX ; 
}
