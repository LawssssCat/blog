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

## Basic

补充 JDK 基本功能

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

### Strings

字符串处理

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/StringsTest.java -->
```

### Preconditions

预校验

Methods for asserting certain conditions you expect variables

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/PreconditionsTest.java -->
```

### Objects

alternate:

- lombok
- ~~<https://commonclipse.sourceforge.net>~~

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/PreconditionsTest.java -->
```

## Cache

In Menory 缓存

alternate

- Apache commons JUC
- OsCache
- SwarmCache
- Ehcache

### 原理：LRU 算法

LRU（Least Recently Used，最少最近使用）

todo https://www.bilibili.com/video/BV1R4411s7GX?p=29

### 原理：引用类型

- StronReference 强引用 —— 只要有引用，就不会被 gc 回收
- SoftReference 软引用 —— 尽管还有引用，但是会被 full gc 回收
- WeakReference 弱引用 —— 尽管还有引用，但是会被 Major gc （仅清理老年代） 和 full gc （清理整个堆） 回收
- PhantomReference 幽灵引用 —— 尽管还有引用，但不管有没有被 gc 回收，都是无法通过引用访问内存内容，**但是可以收到该内存被 gc 回收的通知** | 参考： apache common-io FileCleaningTracker

### 例子

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/CacheLoaderTest.java -->
```
