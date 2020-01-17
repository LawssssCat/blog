package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
public class PageController {
	
	@RequestMapping("/doIndexUI")
	public String toPage() {
		return "/starter"; 
	}
}
