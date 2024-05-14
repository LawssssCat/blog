---
title: Quartz 使用
date: 2024-05-12
tag:
  - java
  - schedule
order: 32
---

Quartz 由 OpenSymphony 开源组织发起的项目，该项目 2009 年被 Terracotta 收购。
Quartz 完全由 Java 编写， 专注于 “任务调度库”（job scheduling） 功能搭建，可以用来管理上万个 jobs 复杂程序。

<!-- more -->

官网： <http://www.quartz-scheduler.org/>

特点：

- 支持多种任务调度方法
- 支持多种调度数据存储方式
- 支持分布式和集群能力
- 易于与 Spring 集成（Quartz 是 Spring 默认的调度框架）

概念：

- 任务详情（JobDetail）
  - Job 实现： 开发者根据业务需要，实现 `org.quartz.job` 接口的类
  - Job 相关类：
    - JobBuilder
    - JobDataMap
- 触发器（Trigger） —— 触发规则 💡 `任务:触发器=1:n`
  - Trigger 实现包括：
    - SimpleTrigger： 延时任务/定时任务
    - CronTrigger： 使用 cron 表达式定义触发任务的时间规则
    - DateIntervalTrigger： 日期周期的规则触发器
    - NthIncludedDateTrigger： 排除指定时间周期和日期的触发器
    - DailyTimeIntervalTrigger
    - CalendarIntervalTrigger
  - Trigger 相关类：
    - JobDataMap
    - TriggerBuilder
    - ScheduleBuilder
- 调度器（Scheduler） —— 将 “任务” 和 “触发器” 关联
  - Scheduler 实现包括：
    - RemoteScheduler
    - StdSchduler
  - Scheduler 状态：
    - start
    - stop
    - pause
    - resume
  - Scheduler 相关类：
    - SchedulerFactory： 用于创建 Scheduler
      - StdSchedulerFactory： properties 配置
      - DirecttSchedulerFactory： 通过代码配置
    - JobStore： 存储运行时信息，包括 Trigger/Scheduler/JobDetail/业务锁等 | 默认只在 Job 被添加到调度程序（Scheduler，任务执行计划表）的时候，存储一次关于该任务的状态信息数据
      - RAMJobStore： 内存存储任务调度状态
      - JDBCJobStore： 通过数据库持久化任务调度状态 【持久化、集群】
        - JobStoreTX： JDBC 事务由 Quartz 管理
        - JobStoreCMT： JDBC 使用容器实务
        - ClusteredJobStore： 集群实现
        - TerracottaJobStore： Terracotta 中间件
    - ThreadPool
      - SimpleThreadPool
      - 自定义线程池
- 监听器（Listener）

## 任务调用：间隔 SimpleTrigger

SimpleTrigger 是比较简单的一类触发器，支持场景：

- 在指定时间内执行一次任务 —— 设定开始时间，不设定循环，（~~设定结束时间~~）
- 在指定时间内执行多次任务（可指定任务执行次数/任务执行时间段） —— 设定开始时间，设定循环（间隔/次数），（~~设定结束时间~~）

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-01-simple/src/test/java/org/example/job/SimpleJobTest.java -->
```

@tab 抽象类

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-01-simple/src/test/java/org/example/job/AbstractSimpleJobTest.java -->
```

:::

## 任务调用：日期 CronSchedule

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-01-simple/src/test/java/org/example/job/CronJobTest.java -->
```

## 任务错过处理 misfire

任务调度错过情况：

- 调度器（scheduler）设置任务/触发器 pause
- 线程池资源资源

Misfire 策略：

- SimpleTrigger：间隔
  - MISFIRE_INSTRUCTION_SMART_POLICY （默认）
  - MISFIRE_INSTRUCTION_FIRE_NOW
  - MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY —— 立即执行（错过的）所有任务
  - MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT —— （如果有错过任务）立即执行一次，按原来间隔执行下次，执行总数不变（即：将所有任务推迟到恢复后执行）
  - MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT —— （如果有错过任务）立即执行一次，按原来间隔执行下次，忽略错过的执行次数（即：将所有错过的任务丢弃）
  - MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
  - MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
- CronTrigger
  - MISFIRE_INSTRUCTION_DO_NOTHING —— 忽略错过任务
  - MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY —— 立即执行（错过的）所有任务，其他任务按计执行
  - MISFIRE_INSTRUCTION_ONE_NOW —— 立即执行一个任务，其他任务按计划执行

## 任务线程异步

Job 间掉调度，默认情况是异步执行的 —— 即前一个任务的线程状态不影响下一个任务的调用。
但我们也可以通过在类上添加 `@DisallowConcurrentExecution` 注解关闭 Job 异步调用。

todo demo

## 任务状态持久化

::: warning
**（默认）无状态任务**： 每次调度器执行 job 时，会创建新的 job 实例，然后调用 execute 方法，然后关联的 job 对象实例释放（可被 gc）。
💡 每次创建新实例能避免并发问题

**状态任务**： 通过添加 `@PersistJobDataAfterExecution` 注解，让 job 的 data 持久化，下一个 job 依然能获取之前持久化的数据
（持久化 JobDetail 数据，但没有持久化 Trigger 数据）
:::

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-01-simple/src/test/java/org/example/job/SimpleJobStateTest.java -->
```

## 监听器 listener

接口：

- JobListener —— job 开始前/job 完成后/job 失效
- TriggerListener ——
- SchedulerListener

### JobListener

```java
scheduler.getListenerManager().addJobListener(new MyJobListener());
scheduler.getListenerManager().addJobListener(new MyJobListener(), GroupMatcher.jobGroupEquals("group1"));
scheduler.getListenerManager().addJobListener(new MyJobListener(), KeyMatcher.keyEquals(new JobKey("job1", "group1")));
```

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-01-simple/src/main/java/org/example/listener/MyJobListener.java -->
```

## 调度持久化 JobStore

JobStore 负责存储调度器的 “工作数据”： 任务（Job）、触发器（Trigger）、数据（JobDataMap）

## 配置 prperties

<https://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration>

```properties title="quartz.properties"
org.quartz.scheduler.instanceName: CustomizeQuartzScheduler
org.quartz.scheduler.rmi.export: false
org.quartz.scheduler.rmi.proxy: false
org.quartz.scheduler.rmi.wrapJobExecutionInUserTransaction: false

org.quartz.threadPool.class: org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount: 3
org.quartz.threadPool.threadPriority: 5
org.quartz.threadPool.threadInheritContextClassLoaderOfInitializingThread: true

org.quartz.jobStore.misfireThreadhold: 6000

org.quartz.jobStore.class: org.quartz.simpl.RAMJobStore
```

## 插件

```properties title="quartz.properties"
# ===================
# Configure Plugins
# ===================

org.quartz.plugin.triggHistory.class: org.quartz.plugins.history.LoggingJobHistoryPlugin
org.quartz.plugin.jobInitializer.class: org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin
org.quartz.plugins.jobInitializer.fileNames: quartz_data.xml
org.quartz.plugins.jobInitializer.failOnFileNoFound: true
org.quartz.plugins.jobInitializer.scanInterval: 120
org.quartz.plugins.jobInitializer.wrapInUserTransaction: false
```

## 集成 spring

spring 提供了 quartz 相关的封装

```xml
<!-- @include: @project/code/demo-java-schedule/demo-quartz-02-spring/pom.xml -->
```

::: tabs

@tab 配置类

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-02-spring/src/main/java/org/example/config/QuartzConfiguration.java -->
```

@tab 任务类

```java
<!-- @include: @project/code/demo-java-schedule/demo-quartz-02-spring/src/main/java/org/example/job/HelloJob.java -->
```

:::
