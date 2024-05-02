---
title: Slf4j + Log4j2 日志框架
date: 2024-05-01
tag:
  - java
order: 33
---

Apache Log4j2 是对 Log4j 的升级版本，参考了 logback 的设计并修复了一些问题。主要提升有：

- 异常处理 —— 在 logback 中，Appender 中的异常不会被应用感知到，在 log4j2 中提供了一些异常处理机制。
- 性能提升 —— log4j2 比较 log4j 和 logback 都有很明显的性能提升。
- 自动重载配置 —— 参考 logback 的设置，提供自动刷新参数配置的功能。（我们在生产上可以动态修改日志级别而不需要重启应用）

==官网： <https://logging.apache.org/log4j/2.x/>==

<!-- more -->

## 例子：适配 Slf4j

引入依赖

```xml title="pom.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-log4j2/pom.xml -->
```

配置（参考： [link](https://logging.apache.org/log4j/2.x/manual/configuration.html)）

```xml title="log4j2.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-log4j2/src/main/resources/log4j2.xml -->
```

## 例子：桥接 Log4j（旧版）

引入依赖：

1. 日志依赖引入、配置
1. 业务依赖引入、排除旧日志依赖
1. 桥接依赖引入

::: tabs

@tab 依赖配置

```xml title="pom.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-log4j2-over-log4j/pom.xml -->
```

@tab 日志配置

```xml title="log4j2.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-log4j2-over-log4j/src/main/resources/log4j2.xml -->
```

:::

::: tabs

@tab 桥接前

```
over 2024-05-02 08:32:02.554 [main] INFO  org.example.OverTest - ---------- log4j -----------
2024-05-02 08:32:02 DEBUG org.example.Log4jService 9:Alpha doSomething debug
2024-05-02 08:32:02 INFO  org.example.Log4jService 10:Alpha doSomething info
2024-05-02 08:32:02 WARN  org.example.Log4jService 11:Alpha doSomething warn
2024-05-02 08:32:02 ERROR org.example.Log4jService 12:Alpha doSomething error
over 2024-05-02 08:32:02.596 [main] INFO  org.example.OverTest - ---------- logback -----------
over 2024-05-02 08:32:02.598 [main] INFO  org.slf4j.LoggerFactory - info
over 2024-05-02 08:32:02.598 [main] WARN  org.slf4j.LoggerFactory - warn
over 2024-05-02 08:32:02.598 [main] ERROR org.slf4j.LoggerFactory - error
```

log4j 旧版日志未接入；logback 日志已接入（因为都是使用 slf4j 的接口，所以可以无缝接入）

@tab 桥接后

```
over 2024-05-02 08:45:18.542 [main] INFO  org.example.OverTest - ---------- log4j -----------
over 2024-05-02 08:45:18.549 [main] INFO  org.example.Log4jService - Alpha doSomething info
over 2024-05-02 08:45:18.550 [main] WARN  org.example.Log4jService - Alpha doSomething warn
over 2024-05-02 08:45:18.550 [main] ERROR org.example.Log4jService - Alpha doSomething error
over 2024-05-02 08:45:18.550 [main] INFO  org.example.OverTest - ---------- logback -----------
over 2024-05-02 08:45:18.551 [main] INFO  org.slf4j.LoggerFactory - info
over 2024-05-02 08:45:18.551 [main] WARN  org.slf4j.LoggerFactory - warn
over 2024-05-02 08:45:18.551 [main] ERROR org.slf4j.LoggerFactory - error
```

:::
