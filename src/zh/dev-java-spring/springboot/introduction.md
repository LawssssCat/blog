---
title: SpringBoot 介绍
date: 2024-04-05
tag:
  - springboot
order: 1
---

SpringBoot 目的在于简化 Java 服务的配置、部署。

<!-- more -->

SpringBoot 核心功能：

- 简化配置
- 内嵌 web 服务器
- 独立运行 `java -jar xxx.jar`
- 准生产的应用监控

todo 区别 `ApplicationRunner` 和 `CommandLineRunner`

```java
(runner).run(args) // ApplicationRunner
(runner).run(args.getSourceArgs()) // CommandLineRunner
```

参考：

- Spring Boot 最佳实践 —— <https://github.com/javastacks/spring-boot-best-practice>
