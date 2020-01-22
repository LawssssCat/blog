package com.cy.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService ;

	@RequestMapping("/doLogin")
	public JsonResult doLogin(String username , String password , boolean isRememberMe) {
		userService.doLogin(username , password , isRememberMe);
		return new JsonResult(new Exception("功能未完成！")) ; 
	}
	
}
