package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/doIndexUI")
	public String toPage() {
		return "/index"; 
	}
}
