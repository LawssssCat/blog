---
title: Timer 使用
date: 2024-05-12
tag:
  - java
  - schedule
order: 21
---

JDK 提供的定时任务工具

<!-- more -->

Demo： <SiteLink path="/code/demo-java-schedule/demo-timer-01-simple" />

## 简单使用

::: tip
Timer 使用 “小顶堆” 方式管理任务调度
:::

```java
<!-- @include: @project/code/demo-java-schedule/demo-timer-01-simple/src/test/java/org/example/task/HelloTaskTest.java -->
```

## 任务丢失问题

虽然任务添加进队列，但由于上一个任务未执行完成，导致后面的任务延时完成，最终在程序关闭前依然未执行任务。

```java
<!-- @include: @project/code/demo-java-schedule/demo-timer-01-simple/src/test/java/org/example/task/MissTaskTest.java -->
```

## 线程池执行

上面任务丢失问题是因为 Timer 对 schedule 中的任务是单线程执行的，所以有线程阻塞问题。
解决方法就是添加线程池，避免线程阻塞问题。

::::: tabs

@tab FixPool 方案

```java
<!-- @include: @project/code/demo-java-schedule/demo-timer-01-simple/src/test/java/org/example/task/FixPoolTaskTest.java -->
```

@tab SchedulePool 方案

SchedulePool 相关方法

```java
// 延时执行一次某个任务
public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit); // 会有返回值

// 延时，并周期性执行某个任务
// 间隔时间是固定的，无论上一个任务是否执行完成
public ScheduledFuture<?> scheduleWithFixedDalay(Runnable command, long initialDelay, long delay, TimeUnit unit)

// 间隔是不固定的，会在周期任务的上一个任务执行完成之后才开始计时，并在指定时间间隔之后才开始执行任务
public ScheduledFuture<?> scheduleAtFixeRate(Runnable command, long initialDelay, long period, TimeUnit unit)
```

::: info
**Leader-Follower 模式**
在 SchedulePool 中，所有工作线程只会有一个 leader 线程，其他线程都是 follower 线程。
只有 leader 线程能执行任务，而 follower 线程则不会执行任务（它们会处于 “休眠” 状态）。
当 leader 线程拿到任务后执行任务前，leader 线程会变成 follower 线程，并选出一个新的 leader 线程，然后才去执行任务。
然后新的 leader 等待下一个可执行任务的到来，循环上面过程。
这种方法保证了线程池中的线程有序执行任务。
:::

代码：

```java
<!-- @include: @project/code/demo-java-schedule/demo-timer-01-simple/src/test/java/org/example/task/SchedulePoolTaskTest.java -->
```

::: tip
使用 SchedulePool 就不需要自己创建 Timer 了。
:::

:::::
