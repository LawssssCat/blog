---
title: 使用 XML 工具： xstream
date: 2024-04-14
tag:
  - xml
  - java
  - jaxb
order: 38
---

jdk jaxb 的封装

<!-- more -->

<SiteInfo
  name="XStream User Guide: Converting Objects to XML"
  url="https://www.baeldung.com/xstream-serialize-object-to-xml"
  preview="/assets/images/cover3.jpg"
/>

<RepoLink path="code/demo-java-xml/n13-springoxm-usage/test/java/org/example/" />

## 编程式

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamTest.java -->
```

@tab 测试类（抽象）

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/AbstractXmlXStreamCommonTest.java -->
```

@tab 实体类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/main/java/org/example/entity/raw/Person.java -->
```

:::

## 注解式

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamAnnotationTest.java -->
```

@tab 实体类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/main/java/org/example/entity/anno/Person.java -->
```

:::

## 自定义转换器

::: tabs

@tab 转换器

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/main/java/org/example/converter/SiteConverter.java -->
```

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamConverterTest.java -->
```

:::
