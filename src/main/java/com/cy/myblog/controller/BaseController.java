package com.cy.myblog.controller;

import org.springframework.util.ObjectUtils;

import com.cy.myblog.common.constant.ServiceState;
import com.cy.myblog.common.utils.SubjectUtils;
import com.cy.myblog.controller.ex.NoLoginException;
import com.cy.myblog.pojo.po.User;

/**
 * 控制器的基类
 */
public abstract class BaseController {

	/**
	 * 响应正确 - 状态码 200
	 */
	public static final int OK  = ServiceState.OK.getCode();
	public static final String OK_MSG = ServiceState.OK.getRemark();
	/**
	 * 响应错误 - 状态码 500
	 */
	public static final Integer ERROR = ServiceState.ERROR.getCode();
	public static final String ERROR_MSG = ServiceState.ERROR.getRemark(); 
	
	/**
	 * 获取当前登录用户
	 * @return 当前登录用户
	 */
	protected final User getUer() {
		User u = SubjectUtils.getUser() ;
		if(ObjectUtils.isEmpty(u)) throw new NoLoginException("没有登录");
		return u ; 
	}
	/**
	 * 获取当前登录用户的id
	 * @return 当前登录用户的id
	 */
	protected final Long getUerId() {
		return getUer().getId() ;  
	}
	
//	/**
//	 * 获取当前登录用户的avatarUrl
//	 * @return 当前登录用户的id
//	 */
//	protected final String getUerAvatarUrl() {
//		return getUer().getAvatarUrl(); 
//	}
	
	/**
	 * 获取当前登录用户的 username
	 * @return 当前登录用户的 username
	 */
	protected final String getUsername() {
		return getUer().getUsername()  ; 
	}
	
}
