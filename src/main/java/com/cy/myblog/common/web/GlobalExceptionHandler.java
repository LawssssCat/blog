package com.cy.myblog.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.myblog.common.vo.JsonResult;

import lombok.extern.slf4j.Slf4j;

//异常(last) -> view
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public JsonResult doExceptionHandle(Exception e , HttpServletRequest r ) {
		log.error("request URL : {} , Exception : {}" ,r.getRequestURL(), e.getMessage()); //URL 详尽的资源信息
		return new JsonResult(e) ; 
	}
}
