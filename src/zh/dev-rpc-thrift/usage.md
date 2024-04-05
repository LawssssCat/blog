---
title: Thrift 基本使用
date: 2024-04-05
category:
  - thrift
order: 3
---

记录 Thrift 的安装、使用。

<!-- more -->

参考：

- <https://www.cnblogs.com/fingerboy/p/6424248.html>

## 安装

Thrfit 是 c++ 编写的，源码存放在 [github 仓库](https://github.com/apache/thrift)上，一般需要源码编译安装。

:::: details 安装脚本

```bash
<!-- @include: @project/code/demo-thrift/setup.sh -->
```

::::

## 编译 IDL 文件

```bash
thrift -help

thrift -gen java user.thrift
thrift -gen cpp user.thrift
thrift -gen php user.thrift
thrift -gen js:node user.thrift

thrift -gen java -o target user.thrift
```

## 例子：简单 C/S 通信

代码： todo

### 生成接口文件

```bash
<!-- @include: @project/code/demo-thrift/script/thrift-gen.sh -->
```

```bash
# tree thrift-api/
thrift-api/
├── pom.xml
└── src
    └── main
        └── java
            └── com
                └── example
                    └── thrift
                        ├── User.java
                        └── UserService.java

9 directories, 3 files
```

### 客户端/服务端代码 java/java

引入依赖

```xml title="pom.xml"
<dependency>
  <groupId>org.apache.thrift</groupId>
  <artifactId>libthrift</artifactId>
  <version>0.20.0</version>
</dependency>
```

对于开发人员而言，使用原生的 Thrift 框架，需要关注以下四个核心内部接口类：

- Iface —— 服务端通过实现 UserService.Iface 接口，向客户端提供具体的同步业务逻辑。
- AsyncIface —— 服务端通过实现 UserService.Iface 接口，向客户端提供具体的异步业务逻辑。
- Client —— 客户端通过 UserService.Client 的实例对象，以同步的方式访问服务端提供的服务方法。
- AsyncClient —— 客户端通过 UserService.Client 的实例对象，以异步的方式访问服务端提供的服务方法。

::: tabs

@tab 服务端 - 处理层

```java title="UserServiceImpl.java in server"
<!-- @include: @project/code/demo-thrift/thrift-server/src/main/java/org/example/service/processor/UserServiceImpl.java -->
```

@tab 服务端 - 协议层/传输层

```java title="SimpleService.java in server"
<!-- @include: @project/code/demo-thrift/thrift-server/src/main/java/org/example/service/SimpleService.java -->
```

@tab 客户端 - 协议层/传输层

```java title="SimpleClient.java in client"
<!-- @include: @project/code/demo-thrift/thrift-client/src/main/java/org/example/client/SimpleClient.java -->
```

:::

### 客户端/服务端代码 python/java

服务端用上面的 java，下面写 python 客户端代码。

首先，下载 thrift 的 python 模块

```bash
pip install thrift
```

客户端 - 传输层/协议层/处理层

```py
<!-- @include: @project/code/demo-thrift/thrift-client-python/SimpleClient.py -->
```
