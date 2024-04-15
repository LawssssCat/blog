---
title: 容器化微服务 Demo 项目开发笔记
date: 2024-04-06
category:
  - 示例项目
tag:
  - 微服务
order: 1
index: false
---

![](https://s2.loli.net/2024/04/07/1pGe5USA4aznZsg.png)

搭建 “课程管理系统” Demo，了解微服务架构玩法。

<!-- more -->

代码： <RepoLink path="/code/demo-microk8s/" />

编译 thrift 脚本

```bash title="script/gen-code.sh"
<!-- @include: @project/code/demo-microk8s/microservice/script/gen-code.sh -->
```

## 信息服务（Python）

::: tabs

@tab 接口定义语言

```go title="script/thrift/message.thrift"
<!-- @include: @project/code/demo-microk8s/microservice/script/thrift/message.thrift -->
```

@tab 服务器设置

```python title="message_service.py"
<!-- @include: @project/code/demo-microk8s/microservice/message-thrift-python-service/message_service.py -->
```

:::

## 用户服务

接口实现

::: tabs

@tab 接口定义语言

```go title="script/thrift/user.thrift"
<!-- @include: @project/code/demo-microk8s/microservice/script/thrift/user.thrift -->
```

@tab 接口实现

```java title="UserServiceImpl.py in user-thrift-java-service"
<!-- @include: @project/code/demo-microk8s/microservice/user-thrift-java-service/src/main/java/org/example/thrift/user/service/UserServiceImpl.java -->
```

@tab 数据库查询

```java title="UserMapper.java in user-thrift-java-service"
<!-- @include: @project/code/demo-microk8s/microservice/user-thrift-java-service/src/main/java/org/example/thrift/user/mapper/UserMapper.java -->
```

@tab 数据库定义

```java title="db/data.sql"
<!-- @include: @project/code/demo-microk8s/microservice/user-thrift-java-service/src/main/resources/db/data.sql -->
```

@tab 数据库数据

```java title="db/schema.sql"
<!-- @include: @project/code/demo-microk8s/microservice/user-thrift-java-service/src/main/resources/db/schema.sql -->
```

:::

服务器

```java title="ThriftServer.java in user-thrift-java-service"
<!-- @include: @project/code/demo-microk8s/microservice/user-thrift-java-service/src/main/java/org/example/thrift/user/thrift/ThriftServer.java -->
```
