---
title: Spring Boot + Redis 数据库
shortTitle: 整合 Redis 数据库
date: 2024-04-06
tag:
  - springboot
  - redis
---

整合 Spring Boot/[Redis](/zh/ops-db-redis/README.md) 用来简单开发、测试。

<!-- more -->

### Redis 的 Java 客户端

SpringBoot 整合 Redis 一般有两种方式，分别是 Jedis 和 RedisTemplate。

两者区别：

- Jedis 是 Redis 官方推荐的面向 Java 的操作 Redis 的客户端

  ```java
  Jedis jedis = new Jedis("localhost", 6379); // 建立连接
  try {
    jedis.set("name", "steven");
    System.out.println(jedis.get("name"));
  } finally {
    jedis.close(); // 销毁连接
  }
  ```

- [Spring Data Redis](https://spring.io/projects/spring-data-redis) 中对 JedisApi 的高度封装 （SpringBoot 推荐使用这种）

  优点：

  - 可以方便地更换 Redis 的 Java 客户端，如 jedis,redission,lettuce,...
  - 比 Jedis 多了自动管理连接池的特性（不用频繁的手动的创建/销毁 redis 连接）
  - 提供了模板类 RedisTemplate 执行各种 Redis 操作、异常转换和序列化支持提供高级抽象
  - 方便与其他 Spring 框架进行搭配使用如：
    - Repository 接口的自动实现 `@EnableRedisRepositories`
    - SpringCache
    - 发布订阅支持，用于消息驱动的 POJO 的 MessageListenerContainer

## SpringBoot + Jedis

代码： <RepoLink path="/code/demo-springboot-redis/demo-jedis" />

具体分为：添加依赖 + redis 配置信息 + JedisPool 工厂 + RedisService（包含序列化）

```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.7.0</version>
</dependency>
```

## SpringBoot + Spring Data Redis

代码： <RepoLink path="/code/demo-springboot-redis/demo-springdataredis" />

