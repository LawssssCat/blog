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

## ~~Funtional Programming~~ （JDK8 已有原生替代）

Functional Programming emphasizes the use of functions to achieve its objectives versus changing state.

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/FunctionalTest.java -->
```

## ~~Collections~~ （JDK8 已有原生替代）

Guava 开始时就是为了处理集合而产生的项目，但现在这些方法已有 JDK8 原生替代方法。

包含方法有：

- FluentIterable
- Range/Lists/Sets/Maps
- Immutable Collections
- Multimaps
- BitMap

参考：

- todo <https://blog.csdn.net/wuyuxing24/article/details/100594173>
- todo <https://blog.csdn.net/pzjtian/article/details/106739606>
- todo <https://blog.csdn.net/zhiwenganyong/article/details/122770670>

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/CollectionsTest.java -->
```

## Cache

In Menory cache 缓存

> alternate
>
> - Apache commons JUC
> - OsCache
> - SwarmCache
> - Ehcache

基本接口

| 接口/类                  | 描述                                                                |
| ------------------------ | ------------------------------------------------------------------- |
| `Cache<K, V>`            | 缓存的核心接口。表示一种能够存储键值对的缓存结构                    |
| `LoadingCache<K, V>`     | 【继承 Cache 接口】 用于在缓存中自动加载缓存项                      |
| `LocalManualCache<K, V>` | 【继承 Cache 接口】 每次取数据时，指定缓存加载方式 （类似 ehcache） |
| `CacheLoader<K, V>`      | 在使用 LoadingCache 时提供加载缓存项的逻辑                          |
| `CacheBuilder`           | 用于创建 Cache 和 LoadingCache 实例的构建器类                       |
| `CacheStats`             | 用于表示缓存的统计信息，如命中次数、命中率、加载次数、存储次数等    |
| `RemovalListener<K, V>`  | 用于监听缓存条目被移除的事件，并在条目被移除时执行相应的操作        |

具体接口说明： <https://blog.csdn.net/JokerLJG/article/details/134596900>

### 原理：LRU 算法

LRU（Least Recently Used，最少最近使用）

todo https://www.bilibili.com/video/BV1R4411s7GX?p=29

todo LinkedHashMap 实现

todo LinkedList 实现

### 原理：引用类型

- StronReference 强引用 —— 只要有引用，就不会被 gc 回收
- SoftReference 软引用 —— 尽管还有引用，但是会被 full gc 回收
- WeakReference 弱引用 —— 尽管还有引用，但是会被 Major gc （仅清理老年代） 和 full gc （清理整个堆） 回收
- PhantomReference 幽灵引用 —— 尽管还有引用，但不管有没有被 gc 回收，都是无法通过引用访问内存内容，**但是可以收到该内存被 gc 回收的通知** | 参考： apache common-io FileCleaningTracker

todo 内存敏感实现

### 例子：get-if-absent-compute

Guava Cache 提供两种实现了 get-if-absent-compute 语义的方式：

> 所谓 get-if-absent-compute 语义：在调用 get 方法时，如果发现指定的值不存在，则通过加载、计算等方式来提供值。也可理解为 lazy load（懒加载、按需加载）。

- `Cache.get(key, Callable)` —— 在调用 get 时，指定一个 Callable，如果值不存在时，调用 Callable 来计算值。计算到值后放入 Cache 中，并返回结果。
- `LoadingCache` —— 定义 Cache 时提供一个 CacheLoader 指定统一的缓存加载方式。

  LoadingCache 与 CacheLoader 的几个方法的调用关系： （CacheLoader 是不保证一定可以加载成功，所以它的所有方法都是有异常的）

  ```
  LoadingCache.get（k） ->  CacheLoader.load(k)
  LoadingCache.refresh(k) ->   CacheLoader.reload(k)
  LoadingCache.getAll(keys) -> CacheLoader.loadAll(keys)
  ```

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/CacheLoaderTest.java -->
```

### 策略：清理、过期

Guava Cache 采用基于容量、Soft 引用、Weak 引用的清理触发条件，基于过期时间、权重来决定哪些数据优先清理，采用 LRU 算法来清理数据。

### 策略：重载 refresh

LoadingCache 中的 refresh 提供了值替换的功能。在调用 refresh 时，会先调加载新值，新值加载到后替换掉老值，并返回老值。如果加载不到新值，老值是被保留的，不会被替换掉的。
