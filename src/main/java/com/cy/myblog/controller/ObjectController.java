package com.cy.myblog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cy.myblog.common.config.WebUrlProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ObjectController extends BaseController {
	
	/**设置page参数*/
	@ModelAttribute
	public void getPage(HttpServletRequest request , Model model ) {
		String path = request.getServletPath().trim();
		if(path.startsWith("/")) path = path.substring(1);
		
		String page = null ;
		String[] split= path.split("/");
		if(split!=null&& split.length>0) page = split[0];
		model.addAttribute("page", page) ; 
		
		log.info("servletPaht={} => page={}",path , page);
	}
	
	@Autowired
	private WebUrlProperties webUrlProperties ; 
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     s y s t e m                                                                  *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	/**到管理页面*/
	@GetMapping
	public String toSystemPage(@ModelAttribute("page") String page , Model model) {
		model.addAttribute("username", getUsername()) ; //添加用户名
		//model.addAttribute("avatarUrl", getUerAvatarUrl()) ; //添加头像 url
		
		log.debug("toSystemPage [page={}] " ,  page);
		
		return webUrlProperties.getSystemPrefix() + "/" + page ;
	}
	
	
	@GetMapping("/edit")
	/**到管理编辑页面*/
	public String toSystemPageEdit(@ModelAttribute("page") String page , Model model) {
		model.addAttribute("username", getUsername()) ; //添加用户名
		//model.addAttribute("avatarUrl", getUerAvatarUrl()) ; //添加头像 url
		
		log.debug("toSystemPageEdit [page={}] " ,  page );
		
		return webUrlProperties.getSystemPrefix() + "/" + page + "_edit"  ;
	}
	
	
}
