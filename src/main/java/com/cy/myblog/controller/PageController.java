package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {
	
	@RequestMapping("/doLoginUI")
	public String doLoginUI() {
		return "/login"; 
	}
	
	@RequestMapping("/doSysUI")
	public String doSysUI() { ///sys/blogs
		return "/system";
	}
	
	@RequestMapping("/doIndexUI")
	public String toIndexUI() {
		return "/starter"; 
	}
	
	@RequestMapping("/{prefix}/{page}")
	public String loadPage(
			@PathVariable("prefix") String prefix , 
			@PathVariable("page") String page , 
			Model model ) {
		String url = "/"+prefix+"/"+page ;
		model.addAttribute("url", url);
		log.debug(url);
		return url; 
	}
	
}
