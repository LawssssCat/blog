---
title: JDK IO 功能
date: 2024-09-16
tag:
  - java
order: 66
---

JDK IO 使用

todo https://howtodoinjava.com/java/io/outputstream-to-inputstream/

## todo File

todo

Java BIO INPUTSTREAM/OUT/READER/WRITER/FILE/PIP/ZIP
JAVA NIO FileChannel/bytebuffer/mapbytebuffer
JAVA NIO2.0 PATHS/FILES/WATCH/

## todo Socket

参考： https://www.bilibili.com/list/watchlater?

bio 阻塞

`strace -ff -o ./out java BIOSocket.Java`

nio 非阻塞

IO 多路复用 select/poll/epoll

netty

aio 异步处理

## apache commons-io

参考： todo https://www.tutorialspoint.com/commons_io/commons_io_teeinputstream.htm

FileSystemUtils

todo

FileMonitor

todo

Stream Fork

```java
<!-- @include: @project/code/demo-java-common/demo-io/src/test/java/org/example/commonio/TeeIOStreamTest.java -->
```
