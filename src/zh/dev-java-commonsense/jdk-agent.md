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

## 主程序运行前指定的Agent

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

## 主程序运行后指定的Agent

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
  // 热替换
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
