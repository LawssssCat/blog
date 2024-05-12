---
title: Quartz 使用
date: 2024-05-12
tag:
  - java
  - schedule
order: 32
---

Quartz 由 OpenSymphony 开源组织维护，是一个 “任务调度库” （Job Scheduling）。

<!-- more -->

官网： <http://www.quartz-scheduler.org/>

概念：

- 任务详情（JobDetail）
  - Job 实现： 开发者根据业务需要，实现 `org.quartz.job` 接口的类
  - Job 相关类：
    - JobBuilder
    - JobDataMap
- 触发器（Trigger） —— 触发场景
  - Trigger 实现包括：
    - SimpleTrigger
    - CronTrigger
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
      - RAMJobStore： 内存实现
      - JobStoreTX： JDBC 实务由 Quartz 管理
      - JobStoreCMT： JDBC 使用容器实务
      - ClusteredJobStore： 集群实现
      - TerracottaJobStore： Terracotta 中间件
    - ThreadPool
      - SimpleThreadPool
      - 自定义线程池
- 监听器（Listener）

## 简单使用 SimpleTrigger

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
