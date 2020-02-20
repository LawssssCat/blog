																																																																								package com.cy.myblog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.myblog.common.vo.OkVo;
import com.cy.myblog.common.vo.PageVo;
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
	public OkVo findObjectById(@PathVariable("id") Long id)  {
		Avatar avatar = avatarService.doFindObjectById(id) ;
		
		log.info("avatar={}" ,avatar);
		
		return new OkVo(OK,OK_MSG,avatar);
	}
	
	@ResponseBody
	@GetMapping("/list")
	public OkVo fondPageObject(
			@RequestParam(value = "pageCurrent") Integer pageCurrent , 
			@RequestParam(value = "name", required = false) String name ) {
		PageVo<Avatar> page = avatarService.doFondPageObject(pageCurrent, name) ;
		
		log.info("page={}" ,page);
		
		return new OkVo(OK,OK_MSG,page);
	}
	
	@ResponseBody
	@PostMapping("/upload")
	public OkVo upload(
			@RequestParam("avatar") MultipartFile file , 
			@RequestParam("name") String name) throws IOException {
		log.info("filename={} , name={}" ,file.getOriginalFilename(), name ) ; 
		
		return new OkVo() ; 
	}
	
}
