package com.cy.myblog.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.myblog.common.vo.JsonResult;

import lombok.extern.slf4j.Slf4j;

//异常(last) -> view
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ShiroException.class)
	public JsonResult doHandleShiroException(
			ShiroException e) {
		JsonResult r = new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException) {
			r.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			r.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			r.setMessage("没有此操作权限");
		}else {
			r.setMessage("系统维护中");
			log.error("ShiroException {}" , e.getMessage());
		}
		return r;
	}
	
	
	//放最后,异常按顺序触发
	@ExceptionHandler(Exception.class)
	public JsonResult doExceptionHandle(Exception e , HttpServletRequest r ) {
		log.error("request URL : {} , Exception : {}" ,r.getRequestURL(), e.getMessage()); //URL 详尽的资源信息
		return new JsonResult(e) ; 
	}
	
	
}
