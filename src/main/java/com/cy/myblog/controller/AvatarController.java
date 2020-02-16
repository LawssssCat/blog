package com.cy.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.common.vo.Page;
import com.cy.myblog.pojo.po.Avatar;
import com.cy.myblog.service.AvatarService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/avatar")
@Controller
public class AvatarController extends ObjectController {
	

	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     A P I                                                                        *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	
	
	@Autowired
	private AvatarService avatarService;
	
	@ResponseBody
	@GetMapping("/{id}")
	public JsonResult findObjectById(@PathVariable("id") Long id)  {
		Avatar avatar = avatarService.doFindObjectById(id) ;
		
		log.info("avatar={}" ,avatar);
		
		return new JsonResult(OK,OK_MSG,avatar);
	}
	
	@ResponseBody
	@GetMapping("/list")
	public JsonResult fondPageObject(
			@RequestParam(value = "pageCurrent") Integer pageCurrent , 
			@RequestParam(value = "name", required = false) String name ) {
		Page<Avatar> page = avatarService.doFondPageObject(pageCurrent, name) ;
		
		log.info("page={}" ,page);
		
		return new JsonResult(OK,OK_MSG,page);
	}
}
