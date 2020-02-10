package com.cy.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.JsonError;
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
		return new JsonResult(OK,OK_MSG,list) ; 
	}
	
	@ResponseBody
	@GetMapping("/{state}")
	public JsonResult findObjectById(
			@PathVariable("state") Long id) {
		UserState us = userStateService.doFindObjectById(id) ; 
		return new JsonResult(OK, OK_MSG, us) ; 
	}
	
	@ResponseBody
	@GetMapping("/valid")
	public JsonResult validObject(
			@RequestParam(value = "id" , required = false) Integer id,
			@RequestParam("state") Integer state) {
		userStateService.doValidObject(id,state); // 验证
		return new JsonResult(OK, OK_MSG); 		// 成功
		// 失败 会到 GlobalExceptionHandler 处理
	}
	
	@ResponseBody
	@PostMapping("")
	public JsonResult saveObject(UserState userState) {
		int rows = userStateService.doSaveObject(userState);
		return new JsonResult(OK,OK_MSG,rows);
	}
	
	@ResponseBody
	@PutMapping("") 
	public JsonResult updateObject(UserState userState) {
		int rows = userStateService.doUpdateObject(userState);
		return new JsonResult(OK,OK_MSG,rows);
	}
	
}
