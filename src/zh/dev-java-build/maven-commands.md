---
title: Maven 命令行
date: 2024-04-13
tag:
  - maven
order: 2
---

介绍 Maven 的基本用法：目录结构、生命周期触发。

<!-- more -->

## 基本使用

Maven 通过 POM （Project Object Model，项目对象模型）文件管理项目构建

目录结构

```txt
pom.xml             —— 项目描述文件 【重点】
/src/main/java
/src/main/resources
/src/main/webapp    —— Web应用程序
/src/main/config
/src/test/java
/target
LICENSE.txt         —— 项目许可证
NOTICE.txt          —— 通知和归因
```

帮助

```bash
$ mvn help:describe -Dcmd=[PHASENAME]

$ mvn help:describe -Dcmd=clean
[INFO] 'clean' is a phase within the 'clean' lifecycle, which has the following phases:
* pre-clean: Not defined
* clean: org.apache.maven.plugins:maven-clean-plugin:2.5:clean
* post-clean: Not defined
```

```bash
# 编译命令
mvn clean install -Dcheckstyle.skip=true -DskipTests -Dmaven.test.skip=true

# 查看依赖书上
mvn dependency:tree

# 查看有效POM
mvn help:effective-pom

# 安装一个包到本地仓库
mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile>

# 部署一个包到远程仓库
mvn deploy:deploy-file \
-DgroupId=<group-id> \
-DartifactId=<artifact-id> \
-Dversion=<version> \
-Dpackaging=<type-of-packaging> \
-Dfile=<path-to-file> \
-DrepositoryId=<id-to-map-on-server-section-of-settings.xml> \
-Durl=<url-of-the-repository-to-deploy>
```
