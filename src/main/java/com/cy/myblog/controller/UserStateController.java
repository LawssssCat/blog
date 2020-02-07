package com.cy.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.pojo.po.UserState;
import com.cy.myblog.service.UserStateService;

@RequestMapping("/us")
@Controller
public class UserStateController extends BaseController {

	@Autowired
	private UserStateService userStateService ; 

	@ResponseBody
	@GetMapping("/list")
	public JsonResult findObjects() {
		List<UserState> list = userStateService.doFindObjects() ;
		return new JsonResult(OK,"success",list) ; 
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public JsonResult findObjectById(
			@PathVariable("id") Long id) {
		UserState us = userStateService.doFindObjectById(id) ; 
		return new JsonResult(OK, "success", us) ; 
	}
}
