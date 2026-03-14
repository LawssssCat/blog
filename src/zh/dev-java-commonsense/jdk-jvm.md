---
title: JDK 介绍
date: 2024-10-24
tag:
  - java
order: 1
---

Java 程序运行环境：
JRE仅提供运行时库、JVM（Java Virtual Machine，Java虚拟机）和其他一些运行Java应用程序所必须的组件。

- JRE（Java Runtime Environment） —— 包括了Java运行时的JVM、Libraries等。
- JDK（Java Development Kit） —— 包括了JRE的所有内容，并包含javac、jdb等开发者必须的编译器和调试器。

参考：

- 【方向盘】逐渐碎片化的Java生态圈：Oracle JDK、OpenJDK、阿里Dragonwell、华为毕昇 <https://developer.aliyun.com/article/1108370>

## OpenJDK

官网：
<https://www.oracle.com/java/technologies/downloads/>

### OpenJDK 发展

历史：

- 1996 年 1 月，Sun 公司发布了 Java 的第一个开发工具包，我们称它为 Sun JDK。
- 2006 年 Sun 公司在 JavaOne 大会上宣布将 Java 开源，并于 2009 年 4 月 15 日正式发布 [OpenJDK](https://github.com/openjdk/jdk)。
- 2009 年，甲骨文（Oracle）公司宣布收购 Sun 公司，从此更名为 Oracle JDK。
- 2019 年 4 月 16 号 Oracle 宣布 JDK 开始商用收费，JDK 从 8u211 版本开始。
  - 随着Oracle宣布“Oracle JDK商用收费”，Red Hat、AWS、Alibaba、Tencent宣布加入开源OpenJDK阵营
- 2021 年 9 月，Oracle 在发布 Java 17 时，开始提供一个不收费的许可模式（[NFTC协议](https://www.oracle.com/downloads/licenses/no-fee-license.html)）。
  - 该许可会在三年内免费提供季度更新，但该模式只限于该版本的迭代，允许用户免费使用，甚至包括商业用途（不能与付费产品捆绑在一起），而不适用于 Java 7、8和11等早期版本。
  - 这就是口口相传的Java 17开始“免费”了，但**只要你是从Oracle的网站上下载补丁和更新，那就得需要一个支持许可证，就（可能）需要收费**。
- 2022 年，各大厂无法接受“免费”Oracle JDK发行版带来的风险，也不愿意定期仅为Oracle JDK的使用买单。因此，纷纷推出自己的JDK发新版，这既能解决法律风险，也能培养一支能处理底层问题的技术团队。
  > 目前（2026年），OpenJDK 是由 OpenJDK Community 、Oracle、IBM 领导，连同
  > Alibaba，
  > Amazon，
  > Ampere，
  > Azul，
  > BellSoft，
  > Canonical，
  > Fujitsu，
  > Google，
  > Huawei，
  > Intel，
  > Java Community，
  > JetBrains，
  > London Java Community，
  > Microsoft，
  > Red Hat，
  > SAP，
  > SouJava，
  > SUSE，
  > Tencent，
  > Twitter，
  > VMWare
  > 等第三方共同开发、维护的 Java SE 开源参考实现。

### OpenJDK 发行版

OpenJDK builds，也叫 OpenJDK 发行版。

所有的 JDK 都源自于 OpenJDK。
所以严格意义上来说 Oracle JDK 也是 Open JDK 的一个发行版而已。
不同的是 Oracle JDK 持有 Java 商标，可以使用它以及宣传它，而 OpenJDK 不能使用 Java 商标（关键字）。
这一点从 `java -version` 里能看出来：

```bash
# Oracle JDK
# 下载地址： https://www.oracle.com/java/technologies/downloads/
$ java -version
java version "11.0.20" 2023-07-18 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.20+9-LTS-256)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.20+9-LTS-256, mixed mode)
# 上面打印的 java/Java(TM)/Java HotSpot(TM) 就是 Oracle 手握的商标，其他发行版无法使用，否则律师函警告。

# Eclipse Temurin （原 AdoptOpenJDK，后加入 Eclipse 基金会并改名）
# 官网： https://adoptopenjdk.net （原 AdoptOpenJDK 官网）
# 官网： https://adoptium.net
# todo 特点时提供两种虚拟机实现：HotSpot（基于标准OpenJDK构建）和OpenJ9（支持IBM的J9 JVM虚拟机），前者更成熟稳定，后者占用资源更少、启动更快，更适合容器化

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
$ java -version
openjdk version "21.0.5" 2024-10-15 LTS
OpenJDK Runtime Environment Zulu21.38+21-CA (build 21.0.5+11-LTS)
OpenJDK 64-Bit Server VM Zulu21.38+21-CA (build 21.0.5+11-LTS, mixed mode, sharing)

# Dragonwell OpenJDK （龙井 JDK）
$ java -version
openjdk version "21.0.4.0.4" 2024-07-16
OpenJDK Runtime Environment (Alibaba Dragonwell Extended Edition)-21.0.4.0.4+7-GA (build 21.0.4.0.4)
OpenJDK 64-Bit Server VM (Alibaba Dragonwell Extended Edition)-21.0.4.0.4+7-GA (build 21.0.4.0.4, mixed mode, sharing)

# BiSheng OpenJDK （毕昇 JDK）
# 下载地址： https://www.hikunpeng.com/developer/devkit/download/jdk
$ java -version
bin/java -version
openjdk version "21.0.9" 2025-10-21
OpenJDK Runtime Environment BiSheng (build 21.0.9+11)
OpenJDK 64-Bit Server VM BiSheng (build 21.0.9+11, mixed mode, sharing)
```

### JCP（Java Community Process）

为了保证多种 OpenJDK 发行版是 “靠谱” 的，社区提供 [JCP（Java Community Process）](https://jcp.org) 机制。
在发行版发布前，只需要通过 [TCK 兼容性测试](https://mp.weixin.qq.com/s/F4y3GIVSFAVj4EmjAkJ4FQ?spm=a2c6h.12873639.article-detail.22.6b473f045jDP2G)，既可认证该发行版是 “靠谱” 的。

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

