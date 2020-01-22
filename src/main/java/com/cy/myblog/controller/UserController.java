package com.cy.myblog.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.vo.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/doLogin")
	public JsonResult doLogin(String username , String password , Boolean isRememberMe) {
		log.debug("doLogin - username={}, password={}, rememberMe={}",username,password,isRememberMe);
		Subject subject = SecurityUtils.getSubject();//subject
		AuthenticationToken token = 
				new UsernamePasswordToken(
						username, 
						password, 
						isRememberMe);
		
		subject.login(token);//登录
		return new JsonResult("login OK !") ; 
	}
	
}
