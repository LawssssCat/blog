---
title: 依赖软件选型
date: 2023-10-01
order: 1
---

理解后可以尝试 “[自由软件许可证知识测验](https://linuxtoy.org/archives/free-software-licensing-quiz.html)”。

---

## 依赖识别

无明确构建配置文件的项目，如 C/C++ 使用 Makefile 类的项目，则根据编译过程中具体执行的依赖行为进行判别。

存在构建配置文件的项目，如 Java 的 `pom.xml` 文件，通过对应的依赖管理器作为识别依据。

> **为什么只能作为依据？**
>
> 存在跳脱依赖管理器识别规则的依赖，如 Spring 的 `spring-boot-loader` 依赖是通过 `spring-boot-maven-plugin` 在打包时注入的，没法通过 maven 识别出来。

e.g.

```bash
# java
mvn dependencies

# python
pipenv graph

# go
go mod graph
```

| 语言       | 包管理工具 | 依赖配置文件     | groupId | packageName | version |
| ---------- | ---------- | ---------------- | ------- | ----------- | ------- |
| Java       | Maven      | pom.xml          | ✅      | ✅          | ✅      |
| Java       | Gradle     | build.gradle     |         | ✅          | ✅      |
| JavaScript | NPM        | package.json     |         | ✅          | ✅      |
| Python     | PyPi       | requirements.txt |         | ✅          | ✅      |
| Go         | gomod      | go.mod           |         | ✅          | ✅      |
| Rust       | cargo      | Cargo.toml       |         | ✅          | ✅      |

## 依赖软件选型能力重要性

从能力划分上看，无论设计/开发/测试，高级工程师必备依赖选型能力。

| 能力划分 | 入门                             | 初级                                      | 中级                              | 高级                              |
| -------- | -------------------------------- | ----------------------------------------- | --------------------------------- | --------------------------------- |
| 设计     | 特性分析                         | 跨模块特性分析                            | 软件技术规划（BP，Business Plan） | 软件技术洞察（SP，Strategy Plan） |
| 开发     | 编写核心代码                     | Committer                                 | Maintainer                        | 架构、规划                        |
| 测试     | 开发者测试（DT，Developer Test） | 系统设计验证（SDV，System Design Verify） | 测试框架设计                      | 测试流程设计                      |

## 软件定义

todo

## 软件信息

基本信息：

- 基本属性 —— 名称、语言、官网、作者、社区地址
- 版本属性 —— 版本编号、证书（license）、发布时间（Release Date）、源码、编译二进制、数字签名、版权（copyright）

开源社区 EOL 时间： （大部分社区无 EOL 时间，采用滚动演进式发布）

- Spring Boot 1Y
- PHP 3Y
- Node.js 3Y
- Kernel 4~6Y
- ...

生态：

- 软件供应商 —— 百度（baidu）、阿里（alibaba）、华为（huawei）、红帽（RedHat）、英特尔（Intel）、...
- 软件社区/基金会 —— Linux、Apache、Eclipse、OpenStack（Open Infrastructure）、mozilla、Linaro、OpNFV、...
- 云原生社区 —— [cncf](https://www.cncf.io/enduser/)

质量：

- 不安全函数数量/密度
- 圈复杂度
- 重复度
- 调试能力（是否可开启/关闭）
- 自动化构建能力

### 名称

项目名称： `[组织] + 软件名称`

### 开发商

项目的主要版权所有者/管理者/发布者，可以为组织/个人。

### 官网地址

官网/托管地址

### 源码仓地址

代码托管仓库地址

- git 地址
- 源码下载地址
- 二进制下载地址

### 主要语言

- C/C++
- Java
- JavaScript
- Python
- Go

- Rust
- TypeScript
- Scala
- Lua
- Dart
- Swift
- Erlang

扫描工具： cloc

### Copyright

e.g.

```txt
Copyright (c) XXXX人名/公司名
Copyright © XXXX人名/公司名
Copyright XXXX人名/公司名
(c) XXXX人名/公司名
© XXXX人名/公司名
```

### License

开源协议

#### GPLv2，General Public License 【传染性】

##### GPL 版本

GPL 总共有三个版本

- GPLv1：1989 年 2 月 25 日发布。
- [**GPLv2**](https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt)（[FAQ](https://www.gnu.org/licenses/gpl-faq.html)）：1991 年 6 月发布。
- GPLv3：2007 年 6 月 29 日发布。

每一个版本之间是有区别的，大部分使用 GPL 开源软件都是使用 GPLv2 的开源协议，因此默认下面说的都是 GPLv2 的规则。

##### GPLv2 生效范围

生效范围：

- 产品属于 “GPL 衍生作品（a work based on the Program）”

  是否属于 GPL 衍生作品，有以下两种情况

  1. 产品直接使用 GPL 代码或者使用 GPL 静态库 —— 产品属于 GPL 衍生作品
  1. 产品动态链接 GPL 动态库 —— 如果一起分发，则属于 GPL 衍生作品；如果不是一起分发，则 “可能” 以 “独立性和可区分性”（Separate and Independent） 为由不属于 GPL 衍生作品

- 产品对外分发（redistribution）或分销

使用 GPL 的软件如： Linux Kernel / Busybox / openjdk / ...

> **Linux 内核使用 GPL 引发的问题：**
>
> 理论上一个 Linux GPL 的操作系统核心运行在 “ 内核空间 ” ，上层的类库、框架、服务、应用运行在 “ 用户空间 ” 。
> 用户空间上的任何服务不可避免的需要 Linux 内核的头文件，进行系统调用，因此，中间层服务必须遵循 GPL 进行开放源代码。
> 调用中间服务层的框架或者其他服务使用了 GPL 的类库，因此，也必须是 GPL 的。
> 同理，上层应用也被 “ 传染 ” ，必须是 GPL 的。
> 于是，理论上从内核到驱动到中间服务到上层应用，形成了一个 GPL 一体化软件授权的软件发布整体。
> 可以认为，这个整体上任何开发成果都是 GPL 的，除非极少数的例外程序能够证明自身独立于系统的 GPL 环境。
> 这样的一个 “软件闭包” 排斥的商业化的软件模块以及 “想要钱” 普通开发者，将整个软件世界划分为 “ GPL 与 GPL 兼容的” 的和非 GPL 的，每个开发从业者面临着选择，要么 Linux + GPL ，要么 Linux 与你无关。
>
> 针对上述情况，有三个主要的问题：
>
> 1. **使用 Linux 内核的头文件定义，进行系统调用的程序是否会被定性为衍生产品？**
>    对该问题已有定论： 否 —— “普通系统调用为非 GPL 的作用范围” （系统调用可以理解为 Linux API 的接口调用） —— 这被 Linus Torvalds 以及内核开发人员多次澄清，甚至固化在 Linux 内核的源码 COPYING 文档中，为 Linux 用户空间的程序采用非 GPL 的授权许可证打下了基础。
> 2. **链接使用了其他 GPL 的类库的程序是否会被定性为衍生产品？**
>    对该问题也已有定论： 是 —— 一旦程序使用了 GPL 的模块，本身即被传染，程序必须成为 GPL。若要规避，也是可能的：必须证明主程序与 GPL 模块之间具有 “独立性和可区分性”（Separate and Independent）。
> 3. **Linux 内核动态载入的模块 LKM（Loadable Kernel Modules）是否会被定性为衍生产品，以 LKM 形式开发的 Linux 驱动程序是不是衍生产品？**
>    对该问题仍有争论： 硬件厂商如图形显示设备厂商 AMD/ATI、NVidia 出自硬件规格保密以及知识产权的考虑，长期以二进制软件包的方式独立发布图形驱动，涉嫌违反了 Linux 内核开放源代码的软件授权协议 GPL。这至今仍是 Unity 与 Gnome 3 等依赖于硬件图形加速的新型桌面技术发展上的一大阴影。

##### GPLv2 开源义务

“GPL 衍生作品” 开源义务：

- 产品本身需要 GPL 开源
- 产品使用的静态链接库（Linux 的`.a`和 Windows 的`.lib`）需要 GPL 开源
- 产品使用的动态链接库（Linux 的`.so`，Windows 的`.dll`）需要 GPL 兼容（即 GPL 软件中可以使用的库。[link:List of FSF（Free Software Foundation） approved software licenses](https://en.wikipedia.org/wiki/Comparison_of_free_and_open-source_software_licenses)）

##### 规避方式

- 产品不分发 —— 用户使用产品时，没有直接获取软件本身
- 让不属于 GPL 衍生作品
  - 插件模式 —— 通过动态链接形式调用 GPL 功能；分发时，该动态链接以 “插件” 形式分开分发
  - 进程隔离 —— 通过进程间通信来交互（如通过插件、API 调用等方式与 GPL 软件交互）

规避例子：

- **Android 模式** —— 把 GPL 局限在内核空间

  - 应用软件层面： Linux 操作系统之上构建软件一般都会需要 c 运行时库的支持即 GPL 协议的 glibc。而 Android 采用了 Free BSD 的 Bionic Libc 作为替代的运行时库，该类库上层更是采用 “温和” 的 Apache-2.0 的软件授权进行发布；
  - 驱动层面： Android 采用了一个 HAL（Hardware Abstraction Layer） 层，将用户驱动运行在 userspace，通过系统调用完成驱动，把本来跟 Linux 内核一起运行的驱动变成了驱动运行在 Linux 之上；

  > 依据：
  > 用户空间（userspace）的类库以及程序使用 Linux 内核的系统调用不被视为是 Linux 内核的衍生产品

- **OpenJDK 模式** —— 把 GPL 限制在虚拟机层面

  - 虚拟机（JVM，HotSpot）使用 GPLv2 协议；
  - 类库和 JVM 的 API 调用封装使用 [GPLv2 + CE，GPL with Classpath Exception](https://openjdk.org/legal/gplv2+ce.html) 协议 —— 这种协议允许后面的开发者，如果只是 `import` OpenJDK 的类库或者 JVM 的 API 调用封装的话，可以不受 GPL 限制。
    甚至可以闭源，如 WebLogic, WebSphere
    这也是为什么 Oracle JDK 可以兜售商业许可的原因
    另外，GPL with Classpath Exception 规定如果修改了 OpenJDK 源码然后还还二次分发，则必须继承协议，这有效规范 JVM 的实现，即减少魔改 JDK（如 Scala / Kotlin / Clojure / ...） 的出现。

  > 依据：
  >
  > - JVM 使用 GPL 动态/静态链接库，所以必须 GPL 协议
  > - 类库以及 JVM 的 API 调用与 JVM 甚至没有链接关系，它们只是在 JVM 上运行（也就是被 JVM 调用），所以不再 GPL 协议生效范围内

- **SaaS 模式** —— GPLv2 规定任何人都可以以提供技术服务为目的，运行私有修改的 GPL 许可下的程序，只要不发布软件，则不需要公开源代码。
  由于在 SaaS 服务场景中，用户通过云端访问软件，软件商不存在将软件源代码提供给用户的实质动作，也就没有触及 GPL 协议中“分发（Distribute）”的概念，也因此，GPL 协议被认为是拥有“SaaS Loophole”（SaaS 服务漏洞）。

  在很长一段时间内，没有成规模的开源许可证可以针对性地对 SaaS 服务进行约束，因而 GPL 类开源软件的传染性对 SaaS 场景失效。

  更多： 为 SaaS 而生的 AGPL 和 SSPL 协议

- **插件模式** —— 通过动态链接，以 “独立性和可区分性”（Separate and Independent） 来避免被认为是 “GPL 衍生作品”。

#### AGPLv3，GNU Affero General Public License

面对 SaaS 服务的崛起，出于保护其自研软件的目的，Affero Inc.发布了 AGPL v1，以限制 SaaS 服务提供商对其自研软件的自由使用。
自由软件基金会（FSF，Free Software Foundation）又在此基础上发布了 AGPL v3，后续被广泛应用到各个开源软件中。

根据 AGPLv3 协议第 13 条的约定：
如果对 AGPL 开源软件进行了修改（Modify），则需要向所有通过计算机远程网络交互的用户免费提供相应的源代码。

> **“通过计算机远程网络交互” 的场景**：
> 包括网络和邮件服务器、基于互动的网络应用程序和在线播放的游戏服务器等，当然这也包括 SaaS 服务。

> **对 AGPL 的修改**：
> 在 SaaS 应用场景下，触发 AGPL 协议传染性的条件是对原 AGPL 程序进行了“修改（Modify）”。
> 也就是如果对 AGPL 程序进行了修改，那么需要开源包括 AGPL 修改版程序在内的帮助运行 SaaS 服务的所有程序的源代码，也就是进行整体开源。
> 在实践中，大多数 SaaS 服务商为了避免自研代码泄露的风险，对 AGPL 软件一律采取强硬的拒绝态度。例如，谷歌在其内部政策中明确禁止开发者使用 AGPL 软件。

涉及软件： Humhub / Grafana / MongoDB（后改用 SSPL） / ...

规避方式： 同 GPLv2

#### SSPL，Server Side Public License

> 进阶的 AGPL：SSPL

2018 年，MongoDB Inc. 宣布其开发的 MongoDB 开源软件不再适用 AGPL v3，而是改用 SSPL 开源。

SSPL 许可协议第 13 条中明确规定：
如果将 SSPL 程序或程序的修改版本的功能作为服务向第三方提供时，则需要提供服务源代码。

> **扩大的生效范围**：
> SSPL 不再将 “修改（Modify）” 作为触发开源传染性的条件。
> 而是明确了，如果在为 SaaS 服务开发的软件中使用了 SSPL 软件，那么需要把所有使该软件能运行成 SaaS 服务的相应源码都进行开源。
>
> **扩大的开源范围**：
> 第 13 条明确了需要开源的 “服务源代码” 包括但不限于 “管理软件、用户界面、应用程序接口、自动化软件、监控软件、备份软件、存储软件和托管软件，所有这些都是为了让用户可以使用您提供的服务源代码运行服务实例”，也就是说开源范围不仅仅包括了本程序的源码、还要包括和本程序配套使用的所有程序的源码。
> ~~粗暴的理解： 如果提供服务的 “本程序” 中包含了 SSPL 组件，“你就要大方地把提供服务的整个配套程序（包括前后台）都贡献出来”。~~

涉及软件： MongoDB / Elasticsearch / ...

规避方式：

- 仅内部使用，不分发
- 购买商业协议

#### LGPL，Lesser GPL

LGPL 定义： 如果修改 LGPL 发布的库，新的库必须以 LGPL 发布。如果仅是动态链接，那么则不受任何限制。

生效范围：

- 产品使用
- 产品分发

开源义务：

- 如果与 LGPL 静态链接，则必须以 LGPL 开源
- 如果与 LGPL 动态链接，则不受限制

例如： Hibernate / glibc / ...

规避方式：

- 动态链接引用

#### MPL 类，如：MPL/EPL/...

生效范围：

- 源码修改
- 产品使用
- 产品分发

开源义务：

- 若无修改，则无需开源
- 若修改了，则需开源修改部分

例如： FireFox / Eclipse / ...

规避方式：

- 不修改

#### BSD 类：Apache/BSD/MIT/...

##### Apache 2.0

- Apache 2.0 发布的软件代码，而不需要开放源代码，只需要提及代码的原出处就可以。例如： Android / Tomcat / Spring / ...

## 版本信息

### 版本号

非正式版：

- alpha
- beta
- RC + num
- master
- trunk
- r + num
- ...

## 选型风险

### 专利风险

专利问题： todo

> e.g.
> 专利流氓 SCO（Santa Cruz Operation）公司曾对 IBM 公司发起专利起诉，并获利 1425 万美金。

[OIN（Open Invention Network）组织](https://openinventionnetwork.com/linux-system/)：
防御性的专利池社区。
OIN 成员间可进行 Linux 相关专利的交叉许可。
任何从事 Linux、GNU、Android 等软件开发的团队都可以加入 OIN。

### 安全风险

- 下载的二进制对比数字签名
- 跟踪漏洞披露和修复情况
  - 项目披露
    - Github 上的 Security
    - Xen 的漏洞披露 <https://xenbits.xen.org/xsa/>
    - Openssl 的漏洞披露 <https://www.openssl.org/news/vulnerabilities.html>
    - Spring 的漏洞披露 <https://spring.io/security>
    - Samba 的漏洞披露 <https://www.samba.org/samba/history/security.html>
    - Linux Kernel 的漏洞披露 （非官方，官方没有开放相关地址）
      - <https://linuxkernelcves.com/>
      - <https://github.com/nluedtke/linux_kernel_cves/tree/master/data>
  - 漏洞库披露
    - https://nvd.nist.gov/
    - https://www.cvedetails.com/
    - https://cve.mitre.org/
    - https://vuldb.com/
    - https://www.rapid7.com/
    - https://snyk.io/vuln
    - https://www.cnvd.org.cn/
    - https://www.cnnvd.org.cn/

## 依赖选型

选型原则：

- 生命周期 —— 留意依赖生命周期，不选衰退期依赖
  - 不选 EOL（End of Life，生命终止） 的依赖，因为有漏洞修复也只会合入到新版本中，不会合入到 EOL 的版本中。
  - 可选 LTS（Long time Support，长期支持） 的依赖，因为社区承诺长期支持。
- 版本更新 —— 优选较新的稳定版本
  - RC（Released Candidate，发布候选）
- 版本归一 —— 一个依赖在整个产品中（尽量）只有一个版本
- 全量引入 —— 不片段引入依赖
- 全量监控 —— 除了关注产品直接依赖的软件/组件，还需要关注这些依赖所依赖的软件/组件

## 参考

- Copyleft 和 GFDL 许可证 https://www.ruanyifeng.com/blog/2008/04/copyleft_and_gfdl.html
- Android ，在争议中逃离 Linux 内核的 GPL 约束 https://www.ifanr.com/92261
- “开源合规”系列之三 — SaaS 应用场景下的开源合规 https://www.lexology.com/library/detail.aspx?g=34e521c4-63a8-4185-9175-e43b7f09d25c
