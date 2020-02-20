package com.cy.myblog.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.OkVo;
import com.cy.myblog.common.vo.PageVo;
import com.cy.myblog.pojo.po.Tag;
import com.cy.myblog.pojo.po.Type;
import com.cy.myblog.service.TagService;
import com.cy.myblog.service.TypeService;
import com.fasterxml.jackson.core.json.JsonReadContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService ; 

	@PostMapping("/insertObject")
	public OkVo doInsertType(Tag tag) {
		int rows = tagService.doInsertObject(tag) ;
		return new OkVo("add ok! rows="+rows);
	}
	
	@GetMapping("/isExistName")
	public OkVo IsExistName(String name ) {
		Boolean statement = tagService.doIsExistName(name);
		return new OkVo(statement); 
	}
	
	@GetMapping("/findObjectById")
	public OkVo doFindObjectById(Integer id ) {
		Tag tag = tagService.doFindObjectById(id) ; 
		return new OkVo(tag) ; 
	}
	
	@GetMapping("/findPageObject")
	public OkVo doFindPageObject(String name , Integer pageCurrent) {
		PageVo<Tag> pageObject = tagService.doFindPageObject(name , pageCurrent) ;
		return new OkVo(pageObject);
	}
	
	@DeleteMapping("/deleteObjects")
	public OkVo doDeleteObjects(Integer ... ids) {
		log.debug("delete ids={}" , Arrays.toString(ids));
		int rows = tagService.doDeleteObjects(ids) ; 
		return new OkVo("delete ok! rows="+rows);
	}
	
	@PutMapping("/updateObject")
	public OkVo doUpdateObject(Type type) {
		log.debug("update type={}" , type);
		int rows = tagService.doUpdateObject(type) ; 
		return new OkVo("update ok! rows="+rows) ; 
	}
	
}
