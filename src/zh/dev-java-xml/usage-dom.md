---
title: 使用 XML 工具： dom
date: 2024-04-14
tag:
  - xml
  - java
order: 20
---

介绍 java 原始的、不经过封装的 xml 操作方式。

<!-- more -->

Java 中遵循 w3c（World Wide Web Consortium） 标准实现了各种 dom（Document Object Model，在 `org.w3c.dom` 包下） 类来抽象描述 XML（Extensible Markup Langurage） 文件。
Java 提供了 `javax.xml` 包下的各种工具来操作这些 dom 类。

<RepoLink path="/code/demo-java-xml/n01-original-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n01-original-usage/src/test/java/org/example/XmlOriginalReadTest.java -->
```

:::

写

::: tabs

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n01-original-usage/src/test/java/org/example/XmlOriginalWriteTest.java -->
```

:::
