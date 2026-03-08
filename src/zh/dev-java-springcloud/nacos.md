---
title: Nacos笔记
---

[nacos](https://nacos.io/)构建云原生应用的动态服务发现（Nacos Discovery）、服务配置（Nacos Config）和服务管理平台。

> 参考：
>
> - Getting Start <https://nacos.io/zh-cn/docs/quick-start.html>
> - book:Nacos 架构&原理 <https://developer.aliyun.com/ebook/36/read>

<!-- more -->

## 介绍

Nacos 的关键特性包括：

- 服务发现和服务健康检测
- 动态配置服务
- 动态 DNS 服务
- 服务及其元数据管理

> 分布式特性对比: （基于CAP理论。 C 一致性 A 可用性 P 分区容错性）
>
> - Nacos - CP/AP —— 支持一致+分区容错，或者支持可用性+分区容错
> - ~~Eureka（闭源） - AP~~
> - Consul - CP
> - ~~CoreDNS（功能少） - xx~~
> - Zookeeper - CP
