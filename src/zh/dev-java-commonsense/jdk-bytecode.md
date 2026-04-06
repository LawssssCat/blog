---
title: Java字节码操作
order: 66
---

Java字节码操作工具：

- JDK原生
  - java-proxy —— Java原生代理接口，略
  - ClasFile —— Java22预览特性，有望将ASM替代
- 三方实现
  - ~~javassist —— 通过内置编译器方式生成字节码~~
  - [Java ASM](#tid-asm) —— 通过API方式生成字节码，已集成至JDK标准 （类似：AST，Abstract Syntax Tree，抽象语法树）
    - bytebuddy —— 基于ASM的库
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

## Spring AOP

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
