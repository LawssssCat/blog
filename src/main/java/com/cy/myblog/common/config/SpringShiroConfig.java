package com.cy.myblog.common.config;


import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringShiroConfig {
	
	@Autowired
	private WebServerProperties webServerProperties ; 
	

	//总管
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
	
	
	//工具人
	@Autowired
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(
			@Qualifier("shiroSecurityManager") SecurityManager securityManager) {
		/**工具人的产生*/
		ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
		/**设置工具人管理器*/
		shiroFilterFactory.setSecurityManager(securityManager);
		/**设置登入url*/
		shiroFilterFactory.setLoginUrl(webServerProperties.getLoginUrl());//login重定向,默认:/
		shiroFilterFactory.getFilterChainDefinitionMap().put(webServerProperties.getLoginUrl() , "anon");//放行login页面
		/**自定义登出*/
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl(webServerProperties.getLogoutUrl());//logout重定向,默认:/
		shiroFilterFactory.getFilterChainDefinitionMap().put(webServerProperties.getLogoutUrl() , "anon");//放行logout页面
		shiroFilterFactory.getFilters().put("logout", logoutFilter); 
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
		//可以匿名
		shiroFilterFactory.getFilterChainDefinitionMap().put("/", "anon") ;//index
		shiroFilterFactory.getFilterChainDefinitionMap().put("/blog/**", "anon") ;//页面
		shiroFilterFactory.getFilterChainDefinitionMap().put("/article/**", "anon") ;//文章
		shiroFilterFactory.getFilterChainDefinitionMap().put("/dist/**", "anon") ;//静态资源
		shiroFilterFactory.getFilterChainDefinitionMap().put("/**.ico", "anon") ;//图标
		//不可匿名
		shiroFilterFactory.getFilterChainDefinitionMap().put("/doLogout", "logout") ;//设置登出 logout
		shiroFilterFactory.getFilterChainDefinitionMap()
		.put(webServerProperties.getSystemPrefix()+"/**"  , "user") ; //管理页面,默认:system/**
		shiroFilterFactory.getFilterChainDefinitionMap().put("/user/doLogin", "anon") ;//登录
		shiroFilterFactory.getFilterChainDefinitionMap().put("/user/**", "user") ;//
		shiroFilterFactory.getFilterChainDefinitionMap().put("/type/find**", "anon") ;//
		shiroFilterFactory.getFilterChainDefinitionMap().put("/type/**", "user") ;//
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
