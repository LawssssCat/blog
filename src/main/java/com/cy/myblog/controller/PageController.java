package com.cy.myblog.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     l o g i n                                                                    *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	
	@RequestMapping("/login")
	public String toLoginPage() {
		log.info("to login page");
		return "/" +webUrlProperties.getLogin() ; 
	}
	
	/**
	 * 转发 /xxx 请求 
	 * @param page
	 * @param model
	 * @return
	 */
//	@RequestMapping(value="/{page}" )
//	public String parsePath(
//			@PathVariable("page") String page, 
//			Model model ) {
//		//to 登录
//		if(webUrlProperties.getLogin().equals(page)) {
//			return toLoginPage(page) ;
//		}else if(Arrays.asList(webUrlProperties.getSystems())
//				.contains(page)) { 
//		//to 管理
//			return toSystemPage(page , model );
//		}
//		throw new PageUnfoundException("404 页面找不到了") ; 
//	}
	
	/**
	 * 转发 /xxx/edit 请求
	 * @param page
	 * @param model
	 * @return
	 */
//	@RequestMapping(value="/{page}/edit")
//	public String parsePathEdit(
//			@PathVariable("page") String page, 
//			Model model ) {
//		//to 管理
//		String[] systems = 
//				webUrlProperties.getSystems();
//		if(Arrays.asList(systems).contains(page)) {
//			return toSystemPageEdit(page , model );
//		}
//		throw new PageUnfoundException("404 页面找不到了") ; 
//	}
	
	
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
