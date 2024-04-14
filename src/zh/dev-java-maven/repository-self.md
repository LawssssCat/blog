---
title: Maven 私有仓库
date: 2024-04-13
tag:
  - maven
order: 15
---

介绍 Maven 私有仓库的搭建和管理。

<!-- more -->

## Maven 私服

常见的 Maven 私服产品：

- Apache 的 Archiva
- JFrog 的 Artifactory
- Sonatype 的 Nexus （关系；联系；） —— 最流行、最广泛

### Nexus 安装、使用

官网： <https://help.sonatype.com/repomanager3/product-information>

下载： <https://help.sonatype.com/en/download.html>

选择版本下载，管理员启动

```bash
./nexus /run
```

`localhost:8081`

初始配置

```bash
# 更改默认密码！
右上角 login in
默认账户/密码
admin/按操作提示
```

默认仓库： 我们关心 maven 开头的仓库即可

```
maven-central（proxy） —— proxy=远程中心仓库的代理。设置界面可以设置代理的远程中心仓库地址
maven-public（group） —— group=主仓库。远程下载的jar包会存放在这个地址。
maven-releases（hosted） —— hosted=本地仓库。存放公司内部上传的jar包。releases=发布，正式版本。
maven-snapshots（hosted） —— snapshots=快照，测试版本。
nuget-group
nuget-hosted
nuget.org-proxy
```

#### 配置 maven 仓库

settings.xml

```xml
<mirror>
  <id>nexus-mine</id>
  <mirrorOf>central</mirrorOf>
  <name>Nexus mine</name>
  <url>http://localhost:8081/repository/maven-public/</url>
</mirror>
```

如果禁用了匿名访问，需要添加用户设置

```xml
<server>
  <id>nexus-mine</id>
  <username>admin</username>
  <password>xxx</password>
</server>
```

下载依赖、编译工程

```bash
mvn clean compile
```

##### 部署 jar 包

pom.xml

```xml
<!-- 管理工程部署位置配置 -->
<distributionManagement>
  <snapshotRepository>
    <!-- id 需要与 settings.xml 上一致 -->
    <id>nexus-mine</id>
    <name>Nexus mine</name>
    <url>http://localhost:8081/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

```bash
mvn clean deploy
```

#### 引用部署的 jar 包

pom.xml

```xml
<repositories>
  <repository>
    <id>nexus-mine</id>
    <name>nexus-mine</name>
    <url>http://localhost:8081/repository/maven-snapshots/</url>
    <!-- 是否使用 snapshot/relase 版本依赖 -->
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
    <releases>
      <enabled>true</enabled>
    </releases>
  </repository>
</repositories>
```
