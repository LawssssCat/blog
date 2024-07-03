package org.example.config.web;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.entity.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
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
    public Result process(ProceedingJoinPoint joinPoint, ConcurrentLimit concurrentLimit) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String identity = Optional.ofNullable(concurrentLimit.identity())
                .filter(StringUtils::hasText)
                .orElseGet(method::getName);
        // String[] params = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        AtomicInteger concurrentCounter = concurrentCounterMap
                .computeIfAbsent(identity, key -> new AtomicInteger(0));

        AtomicBoolean isOverConcurrent = new AtomicBoolean(false);
        int max = concurrentLimit.max();
        int cur = concurrentCounter.updateAndGet(pre -> {
            if (pre < max) {
                return pre + 1;
            }
            isOverConcurrent.set(true);
            return pre;
        });

        log.warn("concurrent limit {}/{}={} in {}.{}", cur, max, isOverConcurrent.get(),
                joinPoint.getTarget().getClass(), identity);
        if (isOverConcurrent.get()) {
            return Result.errorOf(500, "任务忙，请稍后重试", null);
        }
        try {
            return (Result) joinPoint.proceed(joinPoint.getArgs());
        } finally {
            concurrentCounter.decrementAndGet();
        }
    }
}
