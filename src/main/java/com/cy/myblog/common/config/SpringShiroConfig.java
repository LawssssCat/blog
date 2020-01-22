package com.cy.myblog.common.config;


import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringShiroConfig {
	
	@Autowired
	private WebServerProperties webServerProperties ; 
	

	@Autowired
	@Bean("shiroSecurityManager")
	public SecurityManager shiroSecurityManager(
			@Qualifier("shiroUserRealm") Realm realm) {
		
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		
		sManager.setRealm(realm);
		return sManager ;  
	}
	
	
	
	@Autowired
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(
			@Qualifier("shiroSecurityManager") SecurityManager securityManager) {
		// 
		
		ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean(); 
		shiroFilterFactory.setSecurityManager(securityManager);
		
		//定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
		
		//静态资源允许匿名访问:"anon"
		//map.put("/dist/**", "anon");
		//map.put("/common/**" , "anon") ;
		
		
		//map.put("/blog/**", "anon"); //游客浏览博客 
		//map.put("/doIndexUI", "anon") ; 
		
		//map.put("/user/doLogin" , "anon") ; //登录
		//map.put("/doLogout", "logout") ; 
		
		//其它都要认证("authc")后访问  
		//其它都要认证("user")后访问  
		//map.put("/sys/**", "user") ; //controller 转跳 x
		//map.put("/doSysUI", "user") ; //controller 转跳 x
		
		
		
		shiroFilterFactory.getFilterChainDefinitionMap().put("/doLogout", "logout") ; 
		shiroFilterFactory.getFilterChainDefinitionMap().put("/sys/**", "user") ; 
		shiroFilterFactory.getFilterChainDefinitionMap().put("/doSysUI", "user")  ; 
		
		
		
		shiroFilterFactory.setLoginUrl(webServerProperties.getLoginUrl());

		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl(webServerProperties.getLogoutUrl());//logout转跳
		shiroFilterFactory.getFilters().put("logout", logoutFilter ) ; 
		
		return shiroFilterFactory ; 
	}
	

}
