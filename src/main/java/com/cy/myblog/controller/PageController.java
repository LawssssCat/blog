package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {
	
	@RequestMapping("/doIndexUI")
	public String toIndexUI() {
		return "/starter"; 
	}
	
	@RequestMapping("/{model}/{page}")
	public String loadPage(
			@PathVariable("model") String model , 
			@PathVariable("page") String page) {
		String msg = "/"+model+"/"+page ;  
		log.debug(msg);
		return msg; 
	}
	
}
