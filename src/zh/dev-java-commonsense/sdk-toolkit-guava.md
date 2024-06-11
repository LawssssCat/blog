---
title: Guava 使用笔记
date: 2024-06-09
tag:
  - java
order: 10
---

Google Guava 官方教程（中文版）： <https://wizardforcel.gitbooks.io/guava-tutorial/content/1.html>

B 站 | Guava 讲解： https://www.bilibili.com/video/BV1R4411s7GX/

```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>23.0</version>
</dependency>
```

## Utilities

Demonstrate the basic functionalities provided by Guava

### Joiner

Concatenate strings together with a specified delimiter

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/JoinerTest.java -->
```

### Splitter

Produce substrings broken out by the provided delimiter

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/SplitterTest.java -->
```

### Preconditions

Methods for asserting certain conditions you expect variables

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/PreconditionsTest.java -->
```

## Objects

alternate:

- lombok
- ~~<https://commonclipse.sourceforge.net>~~

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/PreconditionsTest.java -->
```
