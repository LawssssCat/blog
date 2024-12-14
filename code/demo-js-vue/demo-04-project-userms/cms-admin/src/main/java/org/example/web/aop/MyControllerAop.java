package org.example.web.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.common.utils.web.ServletUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class MyControllerAop {
    /**
     * Controller切点
     *
     * <br>
     * <a href="https://springdoc.cn/spring/core.html#expressions">SpEL（Spring Expression Language）</a>
     */
    @Pointcut("execution(public * org.example.web.controller.*.*Controller.*(..))")
    public void controllerAspect() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void requestMappingAspect() {}

    @Around("controllerAspect() && requestMappingAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();

        try {
            log.debug("<-- {} {}", method, uri);
            Object result = joinPoint.proceed();
            log.debug("--> {} {}", method, uri);
            return result;
        } catch (Throwable e) {
            log.error("--x {} {} - {}", method, uri, e.getMessage());
            throw e;
        }
    }
}
