---
title: Redis 介绍
date: 2024-04-08
tag:
  - redis
order: 1
---

Redis（Remote Dictionary Server，远程字典服务） 是一个开源（BSD 许可）的使用 ANSI C 语言编写数据库。
Redis 是一种以 `key-value` 的模式存储数据的 nosql 数据库，支持数据在内存中读写，所以一般用作缓存、分布式锁、自增序列等。

::: note

NoSQL（Not Only SQL，不仅仅是 SQL）泛指非关系型的数据库。
NoSQL 不依赖业务逻辑方式存储，而以简单的 `key-value` 模式存储。因此大大的增加了数据库的扩展能力。

NoSQL 特点：

- 不遵循 SQL 标准
- 不支持 ACID（关系型数据库四大特性）

:::

<!-- more -->

参考：

- <https://www.runoob.com/redis/redis-intro.html>
- <https://mijingdui.github.io/redis/introduce/>

## 数据类型

Redis 支持字符串（String） 、哈希表（Hash）、列表（List）、集合（Set）、有序集合（sorted set）、位图、hyperloglogs 等数据类型。

如何操作各种数据类型，参考： [](./usage.md)

