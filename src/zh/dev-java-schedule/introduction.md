---
title: 定时任务
date: 2024-05-12
tag:
  - java
  - schedule
order: 1
---

java 定时任务创建/管理/调度/分布式/弹性

<!-- mroe -->

## 定时任务管理原理

小顶堆 —— 构建/增加/删除、时间/空间复杂度、作用

时间轮询算法 —— 分层时间轮（cron）

分层时间轮

- 使用多个不同时间维度的轮
  - 天轮：记录几点执行
  - 月轮：记录几号执行
- 月轮遍历到了，将任务取出放到天轮里，即可实现几号几点执行

## API/框架

[quartz](./quartz.md)

xxl-job

elastic-job

power-job
