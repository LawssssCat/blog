---
title: Slf4j + Logback 日志框架
date: 2024-05-01
tag:
  - java
order: 34
---

目前（2024 年 5 月 2 日）主流日志框架之一。

<!-- more -->

## 例子：Slf4j 适配 Logback

依赖

```xml title="pom.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-logback/pom.xml -->
```

配置文件

```properties title="logback.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-logback/src/main/resources/logback.xml -->
```

::: info

logback 不像 log4j 那样必须要有配置文件，它配置了默认的输出方式。

:::

使用例子

::: tabs

@tab 测试代码

```java
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-logback/src/test/java/org/example/InitTest.java -->
```

@tab 输出（默认格式）

```
17:54:52.524 [main] DEBUG org.example.InitTest - debug
17:54:52.527 [main] INFO org.example.InitTest - info
17:54:52.527 [main] WARN org.example.InitTest - warn
17:54:52.527 [main] ERROR org.example.InitTest - error
```

@tab 输出（关联配置格式）

```
2024-05-01 18:14:19.321 [main] DEBUG org.example.InitTest 12: debug
2024-05-01 18:14:19.324 [main] INFO  org.example.InitTest 13: info
2024-05-01 18:14:19.325 [main] WARN  org.example.InitTest 14: warn
2024-05-01 18:14:19.325 [main] ERROR org.example.InitTest 15: error
```

:::

## 异步日志

```xml
<appender class="ch.qos.logback.classic.AsyncAppender" >
    <appender-ref ref="stdout" />
</appender>
```

## 例子：Slf4j 桥接 Log4j 到 Logback

统一日志输出框架、日志输出格式

首先按上面步骤配置好 logback

::: tabs

@tab 依赖引入

```xml title="pom.xml"
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
</dependency>
```

@tab 日志框架配置

```properties title="logback.xml"
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-logback-over-log4j/src/main/resources/logback.xml -->
```

:::

然后引入桥接依赖

```xml title="pom.xml"
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>log4j-over-slf4j</artifactId>
</dependency>
```

然后==排除三方件中的原日志框架依赖==（~~或者将桥接依赖放在三方依赖前~~ - maven 优先级）

```xml title="pom.xml"
<!-- 三方件 -->
<dependency>
    <groupId>org.example</groupId>
    <artifactId>demo-usage-log4j-basic</artifactId>
    <version>1.0-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

使用例子

::: tabs

@tab 测试代码

```java
<!-- @include: @project/code/demo-java-log/demo-usage-slf4j-logback/src/test/java/org/example/InitTest.java -->
```

@tab 桥接前

```
OVER | 2024-05-01 19:43:00.644 [main] INFO  org.example.OverTest 11: ---- over start ----
2024-05-01 19:43:00 DEBUG    org.example.Alpha 9:Alpha doSomething debug
2024-05-01 19:43:00 INFO     org.example.Alpha 10:Alpha doSomething info
2024-05-01 19:43:00 WARN     org.example.Alpha 11:Alpha doSomething warn
2024-05-01 19:43:00 ERROR    org.example.Alpha 12:Alpha doSomething error
OVER | 2024-05-01 19:43:00.695 [main] INFO  org.example.OverTest 14: ---- over end ----
```

日志格式不一致

@tab 桥接后

```
OVER | 2024-05-01 22:06:51.748 [main] INFO  org.example.OverTest 11: ---- over start ----
OVER | 2024-05-01 22:06:51.756 [main] INFO  org.example.Alpha 10: Alpha doSomething info
OVER | 2024-05-01 22:06:51.756 [main] WARN  org.example.Alpha 11: Alpha doSomething warn
OVER | 2024-05-01 22:06:51.756 [main] ERROR org.example.Alpha 12: Alpha doSomething error
OVER | 2024-05-01 22:06:51.756 [main] INFO  org.example.OverTest 14: ---- over end ----
```

日志格式一致

:::
