---
title: 使用 XML 工具： jdom
date: 2024-04-14
tag:
  - xml
  - java
  - jaxp
order: 25
---

JDOM 是一种解析 XML 的 Java 工具包。
JDOM 是 Breet Mclaughlin 和 Jason Hunter 两大 Java 高手的创作成果，2000 年初，JDOM 作为一个开放源代码项目正式开始研发。

<!-- more -->

<RepoLink path="/code/demo-java-xml/n03-jdom-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n03-jdom-usage/src/test/java/example/XmlJDomReadTest.java -->
```

:::

写

::: tabs

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n03-jdom-usage/src/test/java/example/XmlJDomWriteTest.java -->
```

:::

---

参考：

- https://www.cnblogs.com/wordless/p/16208704.html
