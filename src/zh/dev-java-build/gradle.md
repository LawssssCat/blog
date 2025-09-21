---
title: Gradle 用法
date: 2024-00-06
tag:
  - gradle
order: 66
---

gradle 和 maven 一样，都是 java 项目的构建工具

gradle 相比 maven 更脚本化，更便于扩展（通过 Groovy/Kotlin 语法）。

常见场景： Android, Java 开发

下载： https://gradle.org/releases/

参考：

- https://www.youtube.com/watch?v=Ajs8pTbg8as&list=PLWQK2ZdV4Yl2k2OmC_gsjDpdIBTN0qqkE

## 基本使用

gradle dsl <https://gradle.org/docs/current/dsl/>

```bash
$ gradle -v
------------------------------------------------------------
Gradle 8.9
------------------------------------------------------------

Build time:    2024-07-11 14:37:41 UTC
Revision:      d536ef36a19186ccc596d8817123e5445f30fef8

Kotlin:        1.9.23
Groovy:        3.0.21
Ant:           Apache Ant(TM) version 1.10.13 compiled on January 4 2023
Launcher JVM:  11.0.20 (Oracle Corporation 11.0.20+9-LTS-256)
Daemon JVM:    C:\Program Files\Java\jdk-11 (no JDK specified, using current Java home)
OS:            Windows 11 10.0 amd64
```

### build.gradle

#### 概念：Project

一个 Project 代表一个正在构建的组件（Jar/War 文件）。
当构建开始时，Gradle 会基于 `build.gradle` 实例化一个 `org.gradle.api.Project` 对象，并通过 project 变量来隐式调用成员。

Project 属性：

| 名称        | 类型       | 默认值                |
| ----------- | ---------- | --------------------- |
| project     | Project    | Project 实例          |
| name        | String     | 项目名称              |
| path        | String     | 项目绝对路径          |
| description | String     | 项目描述              |
| projectDir  | File       | 包含生成脚本的目录    |
| buildDir    | File       | `${projectDir}/build` |
| group       | Object     | 未指定                |
| version     | Object     | 未指定                |
| ant         | AntBuilder | AntBuilder 实例       |

```groovy
plugins {
    id 'groovy'
}

group = 'org.example' // 等同 project.group
version = '1.0-SNAPSHOT'

repositories {
    // 含义：先找本地，没有则找中央
    mavenLocal() // 本地库
    mavenCentral() // 中央仓库
}

dependencies {
    implementation 'org.apache.groovy:groovy:4.0.14'
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile) {
    options.encoding = "UTF-8"
}
```

#### 概念：Task

##### 任务定义

```groovy
// 自定义任务
task t1 {
    // 配置代码：在 build 阶段执行
    println '任务1'
    group("mytask") // 指定分组，默认 other 分组
    // 动作代码
    doFirst {
        println '动作1'
    }
    doLast {
        println '动作2'
    }
}
/*
上述语法等同于
task(t1, {...}) // 💡在 build 阶段会执行 “配置代码”
or
tasks { // 💡在 build 阶段会执行 “配置代码”
    task t1 {...}
}
or
tasks.create('t1') {...} // 💡在 build 阶段会执行 “配置代码”
or
tasks.register('t4') {...} // ❗通过 register 注册的任务，在 build 阶段不执行
or // 一次定义多个任务
1.times {index -> task("task${index}") {...}} // 💡在 build 阶段会执行 “配置代码”
*/
```

##### 任务依赖

```groovy
task a {
    doFirst {
        println '任务a'
    }
}
task b(dependsOn:a) {
    doFirst {
        println '任务b'
    }
}
task c {
    dependsOn 'b'
    doFirst {
        println '任务c'
    }
}
task d {
    doFirst {
        println '任务d'
    }
}
d.dependsOn c
```

#### 任务扩展

```groovy
clean.doLast {
    println 'clean 之后执行'
}
tasks.named('clean').get().doFirst {
    println 'clean 之前执行'
}
```

#### 概念：Plugin

todo 发布和使用自定义 jar 包 —— https://www.bilibili.com/video/BV1214y1r76z?p=48

todo 自定义插件 —— https://www.bilibili.com/video/BV1214y1r76z?p=49

### 多项目构建

todo https://www.bilibili.com/video/BV1214y1r76z/?p=51
