package com.cy.myblog.common.config;


import java.util.Map;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringShiroConfig {
	
	@Autowired
	private WebUrlProperties webUrlProperties ; 
	

	@Autowired
	@Bean("shiroSecurityManager")
	public SecurityManager shiroSecurityManager(
			@Qualifier("shiroUserRealm") Realm realm , 
			@Qualifier("shiroRememberMeManager") RememberMeManager rememberMeManager) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setRememberMeManager(rememberMeManager);
		return sManager ;  
	}
	
	
	@Autowired
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(
			@Qualifier("shiroSecurityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
		shiroFilterFactory.setSecurityManager(securityManager);
		
		// 访问过滤映射
		Map<String, String> map = shiroFilterFactory.getFilterChainDefinitionMap();
		
		// 设置登入url
		String login = webUrlProperties.getLogin();
		String logout = webUrlProperties.getLogout();
		String[] systems = webUrlProperties.getSystems();
		String[] anons = webUrlProperties.getAnons() ; 
		map.put(login, "anon");// 放行login页面
		map.put(logout, "logout") ;  // 设置登出页面
		/**定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)*/
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		// 可以匿名
		for(String a : anons) {
			map.put(a, "anon") ;
		}
		// 不可匿名
		for (String s : systems) { 
			map.put(s, "user") ; 
		}
		// 登出
		//...
		//未完待续...
		//...
		
		//授权过滤器
		//注意：当前授权拦截后，shiro会自动跳转到未授权页面
		//perms括号中的内容是权限的值
		//filterMap.put("/add", "perms[user:add]");
		//filterMap.put("/update", "perms[user:update]");
		//设置未授权提示页面
        //shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		
		shiroFilterFactory.setLoginUrl(login);// login重定向,默认:/
		return shiroFilterFactory ; 
	}
	
	//rememberMe
	@Bean("shiroRememberMeManager")
	public RememberMeManager shiroRememberMemanager() {
		CookieRememberMeManager manager = new CookieRememberMeManager();
		manager.getCookie().setMaxAge(1800);//30min
		return manager; 
	}
	

}
