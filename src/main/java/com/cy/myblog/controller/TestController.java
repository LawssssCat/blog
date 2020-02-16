package com.cy.myblog.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/test")
@Controller
public class TestController {


	@RequestMapping("/responseEntity")
	public ResponseEntity<byte[]> responseEntity(HttpSession session) throws IOException {
		//读文件
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/banner.txt") ;//servletContext.getRealPath("/banner.txt") ; 
		log.info(in.getClass().toString());
		body = new byte[in.available()] ;
		in.read(body) ; 
		//返回状态
		HttpStatus status = HttpStatus.OK ;
		//响应求头
		HttpHeaders headers = new HttpHeaders() ;
		headers.add("Content-Disposition", "attachment;filename=FileName.txt");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, status) ; 
		return response ; 
	}
	
	
	@ResponseBody
	@RequestMapping("/httpMessageConverter")
	public String testHttpMessageConverter(String body) {
		log.info(body);
		return "helloworld!"+new Date() ; 
	}
	
	
	@RequestMapping
	public String toTestPage() {
		log.info("to test Page");
		return "/test" ;
	}
}

