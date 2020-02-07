package com.cy.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.pojo.po.UserState;
import com.cy.myblog.service.UserStateService;

@Controller("/us")
public class UserStateController extends BaseController {

	@Autowired
	private UserStateService userStateService ; 
	
	@GetMapping("/list")
	public JsonResult findObjects() {
		List<UserState> list = userStateService.doFindObjects() ;
		return new JsonResult(list) ; 
	}
}
