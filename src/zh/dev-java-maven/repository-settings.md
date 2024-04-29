---
title: Maven 配置：仓库管理
date: 2024-04-13
tag:
  - maven
order: 10
---

介绍 Maven 仓库的概念、配置。

<!-- more -->

## Maven 仓库

Maven 仓库是用来存放项目中依赖的软件包（jar、war、pom）和元素据（坐标信息、源码、作者、SCM、许可证）等信息

<SiteInfo
  name="Maven 仓库设置"
  url="https://www.runoob.com/maven/maven-repositories.html"
  preview="/assets/images/cover3.jpg"
/>

Maven 仓库有三种类型：

- **本地（Local）** —— 本地缓存
- **远程（Remote）** —— 企业级内部构建的仓库，主要是为了统一管理和加快下载速度
- **中央（Central）** —— 开源仓库

优先级： 本地 > 远程 > 中央

### 配置远程仓库列表

如果 Maven 在中央仓库中也找不到依赖的文件，它会停止构建过程并输出错误信息到控制台。
为避免这种情况，Maven 提供了远程仓库的概念，它是开发人员自己定制仓库，包含了所需要的代码库或者其他工程中用到的 jar 文件。

::: info
在 pom.xml 或 settings.xml 的 profile 中配置
:::

```xml
<repositories>
  <!--包含需要连接到远程仓库的信息 -->
  <repository>
    <!--远程仓库唯一标识 -->
    <id>codehausSnapshots</id>
    <!--远程仓库名称 -->
    <name>Codehaus Snapshots</name>
    <!--如何处理远程仓库里发布版本的下载 -->
    <releases>
      <!--true或者false表示该仓库是否为下载某种类型构件（发布版，快照版）开启。 -->
      <enabled>false</enabled>
      <!--该元素指定更新发生的频率。Maven会比较本地POM和远程POM的时间戳。这里的选项是：always（一直），daily（默认，每日），interval：X（这里X是以分钟为单位的时间间隔），或者never（从不）。 -->
      <updatePolicy>always</updatePolicy>
      <!--当Maven验证构件校验文件失败时该怎么做-ignore（忽略），fail（失败），或者warn（警告）。 -->
      <checksumPolicy>warn</checksumPolicy>
    </releases>
    <!--如何处理远程仓库里快照版本的下载。有了releases和snapshots这两组配置，POM就可以在每个单独的仓库中，为每种类型的构件采取不同的策略。例如，可能有人会决定只为开发目的开启对快照版本下载的支持。参见repositories/repository/releases元素 -->
    <snapshots>
      <enabled />
      <updatePolicy />
      <checksumPolicy />
    </snapshots>
    <!--远程仓库URL，按protocol://hostname/path形式 -->
    <url>http://snapshots.maven.codehaus.org/maven2</url>
    <!--用于定位和排序构件的仓库布局类型-可以是default（默认）或者legacy（遗留）。Maven 2为其仓库提供了一个默认的布局；然而，Maven 1.x有一种不同的布局。我们可以使用该元素指定布局是default（默认）还是legacy（遗留）。 -->
    <layout>default</layout>
  </repository>
</repositories>
```

### 配置插件远程仓库列表

和 repository 类似，只是 repository 是管理 jar 包依赖的仓库，pluginRepositories 则是管理插件的仓库。
maven 插件是一种特殊类型的构件。由于这个原因，插件仓库独立于其它仓库。

::: info
在 pom.xml 或 settings.xml 的 profile 中配置
:::

```xml
<pluginRepositories>
  <!-- 包含需要连接到远程插件仓库的信息.参见profiles/profile/repositories/repository元素的说明 -->
  <pluginRepository>
    <releases>
      <enabled />
      <updatePolicy />
      <checksumPolicy />
    </releases>
    <snapshots>
      <enabled />
      <updatePolicy />
      <checksumPolicy />
    </snapshots>
    <id />
    <name />
    <url />
    <layout />
  </pluginRepository>
</pluginRepositories>
```

### 配置镜像仓库列表

为仓库列表（repositories）配置的下载镜像列表（mirrors）。

::: info
在 settings.xml 中配置
:::

maven 仓库的搜索顺序如下：

`local_repo` > `settings_profile_repo` > `pom_profile_repo` > `pom_repositories` > `settings_mirror` > `central`

> 在同一个配置向中，先后定义的仓库优先级不同。
> 从上到下，上面的优先级更高，优先从更高优先级的仓库下载依赖。

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd">
  ...
  <mirrors>
    <!-- 给定仓库的下载镜像。 -->
    <mirror>
      <!-- 该镜像的唯一标识符。id用来区分不同的mirror元素。 -->
      <id>planetmirror.com</id>
      <!-- 镜像名称 -->
      <name>PlanetMirror Australia</name>
      <!-- 该镜像的URL。构建系统会优先考虑使用该URL，而非使用默认的服务器URL。 -->
      <url>http://downloads.planetmirror.com/pub/maven2</url>
      <!-- mirrorOf 可以理解 “为某个仓库（repository）的做镜像” -->
      <!-- 值为被镜像的仓库（repository）的 id （包括 repositories 和 pluginRepositories） -->
      <!-- e.g. central = 设置为 Maven 中央仓库（http://repo.maven.apache.org/maven2/）的镜像仓库 -->
      <!-- e.g. * = 设置为所有仓库的镜像仓库 —— 相当于拦截了所有通往 repository 的请求，重定向到 mirror 地址 -->
      <!-- e.g. external:* = 设置为远程仓库（remote repository）的镜像仓库 —— 如果本地库存在就用本地库的，如果本地没有所有下载就用 mirror 配置的 url 下载 -->
      <!-- e.g. *,!my-repo-id = 设置除 my-repo-id 外的所有仓库的镜像仓库  -->
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
  ...
</settings>
```

e.g.

```xml
<!-- 加速：国内镜像源 -->
<mirror>
  <id>alimaven</id>
  <name>aliyun maven</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  <mirrorOf>central</mirrorOf>
</mirror>

<!-- 默认 -->
<!-- apache-maven 的 settings.xml 不做任何配置时，默认的 central 仓库就是 https://repo.maven.apache.org/maven2/ -->
<mirror>
  <id>defaultmaven</id>
  <name>default maven</name>
  <url>https://repo.maven.apache.org/maven2/</url>
  <mirrorOf>central</mirrorOf>
</mirror>
```

## Maven 国内镜像源

```
https://maven.aliyun.com/nexus/content/groups/public/
https://maven.aliyun.com/nexus/content/repositories/central/
https://maven.ibiblio.org/maven2/
https://mirrors.ibiblio.org/pub/mirrors/maven2/
https://mirrors.huaweicloud.com/repository/maven/
https://repository.jboss.org/nexus/content/groups/public/
https://maven.net.cn/content/groups/public/
https://maven.oschina.net/content/groups/public/
https://central.maven.org/maven2/
https://repo1.maven.apache.org/maven2/
https://repo1.maven.org/maven2/
https://repo2.maven.org/maven2/
```

---

参考：

- maven | Maven Repositories - https://maven.apache.org/repositories/
- maven | Using Mirrors for Repositories - <https://maven.apache.org/guides/mini/guide-mirror-settings.html>
- cnblogs | 浅谈 maven setting.xml 设置的 mirrorof 标签作用 - <https://www.cnblogs.com/kelelipeng/p/11733903.html>
