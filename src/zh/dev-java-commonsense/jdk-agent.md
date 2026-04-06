---
title: JDK Agent 功能
order: 66
---

Java Agent 是能够利用 jvm 提供的 Instrumentation API （Java1.5开始提供）实现字节码修改功能，具体能实现代码热更新、AOP、JVM监控等功能。

Java Agent 分为两种：

- 主程序运行前指定的Agent —— jdwp（Java Debugger Wire Protocol，Java调试线协议/远程调试工具）
- 主程序运行后指定的Agent —— Arthas（阿里的JVM性能调试工具）

> 参考：
>
> - <https://www.bilibili.com/video/BV1XP4y1N7ZF>

## 例子

### 主程序运行前指定的Agent

1、入口类需要定义有premain方法

```java
public class AgentTest {
  // 该方法在main方法前执行，与main方法运行在同一个JVM中
  // 被同一个System ClassLoader装载，被统一的安全策略（Security Policy）和上下文（Context）管理
  public static void premain(String agentOps, Instrumentation inst) {
    System.out.println("======= premain 方法执行");
    System.out.println("参数1： " + agentOps);
    System.out.println("参数2： " + inst);
  }
  // 如果上面方法存在，忽略该方法
  // 如果上面方法不存在，执行该方法
  public static void premain(String agentOps) {
    System.out.println("======= premain 方法执行");
    System.out.println("参数1： " + agentOps);
  }
}
```

2、在MANIFEST.MF配置环境参数

```bash
Manifest-Version: 1.0
Premain-Class: org.example.AgentTest
Can-Redefine-Classes: true
Can-Retransform-Classes: true
```

在maven项目中对应配置如下

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-jar-plugin</artifactId>
      <version>2.4</version>
      <configuration>
        <archive>
          <manifest>
            <addClasspath>true</addClasspath>
          </manifest>
          <manifestEntries>
            <Premain-Class>org.example.AgentTest</Premain-Class>
            <Can-Redefine-Classes>true</Can-Redefine-Classes>
            <Can-Retransform-Classes>true</Can-Retransform-Classes>
            <Manifest-Version>true</Manifest-Version>
          </manifestEntries>
        </archive>
      </configuration>
    </plugin>
  </plugins>
</build>
```

3、使用Agent程序： `-javaagent:AgentTest.jar=params` （这里params对应premain函数的agentOps参数值）
例如： `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005`

### 主程序运行后指定的Agent

探针程序可以在主程序启动后直接连接到已经启动的jvm中，从而实现例如动态替换类，查看加载类信息的一些功能。

1、入口类需要定义有agentmain方法

```java
// 例子：类热替换
public static void agentmain(String agentOps, Instrumentation inst) {
  System.out.println("===== agentmain 方法开始");
  String[] split = agentOps.split(",");
  String className = split[0];
  String classFile = split[1];
  System.out.println("替换类为： " + className);
  Class<?> redefineClass = null;
  Class<?>[] allLoadedClasses = inst.getAllLoadedClasses();
  for (Class<?> clazz : allLoadedClasses) {
    if (className.equals(clazz.getCanonicalName())) {
      redefineClass = clazz;
    }
  }
  if (redefineClass == null) {
    return;
  }
  // 热替换（redefine）
  try {
    byte[] classBytes = Files.readAllBytes(Paths.get(classFile));
    ClassDefinition classDefinition = new ClassDefinition(redefineClass, classBytes);
    inst.redefineClasses(classDefinition);
  } catch (ClassNotFoundException | UnmodifiableClassException | IOException e) {
    e.printStackTrace();
  }
  System.out.println("====== agentmain 方法结束");
}
```

2、在MANIFEST.MF配置环境参数

```bash
Manifest-Version: 1.0
Agent-Class: org.example.AgentTest
Can-Redefine-Classes: true
Can-Retransform-Classes: true
```

在maven项目中对应配置如下

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-jar-plugin</artifactId>
      <version>2.4</version>
      <configuration>
        <archive>
          <manifest>
            <addClasspath>true</addClasspath>
          </manifest>
          <manifestEntries>
            <Agent-Class>org.example.AgentTest</Agent-Class>
            <Can-Redefine-Classes>true</Can-Redefine-Classes>
            <Can-Retransform-Classes>true</Can-Retransform-Classes>
            <Manifest-Version>true</Manifest-Version>
          </manifestEntries>
        </archive>
      </configuration>
    </plugin>
  </plugins>
</build>
```

3、使用探针程序：1、找到要附加的`pid`（通过`jps`或者`ps -aux|grep java`命令）；2、通过VirtualMachine附加到目标程序

```java
public static void main(String[] args) throws Exception {
  VirtualMachine target = VirtualMachine.attach("96003"); // 目标PID
  String agentOps = "com.api.rcode.controller.HomeController,/User/xxx/com/api/rcode/controller/HomeController.class";
  target.loadAgent("/User/xxx/org/example/TestAgent.jar", agentOps);
  // 查看properties
  Properties agentProperties = target.getAgentProperties();
  System.out.println("agentProperties = " + agentProperties);
  Properties systemProperties = target.getSystemProperties();
  System.out.println("systemProperties = " + systemProperties);
  target.detach();
}
```

## 概念：探针修改Class的限制

- 主程序运行前指定的Agent
  - 除了类名外，可以更改任意内容（理论上类名也可以该，但是类名改了的话，ClassLoader会出问题）
- 主程序运行中指定的Agent
  - 不能修改Class的文件结构，及不能添加方法、不能添加字段，只能修改方法体的内容，否则会报错 `UnsupportedOperationException: class redefinition failed: attempted to change the schema (add/remove fields)`

## 概念：两种修改类的方法

- retransformClasses
  - 修改class
  - 在已存在的Class字节码文件上修改后再进行替换，类似于对class文件进行包装。

  ```java
  public class AgentPre {
    public static void premain(String agentOps, Instrumentation inst) {
      inst.addTransformer(new BaseTransformer());
    }
  }
  public class AgentDynamic {
    public static void agentmain(String agentOps, Instrumentation inst) {
      List<Class<?>> data = new ArrayList<>();
      for (Class<?> clazz : inst.getAllLoadedClasses()) {
        if ("org.example.controller.HelloController".equals(clazz.getName())) {
          data.add(clazz);
        }
      }
      inst.addTransformer(new BaseTransformer());
      inst.retransformClasses(data.toArray(new Class<?>[0]));
    }
  }
  // 一般是使用ASM、javassist子类的字节码操纵技术对字节码进行包装
  public class BaseTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, // 要转换的类定义加载程序
      String className, // Java虚拟机规范中定义的完全限定类和接口名称的内部形式的类名称。例如java/util/List
      Class<?> classBeingRedefined, // 原始的类定义。如果是由重定义或重传出发的，则被重定义或重传的类；如果是类加载，则为null
      ProtectionDomain protectionDomain, // 正在定义或重新定义的类的保护域
      byte[] classfileBuffer // 类格式的输入字节缓冲区，不得修改
    ) throws IllegalClassFormatException {
      // 例子：ASM
      ClassReader classReader = new ClassReader(classfileBuffer);
      PreClassVisitor preClassVisitor = new PreClassVisitor(new ClassWriter(ClassWriter.COMPUTE_MAXS)); // 自定义的类
      classReader.accept(preClassVisitor, 0);
      return preClassVisitor.toByteArray();
    }
  }
  ```

- redefineClasses
  - 重写定义class
  - 自身提供的Class字节码文件替换掉已存在的class文件

  ```java
  byte[] classBytes = Files.readAllBytes(Paths.get(classFile));
  ClassDefinition classDefinition = new ClassDefinition(redefineClass, classBytes);
  inst.redefineClass(classDefinition);
  ```
