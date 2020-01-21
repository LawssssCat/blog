package com.cy.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.vo.JsonResult;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/doLogin")
	public JsonResult doLogin() {
		return new JsonResult(new Exception("功能未完成！")) ; 
	}
	
}
