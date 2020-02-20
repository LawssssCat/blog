package com.cy.myblog.dao.plugin;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import lombok.extern.slf4j.Slf4j;

/**
 * 完成插件签名：
 * 告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts({
	@Signature(type = StatementHandler.class , method = "parameterize" , args = Statement.class)
})
@Slf4j
public class MyFirstPlugin implements Interceptor{

	/**
	 * 拦截：
	 * 拦截目标对象的目标方法的执行 ； 
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object proceed = invocation.proceed();//执行目标方法 
		log.debug("intercept:{}" , invocation.getMethod());
		return proceed;//返回执行后的返回值
	}


	/**
	 * 包装目标对象的：
	 * 包装为目标对象创建一个代理对象
	 */
	@Override
	public Object plugin(Object target) {
		//我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们的目标对象。
		//返回为当前target创建的动态代理
		//下面代码 核心底层：Object wrap = Plugin.wrap(target, this);
		log.debug("class:{}",target);
		return Interceptor.super.plugin(target);
	}
	
	
	/**
	 * 将插件注册时的 property 属性设置进来
	 */
	@Override
	public void setProperties(Properties properties) {
		//Interceptor.super.setProperties(properties);//NOP
		log.debug("插件配置的信息：{}",properties);
	}
	
}
