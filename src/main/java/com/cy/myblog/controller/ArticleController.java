package com.cy.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/article")
public class ArticleController {

	final private String prefix = "/article" ;  
	
	@RequestMapping("detail/{id}")
	public String getDetail(@PathVariable("id") Integer id) {
		log.debug("id={}" , id);
		return prefix+"/detail";
	}
}
