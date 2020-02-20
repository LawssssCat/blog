package com.cy.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.OkVo;
import com.cy.myblog.common.vo.PageVo;
import com.cy.myblog.pojo.po.Type;
import com.cy.myblog.service.TypeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/type")
public class TypeController {
	
	@Autowired
	private TypeService typeService ; 

	@PostMapping("/insertObject")
	public OkVo doInsertType(Type type) {
		int rows = typeService.doInsertObject(type) ;
		return new OkVo("add ok! rows="+rows);
	}
	
	@GetMapping("/isExistName")
	public OkVo IsExistName(String name  , Integer id ) {
		Boolean statement = typeService.doIsExistName(name , id);
		return new OkVo(statement); 
	}
	
	@GetMapping("/findObjectById")
	public OkVo doFindObjectById(Integer id ) {
		Type type = typeService.dofindObjectById(id) ;
		return new OkVo(type); 
	}
	
	@GetMapping("/findPageObject")
	public OkVo doFindPageObject(String name , Integer pageCurrent) {
		PageVo<Type> pageObject = typeService.doFindPageObject(name , pageCurrent) ;
		return new OkVo(pageObject);
	}
	
	@DeleteMapping("/deleteObject")
	public OkVo doDeleteObject(Integer id) {
		log.debug("delete id={}" , id);
		int rows = typeService.doDeleteObject(id) ; 
		return new OkVo("delete ok! rows="+rows);
	}
	
	@PutMapping("/updateObject")
	public OkVo doUpdateObject(Type type) {
		log.debug("update type={}" , type);
		int rows = typeService.doUpdateObject(type) ; 
		return new OkVo("update ok! rows="+rows) ; 
	}
	
}
