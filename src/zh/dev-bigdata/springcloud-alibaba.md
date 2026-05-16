---
title: SpringCloud 介绍
---

Spring Cloud Alibaba 提供 **微服务开发一站式解决方案**。
Spring Cloud Alibaba 包含包含开发微服务架构的必需组件，只需要添加一些注解和配置，就能完成分布式应用系统的搭建。

> 参考
>
> - todo | SpringCloud Alibaba - https://www.bilibili.com/video/BV1ei4y1h7ZP
> - todo | SpringCloud Netfix + Alibaba - https://www.bilibili.com/video/BV1My4y1672a
> - todo | RuoYi-Cloud - https://www.bilibili.com/video/BV1ru4m1F7aV/

<!-- more -->

## 架构

基于 SpringCloud 标准，有以下组件的组合：

```bash
- 通讯 —— restful/rpc/dubbo/feign `httpclient("url", params)`/`restTemplate("url", params)`
- 服务治理/注册中心（服务注册/发现/剔除） —— nacos/nacos config
- 网关 —— gateway
- 容错 —— sentinel
- 排错/链路追踪 —— skywalking
```

![image.png](https://s2.loli.net/2024/08/11/R3bqshMjdSYgXN2.png)

![SpringCloud Alibaba 架构图](https://s2.loli.net/2024/08/10/DIg79TwEkX3bixl.png)

## 构建

脚手架： <https://start.aliyun.com/>

todo
