---
title: 通用 Mapper 使用
date: 2024-09-14
tag:
  - java
  - jdbc
  - mybatis
order: 66
---

官网：https://github.com/abel533/Mapper

Mybatis 插件，辅助 SQL 语句动态生成

## 整合

::: tabs

@tab 引入依赖

```xml
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper</artifactId>
    <version>5.0.0</version>
</dependency>
```

@tab 更新配置

```java
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("org.example.**.mapper") // org.mybatis -> tk.mybatis
```

:::

## 功能

### 基础增删改查

todo

### QBC 查询

Query By Criteria

Criteria 是 Criterion 的复数形式。意思是：规则、标准、准则。在 SQL 语句中相当于查询条件。

QBC 查询是将查询条件通过 Java 对象进行模块化封装。

## 扩展

### mapper-extra

```xml
<dependency>
     <groupId>tk.mybatis</groupId>
     <artifactId>mapper-spring-boot-starter</artifactId>
     <version>4.2.3</version>
</dependency>
<dependency>
     <groupId>tk.mybatis</groupId>
     <artifactId>mapper-extra</artifactId>
     <version>4.2.3</version>
</dependency>
```

批量更新
