---
title: 使用 XML 工具： dom4j
date: 2024-04-14
tag:
  - xml
  - java
order: 25
---

开源三方件，对原生 java xml 读写做了封装。

<!-- more -->

<RepoLink path="/code/demo-java-xml/n02-dom4j-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n02-dom4j-usage/src/test/java/org/example/XmlDom4jReadTest.java -->
```

:::

写

::: tabs

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n02-dom4j-usage/src/test/java/org/example/XmlDom4jWriteTest.java -->
```

:::
