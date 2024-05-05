---
title: 使用 XML 工具： spring-oxm
date: 2024-04-14
tag:
  - xml
  - java
  - spring
order: 44
---

spring 为 jaxb, jibx, xstream 这些封装提供了统一的接口。

<!-- more -->

<RepoLink path="code/demo-java-xml/n13-springoxm-usage/test/java/org/example/" />

::: tabs

@tab 测试

```java
<!-- @include: @project/code/demo-java-xml/n13-springoxm-usage/src/test/java/org/example/OxmTest.java -->
```

@tab 实体类

```java
<!-- @include: @project/code/demo-java-xml/n13-springoxm-usage/src/main/java/org/example/Person.java -->
```

@tab 输出

```xml
<org.example.Person><id>111</id><name>222</name><age>333</age></org.example.Person>
```

:::
