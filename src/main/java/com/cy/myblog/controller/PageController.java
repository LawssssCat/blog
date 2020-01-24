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
		log.debug("doIndexUI forward:/index");
		return "forward:/index";
	}
	
	
	
	
	@RequestMapping("/doLoginUI")
	public String doLoginUI() {
		log.debug("doLoginUI forward:/login");
		return "forward:/login"; 
	}
	
	@RequestMapping("/doSysUI")
	public String doSysUI(Model model ) { ///sys/blogs
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		
		model.addAttribute("nickname", user.getNickname()) ; 
		model.addAttribute("avatarUrl", user.getAvatarUrl()) ; 
		log.debug("model add ... nickname={},avatarUrl={} " , user.getNickname() , user.getAvatarUrl());
		
		
		return "/system";
	}
	
	
	@RequestMapping("/{page}") //index login
	public String toCommonPage( 
			@PathVariable("page") String page) {
		String url = "/"+page ;
		log.debug("toCommonPage url={}",url);
		return url; 
	}
	
}
