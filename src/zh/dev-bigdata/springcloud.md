---
title: SpringCloud微服务架构
---

SpringCloud是Java微服务（microservice）架构的构建标准之一。
SpringCloud为开发人员提供了一些工具用来快速构建分布式系统中的一些常见模式和解决一些常见问题（例如配置管理、服务发现、断路器、智能路由、微代理、控制总线、一次性令牌、全局锁、领导选举、分布式会话、集群状态）。分布式系统的协调导致了很多样板式的代码，使用SpringCloud开发人员可以快速建立实现这些模式的服务和应用程序。

标准中有如下元素：

```bash
API Gateway —— 接口网关
Service Registry —— 服务注册
Config Server —— 配置中心
Distributed Tracing —— 分布式链路追踪
Microservices —— 微服务
```

> 参考：
>
> - 微服务论文 - <https://martinfowler.com/articles/microservices.html> （[link_翻译](http://blog.cuicc.com/blog/2015/07/22/microservices)）
> - SpringCloud 官方文档 - <https://sca.aliyun.com/en/docs/2022/overview/what-is-sca/>
> - SpringCloud 介绍 - <https://www.cnblogs.com/qdhxhz/p/14563991.html>

<!-- more -->

## 功能：服务注册和发现

Spring Cloud 提供了多种服务注册与发现的实现方式，例如：Eureka、Consul，Zookeeper。

- 服务注册 —— 将服务所在的主机、端口、版本号、通信协议等信息登记到注册中心上。
- 服务发现 —— 向注册中心请求已经登记的服务列表，获得某个服务的主机、端口、版本号、通信协议等信息，从而实现对具体服务的调用。

服务 | C（一致性）、A（可用性）、P（分区容错性） | 说明
--- | --- | ---
Zookeeper | CP | ZK选举期间不可用
Eureka | AP | Eureka各节点平等，只要一台Eureka还在就能保证注册服务可用，只不过查到的信息可能不是最新的

## 功能：负载均衡

Spring Cloud 提供了一套客户端负载均衡器，例如：Ribbon、Feign（封装Ribbon调用）。

## 功能：断路器/熔断器

熔断器（类似在电路上保护线路过载如短路时能够及时切断故障电路，防止发生过载、发热火灾等后果）概念来源微服务之父 Martin Fowler 的论文 CircuitBreaker 一文，当某个服务单元发生故障会向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，从而避免故障在分布式系统中的蔓延乃至雪崩。

Spring Cloud 提供了一套熔断隔离组件，例如：Hystrix。

- 熔断降级 —— 如接口响应超时（1s）、接口报错等时降级
- 请求限流

  > 限流方法有很多种：
  > 组件 | 方法
  > --- | ---
  > Nginx | 略
  > Redis + Lua | 略
  > Sentinel | 略
  > 基于限流算法自己实现 | 令牌桶、漏桶算法
  > hystrix | 基于线程数、信号量

## 功能：服务网关/路由

对外提供服务，需要实现请求路由、负载均衡、权限验证等功能。

组件 | 说明
--- | ---
Nginx
LVS
HAProxy
Zuul | 路由、过滤

## 功能：配置中心

功能：
配置集中管理、配置加解密、配置动态更新

配置方式 | 对比
--- | ---
传统 | 分散各系统、配置文件、代码中
集中配置中心 | 将系统配置信息作为一个服务模块，进行集中统一管理
分布式配置中心 | 分布式，独立管理

组件 | 说明
--- | ---
Apollo（阿波罗） | 携程的分布式配置中心
ldiamond | 淘宝的分布式配置中心
XDiamond
Qconf | 奇虎360的分布式配置中心
Disconf | 百度的分布式配置中心
Spring Cloud Config | 过渡到Nacos/Consul组件

## 功能：分布式链路跟踪/服务与服务间的调用

Spring Cloud 提供了分布式链路跟踪的解决方案，例如 Spring Cloud Sleuth。

解决问题：

- 串联整个调用链路，快速定位问题
- 理清微服务之间的依赖关系
- 分析微服务之间的性能情况

组件 | 作用 | 说明
--- | --- | ---
Spring Cloud Sleuth | 采集数据 | 借用 Google Dapper、Twitter Zipkin、Apache HTrace的设计。
[Zipkin](https://zipkin.io) | 呈现数据 | 由Twitter开源的分布式实时数据跟踪系统（Distributed Tracking System），基于Google Dapper的论文设计形成。 《Dapper, a Large-Scale Distributed Systems Tracing Infrastructure》
Pinpoint | APM（应用性能管理） | 韩国Naver提供
Apache Htrace | APM（应用性能管理） |
EagleEye（鹰眼） | APM（应用性能管理） | 阿里巴巴提供

概念： <https://cloud.spring.io/spring-cloud-static/Greenwich.SR3/single/spring-cloud.html#_spring_cloud_sleuth>

- 跟踪（trace） —— 一个请求在分布式系统中穿透所有相关微服务节点的有向无环图（DAG，Directed Acyclic Grap）。
- 跨度（span） —— 整个trace中的某一段，一个trace由多个span组成，是trace的“基本工作单元”。它代表了一个服务内部具有边界的、具体的操作阶段（如一次 RPC 调用、一次数据库查询、一次本地方法执行）。
- 标注/事件（annotation） —— 一个span中的关键事件点。
  有如下基本标准的时间戳事件：
  - CS（Client Sent，客户端发送）
  - SR（Server Received，服务端接收）
  - SS（Server Sent，服务端发送）
  - CR（Client Received，客户端接收）

```bash
[Trace] 整个分布式调用的生命周期
   ├── [Span A] 网关接收请求并处理
   └── [Span B] 服务A 通过 RPC/HTTP 调用 服务B (产生 RPC Span)
          ├── [Annotation: CS] -> 客户端发送
          ├── [Annotation: SR] -> 服务端接收
          ├── [Annotation: SS] -> 服务端发送
          └── [Annotation: CR] -> 客户端接收
```

