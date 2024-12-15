---
title: Mybatis 使用
date: 2024-12-14
tag:
  - java
  - jdbc
  - mybatis
order: 66
---

## 基本功能

todo

## 拦截器

> 参考：
>
> - SpringBoot + Mybatis 系列之插件机制 Interceptor —— https://cloud.tencent.com/developer/article/1858587

MyBatis 将代理模式（JDK 代理/~~CGLIB 代理~~）进一步扩展，提供了拦截器的机制（`org.apache.ibatis.plugin.Interceptor`），本质希望在执行某些核心的调用操作之前执行某些拦截的处理逻辑。

拦截点：

- **调用前拦截**
- 方法拦截
- **调用后拦截**
- **异常处理**
- **结果返回**

通过织入拦截器，在不同节点修改一些执行过程中的关键属性，从而影响 SQL 的生成、执行和返回结果。
如：

- 来影响 Mapper.xml 到 SQL 语句的生成 —— 分页
- 执行 SQL 前对预编译的 SQL 执行参数的修改 —— 数据过滤、数据加密、数据脱敏
- SQL 执行后返回结果到 Mapper 接口方法返参 POJO 对象的类型转换和封装 —— SQL 执行时间性能监控和告警

### 拦截器的定义 `@Intercepts`

`@Intercepts` 注解用于标记一个拦截器，它接收一组 `@Signature` 数组参数详细定义了拦截的范围和条件：

1. **type**: 这个属性指定了要拦截的接口或者类：

   1. **Executor**: MyBatis 的核心执行器接口，负责查询和更新等操作的执行。常见的方法如 update、query 等，分别对应 SQL 的更新和查询操作。
   1. **ParameterHandler**: 参数处理器接口，负责设置预编译语句（PreparedStatement）的参数值。
   1. **ResultSetHandler**: 结果集处理器接口，负责处理查询结果集，转换为用户定义的对象。
   1. **StatementHandler**: 语句处理器接口，负责创建和执行 SQL 语句对象（PreparedStatement 或 Statement）。

1. **method**: 指定要拦截的接口或类中的具体方法名称。

   可选的方法名称与上述 type 拦截类型一一对应：

   1. Executor —— SQL 执行
      - update
      - query
      - flushStatements
      - commit
      - rollback
      - getTransaction
      - close
      - isClosed
   1. ParameterHandler —— 处理参数
      - getParameterObject
      - setParameters
   1. ResultSetHandler —— 处理结果集
      - handleResultSets
      - handleOutputParameters
   1. StatementHandler —— 参数、结果集、sql 都可处理
      - prepare
      - parameterize
      - batch
      - update
      - query

1. **args**: 这是一个 Class 数组，表示要拦截方法的参数类型列表。这些类型必须与被拦截方法的实际参数类型严格匹配。例如，{MappedStatement.class, Object.class}意味着拦截的方法接受两个参数，第一个参数类型为 MappedStatement（映射语句对象，包含了 SQL 语句和相关配置信息），第二个参数类型为 Object（通常为执行 SQL 时需要的参数对象）。

MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

### 拦截器的编写

编写插件的步骤：

- 实现 `Interceptor` 接口。
- 使用 `@Intercepts` 注解完成插件签名。
- 在全局配置文件中注册插件。

例子：

:::::: tabs

@tab 编写接口和签名

```java
/**
 * 拦截所有更新语句
 */
//注解的意思是：拦截Executor类的update(MappedStatement,Object)方法
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class EditPlugin implements Interceptor {
    /**
     * 拦截目标对象的目标方法的执行
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        String sql = statement.getBoundSql(args[1]).getSql();
        System.out.println("--------intercept method： " + statement.getId() + "-----sql： " + sql);
        //执行目标方法
        return invocation.proceed();
    }
    /**
     * 包装目标对象：为目标对象创建一个代理对象
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    /**
     * 将插件注册时的properties属性设置进来
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息 = " + properties);
    }
}
```

> alias: 扩展接口 Interceptor 实现原理
>
> 其中 Interceptor 接口的三个方法作用如下：
>
> ```java
> package org.apache.ibatis.plugin;
> import java.util.Properties;
> public interface Interceptor {
>  Object intercept(Invocation invocation) throws Throwable; // 拦截器核心操作
>  default Object plugin(Object target) { // 【已默认实现】用于描述如何注册插件
>    return Plugin.wrap(target, this);
>  }
>  default void setProperties(Properties properties) { // 入参 properties 的值从 mybatis 配置文件读取。该方法提供插件获取 mybatis 配置文件的入口。
>    // NOP
>  }
> }
> ```
>
> 其中 Interceptor 接口主要方法 intercept 的参数 invocation 包含如下属性：
>
> ```java
> public class Invocation {
>  private final Object target; // 被代理对象
>  private final Method method; // 被代理方法
>  private final Object[] args; // 被代理方法参数
> }
> ```
>
> 这些属性与 JDK 代理接口一致，原因是其实现方式就是通过 JDK 代理实现的。
> 具体可以进入 Interceptor 接口的 plugin 方法默认实现中查看：
>
> ```java
> public class Plugin implements InvocationHandler {
>   public static Object wrap(Object target, Interceptor interceptor) {
>     Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
>     Class<?> type = target.getClass();
>     Class<?>[] interfaces = getAllInterfaces(type, signatureMap); // 获取全部接口实现
>     if (interfaces.length > 0) {
>       return Proxy.newProxyInstance( // 注册JDK的动态代理实现
>           type.getClassLoader(),
>           interfaces,
>           new Plugin(target, interceptor, signatureMap));
>     }
>     return target;
>   }
> }
> ```

@tab 注册插件

有两种方式： Mybatis 配置文件注册、Spring Boot Bean 方式注册

Mybatis 配置文件注册方式：

```xml title="resources/mybatis/mybatis-config.xml"
<plugins>
    <plugin interceptor="com.xiaolyuh.mybatis.EditPlugin">
        <property name="args1" value="参数示例"/>
    </plugin>
</plugins>
```

Spring Boot Bean 注册方式

```java
@Configuration
public class MybatisConfig {
//    // 注册插件方式1
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                //插件拦截链采用了责任链模式，执行顺序和加入连接链的顺序有关
//                EditPlugin myPlugin = new EditPlugin();
//                configuration.addInterceptor(myPlugin);
//            }
//        };
//    }

    // 注册插件方式2
    // @Bean
    // public ConfigurationCustomizer configurationCustomizer() {
    //     return configuration -> {
    //         //插件拦截链采用了责任链模式，执行顺序和加入连接链的顺序有关
    //         EditPlugin myPlugin = new EditPlugin();
    //         configuration.addInterceptor(myPlugin);
    //     };
    // }


// ❗坑：在依赖 PageHelper 的情况下，上述注册方式（注册拆建方式1、2）均不会生效，需要用下面的注册方式

    @Resource
    List<SqlSessionFactory> sqlSessionFactories;

    @PostConstruct
    public void postConstruct() {
        log.info("begin to register customer mybatis interceptor");
        EditPlugin myPlugin = new EditPlugin();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
            sqlSessionFactory.getConfiguration().addInterceptor(myPlugin);
        }
        log.info("finish register customer mybatis interceptor");
    }
}
```

::::::

## 案例： Mybatis 拦截器统一管理创建人、更新人、创建时间、更新时间等公共字段

参考：

- https://blog.csdn.net/m0_47743175/article/details/128053257

## Other

todo Mybatis 中的 VFS 是个啥 —— https://xie.infoq.cn/article/93e67d483fe4a0b3eaded877a

todo 《MyBatis 从入门到精通》 https://item.jd.com/12103309.html
