---
title: SpringBoot Schedule 使用
date: 2024-06-26
tag:
  - java
  - spring
  - schedule
order: 32
---

SpringBoot 定时任务调度

<!-- more -->

> 参考：
>
> - Spring 中的 `@Scheduled` 注解 - <https://springdoc.cn/spring-scheduled-tasks/>

## 配置方式

在 SpringBoot 中可以通过下面两步来开启一个定时任务：

1. 在配置类上添加 `@EnableScheduling` 注解来启动定时任务功能
1. 在方法上添加  `@Scheduled`  注解来定义一个定时任务

```java
@Component
@EnableScheduling
public class SecondScheduledTaskDemo {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskDemo.class);

    @Scheduled(cron = "0/10 * * * * *")
    public void second() {
        logger.info("Second scheduled task is starting... ...");
        logger.info("Second scheduled task is ending... ...");
    }

}
```

## 调度策略

### 以固定延迟调度任务

配置一个任务，使其在固定延迟后运行：

```java
@Scheduled(fixedDelay = 1000)
public void scheduleFixedDelayTask() {
    System.out.println(
      "Fixed delay task - " + System.currentTimeMillis() / 1000);
}
```

如上，上一次执行结束与下一次执行开始之间的持续时间是固定的。任务会一直等待到前一个任务结束。

在必须确保上一次执行完成后再次运行的情况下，应使用此选项。

### 以固定频率调度任务

在固定的时间间隔内执行一项任务：

```java
@Scheduled(fixedRate = 1000)
public void scheduleFixedRateTask() {
    System.out.println(
      "Fixed rate task - " + System.currentTimeMillis() / 1000);
}
```

如果任务的每次执行都是独立的，则应使用该选项。

::::: tip

定时任务默认情况下不会并行运行。
因此，即使使用了 fixedRate，在前一个任务完成之前也不会调用下一个任务。

如果想在定时任务中支持并行行为，就需要添加 `@Async` 注解：

```java
@EnableAsync
public class ScheduledFixedRateExample {
    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println(
          "Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }

}
```

现在，即使前一项任务尚未完成，这项异步任务也会每秒被调用一次。

::: warning
需要确保不会超出内存和线程池的大小，如果传入的任务不能很快完成，有可能出现 “Out of Memory exception”。
:::

:::::

### 使用 Cron 表达式调度任务

使用 cron 表达式的灵活性来控制任务的时间表：

```java
@Scheduled(cron = "0 15 10 15 * ?")
public void scheduleTaskUsingCronExpression() {

    long now = System.currentTimeMillis() / 1000;
    System.out.println(
      "schedule tasks using cron jobs - " + now);
}
```

::: tip
默认情况下，Spring 使用服务器的本地时区作为 cron 表达式的时区。不过，可以使用 zone 属性来更改时区：

```java
@Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
```

:::

## 参数化

fixedDelay 任务：

```java
@Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
```

fixedRate 任务：

```java
@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
```

基于 cron 表达式的任务：

```java
@Scheduled(cron = "${cron.expression}")
```

## 配置原理

参考： https://blog.csdn.net/z69183787/article/details/115543858

- 基于  `@Scheduled` 的定时任务，会在 bean 实例化阶段 的   `BeanPostProcessor`（具体： `ScheduledAnnotationBeanPostProcessor`  的 `postProcessAfterInitialization` 方法）将所有 `@Scheduled` 注解的方法检测出来，加入各个任务队列之中。

- 基于 `ScheduledThreadPoolExecutor` 的定时任务，内部基于 `DelayQueue` 调度，每次任务执行完成之后会计算是否需要下次执行，以及下次执行的时间，然后将任务在放入队列之中。

## 配置线程池

SpringBoot Schedule 默认单线程执行。这可能由于线程阻塞导致任务延迟。

配置线程池，开启多线程任务调度：（下面多种配置方法选一个）

::::: tabs

@tab 配置类初始化自定义

在 TaskScheduler 中设置线程池大小

```java
@Configuration
public class ScheduleConfig {
    /**
     * 修复同一时间无法执行多个定时任务问题。@Scheduled默认是单线程的
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        return taskScheduler;
    }
}
```

~~或者在 ScheduledExecutorService 中设置线程池大小~~

```java
@Bean
public ScheduledExecutorService scheduledExecutorService() {
    return Executors.newScheduledThreadPool(10);
}
```

@tab 配置类接口重写

```java
@Configuration
public class ScheduledTaskConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        // 核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        // taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
```

@tab 配置文件配置

```yaml
server:
  port: 8081
spring:
  application:
    name: daily-task
  task:
    scheduling:
      pool:
        size: 8 #配置Scheduled定时任务为多线程
```

@tab 使用异步注解

添加 `@EnableAsync` 注解，在相应方法上添加 `@Async` 注解

```java
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}

@Component
public class TestAJob {
    private static final Logger logger = LoggerFactory.getLogger(TestAJob.class);

    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    public void testA() throws InterruptedException {
        Thread.sleep(10000);
        logger.info("testA 执行==============");
    }
}

@Component
public class TestBJob {
    private static final Logger logger = LoggerFactory.getLogger(TestBJob.class);
    
    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    public void testB() {
        logger.info("testB 执行==============");
    }
}
```

:::::

## 异常处理

spring 通过注解的方式可以通过 scheduler 的 setErrorHandler 处理：

```java
@EnableScheduling
@Configuration
class SchedulingConfiguration implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ThreadPoolTaskScheduler taskScheduler;

    SchedulingConfiguration() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setErrorHandler(t -> logger.error("Exception in @Scheduled task. ", t));
        taskScheduler.setThreadNamePrefix("@scheduled-");

        taskScheduler.initialize();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler);
    }
}
```

亦是在代码中直接 `try-catch`:

```java
@Scheduled(cron = "${schedulerRate}")
public void scheduledJob() {
    try {
        businessLogicService.doBusinessLogic();
    } catch (Exception e) {
        log.error(e);
    }
}
```
