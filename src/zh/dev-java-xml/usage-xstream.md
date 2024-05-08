---
title: 使用 XML 工具： xstream
date: 2024-04-14
tag:
  - xml
  - java
  - jaxb
order: 38
---

Xstream 是一种 OXMapping 技术，是用来处理 XML 文件序列化的框架。

JDK jaxb 的封装。

<!-- more -->

<SiteInfo
  name="XStream User Guide: Converting Objects to XML"
  url="https://www.baeldung.com/xstream-serialize-object-to-xml"
  preview="/assets/images/cover3.jpg"
/>

<SiteInfo
  name="XStream 教程"
  url="https://www.yiibai.com/xstream/"
  preview="/assets/images/cover3.jpg"
/>

官网： <https://x-stream.github.io/tutorial.html>

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

## 转换 JSON

添加依赖

```xml title="pom.xml"
<dependency>
    <groupId>org.codehaus.jettison</groupId>
    <artifactId>jettison</artifactId>
    <version>1.5.4</version>
</dependency>
```

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamJsonTest.java -->
```

@tab 输出

```json
{
  "person": {
    "@age": 18,
    "姓名": "steven",
    "sites": [
      {
        "site": [
          { "id": 111, "url": "http://n1.example.org" },
          { "id": 222, "url": "https://n2.example.org" }
        ]
      }
    ]
  }
}
```

:::

## 对象流

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamObjectIOTest.java -->
```

@tab 输出

```xml
<object-stream>
  <person age="18">
    <姓名>steven</姓名>
    <sites>
      <site>
        <id>111</id>
        <url>http://n1.example.org</url>
      </site>
      <site>
        <id>222</id>
        <url>https://n2.example.org</url>
      </site>
    </sites>
  </person>
  <person age="18">
    <姓名>steven</姓名>
    <sites>
      <site>
        <id>111</id>
        <url>http://n1.example.org</url>
      </site>
      <site>
        <id>222</id>
        <url>https://n2.example.org</url>
      </site>
    </sites>
  </person>
  <string>hello world!</string>
  <int>1024</int>
</object-stream>
```

:::

## 对象持久化

持久化后，会生成 `int@0.xml、int@1.xml、int@2.xml` 这样的文件。这些文件内容就是数组里的内容。

::: tabs

@tab 测试类

```java
<!-- @include: @project/code/demo-java-xml/n12-xstream-usage/src/test/java/example/XmlXStreamObjectPersistentTest.java -->
```

:::
