---
title: 统一主键生成器
date: 2024-09-14
tag:
  - java
  - jdbc
order: 66
---

## UUID

问题：

- 非自增 —— 不方便数据库索引（插入时，B 树频繁重建） 【主要问题】
- 长度大 【非主要问题】
- 有概率冲突 【非主要问题】

## 数据库自增

- todo Mybatis 主键生成

问题：

- 分库分表场景可能导致重复 ID

### 思路： 在唯一的表中统一管理主键

一致性： 在唯一的表中统一管理主键

```sql
BEGIN;
REPLACE INTO test_order_id (stub) VALUES('b');
SELECT LAST_INSERT_ID();
COMMIT;
```

高并发： 按资源数量设置增量

```
1,3,5,7,9,...
2,4,6,8,10,...
```

可扩展： 问题

### 思路： 美团 Leaf

github: Meituan-Dianping/Leaf

分布式服务，每个服务一次拿 1000 个主键

## Redis

todo

## 雪花算法（snowflake）

Snowflake（雪花算法）是由 Twitter 开源的分布式 ID 生成算法，以划分命名空间的方式将 64-bit 位分割成多个部分，每个部分代表不同的含义。

::: tip
Java 中 64bit 的整数是 Long 类型，所以在 Java 中用 long 来存储雪花算法生成的 ID。
:::

```
1bit，表示正负             41bit，时间戳            10bit，工作机器id   12bit，序列号
|                              |                        |              |
0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000000 00 - 00000000 0000
```

问题：

- 时间回拨问题处理

实现参考：

- UniversalIdGenerator
  - https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Framework/Generic/src/main/java/ghidra/util/UniversalIdGenerator.java
- IdWorker
  - https://github.com/zzxadi/Snowflake-IdWorker/blob/master/IdWorker.java
  - https://github.com/sail-y/spring-boot-admin/blob/master/src/main/java/com/dmc/util/id/IdWorker.java
  - https://github.com/sumory/uc/blob/master/src/com/sumory/uc/id/IdWorker.java
