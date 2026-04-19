---
title: Java字节码操作
order: 66
---

Java字节码操作可以做到：

- lombok添加getter/setter方法
- btrace、Arthas和housemd等应用性能监控（APM）、动态诊断Jar包在JVM的运行情况
- Intellij idea 的 HotSwap（热部署）、Jrebel 等
- pinpoint、skywalking、newrelic、听云的 APM 产品等链路监控
- RASP（Runtime Application Self-Protection，运行时程序自我保护能力）

Java字节码操作工具：

- JDK原生
  - java-proxy —— Java原生代理接口，略
  - ClasFile —— Java22预览特性，有望将ASM替代
- 三方实现
  - ~~[javassist](http://www.javassist.org/) —— 通过内置编译器方式生成字节码~~
  - [Java ASM](#tid-asm) —— 通过API方式生成字节码，已集成至JDK标准 （类似：AST，Abstract Syntax Tree，抽象语法树）
    - bytebuddy —— 基于ASM的库
      - todo <https://www.bilibili.com/video/BV13m42137Ct>
    - bytekit —— 基于ASM的库，阿里的库，使用上与bytebuddy类似
- 应用案例
  - Spring代理应用（从SpringBoot2.x起，默认代理使用CGLIB，而不是JDK动态代理）
    - CGLIB —— 能通过克隆类并加工的方式实现代理效果
    - JDK动态代理 —— 只能代理interface类型

参考：

- ASM
  - Java-ASM简介 <https://javaguidepro.com/blog/java-asm/>
  - free-coder|【java】一个视频学会asm <https://www.bilibili.com/video/BV1sW42197eG/>
    - <https://www.xiaogenban1993.com/blog/24.07/java%E7%9A%84asm%E5%BA%93%E6%B7%B1%E5%85%A5%E6%B5%85%E5%87%BA>
  - ~~Java-字节码插桩-ASM <https://www.bilibili.com/video/BV1NK4y1z7oq>~~
    - ~~<https://juejin.cn/post/7323271686034292746>~~
  - Java ASM系列 <https://space.bilibili.com/1321054247/search?keyword=asm> —— 详细到每个关键字的书写，129+个视频。。。
- Spring
  - DeeparchWorks|JDK动态代理vsCGLIB深度对比 <https://www.bilibili.com/video/BV1FDQUBNE9n/>

## Instrumentation（插桩）

文档：

- javaagent使用指南 <https://www.cnblogs.com/rickiyang/p/11368932.html> ⭐⭐⭐⭐⭐
- 官方文档： [Instrumentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.instrument/java/lang/instrument/Instrumentation.html)
- JVM虚拟机字节码指令表 <https://segmentfault.com/a/1190000008722128>
- Java高级特性之Instrumentation <https://www.cnblogs.com/dongguangming/p/12990664.html>

作用：
使用 Instrumentation，开发者可以构建一个独立于应用程序的代理程序（Agent），用来监测和协助运行在 JVM 上的程序（如：用于监控运行程序状态，记录日志，分析代码），甚至可以替换和修改某些类的定义。
这样的特性实际上提供了一种虚拟机级别支持的 AOP方式，使得开发者无需对原有应用做任何修改，就可以实现类的动态修改和增强

发展：

- **Java SE5** —— Instrument 要求在运行前利用命令行参数或者系统参数来设置代理类，但在Java常见的服务器常见中能在运行前添加参数的情况不多，所以这限制了 instrument 的应用上限。
- **Java SE6** —— 通过 Java Tool API 中的 attach 方式，支持将代码植入代码到运行时的 JVM 程序中，这大大提升了 instrument 的应用上限。

### 触发打桩：启动前

```bash
-agentlib:<libname>[=<选项>]
                  加载本机代理库 <libname>, 例如 -agentlib:hprof
                  另请参阅 -agentlib:jdwp=help 和 -agentlib:hprof=help
-agentpath:<pathname>[=<选项>]
                  按完整路径名加载本机代理库
-javaagent:<jarpath>[=<选项>]
                  加载 Java 编程语言代理, 请参阅 java.lang.instrument
```

### 触发打桩：运行时

todo 略

### 打桩接口规范

Instrumentation 在 java.lang.instrument 包中。

> Instrumentation 的 java.lang.instrument 包的实现依赖于 [JVMTI（Java Virtual Machine Tool Interface，Java虚拟机工具接口）](https://docs.oracle.com/javase/8/docs/technotes/guides/jvmti/index.html)：
>
> - JVMTI（Java Virtual Machine Tool Interface）从 Java SE 5 开始引入。
> - JVMTI 规范了一套为 JVM 相关工具提供的本地编程接口集合，通过调用 JVMTI 当中与 Java 类相关的函数，Instrument 可以完成对 Java 类的动态操作。
>（除了 Instrumentation 功能外，JVMTI 还在虚拟机内存管理，线程控制，方法和变量操作等等方面提供了大量可用的函数。关于 JVMTI 的详细信息，可以参考 Java SE 6 JVM TI文档）
> - JVMTI 整合和取代了 JVMPI（Java Virtual Machine Profiler Interface） 和 JVMDI（the Java Virtual Machine Debug Interface）

java.lang.instrument.Instrumentation 接口，常用方法有：

```java
public interface Instrumentation {
    // 增加一个Class 文件的转换器，转换器用于改变 Class 二进制流的数据，参数 canRetransform 设置是否允许重新转换。
    void addTransformer(ClassFileTransformer transformer, boolean canRetransform);

    // 在类加载之前，重新定义 Class 文件，ClassDefinition 表示对一个类新的定义，如果在类加载之后，需要使用 retransformClasses 方法重新定义。addTransformer方法配置之后，后续的类加载都会被Transformer拦截。对于已经加载过的类，可以执行retransformClasses来重新触发这个Transformer的拦截。类加载的字节码被修改后，除非再次被retransform，否则不会恢复。
    void addTransformer(ClassFileTransformer transformer);

    // 删除一个类转换器
    boolean removeTransformer(ClassFileTransformer transformer);

    boolean isRetransformClassesSupported();

    // 对已加载类重新转换（需开启`Can-Retransform-Classes`）
    // 在类加载之后，重新定义 Class。这个很重要，该方法是1.6 之后加入的，事实上，该方法是 update 了一个类。
    void retransformClasses(Class<?>... classes) throws UnmodifiableClassException;

    boolean isRedefineClassesSupported();

    /**
     * 完全替换类定义（慎用，会丢失原有类状态）
     */
    void redefineClasses(ClassDefinition... definitions)
        throws  ClassNotFoundException, UnmodifiableClassException;

    boolean isModifiableClass(Class<?> theClass);

    /**
     * 获取当前 JVM 所有已加载类
     */
    @SuppressWarnings("rawtypes")
    Class[] getAllLoadedClasses();

    @SuppressWarnings("rawtypes")
    Class[] getInitiatedClasses(ClassLoader loader);

    //获取一个对象的大小
    long getObjectSize(Object objectToSize);

    /**
     * 将指定的 JAR 文件添加到 Bootstrap ClassLoader 的搜索路径中。
     * 这种方法通常用于解决类加载器的双亲委派机制问题，尤其是在需要修改或扩展系统类时。
     */
    void appendToBootstrapClassLoaderSearch(JarFile jarfile);

    void appendToSystemClassLoaderSearch(JarFile jarfile);

    boolean isNativeMethodPrefixSupported();

    void setNativeMethodPrefix(ClassFileTransformer transformer, String prefix);
}
```

## ASM {#tid-asm}

Java ASM（Java Abstract Syntax Machines，Java抽象语法机/Java字节码操作库）

- 官网： <https://asm.ow2.io/>
- 源码： <https://gitlab.ow2.org/asm/asm>
- MVN： <https://central.sonatype.com/search?q=asm&smo=true>
- 开发者指南： <https://asm.ow2.io/developer-guide.html>
- 插件：
  - IDEA | ASM Bytecode Outline

asm有两个包路径：

- `jdk.internal.org.objectweb.asm` jdk自己提供的，需要手动指定包路径，否则会打包失败
- `org.objectweb` 引入asm pom即可

asm模块分类：

- asm-core
- asm-tree —— 对core的封装
- asm-common —— 包含core和tree包

asm核心类：

- ClassReader 用于读取字节码文件，解析类的结构信息。
- ClassVisitor 用于访问、操作类的各个部分，如类头、字段、方法等。
- ClassWriter 用于生成新的`byte[]`字节码文件，将修改后的类信息写入文件。

Demo:
<RepoLink path="/code/demo-java-bytcode/demo-01-asm/" />

## 应用

### Spring AOP

调用栈：

```bash
应用层： @Aspect / @Transactional / @Cacheable / ....
框架层： AnnotationAwareAspectJAutoProxy
代理层： DefaultAopProxyFactory ———— ⭐这里选择使用哪种代理，倾向使用CGLIB代理，但只有如下情况使用CGLIB代理：非NativeImage、非接口
字节码层： - JdkDynamicAutoProxy
            - Proxy.newProxy Instance()
            - InvocationHandler
字节码层： - ObjenesisCglibAopProxy
            - Enhancer + ASM 字节码生成子类
            - MethodInterceptor
JVN层（类加载，方法调用，JIT优化）： 拦截器调用链、目标方法
```

::: tip
通过类名判断类使用哪种代理：

- `$Proxy42` -> JDK代理
- `$$EnhancerBySpringCGLIB$$` -> CGLIB

通过方法判断类使用哪种代理：

- AopUtils.isJdkDynamicProxy(bean);
- AopUtils.isCglibProxy(bean);

:::

配置：

```java
spring.aop.proxy-target-class=true

@EnableAspectJAutoProxy(
  proxyTargetClass = true // 强制CGLIB
)

@EnableTransactionManagement(
  proxyTargetClass = true
)
```

对比：

&nbsp; | JdkProxy | CGLIB
--- | --- | ---
初始化速度 | 快 | 慢（需要操作字节码生成类）
方法调用速度 | 慢（methodinvoke需要权限检查、参数装箱） | 快（通过switch直接调用方法）
内存占用 | 低 | 高（会生成、加载额外的类）
问题 | 1、只能代理接口；<br> 2、`instanceof TargetClass = false` <br> 3、自调用问题（`this.xx`不走代理） | 1、不能代理final类/方法 <br> 2、自调用问题（`this.xx`不走代理） <br> 3、在GraalVM Native Image中不支持，因为该模式中要求编译时就确定所有的类 （因此，JdkProxy在AOT场景下依然不可替代）

::: tip
自调用问题有如下解决方法：

- 注入自身 `@Lazy @Autowired OrderService self; self.validate();`
- 调用工具 `AopContext.currentProxy()`
- 将方法拆分到不同类中

:::

### RASP

todo 从零开始的Java RASP实现(一) https://www.cnblogs.com/bitterz/p/15080403.html
    todo https://www.cnblogs.com/rickiyang/p/11368932.html
    todo https://www.cnblogs.com/kendoziyu/p/maven-auto-build-javaagent-jar.html
    todo Java底层防护 - OpenRASP核心源码浅析 https://xz.aliyun.com/news/6601
todo 从零开始的Java RASP实现(二) https://www.cnblogs.com/bitterz/p/15152287.html
    todo https://www.cnblogs.com/rickiyang/p/11368932.html
    todo https://www.cnblogs.com/kendoziyu/p/maven-auto-build-javaagent-jar.html
    todo Java底层防护 - OpenRASP核心源码浅析 https://xz.aliyun.com/t/7005
    todo Java RASP浅析——以百度OpenRASP为例 https://paper.seebug.org/513
    todo 浅谈 RASP https://paper.seebug.org/1041/
    todo appendToBootstrapClassLoaderSearch方法说明 https://www.oschina.net/uploads/doc/javase-6-doc-api-zh_CN/java/lang/instrument/Instrumentation.html#appendToBootstrapClassLoaderSearch(java.util.jar.JarFile)
    todo Instrumentation中文文档 https://www.oschina.net/uploads/doc/javase-6-doc-api-zh_CN/java/lang/instrument/Instrumentation.html#appendToBootstrapClassLoaderSearch(java.util.jar.JarFile)
    todo Instrumentation官方文档 https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/Instrumentation.html#appendToBootstrapClassLoaderSearch(java.util.jar.JarFile)
    todo JVMTI解读 https://blog.csdn.net/duqi_2009/article/details/94518203
    todo 深入理解Java类加载器(ClassLoader) https://blog.csdn.net/javazejian/article/details/73413292
todo Java中的Unsafe在安全领域的一些应用总结和复现 https://www.cnblogs.com/bitterz/p/15952315.html
    todo https://paper.seebug.org/1785
    todo https://tttang.com/archive/1436/
    todo https://www.cnblogs.com/rebeyond/p/15162264.html
