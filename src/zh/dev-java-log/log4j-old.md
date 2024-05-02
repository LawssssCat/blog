---
title: Log4j 日志框架（旧版）
date: 2024-05-01
tag:
  - java
order: 22
---

"最早" 的日志框架

::: warning

该日志框架已处于日落生命周期中。

:::

<!-- more -->

## 直接使用

依赖

```xml title="pom.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-log4j-basic/pom.xml -->
```

配置文件

```properties title="log4j.properties"
<!-- @include: @project/code/demo-java-log/demo-usage-log4j-basic/src/main/resources/log4j.properties -->
```

::: warning

缺少日志配置，所以提示下面信息：

```bash
log4j:WARN No appenders could be found for logger (org.example.InitTest).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
```

:::

::: tabs

@tab 使用例子

```java
<!-- @include: @project/code/demo-java-log/demo-usage-log4j-basic/src/test/java/org/example/InitTest.java -->
```

@tab 输出

```
2024-05-01 17:33:28 DEBUG org.example.InitTest 16:debug
2024-05-01 17:33:28 INFO  org.example.InitTest 17:info
```

:::

## 引入 Slf4j 门面

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>
```
