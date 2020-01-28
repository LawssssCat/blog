package com.cy.myblog.common.utils;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;

import com.cy.myblog.pojo.po.User;

public abstract class ShiroUtils {

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
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			Object principal = subject.getPrincipal();
			if(principal instanceof User) {
				User user = (User) principal ; 
				return  user.getUsername() ; 
			}
		}
		return null ;
	}
	
	public static boolean isLogin() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			Object principal = subject.getPrincipal();
			if(principal instanceof User) {
				User user = (User) principal ;
				if(user.getValid()==1) {
					return  true; 
				}
			}
		}
		return false ; 
	}
	
}
