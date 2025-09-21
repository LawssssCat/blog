---
title: Spring SpEL 介绍
date: 2024-07-16
tag:
  - spring
order: 99
---


## 原理

处理 SpEL 的过程：

- `SpelExpressionParser.parseExpression()`
  - 阶段一：词法分析 —— 字符串分析（根据不同的计算模式有不同的拆分形式）with `Tokenizer` 分词器
  - 阶段二：语义执行 —— 分析语法，生成语法树，把对于内容添加到指定位置 with `SpelNodeImpl` 语法树节点
- `Expression.getValue(EvaluationContext context)`
  - 阶段三：计算结果 —— 通过式上下文配置（根对象的数据、变量、函数等等），处理表达式中的占位符

## 基本使用

### 语法

```java
<!-- @include: @project/code/demo-java-spring/demo-SpEL/src/test/java/org/example/spel/SimpleTest01Syntax.java -->
```

### Spring 配置

```java
<!-- @include: @project/code/demo-java-spring/demo-SpEL/src/test/java/org/example/spel/SimpleTest02Spring.java -->
```
