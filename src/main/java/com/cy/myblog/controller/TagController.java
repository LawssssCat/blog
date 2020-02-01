package com.cy.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.common.vo.PageObject;
import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;
import com.cy.myblog.service.TagService;
import com.cy.myblog.service.TypeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService ; 

	@PostMapping("/insertObject")
	public JsonResult doInsertType(Tag tag) {
		int rows = tagService.doInsertObject(tag) ;
		return new JsonResult("add ok! rows="+rows);
	}
	
	@GetMapping("/isExistName")
	public JsonResult IsExistName(String name ) {
		Boolean statement = tagService.doIsExistName(name);
		return new JsonResult(statement); 
	}
	
	@GetMapping("/findPageObject")
	public JsonResult doFindPageObject(String name , Integer pageCurrent) {
		PageObject<Tag> pageObject = tagService.doFindPageObject(name , pageCurrent) ;
		return new JsonResult(pageObject);
	}
//	
//	@DeleteMapping("/deleteObject")
//	public JsonResult doDeleteObject(Integer id) {
//		log.debug("delete id={}" , id);
//		int rows = typeService.doDeleteObject(id) ; 
//		return new JsonResult("delete ok! rows="+rows);
//	}
//	
//	@PutMapping("/updateObject")
//	public JsonResult doUpdateObject(Type type) {
//		log.debug("update type={}" , type);
//		int rows = typeService.doUpdateObject(type) ; 
//		return new JsonResult("update ok! rows="+rows) ; 
//	}
	
}
