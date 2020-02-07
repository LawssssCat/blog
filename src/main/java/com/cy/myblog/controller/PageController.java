package com.cy.myblog.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.myblog.common.config.WebUrlProperties;
import com.cy.myblog.common.utils.SubjectUtils;
import com.cy.myblog.controller.ex.PageUnfoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController extends BaseController {
	
	@Autowired
	private WebUrlProperties webUrlProperties ; 
	
	@RequestMapping("/{page}")
	public String parsePath(
			@PathVariable("page") String page, 
			Model model ) {
		//to 登录
		if(webUrlProperties.getLogin().equals(page)) {
			return toLoginPage(page) ;
		}else if(
				Arrays.asList(webUrlProperties.getSystems())
				.contains(page)) { 
		//to 管理
			return toSystemPage(page , model );
		}else {
			throw new PageUnfoundException("404 页面找不到了") ;
		}
	}
	
	
	@RequestMapping("/{page}/edit")
	public String parsePathEdit(
			@PathVariable("page") String page, 
			Model model ) {
		//to 管理
		String[] systems = 
				webUrlProperties.getSystems();
		if(Arrays.asList(systems).contains(page)) {
			return toSystemPageEdit(page , model );
		}
		throw new PageUnfoundException("404 页面找不到了") ;
	}
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     i n d e x                                                                    *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/

	//TODO
	
//	@RequestMapping({"/doIndexUI"  , "/article"})
//	public String toIndexUI() {
//		log.debug("toIndexUI redirect:/");
//		return "redirect:/"; 
//	}
//	@RequestMapping("/")
//	public String doIndexUI() {
//		log.debug("doIndexUI /index");
//		return "forward:/blog/index";//骚操作..模仿csdn..
//	}
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     l o g i n                                                                    *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	/**到登录界面*/
	public String toLoginPage(String page) {
		log.debug("toLoginPage");
		return "/" + page ; 
	}
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     s y s t e m                                                                  *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	/**到管理页面*/
	public String toSystemPage(String page  , Model model) {
		model.addAttribute("username", SubjectUtils.getUsername()) ; //添加用户名
		model.addAttribute("avatarUrl", SubjectUtils.getAvatarUrl()) ; //添加头像 url
		log.debug("toSystemPage [page={}] " ,  page);
		return webUrlProperties.getSystemPrefix() + "/" + page ;
	}
	
	
	/**到管理编辑页面*/
	public String toSystemPageEdit(String page  , Model model) {
		model.addAttribute("username", SubjectUtils.getUsername()) ; //添加用户名
		model.addAttribute("avatarUrl", SubjectUtils.getAvatarUrl()) ; //添加头像 url
		log.debug("toSystemPageEdit [page={}] " ,  page );
		return webUrlProperties.getSystemPrefix() + "/" + page + "_edit"  ;
	}
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     c o m m o n                                                                  *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
//	@RequestMapping(value = "/blog/{page}" ) //index login forward:
//	public String toCommonPage( 
//			@PathVariable("page") String page , 
//			Model model) {
//		String url = "/blog/"+page ;
//		if(SubjectUtils.isLogin()) {
//			Object user = SecurityUtils.getSubject().getPrincipal();
//			model.addAttribute("user", user);//添加用户
//			log.debug("toCommonPage url={} and is log user={}",url , user.toString());
//		}else {
//			log.debug("toCommonPage url={}",url);
//		}
//		return url; 
//	}
	
}
