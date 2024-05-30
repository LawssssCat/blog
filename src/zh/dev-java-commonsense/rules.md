---
title: Java 常用约定
date: 2024-05-31
tag:
  - java
order: 1
---

## 命名约定

- post body 请求参数 —— XxxRequest/XxxParam
- 展示 —— XxxVo
- 数据 —— XxxPo/XxxDo/Xxx
- 数据传输/数据组合 —— XxxDto
- es 实体 —— XxxIndexcDo
- mongo 实体 —— XxxDoc
- service —— XxxService/XxxServiceImpl
- service 中负责组合业务处理 —— XxxManager
- 持久化组合（同时包含 db/es/redis 等多种存储形式） —— XxxRepository
- 持久化 —— XxxMapper
