---
title: Spring Boot + Mybatis + H2 database 数据库
shortTitle: 整合 H2 数据库
date: 2024-04-06
category:
  - 示例项目
tags:
  - springboot
  - mybatis
  - h2database
---

整合 Spring Boot/Mybatis/H2 database 用来简单开发、测试。

<!-- more -->

参考：

- <https://www.javaguides.net/2019/08/spring-boot-mybatis-crud-h2-database-example.html>

## H2 介绍

H2 由纯 Java 编写的开源关系数据库，可以直接嵌入到应用程序中，不受平台约束，便于测试。同时提供了 web 界面用于管理数据库。

H2 支持运行三种模式

- **ServerMode（传统模式）**：需要配置本地（或远程）数据库

  ```bash
  jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>
  jdbc:h2:tcp://localhost/~/test
  jdbc:h2:tcp://dbserv:8084/~/sample
  jdbc:h2:tcp://localhost/mem:test
  jdbc:h2:ssl://<server>[:<port>]/[<path>]<databaseName>
  jdbc:h2:ssl://localhost:8085/~/sample;
  jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=TRUE # 是否允许混合模式，即同时支持多个网络连接
  ```

- **Embedded（嵌入式）**：数据库连接关闭时，数据与表结构依然存在

  ```bash
  jdbc:h2:[file:][<path>]<databaseName>
  jdbc:h2:~/test # 示数据库存储在用户主目录中以 “test” 开头的文件中
  jdbc:h2:file:/data/sample # 支持绝对位置
  jdbc:h2:file:C:/data/sample (Windows only)
  jdbc:h2:file:/path/to/your/database;IFEXISTS=TRUE # 如果文件已经存在，则IFEXISTS=TRUE会避免尝试重新创建数据库而引发错误
  ```

- **In-Memory（内存模式）**：数据库连接关闭时，数据与表结构删除。多个线程可以访问同一个数据库，但是数据只能在同一个进程中可见。

  ```bash
  jdbc:h2:mem:
  jdbc:h2:mem:<databaseName>
  jdbc:h2:mem:test_mem # 打开一个名为 “test_mem” 的内存数据库
  jdbc:h2:tcp://localhost/mem:db1
  jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1 # 最后一个数据库连接关闭后，DB_CLOSE_DELAY=-1 让数据库不会被自动关闭，这样可保持数据在应用重启之间仍然可用
  jdbc:h2:file:/data/mydb;CIPHER=AES # 数据库加密（如果需要的话）
  ```

::: info
通过指定 url 方式指定运行模式。
更详细的 url 写法参考官网：
https://www.h2database.com/html/features.html#database_url
:::

## 整合

代码： <RepoLink path="/code/demo-springboot-h2/" />

### 项目初始化

导入依赖

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
  <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

配置

```yml title="application.yml"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/application.yml -->
```

数据库操作

::: tabs

@tab 数据库结构

```sql title="classpath:db/schema.sql"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/db/schema.sql -->
```

@tab 数据库数据

```sql title="classpath:db/data.sql"
<!-- @include: @project/code/demo-springboot-h2/src/main/resources/db/data.sql -->
```

:::

启动后通过 <http://localhost:9088/h2-console> 可以进入 web 管理界面。

### 数据库增删改查

::: tabs

@tab 模板类

```java title="model/Employee.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/model/Employee.java -->
```

@tab 数据库查询接口

```sql title="repository/EmployeeMapper.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/repository/EmployeeMapper.java -->
```

@tab 执行增删改查

```sql title="DemoH2SpringbootApplication.java"
<!-- @include: @project/code/demo-springboot-h2/src/main/java/org/example/DemoH2SpringbootApplication.java -->
```

:::
