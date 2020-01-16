package com.cy.myblog.controller;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.myblog.common.vo.JsonResult;

@RequestMapping("/test")
@RestController
public class TestController {

	@RequestMapping("/doTestJsonResult")
	public JsonResult doTestJsonResult(@PathParam("args") String args ) { //路径上参数 
		return new JsonResult("doTestJsonResult OK! args="+args);
	}
	
	@RequestMapping("doPathVariable")
	public JsonResult doPathVariable(@PathVariable("args") Integer args) { 
		return new JsonResult("do@PathVariable OK! args="+args) ; 
	}

}
