---
title: Maven 基本概念
date: 2024-04-13
tags:
  - java
  - maven
order: 1
---

Maven 是一个自动化的构建工具，主要用于 Java 项目。

Maven 的主要目标是让开发人员能够在最短时间内完成开发工作的完整状态：

- 简化构建过程
- 统一构建环境
- 整合构建信息
- 易扩展

<!-- more -->

## Maven 构建生命周期

Maven 将一个整体的构建任务划分为了多个的阶段。每个阶段的具体工作由一个个对应的 Maven 插件执行。

构建阶段分三个周期：

1. clean —— 清理项目构建结果
   - pre\-clean
   - clean
   - post\-clean
1. default/build —— 部署项目的构建处理
   - 构建
     - validate
     - initialize
     - generate\-sources
     - process-sources
     - generate\-resources
     - process\-resources
     - compile
     - process\-classes
   - 测试
     - generate\-test\-sources
     - process\-test\-sources
     - generate\-test\-resources
     - process\-test\-resources
     - test\-compile
     - process-test\-classes
     - test `mvn test`
   - 打包
     - prepare\-package
     - package `mvn package`
     - pre\-integration\-test
     - integration\-test
     - post\-integration\-test `mvn integration-test`
     - verify
     - install
     - deploy `mvn deploy`
1. site —— 生成项目站点的文档
   - pre-site
   - site
   - post-site `mvn site`
   - site-deploy

::: tip
Maven 生命周期由 `maven-core` 模块的 components.xml 文件定义
:::

## 依赖

### 作用域 scope

作用域定义依赖的存在时期

| 作用域（Scope） | 编译（Compile） | 测试（Test） | 运行（Run） | 打包（Package） | 示例               |
| --------------- | --------------- | ------------ | ----------- | --------------- | ------------------ |
| compile（默认） | ✔               | ✔            | ✔           | ✔               | spring             |
| provided        | ✔               | ✔            | ❌          | ❌              | lombok             |
| test            | ❌              | ✔            | ❌          | ❌              | junit              |
| runtime         | ❌              | ✔            | ✔           | ✔               | tomcat、JDBCDriver |
| system          | ✔               | ✔            | ❌          | ❌              | asm                |

### 依赖管理

todo 优先级、版本范围指定、版本固定之类的东西

使用 dependencyManagement 管理依赖版本。
在 dependencyManagement 中申明的 dependencies 不会被实际下载 jar 包，只有当该 jar 包被实际依赖后，才会去下载对应的 jar 包版本。

```xml
<!-- 只是对版本进行管理，不会实际引入jar -->
<dependencyManagement>  
      <dependencies>  
            <dependency>  
                <groupId>org.springframework</groupId>  
                <artifactId>spring-core</artifactId>  
                <version>3.2.7</version>  
            </dependency>  
    </dependencies>  
</dependencyManagement>  
  
<!-- 会实际下载jar包 -->
<dependencies>  
       <dependency>  
                <groupId>org.springframework</groupId>  
                <artifactId>spring-core</artifactId>  
       </dependency>  
</dependencies>  
```

## Maven 仓库

Maven 仓库是用来存放项目中依赖的软件包（jar、war、pom）和元素据（坐标信息、源码、作者、SCM、许可证）等信息

Maven 仓库有三种类型：

- **本地（Local）** —— 本地缓存
- **远程（Remote）** —— 企业级内部构建的仓库，主要是为了统一管理和加快下载速度
- **中央（Central）** —— 开源仓库

优先级： 本地 > 远程 > 中央

仓库配置：

一般在 `.m2/repository/settings.xml` 上配置远程仓库，也可以在 pom.xml 上配置。
优先级： pom.xml > user settings > global settings

```bash
mvn -X # 查看 settings.xml 文件的读取顺序
mvn help:effective-settings # 查看当前生效的 settings.xml
```

---

参考：

- [(0) 用 Maven 建立專案](https://medium.com/learning-from-jhipster/0-%E7%94%A8maven%E5%BB%BA%E7%AB%8B%E5%B0%88%E6%A1%88-1f504f9a712b)
- [(1) Maven 的生命週期(Phase, Plugin, Goal)](https://medium.com/learning-from-jhipster/1-maven%E7%9A%84%E7%94%9F%E5%91%BD%E9%80%B1%E6%9C%9F-phase-plugin-goal-d69a2591dc45)
- [(2) 使用 Maven 將 Plugin Goals 綁定至 Phase](https://medium.com/learning-from-jhipster/2-%E5%B0%87-plugin-goals-%E7%B6%81%E5%AE%9A%E8%87%B3-phase-13c6b6b8d9bd)
- [(3) 使用 Maven Wrapper](https://medium.com/learning-from-jhipster/3-%E4%BD%BF%E7%94%A8-maven-wrapper-f4b7e460278)
