package com.cy.myblog.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.vo.OkVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

	//post: http://localhost/user/login?username=admin&&password=123456&&rememberMe=false
	@PostMapping("/login")
	public OkVo doLogin(
			String username , 
			String password ,
			@RequestParam(defaultValue = "false") Boolean isRememberMe) {
		log.debug("doLogin - username={}, password={}, rememberMe={}",username,password,isRememberMe);
		AuthenticationToken token = //封装用户信息
				new UsernamePasswordToken(
						username, 
						password, 
						isRememberMe);
		Subject subject = SecurityUtils.getSubject();//subject 提交用户信息
		subject.login(token);//登录
		return new OkVo(OK,"登录成功!") ; 
	}
	
}
