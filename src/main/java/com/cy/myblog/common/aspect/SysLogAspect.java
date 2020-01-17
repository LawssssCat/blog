package com.cy.myblog.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
	
	@Around("doLog()")
	public Object doRound(ProceedingJoinPoint jp ) throws Throwable {
		try {
			log.info("######### doRound - before #########");
			Object result = jp.proceed();
			log.info("######### doRound - after #########");
			return result ; 
		} catch (Throwable e) {
			log.info("######### doRound - exception #########");
			throw e ; 
		} finally {
			log.info("######### doRound - finally #########");
		}
	}
	
	@After("doLog()")
	public void doAfter() {
		log.info("######### doAfter #########");
	}
	
	@AfterReturning(pointcut = "doLog()" , returning = "result")
	public Object doAfterReturn(Object result) {
		log.info("######### doAfterReturn:resutl=[{}] #########", result);
		return result  ; 
	}
	
	@AfterThrowing(pointcut = "doLog()" , throwing = "e")
	public void doAfterReturn(Exception e) {
		log.info("######### doAfterReturn:exception=[{}] #########", e.getMessage());
	}
	
}
