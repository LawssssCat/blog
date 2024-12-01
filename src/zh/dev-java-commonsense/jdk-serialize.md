---
title: JDK 序列化/反序列化功能
date: 2024-08-07
tag:
  - java
order: 66
---

序列化/反序列化

## spring

接口日志

```java
@Aspect
@Component
@Order(1000)
@Slf4j
public class LogInfoAspect {
  /**
   * 通过 @Pointcut 注解声明频繁使用的切点表达式
   */
  @Pointcut("execution(* com.example.demo..*.*(..))")
  public void AspectController() {

  }

  @Pointcut("execution(* com.example.demo..*.*(..))")
  public void AspectController2() {

  }

  /**
   * 先执行、先退出
   */
  @Around("AspectController() || AspectController2()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    log.debug("环绕前");
    MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
    pjp.getTarget();
    LogAnnotation logAnnotationClass = pjp.getTarget().getClass().getAnnotation(LogAnnotation.class);
    LogAnnotation logAnnotationMethod = pjp.getTarget().getMethod().getAnnotation(LogAnnotation.class);
    if (logAnnotationClass == null && logAnnotationMethod == null) {
      return pjp.proceed();
    }
    LogAnnotation logAnnotation = ObjectUtils.defaultIfNull(logAnnotationMethod, logAnnotationClass);
    StopWatch sw = new StopWatch();
    String className = pjp.getTarget().getClass().getName();
    String methodName = methodSignature.getName();
    if (logAnnotation.parameter()) {
      log.info("{}:{}:parameter:{}", className, methodName, pjp.getArgs());
    }
    sw.start();
    Object result = null;
    try {
      result = pjp.proceed();
    } catch (Throwable e) {
      if (logAnnotation.exception()) {
        log.warn(e.getMessage(), e);
      }
      throw e;
    }
    if (logAnnotation.result()) {
      log.info("{}:{}:result:{}", className, methodName, result);
    }
    sw.stop();
    if (logAnnotation.totalConsume()) {
      log.info("{}:{}:totalConsume:{}s", className, methodName, sw.getTotalTimeSeconds());
    }
    log.debug("环绕后");
    return result;
  }
}
```

压缩返回值

```yaml
server:
  compression:
    enabled: true
    mime-types: application/javascript,text/css,application/json,application/xml,text/html,text/xml,text/plain
```
