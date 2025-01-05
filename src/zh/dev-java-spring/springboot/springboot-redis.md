---
title: Spring Boot + Redis 数据库
shortTitle: 整合 Redis 数据库
date: 2024-04-06
tag:
  - springboot
  - redis
---

整合 Spring Boot/[Redis](/zh/ops-db-redis/README.md) 用来简单开发、测试。

<!-- more -->

## 客户端对比

| 客户端                                                                          | 介绍                                                                                                                                                                                                                                   |
| ------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Jedis](http://tool.oschina.net/uploads/apidocs/redis/clients/jedis/Jedis.html) | 老牌的 Redis 的 Java 实现客户端，提供了比较全面的 Redis 命令支持。但客户端不是线程安全的，需要使用者处理。                                                                                                                             |
| [redisson](https://redisson.org/)                                               | 基于 Netty 框架的事件驱动的通信层，其方法调用是异步的。实现了可分布式和可扩展的 Java 数据结构，可通过 Redis 支持延迟队列。客户端线程安全，但和 Jedis 相比，功能较为简单：不支持字符串操作，不支持排序、事务、管道、分区等 Redis 特性。 |
| [lettuce](https://lettuce.io/)                                                  | 基于 Netty 框架的事件驱动的通信层，其方法调用是异步的。支持集群，Sentinel，管道和编码器。客户端线程安全。 <br> （在 Spring Boot 2 中，为默认的 redis 客户端）                                                                          |

## SpringBoot + Jedis

代码： <RepoLink path="/code/demo-springboot-redis/demo-jedis" />

具体分为：添加依赖 + redis 配置信息 + JedisPool 工厂 + RedisService（包含序列化）

::: tabs

@tab 引入依赖

SpringBoot 内默认引入了 Jedis 版本，所以这里不需要写版本

```xml title="pom.xml"
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.7.0</version>
</dependency>
```

@tab 配置类

```yaml title="application.yml"
spring:
  redis:
    port: 6379
    host: 192.168.64.33
    password: root
    jedis:
      pool:
        max-idle: 6 #最大空闲数
        min-idle: 2 #最小空闲数
        max-active: 10 #最大连接数
    timeout: 2000 #连接超时
```

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(maxActive);
        return new JedisPool(config, host, port, timeout, password);
    }
}
```

@tab 使用

线程池获取

```java
@Autowired
private JedisPool jedisPool;

Jedis jedis = jedisPool.getResource();
jedis.exists(key) // 判断key是否存在
jedis.get(key);
jedis.flushDB();
```

手动建立连接

```java
Jedis jedis = new Jedis("192.168.40.4", 6379);
jedis.select(0);
// ...
jedis.close();
```

:::

## SpringBoot + Spring Data Redis （Lettuce）

文档： <https://docs.spring.io/spring-data/redis/reference/#redis:template>

[Spring Data Redis](https://spring.io/projects/spring-data-redis) 中对 JedisApi 的高度封装 （SpringBoot 推荐使用这种）

- 可以方便地更换 Redis 的 Java 客户端，如 jedis、Jredis、rjc、redission、lettuce、... （其中 lettuce 为默认的客户端）
- 提供了 RedisTemplate 模板类：

  - 封装了 Redis 连接池管理的逻辑，业务代码无需关心获取和释放的连接逻辑
  - 执行各种 Redis 操作、异常转换和序列化支持提供高级抽象。

    常用的接口如：

    - valueOps
    - listOps
    - setOps
    - zSetOps
    - list

    序列方式有：

    - **GenericToStringSerializer**：序列化为 `toString` 字符串
    - **Jackson2JsonRedisSerializer**：序列化 Json 字符串
    - **JdkSerializationRedisSerilizer**：序列化为 Serializable 接口实现的二进制内容
    - **StringRedisSerializer**：简单的字符串序列化

- 方便与其他 Spring 框架进行搭配使用如：
  - Repository 接口的自动实现 `@EnableRedisRepositories`
  - SpringCache
  - 发布订阅支持，用于消息驱动的 POJO 的 MessageListenerContainer

代码： <RepoLink path="/code/demo-springboot-redis/demo-springdataredis" />

:::::: tabs

@tab 依赖

```xml title="pom.xml"
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!--redis依赖commons-pool，这个依赖一定要添加，否则会丢失资源-->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

@tab 配置类

```yml title="application.yml"
spring:
  redis:
    port: 6379
    password: root
    host: 192.168.64.33
    lettuce:
      pool:
        max-active: 8 #连接池最大连接数（使用负值表示没有限制）
        max-idle: 8 #连接池中最大空闲连接
        min-idle: 0 #连接池中最小空闲连接
        max-wait: 1000 #连接池最大阻塞等待时间（使用负值表示没有限制）
      shutdown-timeout: 100 #关闭超时时间
```

```java title="RedisConfig.java"
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * Redis的配置类
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * （非必要）自定义缓存key的生成策略。（默认生成策略是乱码的，不方便维护）
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString(); // 类名+方法名+参数1+参数2+...
            }
        };
    }
    /**
     * （非必要）缓存配置管理器
     */
    @Override
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory); // 以锁写入的方式创建RedisCacheWriter对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig(); // 默认缓存配置对象
        return new RedisCacheManager(writer, config);
    }
    /**
     * 配置默认的Redis客户端模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        // 0. 创建 Json 序列化器。需要依赖：jackson-databind
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 0. 创建 String 序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 1. 设置 redis 序列化策略
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(jackson2JsonRedisSerializer);      // 设置对值的所有类型（Object.class）对象进行json序列化
        template.setKeySerializer(stringRedisSerializer);              // key采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);          // hash的key也采用String的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);  // hash的value序列化方式采用jackson
        template.afterPropertiesSet();
        return template;
    }
}
```

@tab 使用

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;
redisTemplate.opsForValue().get(key);
redisTemplate.opsForValue().set(key, val);
Set<String> keys = redisTemplate.keys("*");
redisTemplate.delete(keys);

redisTemplate.opsForHash().put("user", id , new User())
redisTemplate.opsForHash().hasKey("user", id)
redisTemplate.opsForHash().get("user", id)
```

### RedisTemplate 基本使用

todo forList, boundXxxOps
参考： https://www.bilibili.com/video/BV1jD4y1Q7tU

### 问题：事务处理

同数据库一样，有两种事务处理方式：

- 编程式事务
- 声明式事务

**编程式事务**

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;
@Test
public void testTransactionalCode() {
  List<Object> execute = redisTemplate.execute(new SessionCallback<List<Object>>() {
    @Override
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
      operations.multi(); // 开启事务
      // 💡无须写回滚try，出现异常自动回滚
      BoundValueOperations ops = operations.boundValueOps("hxm");
      ops.set(999);
      assertNull(ops.get()); // ⚠️这里为null，因为事务还未执行
      return operations.exec(); // 提交事务
    }
  });
  System.out.println(execute);
}
```

**声明式事务** （参考： https://blog.csdn.net/hxm_Code/article/details/105119273）

::: tabs

@tab 开启声明式配置

```java
@Configuration
@EnableTransactionManagement // 开启注解式事务
public class RedisConfig {
  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    // ⚠️需要显示声明开启transaction支持
    template.setEnableTransactionSupport(true);
    return template;
  }
```

@tab 声明事务

方法上添加 `@Transactional` 注解

:::

## 应用场景

### 分布式锁

todo

### 分布式缓存（Cache）实现

todo

::: warning
使用 Mybatis 二级缓存需要注意以下问题：

- 联表查询的结果如果被缓存，可能不会因为单表的更新而被更新。
  - ~~（有问题的）解决方法： 统一使用相同的缓存命名空间 `<cache-ref namespace="org.example.dao.UserDAO" />`~~

:::

缓存架构：

- redis 分布式缓存 （distribute cache）
- mybatis 二级缓存 （local cache） （通过 `org.apache.ibatis.cache.impl.PerpetualCache` 实现）
- sql 查询

```xml
<mapper namespace="org.example.dao.UserDAO">
  <!-- 开启mybatis二级缓存 -->
  <cache type="org.example.cache.RedisCache" />
  <select id="findAll" redisType="User" >
    select * from t_user
  </select>
</mapper>
```

```java
public class RedisCache implements Cache {
  private static final String PREFIX = "MYBATIS:CACHE:"
  private String id;
  private RedisTemplate redisTemplate;
  public RedisCache(String id) {
    this.id = id;
    this.redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
  }
  @Override
  public String getId() {
    return this.id;
  }
  // 查到值，值放入缓存
  @Override
  public void putObject(Object key, Object value) {
    redisTemplate.opsForHash().put(PREFIX+id, toMD5(key.toString()), value);
  }
  // 查询缓存
  @Override
  public Object getObject(Object key) {
    return redisTemplate.opsForHash().get(PREFIX+id, toMD5(key.toString()));
  }
  @Override
  public Object removeObject(Object key) {
    return null; // UnsupportedException
  }
  @Override
  public void clear() {
    redisTemplate.delete(PREFIX+id); // 清空缓存
  }
  // 计算缓存数量
  @Override
  public int getSize() {
    return redisTemplate.opsForHash().size(PREFIX+id).intValue();
  }
  private String toMD5(String key) {
    return DigestUtils.md5DigestAsHex(key.getBytes());
  }
}
```

### 分布式会话（Session）实现

todo

### 排行榜实现

todo

### 缓存穿透、击穿、雪崩问题

- **缓存穿透**：查询的 key 不在数据库中，查询结果无法被缓存，每次都要查库。
- **缓存击穿**：某个 key 被非常频繁访问，当 key 失效时，有大量线程来构建缓存，导致负载增加，系统奔溃。
- **缓存雪崩**：大量的 key 同时过期或者 redis 服务器宕机

解决方法：

- **缓存穿透**：缓存空结果；通过布隆过滤器判断数据是否存在
- **缓存击穿**：（主动刷新缓存的方式）避免缓存失效，如在 value 中记录下次主动刷新的时间，击鼓传花，最后查询到的线程负责主动刷新
- **缓存雪崩**：不同业务设置不同缓存时间，使缓存过期时间尽量均匀分布；（通过锁/线程池队列）减少同时查询 sql 的连接数量
