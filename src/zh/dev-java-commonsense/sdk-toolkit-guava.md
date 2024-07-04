---
title: Guava 使用笔记
date: 2024-06-09
tag:
  - java
order: 10
---

Guava 是 Google 团队开源的一款 Java 核心增强库，最初名叫 “`google-collections`”，专门做集合工具类功能，如今包含集合、并发原语、缓存、IO、反射等工具功能，性能和稳定性上都有保障，应用十分广泛。

```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>23.0</version>
</dependency>
```

<!-- more -->

> 参考：
>
> - Google Guava 官方教程（中文版）： <https://wizardforcel.gitbooks.io/guava-tutorial/content/1.html>
> - 《Getting Started with Google Guava》 by Bill Bejeck
> - B 站 | Guava 讲解： https://www.bilibili.com/video/BV1R4411s7GX/

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

## Collections （【部分】JDK8 已有原生替代）

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

### Collections

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/CollectionsTest.java -->
```

### Maps/MultiMap 💡

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/MapsTest.java -->
```

### Table 💡

- ArrayTable
- TreeBaseTable
- HashBaseTable
- ImmutableTable

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/TableTest.java -->
```

### Range

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/RangeTest.java -->
```

### ImmutableXxx

- ImmutableCollections
- ImmutableMaps

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/ImmutableXxxTest.java -->
```

### Ording

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/OrderingTest.java -->
```

## IO

::: info
alternate:

- apache common-io （推荐）

:::

### Files 💡

Files —— The Files class offers serveral helpful methods for working with the File objects.

提供了方便的遍历方法

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/io/FilesTest.java -->
```

### XxxSource/XxxSink

- 字节
  - CharSource —— 字节读
  - CharSink —— 字节写
- 字符
  - ByteSource —— A ByteSource class represents a readable source of bytes.
  - ByteSink —— A ByteSink class represents a writable source of bytes.

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/io/XxxSourceAndSinkTest.java -->
```

### XxxStreams

- CharStreams
- ByteStreams

todo https://www.bilibili.com/video/BV1R4411s7GX?p=13

### Closer ❌JDK 替代

Closer —— The Closer class in Guava is used to ensure that all the registered Closeable objects.

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/io/CloserTest.java -->
```

### BaseEncoding

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/io/BaseEncodingTest.java -->
```

## Cache

In Menory cache 缓存

Guava Cache 支持很多特性：

- 基于 LRU 算法实现
- 支持最大容量限制
- 支持两种过期删除策略（插入时间、访问时间）
- 支持简单的统计功能

> alternate
>
> - Apache commons JUC
> - OsCache
> - SwarmCache
> - Ehcache

::: tabs

@tab 依赖

```xml
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>18.0</version>
</dependency>
```

@tab Demo

```java
public class GuavaCacheTest {
​
    public static void main(String[] args) throws Exception {
        //创建guava cache
        Cache<String, String> loadingCache = CacheBuilder.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(17, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        String key = "key";
        // 往缓存写数据
        loadingCache.put(key, "v");
​
        // 获取value的值，如果key不存在，调用collable方法获取value值加载到key中再返回
        String value = loadingCache.get(key, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getValueFromDB(key);
            }
        });
​
        // 删除key
        loadingCache.invalidate(key);
    }
​
    private static String getValueFromDB(String key) {
        return "v";
    }
}
```

:::

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

### 淘汰策略

由于数据量有限制，缓存的数据可能会由于新的数据进入，而 “淘汰” 旧的数据。

Guava cache 基于缓存的 “数量” 或者 “权重” 来触发淘汰事件，基于 LRU 算法来决定哪些数据优先被 “淘汰”。

相应配置：

- maximumSize —— 基于数量淘汰
- maximumWeight + weigher —— 基于权重淘汰

### 过期策略

由于数据时效性，缓存的数据可能存在 “过期”。

相应配置：

- expireAfterWrite —— 写后过期
- expireAfterAccess —— 读后过期（坑：一直读，则一直不过期）

### 刷新策略/重载策略

相应配置：

- refreshAfterWrite

所谓刷新策略，是指缓存数据多久后要重新到数据库拉取数据，需要与过期策略进行区分。

::: tip

区别 refresh 和 expire 细节：

- expire —— 对应的 key 过期后，第一个读 key 的线程负责读取新值，其他读相同 key 的线程阻塞
  - 问题：高并发场景下，可能有大量线程阻塞
- refresh —— 对应的 key 过期后，第一个读取 key 的线程负责读取新值，其他读相同 key 的线程返回旧值

:::

为了提高性能，可以考虑：

1. 配置 refresh < expire，以减少线程阻塞概率
1. 采用**异步刷新策略**（线程异步加载数据，期间所有请求返回旧的缓存值），防止缓存雪崩

#### 异步刷新配置

参考： <https://www.bilibili.com/video/BV1fG411q7Gv/>

默认情况下，Guava Cache 并没有后台任务线程定时地、主动地调用 load 方法来拉取数据，而是在数据请求时才执行数据拉取操作。

但是，刷新策略提供了异步主动刷新数据的机制。 （需要提供线程池）

异步刷新代码：

::: tabs

@tab 重写 reload 方法

```java
// 定义刷新的线程池
ExecutorService executorService = Executors.newFixedThreadPool(5);

CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
  @Override
  public String load(String key) {
    System.out.println(Thread.currentThread().getName() + " 加载 key:" + key);
    // 从数据库加载数据
    return "value_" + key.toUpperCase();
  }
  @Override
  // 💡异步刷新缓存： 当 refreshAfterWrite 到期，或者 LoadingCache.refresh 方法被调用时，该方法会被触发
  public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
    ListenableFutureTask<String> futureTask = ListenableFutureTask.create(() -> {
      System.out.println(Thread.currentThread().getName() + " 异步加载 key:" + key + " oldValue:" + oldValue);
      return load(key);
    });
    executorService.submit(futureTask);
    return futureTask;
  }
}

LoadingCache<String, String> cache = CacheBuilder.newBuilder()
  .maximumSize(20)
  .expireAfterWrite(10, TimeUnit.SECONDS)
  .refreshAfterWrite(5, TimeSECONDS)
  .build(cacheLoader);
```

@tab 实现 asyncReloading 方法

```java
// 定义刷新的线程池
ExecutorService executorService = Executors.newFixedThreadPool(5);

CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
  @Override
  public String load(String key) {
    System.out.println(Thread.currentThread().getName() + " 加载 key:" + key);
    // 从数据库加载数据
    return "value_" + key.toUpperCase();
  }
}

// 💡添加异步处理
cacheLoader = CacheLoader.asyncReloading(cacheLoader, executorService);

LoadingCache<String, String> cache = CacheBuilder.newBuilder()
  .maximumSize(20)
  .expireAfterWrite(10, TimeUnit.SECONDS)
  .refreshAfterWrite(5, TimeSECONDS)
  .build(cacheLoader);
```

:::

### 清理策略

由于内存资源考虑，缓存的数据可能需要被 “清理”。

Guava cache 可以使用 Soft 引用、Weak 引用来避免 gc 阻塞。

相应配置：

- softValues —— 软引用
- weakValues —— 弱引用

::: info

不同引用方式，在 JVM 中的 gc 策略：

- StronReference 强引用 —— 只要有引用，就不会被 gc 回收
- SoftReference 软引用 —— 尽管还有引用，但是会被 full gc 回收
- WeakReference 弱引用 —— 尽管还有引用，但是会被 Major gc （仅清理老年代） 和 full gc （清理整个堆） 回收
- PhantomReference 幽灵引用 —— 尽管还有引用，但不管有没有被 gc 回收，都是无法通过引用访问内存内容，**但是可以收到该内存被 gc 回收的通知** | 参考： apache common-io FileCleaningTracker

:::

todo 内存敏感实现

## Other

### StopWatch

统计代码运行时间

```java
<!-- @include: @project/code/demo-guava/demo-01-simple/src/test/java/org/example/guava/other/StopWatchTest.java -->
```
