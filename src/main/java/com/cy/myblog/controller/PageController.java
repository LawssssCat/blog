package com.cy.myblog.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.myblog.pojo.po.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {

	
	
	@RequestMapping({"/doIndexUI"  , "/article"})
	public String toIndexUI() {
		log.debug("toIndexUI redirect:/");
		return "redirect:/"; 
	}
	@RequestMapping("/")
	public String doIndexUI() {
		log.debug("doIndexUI /index");
		return "/blog/index";
	}
	
	
	
	
	@RequestMapping({"/doLoginUI", "/login"})
	public String doLoginUI() {
		log.debug("doLoginUI /login");
		return "/blog/login"; 
	}
	
	@RequestMapping({"/doSysUI" , "/sys"})
	public String doSysUI(Model model ) { ///sys/blogs
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		
		model.addAttribute("nickname", user.getNickname()) ; 
		model.addAttribute("avatarUrl", user.getAvatarUrl()) ; 
		log.debug("model add ... nickname={},avatarUrl={} " , user.getNickname() , user.getAvatarUrl());
		
		
		return "/system";
	}
	
	
	@RequestMapping(value = "/blog/{page}" ) //index login forward:
	public String toCommonPage( 
			@PathVariable("page") String page) {
		String url = "/blog/"+page ;
		log.debug("toCommonPage url={}",url);
		return url; 
	}
	
}
