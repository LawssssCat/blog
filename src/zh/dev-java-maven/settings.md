---
title: Maven 配置
date: 2024-04-13
tag:
  - maven
order: 10
---

介绍 maven settings.xml 和 pom.xml 配置文件的使用。

<!-- more -->

**settings.xml**

settings.xml 是用来设置 maven 参数的配置文件。
settings.xml 中包含类似本地仓储位置、修改远程仓储服务器、认证信息等配置。

**pom.xml**

settings.xml 是 maven 的全局配置文件，而 pom.xml 文件是所在项目的局部配置。

## 配置文件位置

pom.xml 文件一般位于项目根目录。

settings.xml 文件一般存在于两个位置：

- 全局配置: `${M2_HOME}/conf/settings.xml`
- 用户配置： `~/.m2/settings.xml`

## 配置优先级

核心： **局部配置优先于全局配置**。

配置优先级从高到低： pom.xml > user settings > global settings

::: info
如果这些文件同时存在，在应用配置时，会合并它们的内容，如果有重复的配置，优先级高的配置会覆盖优先级低的。
:::

```bash
mvn -X # 查看 settings.xml 文件的读取顺序
mvn help:effective-settings # 查看当前生效的 settings.xml
```

## settings.xml 元素详解

todo https://www.cnblogs.com/jingmoxukong/p/6050172.html?utm_source=gold_browser_extension
