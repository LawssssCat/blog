---
title: SpringCloud微服务架构
---

SpringCloud是Java微服务（microservice）架构的构建标准之一。

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

## 解决方案

组合后能形成完整分布式系统功能的软件选型集合，用于简化Java服务的分布式系统功能开发

- [Spring Cloud Alibaba](./springcloud-alibaba.md) —— 服务快速集成
  - 通讯 —— restful/rpc/dubbo/feign `httpclient("url", params)`/`restTemplate("url", params)`
  - 服务治理/注册中心（服务注册/发现/剔除） —— nacos/nacos config
  - 网关 —— gateway
  - 容错 —— sentinel
  - 排错/链路追踪 —— skywalking

- ~~dubbo~~ （不完善）
  - 通讯 —— rpc
  - 注册中心 —— zookeeper/redis
  - 配置中心 —— diamond/Apollo

- ~~Netfix~~ （闭源）
  - http restful —— 通信方式
  - Netflix Eureka / consul —— 服务发现
  - Netflix Ribbon —— 服务负载均衡
  - Netflix Hystrix —— 断路器
  - Netflix Zuul —— 服务网关
  - Spring Cloud Config —— 分布式配置
  - Admin + sleuth + zipkin —— 分布式追踪系统

![对比](https://s2.loli.net/2024/08/11/ICRY2FakLbhVy7u.png)

## 组件

### 服务管理

- [zookeeper](./zookeeper/README.md) —— 服务注册中心
- [nacos](./nacos.md) —— 服务管理

### 消息中间件

- [kafka](./kafka.md) —— 消息中间件
