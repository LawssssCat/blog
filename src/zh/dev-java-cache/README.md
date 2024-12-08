---
title: 缓存使用
date: 2024-06-14
tag:
  - java
order: 33
---

Java Cache 缓存的使用和工具封装。

<!-- more -->

> 参考：
>
> - ~~B 站|【高并发三剑客（缓存篇）】- https://www.bilibili.com/video/BV1rQ4y1g7zt/~~
> - Java 本地缓存技术选型（Guava Cache、Caffeine、EhCache） - <https://cloud.tencent.com/developer/article/2411233> <br> <https://www.jianshu.com/p/e5dc3a18dcb8>
> - todo java 专题之缓存模块（ehcache/guava cache/自定义 spring 的 CacheManager/自定义缓存） - <https://www.bilibili.com/video/BV1zP4y1h7tD?p=2>

## 淘汰算法

### LRU 算法

LRU（Least Recently Used，最少最近使用）

todo https://www.bilibili.com/video/BV1R4411s7GX?p=29

todo LinkedHashMap 实现

todo LinkedList 实现

## 缓存分类

- 单机缓存/本地缓存（local cache） —— 应用中的缓存组件，缓存组件和应用在同一个进程中，所以缓存的读写非常快且没有网络开销，但各应用或集群的各节点都需要维护自己的缓存，无法共享缓存，且受 JVM 内存限制，不适合存放大数据。
  - JDK Map
    - HashMap
    - ConcurrentHashMap —— 线程安全
    - LinkedHashMap —— 有序（基于插入顺序）
    - TreeMap —— 有序（基于 Comparable 顺序）
  - 缓存框架：
    - [guava cache](../dev-java-commonsense/sdk-toolkit-guava.md) —— 基于 LRU 淘汰策略
    - caffeine —— 性能强：基于 W-ThiyLFU 淘汰策略
    - ehcache —— 功能多：支持多种淘汰策略（包括 FIFO、LRU、LFU 等），支持额外功能（并发级别控制、生效策略、容量控制、事件通知、统计信息、等）
  - 对象池
- 分布式缓存/远程缓存（remote cache） —— 和应用分离的缓存组件或服务，与本地应用隔离，多个应用可直接共享缓存。
  - 缓存框架：
    - Memcached —— 历史
    - redis —— 支持多种数据结构
    - Tair —— 阿里开源
  - 协议：http/rpc

## 本地缓存（local cache）

应用中的缓存组件，缓存组件和应用在同一个进程中，所以缓存的读写非常快且没有网络开销，但各应用或集群的各节点都需要维护自己的缓存，无法共享缓存，且受 JVM 内存限制，不适合存放大数据。

![Operations per Second](https://s2.loli.net/2024/06/20/ZCzf2LsY8eam5XD.png)

- 从易用性角度，Guava Cache、Caffeine 和 Encache 都有十分成熟的接入方案，使用简单。
- 从功能性角度，Guava Cache 和 Caffeine 功能类似，都是只支持堆内缓存，Encache 相比功能更为丰富
- 从性能上进行比较，Caffeine 最优、GuavaCache 次之，Encache 最差

### JCache

todo https://www.baeldung.com/jcache

JSR107 缓存规范

```xml
<dependency>
  <groupId>javax.cache</groupId>
  <artifactId>cache-api</artifactId>
  <version>1.1.1</version>
</dependency>
```

- jcp 介绍： <https://www.jcp.org/en/jsr/detail?id=107>
- 项目地址： <https://github.com/jsr107/jsr107spec>
- api 结构

  ```
  Application - CachingProvider - CacheManager - Cache - Entry + ExpiryPolicy
  ```

- 实现： redisson

Spring 的缓存抽象

todo https://www.baeldung.com/jcache

### 框架：guava cache

[guava cache](../dev-java-commonsense/sdk-toolkit-guava.md)

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

### 框架：caffine

todo spring 整合

caffine <https://github.com/ben-manes/caffeine>

Caffeine 是基于 JAVA 8 的高性能缓存库。
在 Spring5 (Springboot 2.x) 后，Spring 官方放弃了 Guava，而使用了性能更优秀的 Caffeine 作为默认缓存组件。

Caffeine 和很多的本地缓存一样，有如下特性：

- 支持并发
- 支持 `O(1)` 时间复杂度的数据存取
- 支持自动移除 “不常用” 的数据，以保持内存的合理占用

::: tabs

@tab 依赖

```xml
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>2.5.5</version>
</dependency>
```

@tab Demo

```java
public class CaffeineCacheTest {
​
    public static void main(String[] args) throws Exception {
        //创建guava cache
        Cache<String, String> loadingCache = Caffeine.newBuilder()
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
        // 获取value的值，如果key不存在，获取value后再返回
        String value = loadingCache.get(key, CaffeineCacheTest::getValueFromDB);
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

### 框架：ehcache

todo spring 整合

Encache 是一个纯 Java 的进程内缓存框架，具有快速、精干等特点，是 Hibernate 中默认的 CacheProvider。
同 Caffeine 和 Guava Cache 相比，Encache 的功能更加丰富，扩展性更强：

- 支持多种缓存淘汰算法，包括 LRU、LFU 和 FIFO
- 缓存支持堆内存储、堆外存储、磁盘存储（支持持久化）三种
- 支持多种集群方案，解决数据共享问题

::: tabs

@tab 依赖

```xml
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>3.8.0</version>
</dependency>
```

@tab Demo

```java
public class EncacheTest {
    public static void main(String[] args) throws Exception {
        // 声明一个 cacheBuilder
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("encacheInstance", CacheConfigurationBuilder
                        // 声明一个容量为 20 的堆内缓存
                        .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(20)))
                .build(true);
        // 获取 Cache 实例
        Cache<String,String> myCache =  cacheManager.getCache("encacheInstance", String.class, String.class);
        // 写缓存
        myCache.put("key","v");
        // 读缓存
        String value = myCache.get("key");
        // 移除换粗
        cacheManager.removeCache("myCache");
        cacheManager.close();
    }
}
```

:::

## Spring Cache

参考： https://sherry-02.github.io/2021/04/15/springboot%E4%B8%8E%E7%BC%93%E5%AD%98%E7%9B%B8%E5%85%B3%E4%BF%A1%E6%81%AF/

### 使用

JCache JSR-107

#### @EnableCaching

开启缓存注解驱动

#### @CacheConfig

全局缓存配置，如配置缓存的名字（cacheNames）

#### CachingProvider

todo 管理多个 CacheManager

#### CacheManager

todo 管理多个 Cache

#### KeyGenerator

```java
// 自定义配置类配置 keyGenerator
@Configuration
public class MyCacheConfig {
  @Bean("myKeyGenerator")
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generator(Object target, Method method, Object ... params) {
        return method.getName() + "[" + Arrays.asList(params).toString() + "]";
      }
    }
  }
}
```

#### @Cacheable

如果有缓存，就使用缓存；如果没有缓存，就获取 —— 一般用于查询

运行流程：

- 方法运行前，先查询 Cache（缓存组件），按照 cacheNamesd 指定的名字获取
  - 如果没有找到就创建一个 cache 组件
    - 这里由 CacheManager 来完成提供 cache 组件
  - 在找到的 cache 组件中，使用一个参数 key 来获取缓存的内容
    - 默认按照 `@Cacheable` 注解所在的方法参数

```java
/**
 * cacheNames/value —— 指定缓存组件名称，将方法的返回值存放在哪个缓存中，是数组的方式，可以指定多个缓存
 *
 * cacheManager —— 指定缓存管理器；或者 cacheResolver 获取指定解析器
 *
 * key —— 缓存数据时使用的 key，可以用这个属性值来指定，默认使用方法参数的值。
 * （可以使用 SqlEL 表达式来指定，如 #id 指定方法参数 id 值； #root.args[0] 指定第一个参数；）
 * keyGenerator —— key 生成器，可以自定义 key 的生成器组件
 *
 * condition —— 指定符合条件的情况下才缓存，如 condition="#id>0"
 * unless —— 否定缓存，当 unless 指定的条件为 true，方法的返回值不会被缓存，可以获取到结果进行判断，如 unless="#result==null"
 *
 * sync —— 是否使用异步模式
 */
@Cacheable(cacheNames = "user", keyGenerator = "myKeyGenerator")
public User getUser(Integer id) {
  log.info("查询 {} 用户", id);
  User user = userMapper.getUserId(id);
  return user;
}
```

#### @CachePut

即调用方法，又更新缓存 —— 一般用于更新

运行流程：

- 调用目标方法
  - 将结果添加到缓存中

```java
/**
 * value —— 缓存名
 * key —— 缓存的 key
 * （其中 #result 表示方法返回的结果）
 * （确保更新的 key 和查询一致，即可做到同时更新数据库数据和缓存数据）
 */
@CachePut(value = "user", key = "#result.id")
public User updateUser(User user) {
  System.out.println("updateUser:" + user);
  userMapper.updateUser(user);
  return user;
}
```

#### @CacheEvict

```java
/**
 * key
 * allEntries 指定清除这个缓存中的所有数据，默认是 false
 * beforeInvocation —— 指定清除缓存时机。true=方法执行前清除缓存，false=方法执行后执行缓存（如果方法执行过程中出现异常，就不会删除缓存）
 */
@CacheEvict(value = "user", key = "#id")
public void deleteUser(Integer id) {
  System.out.println("deleteUser:"+id);
  userMapper.deleteUserById(id);
}
```

#### @Caching

定义复杂的缓存规则

```java
@Caching(
  cacheable = {
    @Cacheable()
  },
  pub = {
    @CachePut(),
    @CachePut(),
    ...
  },
  evict = {
    @CacheEvict()
  }
)
public 返回值 方法名（） {
  ....
}
```

### Demo

```java
@MapperScan("org.example")
@SpringBootApplication
@EnableCaching
public class CacheApplication {
  public static void main(String[] args) {
    SpringApplication.run(CacheApplication.class, args);
  }
}
```

```java
@Service("articleService")
@CacheConfig(cacheNames = "articleCache")
public class ArticleServiceImpl implements ArticleService {
  @Resource
  private ArticleDao articleDao;

  @Cacheable(value = "articleCache")
  @Override
  public Article queryById(int id) {
    return this.articleDao.queryById(id);
  }

  @CachePut
  @Override
  public int insert(Article article) {
    return this.articleDao.insert(article);
  }

  @CacheEvict(key = "#article.id")
  @Override
  public int update(Article article) {
    return this.articleDao.update(article);
  }

  @CacheEvict(key = "#id")
  @Override
  public boolean deleteById(Integer id) {
    return this.articleDao.deleteById(id) > 0;
  }
}
```

## 问题：分页列表缓存

业务：缓存商品列表

::: tabs

@tab 处理一：直接缓存分页结果

key：基于 page 和 size 缓存

问题：颗粒度大

```java
public List<Product> getPageList(String param, int page, int size) {
  String key = "productList:page:" + page + "size:"+ size + "param:" + param;
  List<Product> dataList = cacheUtils.get(key);
  if (dataList != null) {
    return dataList;
  }
  dataList = queryFromDataBase(param, page, size);
  if (dataList != null) {
    cacheUtils.set(key, dataList, Constants.ExpireTime);
  }
}
```

@tab 处理二：查询商品 ID，缓存商品数据

key：基于商品编号缓存

```java
public List<Product> getPageList(String param, int page, int size) {
  // 返回结果
  List<Product> result = new ArrayList<>(size);
  // 1. 从数据库中查询分页 ID 列表
  List<Long> productIdList = queryProductIdListFromDataBase(param, page, size);
  if (CollectionUtils.isEmpty(productIdList)) {
    return Collections.EMPTY_LIST;
  }

  // 2. 批量从缓存中获取商品对象
  Map<Long, Product> cachedProductMap = cacheUtils.mget(productIdList);

  // 3. 组装没有命中的商品 ID
  List<Long> noHitIdList = new ArrayList<>(cachedProductMap.size());
  for (Long productId : productIdList) {
    if (!cachedProductMap.containsKey(productId)) {
      noHitIdList.add(productId);
    }
  }

  // 4. 将没有命中缓存的商品，从数据库中查询出来，然后加载到缓存中
  List<Product> noHitProductList = batchQuery(noHitIdList);
  if (CollectionUtils.isNotEmpty(noHitProductList)) {
    // 将没有命中的商品加入到缓存里
    Map<Long, Product> noHitProductMap = noHitProductList.stream().collect(Collectors.toMap(Product::getId, Function::identity, (o1, o2) -> o1));
    cacheUtils.mset(noHitProductMap);
    // 将没有命中的商品加入到聚合 map 里
    cachedProductMap.putAll(noHitProductMap);
  }

  // 最后组装
  for (Long productId : productIdList) {
    Product product = cachedProductMap.get(productId);
    if (product != null) {
      result.add(product);
    }
  }
  return result;
}
```

@tab 处理三：缓存商品 ID，缓存商品数据

key：基于缓存的商品编号

todo

:::
