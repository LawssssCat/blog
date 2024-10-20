---
title: JDK 介绍
date: 2024-10-24
tag:
  - java
order: 1
---

参考：

- 为什么还用 jdk8 https://developer.aliyun.com/article/1108370

## OpenJDK 发展

历史：

- 1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。
- 2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月 15 日正式发布 [OpenJDK](https://github.com/openjdk/jdk)。
- 2009 年，甲骨文（Oracle）公司宣布收购 Sun 公司，从此更名为 Oracle JDK。
- 2019 年 4 月 16 号 Oracle 宣布 JDK 开始商用收费，JDK 从 8u211 版本开始。

> OpenJDK 是由 OpenJDK Community 、Oracle、IBM 领导，连同 Alibaba，Amazon，Ampere，Azul，BellSoft，Canonical，Fujitsu，Google，Huawei，Intel，Java Community，JetBrains，London Java Community，Microsoft，Red Hat，SAP，SouJava，SUSE，Tencent，Twitter ，VMWare 等第三方共同开发、维护的 Java SE 开源参考实现。

## OpenJDK 发行版

OpenJDK builds，也叫 OpenJDK 发行版。所有的 JDK 都源自于 OpenJDK。所以严格意义上来说 Oracle JDK 也是 Open JDK 的一个发行版而已。

不同的是 Oracle JDK 持有 Java 商标，可以使用它以及宣传它，而 OpenJDK 不能使用 Java 商标（关键字）。这一点从 `java -version` 里能看出来：

```bash
# Oracle JDK
$ java -version
java version "11.0.20" 2023-07-18 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.20+9-LTS-256)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.20+9-LTS-256, mixed mode)

# RedHat OpenJDK
$ java -version
openjdk version "11.0.24" 2024-07-16
OpenJDK Runtime Environment (Red_Hat-11.0.24.0.8-2) (build 11.0.24+8)
OpenJDK 64-Bit Server VM (Red_Hat-11.0.24.0.8-2) (build 11.0.24+8, mixed mode, sharing)

# Ubuntu OpenJDK
$ java -version
openjdk version "17.0.12" 2024-07-16
OpenJDK Runtime Environment (build 17.0.12+7-Ubuntu-1ubuntu222.04)
OpenJDK 64-Bit Server VM (build 17.0.12+7-Ubuntu-1ubuntu222.04, mixed mode, sharing)

# Zulu OpenJDK
# Alibaba OpenJDK
```

为了保证多种 OpenJDK 发行版是 “靠谱” 的，[JCP（Java Community Process）](https://jcp.org) 规定发行版发布前需要通过 TCK 兼容性测试来认证该发行版是 “靠谱” 的。

> 阿里巴巴入选的 JCP 最高执行委员会，何方神圣？ —— https://mp.weixin.qq.com/s/F4y3GIVSFAVj4EmjAkJ4FQ?spm=a2c6h.12873639.article-detail.22.6b473f045jDP2G

| 发行版             | LTS   | 未改上游的构建 | TCK 测试 | 宽松式许可证 | 商业支持  |
| ------------------ | ----- | -------------- | -------- | ------------ | --------- |
| AdoptOpenJDK       | 是    | 可选           | 通过     | 是           | 可选(IBM) |
| Eclipse Temurin    | 是    | 可选           | 通过     | 是           | 可选(IBM) |
| Oracle OpenJDK     | 否 ❌ | 是 ✅          | 通过     | 是           | 否        |
| Oracle GraalVM CE  | 否 ❌ | 否             | 通过     | 是           | 否        |
| Oracle Java SE     | 是    | 否             | 通过     | 否 ❌        | 是 ✅     |
| IBM Java SDK       | 是    | 否             | 通过     | 否 ❌        | 是 ✅     |
| Red Hat OpenJDK    | 是    | 否             | 通过     | 是           | 是 ✅     |
| Linux 捆绑发行版   | 是    | 否             | 通过     | 是           | 否        |
| Azul Zulu          | 是    | 否             | 通过     | 是           | 可选      |
| Amazon Corretto    | 是    | 否             | 通过     | 是           | 可选(AWS) |
| Alibaba Dragonwell | 是    | 否             | 通过     | 是           | 否        |
| Tencent Kona       | 是    | 否             | 通过     | 是           | 否        |
| Huawei Bisheng     | 是    | 否             | 通过     | 是           | 否        |

### todo 对比各发新版

https://developer.aliyun.com/article/1108370

