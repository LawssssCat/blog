---
title: Redis 介绍
date: 2024-04-08
tag:
  - redis
order: 1
---

[Redis](https://redis.io/)（Remote Dictionary Server，远程字典服务） 是一个开源（BSD 许可）的使用 ANSI C 语言编写数据库。
Redis 是一种以 `key-value` 的模式存储数据的 nosql 数据库，支持数据**原子操作**（分布式前提）、**在内存中读写**（读写快）、**定时持久化**和**高可用部署**。

应用场景：

- 缓存（文字/二进制，如图片）
- 分布式锁
- 自增序列/计数器
- ~~消息中间件~~ （给专业的干：RabbitMQ）

参考：

- <https://www.runoob.com/redis/redis-intro.html>
- <https://mijingdui.github.io/redis/introduce/> 阿星 精神时の屋
- <https://www.bilibili.com/video/BV1vWsDedEU8/> 内存数据库 Redis7 教程

<!-- more -->

- [编译/安装/部署](./deployment.md)
- 客户端

  - [Another Redis Desktop Manager](https://github.com/qishibo/AnotherRedisDesktopManager) —— electron/js 编写
  - RedisInsight —— electron/ts/js 编写，官方推荐，但 star 相对前者少。

- [基本使用](./usage.md) —— 常用 cli 命令、数据类型
- [配置、部署策略、分布式问题方案](./config.md)

被集成：

- [Java](/zh/dev-java-spring/springboot/springboot-redis.md)

# 附录：

## NoSQL

NoSQL（Not Only SQL，不仅仅是 SQL）泛指非关系型的数据库。（RDBMS，关系型数据库）
NoSQL 不依赖业务逻辑方式存储，而以简单的 `key-value` 模式存储。因此大大的增加了数据库的扩展能力。

应用场景：

- 数据模型非刚性（模型结构允许灵活改变）
- 不需要高度的数据一致性

通过 NoSQL 可以解决的问题：

- High performance - 对数据库高并发读写的需求
  - 商品数量的频繁查询
- Huge Storage - 对海量数据的高效率存储和访问的需求
  - 热搜商品的排行统计
- High Scalability && High Availability - 对数据库的高可扩展性和高可用性的需求
  - 订单超时问题
  - 音频、视频存储问题

### NoSQL 实现分类

#### 键值（KV）存储数据库

这一类数据库主要用到一个 hash 表，这个表中存有一系列 key 和 value 值。
操作这类数据库，就是存取 key 中的 value 值。

相关产品：

- Tokyo Cabinet/Tyrant
- Redis —— 数据存内存
- SSDB —— 数据存磁盘
- Voldemort
- Oracle BDB

- memechache

#### 列存储数据库

这部分数据库通常是用来因对分布式存储的海量数据。
列簇式存储（将同一列数据存在一起）：其 key 指向多个 “列”。这些列由列家族来安排。

相关产品：

- Cassandra
- HBase
- Riak

#### 文档型数据库

该类型的数据模型是版本化的文档，半结构化的文档已特定的格式存储，比如 JSON。

```json
{ "id": "21", "name": "张三", "age": "23", "tags": ["帅哥", "学霸"] }
```

相关产品：

- MongoDB
- CouchDB
- MongoDB（4.x）
- SequoiaDB （国产，开源）

#### 图形（Graph）数据库

相关产品：

- Neo4J
- InfoGrid
- Infinite Graph

### NoSQL 问题

- 不遵循 SQL 标准
- 不支持 ACID（关系型数据库四大特性）

todo 是否影响？是否要解决？如何解决？
