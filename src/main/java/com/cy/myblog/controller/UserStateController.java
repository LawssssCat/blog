package com.cy.myblog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.constant.UserStateMenu;
import com.cy.myblog.common.constant.UserStateMenu.UserState;
import com.cy.myblog.common.vo.OkVo;

@RequestMapping("/us")
@RestController
public class UserStateController extends BaseController {

	@GetMapping("/list")
	public OkVo findObjects()  {
		UserState[] list = UserStateMenu.getUserStateList();
		return new OkVo(OK,OK_MSG,list);
	}
	
}
