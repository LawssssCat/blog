---
title: 使用 XML 工具： jaxb
date: 2024-04-15
tag:
  - xml
  - java
  - jaxb
order: 33
---

介绍 Java 原生 oxm 工具 jaxb 的使用。

<!-- more -->

<SiteInfo
  name="JAXB 教程"
  url="https://www.baeldung.com/jaxb"
  preview="/assets/images/cover3.jpg"
/>

<RepoLink path="/code/demo-java-xml/n11-digester-usage/test/java/org/example/" />

读

::: tabs

@tab XML 文件

```xml
<!-- @include: @project/code/demo-java-xml/test-resources/message001.xml -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-xml/n11-digester-usage/src/test/java/org/example/XmlJaxbReadTest.java -->
```

:::

写

```java
<!-- @include: @project/code/demo-java-xml/n11-digester-usage/src/test/java/org/example/XmlJaxbWriteTest.java -->
```

## maven 插件 jaxb2-maven-plugin

`jaxb2-maven-plugin` 是一个用来代替 xjc 和 schemagen 用来完成 java 类和 schema 文件之间互相转换的 maven 插件。

官方地址：<https://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.2/> （⭐⭐⭐） \
github：<https://github.com/mojohaus/jaxb2-maven-plugin> \

```bash
mvn jaxb2:schemagen  —— 从 Java 类生成 schema
mvn jaxb2:xjc        —— 从 schema 文件生成 Java 类
```

例子： <RepoLink path="/code/demo-java-maven/plugin-usage-jaxb2-maven-plugin/" />

::: tabs

@tab 依赖和插件配置

```xml title="pom.xml"
<!-- @include: @project/code/demo-java-maven/plugin-usage-jaxb2-maven-plugin/pom.xml -->
```

@tab Java 类（源）

```java
<!-- @include: @project/code/demo-java-maven/plugin-usage-jaxb2-maven-plugin/src/main/java/org/example/entity/Person.java -->
```

@tab Schema 文件（生成）

```xml title="schema1.xml"
<!-- @include: @project/code/demo-java-maven/plugin-usage-jaxb2-maven-plugin/src/main/jaxb2/schemagen/schema1.xsd -->
```

@tab Java 类（生成）

```java
<!-- @include: @project/code/demo-java-maven/plugin-usage-jaxb2-maven-plugin/src/main/java/org/example/gen/entity/Person.java -->
```

:::

## maven 插件 maven-jaxb2-plugin

maven 插件 maven-jaxb2-plugin 实现了基于 xsd 配置文件生成对应的 Java 类的功能。
在配置各种模型，属性很多的时候，生成相关文件非常方便。

Github： <https://github.com/highsource/jaxb-tools?tab=readme-ov-file#jaxb-maven-plugin>

```xml
<plugin>
  <groupId>org.jvnet.jaxb2.maven2</groupId>
  <artifactId>maven-jaxb2-plugin</artifactId>
  <version>0.14.0</version>
  <executions>
    <execution>
      <id>generate</id>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <schemaDirectory>src/main/resources/tmsad</schemaDirectory>
        <schemaIncludes>
          <include>*.xsd</include>
        </schemaIncludes>
      </configuration>
    </execution>
  </executions>
</plugin>
```
