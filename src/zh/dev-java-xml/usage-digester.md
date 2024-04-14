---
title: 使用 XML 工具： digester
date: 2024-04-14
tag:
  - xml
  - java
  - jaxb
  - apache
order: 33
---

Digester 最初是 Struct MVC 框架的一个模块，后加入到 Apache Commons 组件库中，形成了一个功能强悍的 XML 解析库。

<!-- more -->

Digester 底层基于 SAX + 事件驱动 + 栈的方式实现的，这三个技术作用如下：

- SAX —— 解析 XML
- 事件驱动 —— 在 SAX 解析的过程中加入事件来了支持对象映射
- 栈 —— 解析 XML 元素的开始和结束时，需要通过 XML 元素映射的类对象入栈和出栈来完成事件的调用

Digester 解析 XML 的核心流程：

1. 开始解析节点，是否需要创建一个类
1. 开始解析节点，是否需要入栈操作
1. 结束解析节点，是否需要执行某个方法
1. 结束解析节点，是否需要出栈操作

<RepoLink path="/code/demo-java-xml/n11-digester-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n11-digester-usage/src/test/java/org/example/XmlDigesterReadTest.java -->
```

:::
