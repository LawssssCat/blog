package org.example.goods.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(public * org.example.goods.controller..*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取当前请求的 Request 属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        String url = request != null ? request.getRequestURL().toString() : "Unknown";
        String method = request != null ? request.getMethod() : "Unknown";
        String ip = request != null ? request.getRemoteAddr() : "Unknown";

        // 获取类名和方法名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        // 获取入参
        Object[] args = proceedingJoinPoint.getArgs();

        // 打印请求前置日志
        log.info("================================== START ==================================");
        log.info("URL            : {}", url);
        log.info("HTTP Method    : {}", method);
        log.info("IP             : {}", ip);
        log.info("Class Method   : {}.{}", className, methodName);
        try {
            log.info("Request Args   : {}", objectMapper.writeValueAsString(args));
        } catch (Exception e) {
            log.info("Request Args   : {}", Arrays.toString(args)); // 防止大文件或特殊对象序列化失败
        }

        // 执行目标方法
        Object result = proceedingJoinPoint.proceed();

        // 打印请求后置日志（包含响应和耗时）
        long takeTime = System.currentTimeMillis() - startTime;
        try {
            log.info("Response Result: {}", objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            log.info("Response Result: {}", result);
        }
        log.info("Time-Cost      : {} ms", takeTime);
        log.info("=================================== END ===================================");

        return result;
    }
}
