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

Spring Cloud 提供了一套客户端负载均衡器，例如：Ribbon。

