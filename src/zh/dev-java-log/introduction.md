---
title: Java 日志系列
date: 2024-05-01
tag:
  - java
order: 1
---

常见日志框架：

- ~~log4j —— 1999 年，ceki 个人开发日志框架。后捐给 apache~~
- ~~jul `java.util.logging` —— 2002 年，Oracle 推的日志门面（facade）~~
- ~~jcl（Jakarta Commons Loggings，`commons-logging.jar`） —— 2003 年， apache 的日志门面~~
- slf4j —— 2006 年，ceki 另起炉灶的日志门面
- log4j2 —— 2012 年，apache 新推出的日志门面

<!-- more -->

## Slf4j 门面架构

官网： <https://slf4j.org/manual.html>

![框架适配关系图](https://slf4j.org/images/concrete-bindings.png)

Slf4j 提供接口定义，其他实现框架通过 “适配器” 或者 “桥接器” 应用自身的实现代码：

- 适配器 `slf4j-xxx`/`xxx-to-slf4j` —— 【落地日志输出】 实现框架通过在适配项目中实现 `org.slf4j.impl.StaticLoggerBinder` 类（静态绑定类） 完成代码适配。适配项目如： `logback-classic`/`slf4j-log12`/... （适配器依赖一般由日志接口实现框架提供）
- 桥接器 `xxx-over-slf4j` —— 【归一三方输出】 将三方件日志框架的输出路由到 slf4j 中，最终输出由 slf4j 适配器指定的实现框架输出。==引入三方件时，需要排除原日志框架依赖。（因为桥接器原理是重写了三方件依赖的日志框架的全部接口实现）== （桥接器依赖一般由 slf4j 提供）

---

参考：

- 混而不乱的 java 日志体系 log4j, jul, jcl, slf4j 和 logback - <https://www.bilibili.com/video/BV13A41137Tc>
