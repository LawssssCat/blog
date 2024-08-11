---
title: SpringCloud 介绍
date: 2024-08-12
tag:
  - springcloud
order: 1
---

Spring Cloud Alibaba 提供 **微服务开发一站式解决方案**。
Spring Cloud Alibaba 包含包含开发微服务架构的必需组件，只需要添加一些注解和配置，就能完成分布式应用系统的搭建。

<!-- more -->

> 参考
>
> - todo | SpringCloud Alibaba - https://www.bilibili.com/video/BV1ei4y1h7ZP
> - todo | SpringCloud Netfix + Alibaba - https://www.bilibili.com/video/BV1My4y1672a
> - todo | RuoYi-Cloud - https://www.bilibili.com/video/BV1ru4m1F7aV/

## SpringCloud

SpringCloud 是 Java 的微服务（microservice）解决方案。
SpringCloud 目的在于简化 Java 服务的分布式系统功能开发。

> 参考：
>
> - SpringCloud 官方文档 - https://sca.aliyun.com/en/docs/2022/overview/what-is-sca/
> - https://www.cnblogs.com/qdhxhz/p/14563991.html
>
> 论文：
>
> - en - https://martinfowler.com/articles/microservices.html
> - cn - http://blog.cuicc.com/blog/2015/07/22/microservices

SpringCloud 是以微服务为核心的分布式系统构建标准。也就是说 SpringCloud 针对分布式系统开发的通用抽象。

![SpringCloud 架构图](https://s2.loli.net/2024/08/11/7METIogt59pUCHF.png)

基于 SpringCloud 标准，有以下实现方式的组合：

:::::: tabs

@tab Alibaba

- 通讯 —— restful/rpc/dubbo/feign `httpclient("url", params)`/`restTemplate("url", params)`
- 服务治理/注册中心（服务注册/发现/剔除） —— nacos/nacos config
- 网关 —— gateway
- 容错 —— sentinel
- 排错/链路追踪 —— skywalking

![image.png](https://s2.loli.net/2024/08/11/R3bqshMjdSYgXN2.png)

![SpringCloud Alibaba 架构图](https://s2.loli.net/2024/08/10/DIg79TwEkX3bixl.png)

@tab ~~dubbo~~ （不完善）

- 通讯 —— rpc
- 注册中心 —— zookeeper/redis
- 配置中心 —— diamond

@tab ~~Netfix~~ （闭源）

- http restful —— 通信方式
- Netflix Eureka / consul —— 服务发现
- Netflix Ribbon —— 服务负载均衡
- Netflix Hystrix —— 断路器
- Netflix Zuul —— 服务网关
- Spring Cloud Config —— 分布式配置
- sleuth + zipkin —— 分布式追踪系统

::::::

![对比](https://s2.loli.net/2024/08/11/ICRY2FakLbhVy7u.png)

## SpringCloud Alibaba

脚手架： <https://start.aliyun.com/>
