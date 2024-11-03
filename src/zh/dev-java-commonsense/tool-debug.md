---
title: 调试工具
date: 2024-06-01
tag:
  - java
order: 10
---

有用 Links:

- idea issue <https://youtrack.jetbrains.com/issues>

## Idea 远程调试

参考：

- <https://www.jetbrains.com/help/idea/attach-to-process.html>
- <https://www.jetbrains.com/help/idea/tutorial-remote-debug.html>
- ~~<https://blog.csdn.net/w8y56f/article/details/116493681>~~

远程调试（对应 IDEA 功能：Remote JVM Debug）使开发人员能远程跟踪程序运行堆栈并且动态添加调试断点。

远程调试方式：

- Attach Remote JVM（附加到远程 JVM）：远程 Jar 包启动侦听服务，IDEA 访问该侦听的端口/内存
- Listen Remote JVM（侦听远程 JVM）：IDEA 启动侦听服务，远程 Jar 包访问该侦听的端口/内存

### 模式：Attach Remote JVM

重点： `server=n,suspend=y`

```bash
-agentlib:jdwp=transport=dt_socket,server=n,address=lpc19:65535,suspend=y
```

### 模式：Listen Remote JVM

重点： `server=y,suspend=n`

不同的 JDK 版本需要设置不同的配置：

```bash
# JDK 9 or later
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9999
# JDK 5-8
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9999
# JDK 1.4.x
-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999
# JDK 1.3.x or earlier
-Xnoagent -Djava.compiler=NONE -Xdebug
-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999

java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9999 ./SwaggerDemo-0.0.1-SNAPSHOT.jar > app.log &
```

## 性能分析

### Linux 系统工具

#### strace

查看软件的系统调用

`strace -ff -o ./out java Test.Java`

### Arthas

参考 <https://www.bilibili.com/video/BV14z421Y7V6/>
