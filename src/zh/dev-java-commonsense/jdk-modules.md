---
title: JPMS（Java Platform Module System，Java 平台模块系统）
---

JPMS（Java Platform Module System，Java 平台模块系统）概念目的：提高jar包的可维护性和封装性。

> 背景说明：
>
> 1. 模块概念的定位
>
>     在Java 9之前，按Java接口有“方法（method）”、“类（class）”、“包（package）”这些概念，这些概念在不同层次上确保了其对应“功能”的稳定性。
>     在Java 9之后，新出的“模块（module）”概念是一个更大层次的接口概念，用于规范不同功能领域（如处理xml和处理json就是不同的功能领域）的描述、开闭规划和依赖关系。
>
> 1. 模块概念的必要性
>
>     在Java 9之前，官方自带的、第三方的、自己开发的功能模块都使用Jar包分发，这存在依赖关系不明确的问题。
>     比如a.jar必须依赖b.jar才能运行，只能在运行到相关代码才会抛出ClassNotFoundException报错。
>
>     ```bash
>     # JDK9之前，运行时需指定classpath包路径（classpath hell，类路径地狱）或者制作fat-jar包来处理三方依赖的问题
>     java -classpath lib/guava-19.0.jar \
>                     lib/hibernate-validator-5.3.1.jar \
>                     lib/validation-api-1.1.0.Final.jar \
>          -jar MyApplication.jar
>     ```
>
> 从Java 9开始引入的模块（module）概念可以解决这个“依赖”问题。通过在模块定义中完成相关声明，可以让程序在编译或运行的时候能自动定位到需要的模块。

参考：

+ Java中文社区|Java教程|模块 - <https://dev.java-lang.cn/learn/modules/>
+ 廖雪峰|Java教程|模块 - <https://liaoxuefeng.com/books/java/oop/basic/module/index.html> ⭐⭐⭐⭐⭐....
+ 北理金旭亮|51-Java模块化技术基础 - <https://www.bilibili.com/video/BV1RD4y1x7Mv/>

<!-- more -->

## 模块查看

从Java 9开始，原有的Java标准库已经由一个单一巨大的rt.jar分拆成了几十个（90+）模块，拆分后这些模块以`.jmod`扩展名标识，可以在`$JAVA_HOME/jmods`目录下找到它们：

```bash
$ java -version
openjdk version "21.0.9" 2025-10-21
OpenJDK Runtime Environment BiSheng (build 21.0.9+11)
OpenJDK 64-Bit Server VM BiSheng (build 21.0.9+11, mixed mode, sharing)
$ ll $JAVA_HOME
total 4
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 bin
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 conf
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 demo
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 include
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 jmods
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 legal
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 lib
drwxrwxrwx 1 xx xx 4096 Oct 31 14:00 man
-rwxrwxrwx 1 xx xx 1240 Oct 31 14:00 release

$ ll $JAVA_HOME/jmods/
total 84976
-rwxrwxrwx 1 xx xx 25065498 Oct 31 14:00 java.base.jmod
-rwxrwxrwx 1 xx xx   132035 Oct 31 14:00 java.compiler.jmod
-rwxrwxrwx 1 xx xx    59307 Oct 31 14:00 java.datatransfer.jmod
...
-rwxrwxrwx 1 xx xx    21751 Oct 31 14:00 jdk.unsupported.desktop.jmod
-rwxrwxrwx 1 xx xx    25231 Oct 31 14:00 jdk.unsupported.jmod
-rwxrwxrwx 1 xx xx    49964 Oct 31 14:00 jdk.xml.dom.jmod
-rwxrwxrwx 1 xx xx   112491 Oct 31 14:00 jdk.zipfs.jmod
```

这些.jmod文件每一个都是一个模块，模块名就是文件名。例如：模块java.base对应的文件就是java.base.jmod。
具体java运行环境支持那些模块，可以通过以下命令查询：

```bash
$ java --list-modules
java.base@21.0.9
java.compiler@21.0.9
java.datatransfer@21.0.9
java.desktop@21.0.9
java.instrument@21.0.9
java.logging@21.0.9
...
jdk.security.auth@21.0.9
jdk.security.jgss@21.0.9
jdk.unsupported@21.0.9
jdk.unsupported.desktop@21.0.9
jdk.xml.dom@21.0.9
jdk.zipfs@21.0.9
```

模块之间的依赖关系已经被写入到模块内的`module-info.class`文件了。
模块的描述可以通过如下命令查看：

```bash
$ java --describe-module java.base
java.base@21.0.9
exports java.io
exports java.lang
exports java.lang.annotation
...
exports javax.security.auth.spi
exports javax.security.auth.x500
exports javax.security.cert
uses java.net.spi.InetAddressResolverProvider
uses java.text.spi.NumberFormatProvider
uses java.util.spi.LocaleNameProvider
...
uses java.text.spi.DecimalFormatSymbolsProvider
uses java.util.spi.CalendarDataProvider
uses java.text.spi.DateFormatProvider
uses java.time.chrono.Chronology
provides java.util.random.RandomGenerator with java.security.SecureRandom java.util.Random java.util.SplittableRandom
provides java.nio.file.spi.FileSystemProvider with jdk.internal.jrtfs.JrtFileSystemProvider
qualified exports sun.security.action to jdk.crypto.ec java.security.jgss java.desktop
qualified exports jdk.internal.perf to java.management jdk.internal.jvmstat jdk.management.agent
...
qualified exports sun.nio.cs to jdk.charsets java.desktop
qualified exports sun.security.tools to jdk.jartool
qualified exports jdk.internal.classfile.constantpool to jdk.jlink jdk.jartool
contains java.lang.foreign.snippets
contains jdk.internal
contains jdk.internal.access.foreign
...
contains sun.util.locale
contains sun.util.resources.cldr
contains sun.util.spi
```

::: tip
通过`java --describe-module java.base`命令可以看到：`java.base`模块没有`require`声明，即不依赖任何其他模块。
实际上，所有的模块都直接或间接地依赖`java.base`模块，只有`java.base`不依赖其他模块。
因此，`java.base`可以被看作是“根模块”。
:::

## 模块定义

### Demo：调用JDK模块并导出定制JRE环境（java/class/jar/jmod/custom-jre）

link: <RepoLink path="/code/demo-java-module/demo-01-manual" />

模块声明位置：项目源代码根目录`module-info.java`文件中。

```bash
.
├── bin
├── build.sh
└── src
    ├── org
    │   └── example
    │       └── Main.java
    └── module-info.java <------------ here
```

将`.java`编译为`.class`文件

```bash
# 编译成功
$ javac -d bin src/module-info.java src/org/example/*.java
$ tree
.
├── bin <------------------- here. build result
│   ├── module-info.class
│   └── org
│       └── example
│           └── Main.class
└── src
    ├── module-info.java
    └── org
        └── example
            └── Main.java
$ (cd bin; java org.example.Main) # 运行结果
xml
# 编译失败：如果注释requires java.xml
$ javac -d bin src/module-info.java src/org/example/*.java
src/org/example/Main.java:4: error: package javax.xml is not visible
import javax.xml.XMLConstants;
            ^
  (package javax.xml is declared in module java.xml, but module hello.world does not read it)
1 error
```

将`.class`打包为`.jar`文件

```bash
$ jar --create --file hello.jar --main-class org.example.Main -C bin .
$ tree
.
├── bin
│   ├── module-info.class
│   └── org
│       └── example
│           └── Main.class
├── hello.jar <------------------- here. package result
└── src
    ├── module-info.java
    └── org
        └── example
            └── Main.java
$ java -jar hello.jar # 运行结果
xml
```

将`.jar`打包为`module`文件

```bash
$ jmod create --class-path hello.jar hello.jmod
$ tree
.
├── bin
│   ├── module-info.class
│   └── org
│       └── example
│           └── Main.class
├── hello.jar
├── hello.jmod <------------------- here. package result
└── src
    ├── module-info.java
    └── org
        └── example
            └── Main.java
# 执行失败：报错，因为直接执行无法指定jmod格式，可以指定jar格式
$ java --module-path hello.jmod --module hello.world
Error occurred during initialization of boot layer
java.lang.module.FindException: JMOD format not supported at execution time: hello.jmod
# 执行成功
$ java --module-path hello.jar --module hello.world
xml
```

制作`module`文件后，可以打包需要的`module`文件作为运行环境

```bash
# --module-path 指定在JAVA_HOME之外的模块路径
# --add-modules 指定要导出的模块名称（说明：会同时导出指定模块的依赖模块，所以如果依赖模块没有直接使用，可以不显式声明。所以下面参数可以简化为--add-modules hello.world形式）
$ jlink --module-path hello.jmod --add-modules java.base,java.xml,hello.world --output jre/
$ tree
.
├── bin
│   ├── module-info.class
│   └── org
│       └── example
│           └── Main.class
├── build.sh
├── hello.jar
├── hello.jmod
├── jre # <---------------- here. package result
│   ├── bin
│   │   ├── java
│   │   └── keytool
│   ├── conf
│   │   ├── jaxp.properties
│   │   ├── net.properties
│   │   ├── sdp
│   │   └── security
│   ├── include
│   ├── legal
│   ├── lib
│   ├── man
│   └── release
└── src
    ├── module-info.java
    └── org
        └── example
            └── Main.java
# 查看导出的jre环境包含有那些模块
$ jre/bin/java --list-modules
hello.world
java.base@21.0.9
java.xml@21.0.9
# 执行成功
$ jre/bin/java --module hello.world
xml
# 这样，要分发我们自己的Java应用程序，只需要把这个jre目录打个包给对方发过去，让对方直接运行上述命令即可。
# 既不用下载安装JDK，也不用知道如何配置我们自己的模块。
# 极大地方便了分发和部署。
```

### 详细定义规则

模块描述的信息有：

+ 模块名称
+ 依赖（requires）的其他模块
+ 对外提供（exports/opens）的接口，即“包（package）”（其他所有内容都是模块内部的，不可访问）
+ 使用（uses）和提供（provides）的服务列表

```java title='module-info.java'
module java.sql { // 模块名
    // 【“要求依赖（requires）”】
    // for each dependency:
    // 模块依赖
    requires java.base; // 可不写，任何模块都会自动引入java.base
    requires java.logging;
    requires static java.transaction.xa; // 可选依赖项 https://dev.java-lang.cn/learn/modules/optional-dependencies/
    requires transitive java.xml; // “转发”依赖项 https://dev.java-lang.cn/learn/modules/implied-readability/


    // 【“导出（exports）”和“打开（opens）”】
    // 默认情况下，所有类型，即使是public 类型，也只在模块内部可访问。要让模块外部的代码访问类型，包含该类型的包需要导出或打开。
    // 1、“导出（exports）”的包中的公共类型和成员在编译时和运行时可用 —— 详细将在关于“强封装”的部分中讨论
    // 2、“打开（opens）”的包中的所有类型和成员可以在运行时通过反射访问 —— 详细将在关于“反射”的部分中讨论
    // 3、“限定词”允许您仅向特定模块导出/打开包。 —— 详细参考： https://dev.java-lang.cn/learn/modules/qualified-exports-opens/
    // -------------------------
    // for each API package:
    // 构成公共API的包
    exports java.sql; // 包中的公共类型和成员在编译时和运行时可用
    exports javax.sql;
    // for each package intended for reflection:
    // opens $PACKAGE; // 包中的所有类型和成员可以在运行时通过反射访问 https://dev.java-lang.cn/learn/modules/opening-for-reflection/
    opens com.example.app.entities;


    // 【“使用（uses）”和“提供服务（provides）”】
    // for each used service:
    // 使用的服务
    uses java.sql.Driver;
    uses com.example.lib.Service;
    // for each provided service:
    // provides $TYPE with $CLASS;
    provides com.example.lib.Service
      with com.example.app.MyService;
}
```
