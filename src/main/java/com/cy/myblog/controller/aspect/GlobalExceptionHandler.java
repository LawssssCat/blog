package com.cy.myblog.controller.aspect;

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
import com.cy.myblog.common.vo.ErrorVo;
import com.cy.myblog.common.vo.OkVo;
import com.cy.myblog.controller.BaseController;
import com.cy.myblog.controller.ex.NoLoginException;
import com.cy.myblog.controller.ex.PageUnfoundException;
import com.cy.myblog.service.ex.ArgumentValidFaildException;
import com.cy.myblog.service.ex.DataUnfoundException;
import com.cy.myblog.service.ex.DuplicationKeyException;
import com.cy.myblog.service.ex.PropertyValidFaildException;

import lombok.extern.slf4j.Slf4j;

//异常(last) -> view
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController{
	
	@ExceptionHandler({ServiceException.class , ShiroException.class})
	public ErrorVo doExceptionHandle(Throwable e , HttpServletRequest r )  {
		log.error("request URL:{} , Exception:{}" ,r.getRequestURL(), e.toString()); //URL 详尽的资源信息
		ErrorVo errorVo = new ErrorVo();
		
		//shrio 异常
		if(e instanceof UnknownAccountException) {
			setMessage(errorVo, e,"账户不存在");
		}else if(e instanceof LockedAccountException) {
			setMessage(errorVo, e, "账户状态异常");
		}else if(e instanceof IncorrectCredentialsException) {
			setMessage(errorVo, e, "密码不正确");
		}else if(e instanceof AuthorizationException) {
			setMessage(errorVo, e, "没有此操作权限");
		}else
		//自定义 controller 异常
		if(e instanceof NoLoginException) {
			setMessage(errorVo, e, "请登录");
		}else if(e instanceof PageUnfoundException){
			setMessage(errorVo, e, "404 页面找不到了");
		}else
		//自定义 servicer 异常
		if(e instanceof ArgumentValidFaildException) {
			setMessage(errorVo, e, "参数异常");
		}else if(e instanceof DataUnfoundException) {
			setMessage(errorVo, e, "数据不存在");
		}else if(e instanceof DuplicationKeyException) {
			setMessage(errorVo, e, "键值不能重复");
		}else if(e instanceof PropertyValidFaildException) {//校验异常
			setMessage(errorVo, e, "校验异常!");
			PropertyValidFaildException ex = (PropertyValidFaildException) e; 
			errorVo.setData(ex.getErrors());
		}
		else {
			setMessage(errorVo, e, "系统维护中");
			log.error("Exception {}" , e.getMessage());
		}
		return errorVo;
	}
	/**
	 * 如果没有异常描述,使用默认异常描述
	 * @param errorVo json 结果串,往message添加异常描述
	 * @param e 异常
	 * @param message 默认异常描述
	 */
	private void setMessage(ErrorVo errorVo , Throwable e , String message) {
		if(StringUtils.isEmpty(e.getMessage())) {
			errorVo.setMessage(message);
		}else{
			errorVo.setMessage(e.getMessage());
		}
	}
	
}
