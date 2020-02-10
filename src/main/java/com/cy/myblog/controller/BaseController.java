package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;

import com.cy.myblog.common.utils.SubjectUtils;

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
	 * 获取当前登录用户的id
	 * @return 当前登录用户的id
	 */
	protected final Integer getUerId() {
		return SubjectUtils.getUserId() ; 
	}
	
	/**
	 * 获取当前登录用户的 username
	 * @return 当前登录用户的 username
	 */
	protected final String getUername() {
		return SubjectUtils.getUsername()  ; 
	}
	
}
