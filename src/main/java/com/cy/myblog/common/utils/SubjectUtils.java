package com.cy.myblog.common.utils;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;

import com.cy.myblog.controller.ex.NoLoginException;
import com.cy.myblog.pojo.po.User;

public abstract class SubjectUtils {

	public static String getIP() {
		Subject subject = SecurityUtils.getSubject();
		if(subject instanceof WebSubject) {
			WebSubject weSubject = (WebSubject) subject ;
			ServletRequest request = weSubject.getServletRequest() ;
			if(request!=null) {
				return request.getRemoteAddr()  ; 
			}
		}
		return null ; 
	}
	
	public static String getUsername() {
		return  getUser().getUsername() ;
	}
	
	public static String getAvatarUrl() {
		return  getUser().getAvatarUrl() ;
	}
	
	public static Integer getUserId() {
		return getUser().getId();
	}
	
	public static User getUser() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			Object principal = subject.getPrincipal();
			if(principal instanceof User) {
				return  (User) principal ;
			}
		}
		throw new NoLoginException("没有登录");
	}
	
	
}
