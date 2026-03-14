---
title: JPMS（Java Platform Module System）
---

Java新增模块（module）概念目的：提高可维护性和封装性

参考：

+ Java中文社区|Java教程|模块 - <https://dev.java-lang.cn/learn/modules/>
+ 廖雪峰|Java教程|模块 - <https://liaoxuefeng.com/books/java/oop/basic/module/index.html>

<!-- more -->

## 模块查看

```bash
$ java -version
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30)
OpenJDK 64-Bit Server VM GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30, mixed mode, sharing)

$ java --list-modules
com.oracle.graal.graal_enterprise
com.oracle.svm.enterprise.truffle
com.oracle.svm.extraimage_enterprise
com.oracle.svm.svm_enterprise
com.oracle.svm_enterprise.ml_dataset
com.oracle.truffle.enterprise
com.oracle.truffle.enterprise.svm
java.base@21.0.2
java.compiler@21.0.2
java.datatransfer@21.0.2
java.desktop@21.0.2
java.instrument@21.0.2
java.logging@21.0.2
java.management@21.0.2
java.management.rmi@21.0.2
java.naming@21.0.2
java.net.http@21.0.2
java.prefs@21.0.2
java.rmi@21.0.2
java.scripting@21.0.2
java.se@21.0.2
java.security.jgss@21.0.2
java.security.sasl@21.0.2
java.smartcardio@21.0.2
java.sql@21.0.2
java.sql.rowset@21.0.2
java.transaction.xa@21.0.2
java.xml@21.0.2
java.xml.crypto@21.0.2
jdk.accessibility@21.0.2
jdk.attach@21.0.2
jdk.charsets@21.0.2
jdk.compiler@21.0.2
jdk.compiler.graal@21.0.2
jdk.compiler.graal.management@21.0.2
jdk.crypto.cryptoki@21.0.2
jdk.crypto.ec@21.0.2
jdk.crypto.mscapi@21.0.2
jdk.dynalink@21.0.2
jdk.editpad@21.0.2
jdk.hotspot.agent@21.0.2
jdk.httpserver@21.0.2
jdk.incubator.vector@21.0.2
jdk.internal.ed@21.0.2
jdk.internal.jvmstat@21.0.2
jdk.internal.le@21.0.2
jdk.internal.opt@21.0.2
jdk.internal.vm.ci@21.0.2
jdk.internal.vm.compiler
jdk.internal.vm.compiler.management
jdk.jartool@21.0.2
jdk.javadoc@21.0.2
jdk.jcmd@21.0.2
jdk.jconsole@21.0.2
jdk.jdeps@21.0.2
jdk.jdi@21.0.2
jdk.jdwp.agent@21.0.2
jdk.jfr@21.0.2
jdk.jlink@21.0.2
jdk.jpackage@21.0.2
jdk.jshell@21.0.2
jdk.jsobject@21.0.2
jdk.jstatd@21.0.2
jdk.localedata@21.0.2
jdk.management@21.0.2
jdk.management.agent@21.0.2
jdk.management.jfr@21.0.2
jdk.naming.dns@21.0.2
jdk.naming.rmi@21.0.2
jdk.net@21.0.2
jdk.nio.mapmode@21.0.2
jdk.random@21.0.2
jdk.sctp@21.0.2
jdk.security.auth@21.0.2
jdk.security.jgss@21.0.2
jdk.unsupported@21.0.2
jdk.unsupported.desktop@21.0.2
jdk.xml.dom@21.0.2
jdk.zipfs@21.0.2
org.graalvm.collections
org.graalvm.extraimage.builder
org.graalvm.nativeimage
org.graalvm.nativeimage.llvm
org.graalvm.truffle.compiler
org.graalvm.word
```

## 模块定义

Java接口有“方法（method）”、“类（class）”、“包（package）”，“模块（module）”模块这些级别概念（其管理范围逐级增大）。

模块声明位置：项目源代码根目录`module-info.java`文件中。

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
