package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import com.cy.myblog.common.utils.SubjectUtils;
import com.cy.myblog.controller.ex.NoLoginException;
import com.cy.myblog.pojo.po.User;

/**
 * 控制器的基类
 */
@Controller
public class BaseController {

	/**
	 * 响应正确 - 状态码
	 */
	public static final Integer OK  = 200 ; 
	public static final String OK_MSG = "success" ; 
	/**
	 * 响应错误 - 状态码
	 */
	public static final Integer FAIL = 400 ; 
	public static final String FAIL_MSG = "fail" ; 
	
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
