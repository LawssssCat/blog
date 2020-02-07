package com.cy.myblog.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.myblog.common.ex.ServiceException;
import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.controller.ex.NoLoginException;
import com.cy.myblog.controller.ex.PageUnfoundException;
import com.cy.myblog.service.ex.ArgumentValidFaildException;
import com.cy.myblog.service.ex.DataUnfoundException;
import com.cy.myblog.service.ex.DuplicationKeyException;

import lombok.extern.slf4j.Slf4j;

//异常(last) -> view
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({ServiceException.class , ShiroException.class})
	public JsonResult doExceptionHandle(Throwable e , HttpServletRequest r )  {
		log.error("request URL:{} , Exception:{}" ,r.getRequestURL(), e.toString()); //URL 详尽的资源信息
		JsonResult j = new JsonResult();
		j.setState(400);
		
		//shrio 异常
		if(e instanceof UnknownAccountException) {
			setMessage(j, e,"账户不存在");
		}else if(e instanceof LockedAccountException) {
			setMessage(j, e, "账户状态异常");
		}else if(e instanceof IncorrectCredentialsException) {
			setMessage(j, e, "密码不正确");
		}else if(e instanceof AuthorizationException) {
			setMessage(j, e, "没有此操作权限");
		}else
		//自定义 controller 异常
		if(e instanceof NoLoginException) {
			setMessage(j, e, "请登录");
		}else if(e instanceof PageUnfoundException){
			setMessage(j, e, "404 页面找不到了");
		}else
		//自定义 servicer 异常
		if(e instanceof ArgumentValidFaildException) {
			setMessage(j, e, "参数异常");
		}else if(e instanceof DataUnfoundException) {
			setMessage(j, e, "数据不存在");
		}else if(e instanceof DuplicationKeyException) {
			setMessage(j, e, "键值不能重复");
		}
		else {
			setMessage(j, e, "系统维护中");
			log.error("Exception {}" , e.getMessage());
		}
		return j;
	}
	private void setMessage(JsonResult j  , Throwable e , String message) {
		if(StringUtils.isEmpty(e.getMessage())) {
			j.setMessage(message);
		}else{
			j.setMessage(e.getMessage());
		}
	}
	
}
