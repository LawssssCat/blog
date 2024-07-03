package org.example.config.web;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.entity.Result;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Aspect
public class ConcurrentLimiterAspect {
    @PostConstruct
    void postConstruct() {
        log.info("post construct obj:{}", ConcurrentLimiterAspect.class);
    }

    private final Map<String, AtomicInteger> concurrentCounterMap = Maps.newConcurrentMap();

    // AOP Aspect 切入点语法
    @Around("@annotation(concurrentLimit) && (execution(org.example.entity.Result *(..)))")
    public Object process(ProceedingJoinPoint joinPoint, ConcurrentLimit concurrentLimit) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> aClass = joinPoint.getTarget().getClass();

        String identity = Strings.isNullOrEmpty(concurrentLimit.identity())
                ? method.getName()
                : concurrentLimit.identity();
        // String[] params = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        AtomicInteger concurrentCounter = concurrentCounterMap
                .computeIfAbsent(identity, key -> new AtomicInteger(0));

        AtomicBoolean isInvokeMethod = new AtomicBoolean(false);
        int max = concurrentLimit.max();
        concurrentCounter.updateAndGet(pre -> {
            if (pre < max) {
                isInvokeMethod.set(true);
                return pre + 1;
            }
            return pre;
        });

        Result res;
        if (isInvokeMethod.get()) {
            Object[] args = joinPoint.getArgs();
            try {
                res = (Result) joinPoint.proceed(args);
            } finally {
                concurrentCounter.decrementAndGet();
            }
        } else {
            log.warn("concurrent limit over {} in {}.{}", max, aClass, identity);
            res = Result.errorOf(500, "任务忙，请稍后重试", null);
        }
        return res;
    }
}
