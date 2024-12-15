---
title: Redis 基本使用
date: 2024-04-08
tag:
  - redis
order: 2
---

## 客户端

- RedisInsight 🔥
- Another Redis Desktop Manager

## 编译安装

todo

## Docker 部署

todo

## 操作数据类型

### 字符串（string）

::: warning
最大存储 512MB
:::

```bash
redis 127.0.0.1:6379> SET runoobkey redis
OK
redis 127.0.0.1:6379> GET runoobkey
"redis"
```

### 哈希表（Hash）

### 链表（list）

```bash
redis 127.0.0.1:6379> LPUSH runoobkey redis
(integer) 1
redis 127.0.0.1:6379> LPUSH runoobkey mongodb
(integer) 2
redis 127.0.0.1:6379> LPUSH runoobkey mysql
(integer) 3
redis 127.0.0.1:6379> LRANGE runoobkey 0 10

1) "mysql"
2) "mongodb"
3) "redis"
```

### 集合（set）

```bash
redis 127.0.0.1:6379> SADD runoobkey redis
(integer) 1
redis 127.0.0.1:6379> SADD runoobkey mongodb
(integer) 1
redis 127.0.0.1:6379> SADD runoobkey mysql
(integer) 1
redis 127.0.0.1:6379> SADD runoobkey mysql
(integer) 0
redis 127.0.0.1:6379> SMEMBERS runoobkey

1) "mysql"
2) "mongodb"
3) "redis"
```

### 有序集合（zset/sorted set）

todo
