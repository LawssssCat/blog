package com.cy.myblog.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SysLogAspect {

	@Pointcut("execution(* com.cy.myblog.controller.*.*(..))")
	public void doLog() {}  
	
	@Before("doLog()")
	public void doBefore() {
		log.info("######### doBefore #########");
	}
	
	@After("doLog()")
	public void doAfter() {
		log.info("######### doAfter #########");
	}
	
	@AfterReturning(pointcut = "doLog()" , returning = "result")
	public void doAfterReturn(Object result) {
		log.info("######### doAfterReturn:resutl=[{}] #########", result);
	}
	
	@Around("doLog()")
	public void doAround() {
		log.info("######### doAround #########");
		
	}
	
}
