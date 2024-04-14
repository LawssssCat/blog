---
title: 使用 XML 工具： sax
date: 2024-04-14
tag:
  - xml
  - java
  - jaxp
order: 21
---

介绍使用 JDK 自带的 SAX（Simple API for XML） 解析器处理 XML 文件。

::: info
事件驱动, 流式操作
:::

<!-- more -->

<RepoLink path="/code/demo-java-xml/n01-original-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n01-original-usage/src/test/java/org/example/XmlSaxReadTest.java -->
```

:::
