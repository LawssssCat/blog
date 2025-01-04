---
title: Redis 介绍
date: 2024-04-08
tag:
  - redis
order: 1
---

Redis（Remote Dictionary Server，远程字典服务） 是一个开源（BSD 许可）的使用 ANSI C 语言编写数据库。
Redis 是一种以 `key-value` 的模式存储数据的 nosql 数据库，支持数据在**内存中读写**（读写快）、**定时持久化**和**高可用部署**。

应用场景：

- 缓存、分布式锁、自增序列
- ~~消息中间件~~ （给专业的干：RabbitMQ）

参考：

- <https://www.runoob.com/redis/redis-intro.html>
- <https://mijingdui.github.io/redis/introduce/>

<!-- more -->

## 数据类型

Redis 支持丰富数据类型：
字符串（String） 、哈希表（Hash）、列表（List）、集合（Set）、有序集合（sorted set）、位图、hyperloglogs 等。

如何操作各种数据类型，参考： [link](./usage.md)

## 其他功能

Redis 有内置复制、Lua 脚本、LRU 收回、事务以及不同级别磁盘持久化功能，同时通过 Redis Sentinel 提高可用性、通过 Redis Cluster 提供自动分区功能。

todo

# 附录：

## NoSQL

NoSQL（Not Only SQL，不仅仅是 SQL）泛指非关系型的数据库。（RDBMS，关系型数据库）
NoSQL 不依赖业务逻辑方式存储，而以简单的 `key-value` 模式存储。因此大大的增加了数据库的扩展能力。

应用场景：

- 数据模型非刚性（模型结构允许灵活改变）
- 不需要高度的数据一致性

通过 NoSQL 可以解决的问题：

- 商品数量的频繁查询
- 热搜商品的排行统计
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
其 key 指向多个 “列”。这些列由列家族来安排。

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
