package com.cy.myblog.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.myblog.common.config.WebServerProperties;
import com.cy.myblog.common.exception.UserLogoutException;
import com.cy.myblog.common.utils.ShiroUtils;
import com.cy.myblog.pojo.po.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {
	
	@Autowired
	private WebServerProperties webServerProperties ; 
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     i n d e x                                                                    *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	@RequestMapping({"/doIndexUI"  , "/article"})
	public String toIndexUI() {
		log.debug("toIndexUI redirect:/");
		return "redirect:/"; 
	}
	@RequestMapping("/")
	public String doIndexUI() {
		log.debug("doIndexUI /index");
		return "forward:/blog/index";//骚操作..模仿csdn..
	}
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     l o g i n                                                                    *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	/**到登录界面*/
	@RequestMapping({"/doLoginUI", "/login"})
	public String doLoginUI() {
		log.debug("doLoginUI /login");
		return "/blog/login"; 
	}
	
	
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     s y s t e m                                                                  *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	@RequestMapping({"/system/{page}" , "/system"}) 
	public String toSysUI(
			@PathVariable(required = false ,value = "page") String page  , 
			Model model) {
		if(!ShiroUtils.isLogin()) throw new UserLogoutException("没有登录");
		model.addAttribute("user", SecurityUtils.getSubject().getPrincipal()) ; 
		if(StringUtils.isEmpty(page)) page = webServerProperties.getSystemIndex() ; 
		log.debug("model add user and to system page: [{}] " ,  page);
		return webServerProperties.getSystemPrefix()+"/"+page ; 
	}
	
	
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/*********                                                                                  *********/
	/*********     c o m m o n                                                                  *********/
	/*********                                                                                  *********/
	/****************************************************************************************************/
	/****************************************************************************************************/
	@RequestMapping(value = "/blog/{page}" ) //index login forward:
	public String toCommonPage( 
			@PathVariable("page") String page , 
			Model model) {
		String url = "/blog/"+page ;
		if(ShiroUtils.isLogin()) {
			Object user = SecurityUtils.getSubject().getPrincipal();
			model.addAttribute("user", user);
			log.debug("toCommonPage url={} and is log user={}",url , user.toString());
		}else {
			log.debug("toCommonPage url={}",url);
		}
		return url; 
	}
	
}
