---
title: JDK Jar 包操作
date: 2024-10-24
tag:
  - java
order: 66
---

JAR（Java Archive File） 文件是一种 Java 归档文件（本质上是一个采用zip标准构建出来的“压缩包”）。

> 背景说明：
> 由于，`.class`文件是JVM看到的最小可执行文件，而一个大型程序需要编写很多Class，并生成一堆`.class`文件。
> 所以，使用jar文件打包`.class`文件。
> e.g.
> JVM自带的Java标准库rt.jar实际上就是以jar文件形式存放的，一共有60多M。

```bash
jar cf jar-file input-file...      用一个单独的文件创建一个 JAR 文件
jar cf jar-file dir-name           用一个目录创建一个 JAR 文件
jar cf0 jar-file dir-name          创建一个未压缩的 JAR 文件
jar uf jar-file input-file...      更新一个 JAR 文件
jar tf jar-file                    查看一个 JAR 文件的内容
jar xf jar-file                    提取一个 JAR 文件的内容
jar xf jar-file archived-file...   从一个 JAR 文件中提取特定的文件
java -jar app.jar                  运行一个打包为可执行 JAR 文件的应用程序
```

<!-- more -->

## Jar包结构

官方文档: <https://docs.oracle.com/javase/7/docs/technotes/guides/jar/jar.html> （[link_中文like](https://www.cnblogs.com/applerosa/p/9736729.html)）

JAR 文件包含了程序运行所需的`.class`文件、相关资源和相应的元数据（放在`META-INF`文件夹中）。

e.g. 目录结构

```bash
Demo.jar
- org
    - example
        - Main.class —— 编译出来的字节码文件，按其所在的包进行存放
- META-INF/ —— 存放应用程序配置、扩展程序配置、类加载器配置和服务元数据（MANIFEST.MF）文件，在用jar命令打包时自动生成
- META-INF/META-INFO.MF
- WEB-INF            —— （可选）WEB应用安全目录，只有服务端可以访问的目录
- WEB-INF/web.xml    —— 描述web应用程序文件，描述servlet和其他的应用组件配置及命名规则
- WEB-INF/lib/       —— 包含web应用需要的jar文件，如数据库驱动jar包
- WEB-INF/classes/   —— 包含站点所有有用的class文件，包括servlet class和非servlet class文件（这些文件不能包含在.jar文件中）
- WEB-INF/src/       —— 包含源码文件的目录，按照包名结构放置各个java文件
- WEB-INF/database.properties —— 数据库配置文件
- WEB-INF/jsp/       —— 存放jsp1.2以下版本的jsp文件
- WEB-INF/jsp2/      —— 存放jsp2.0以下版本的jsp文件
- WEB-INF/tags/      —— 存放jsp自定义标签文件，通过声明标签文件库路径为tags启用，如 <%@ taglibprefix="tags" tagdir="/WEB-INF /simpleTags" %>
```

```bash title="META-INF/META-INF.MF"
Manifest-Version: 1.0
Build-Jdk-Spec: 1.8
Created-By: core engine team, middleware group, alibaba inc.
# Premain-Class: xxxxx # 启动jar包时，通过指定agent方式进入的主程序入口
# Agent-Class: xxxxx   # 运行中jar包，通过virtualMachine.loadAgent指定agent方式进入的主程序入口
Main-Class: com.taobao.arthas.boot.Bootstrap  # 主程序入口
# Extension-Name: This attribute specifies a name for the extension contained in the Jar file. The name should be a unique identifier such as the name of the main package comprising the extension.
Specification-Title: arthas-boot  # Extension的规范
Specification-Version: 3.6.8
Implementation-Title: arthas-boot # Extension的实现
Implementation-Version: 3.6.8
```

## Jar包使用

一个大型Java程序会生成自己的jar文件，同时引用依赖的第三方jar文件。
因此，运行一个Java程序，一般来说，命令行写这样：

```bash
java -cp app.jar:a.jar:b.jar:c.jar org.example.Main
# 如果漏写了某个运行时需要用到的jar，那么在运行期极有可能抛出ClassNotFoundException。
```

## Jar包编辑工具

todo <https://www.cnblogs.com/applerosa/p/9736729.html>

## Jar包开发库

Java 中的 `java.util.jar.JarOutputStream` 类是用于创建和输出 JAR 文件的输出流。
JarOutputStream 类提供了一系列方法来向 JAR 文件中写入条目（Entry，`JarEntry extends ZipEntry`）并设置相关属性。

使用场景：

- **创建 JAR 文件**：可以使用 JarOutputStream 类来创建一个 JAR 文件，将多个文件打包到一个 JAR 文件中。这在构建和分发 Java 应用程序时非常有用。
- **压缩文件**：JarOutputStream 类提供了压缩文件的功能，可以选择性地压缩 JAR 文件中的文件。通过设置 ZipEntry 对象的压缩属性，可以控制是否对文件进行压缩。
- **添加和更新文件**：使用 JarOutputStream 可以向现有的 JAR 文件中添加新的文件或更新已有文件。通过设置 ZipEntry 对象的属性，可以指定新文件的名称和位置。
- **创建清单文件**：在 JAR 文件中，清单文件（Manifest）用于记录 JAR 文件的元数据和配置信息。可以使用 JarOutputStream 类的构造函数，指定一个 Manifest 对象，以创建一个包含清单文件的 JAR 文件。

常用的 JarOutputStream 类的方法：

- `public JarOutputStream(OutputStream out, Manifest man)` 构造一个新的 JarOutputStream 对象，并将输出写入指定的输出流，同时使用指定的清单对象。
- `public void putNextEntry(ZipEntry ze)` 开始编写一个新的“文件”输入，并将流定位到输入数据的开始位置。
- `public void write(byte[] b, int off, int len)` 将指定的字节数组写入当前条目中。
- `public void closeEntry()` 完成当前条目的写入，并关闭当前条目。
- `public void finish()` 完成 Jar 文件的写入，并关闭输出流

```java
<!-- @include: @project/code/demo-java-common/demo-io-jar/src/test/java/org/example/jar/CreateJarTest.java -->
```

### 创建 Jar 包

```java
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class CreateJarFile {
    public static void main(String[] args) {
        try {
            // 创建一个输出流
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream("output.jar"));

            // 创建一个新的Jar条目
            ZipEntry entry = new ZipEntry("file.txt");
            jarOutputStream.putNextEntry(entry);

            // 写入数据到条目中
            String data = "Hello, World!";
            jarOutputStream.write(data.getBytes());

            // 完成条目的写入，并关闭条目
            jarOutputStream.closeEntry();

            // 完成JAR文件的写入，并关闭输出流
            jarOutputStream.finish();
            jarOutputStream.close();

            System.out.println("JAR文件创建成功！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 加载 Jar 包

::: tabs

@tab 复制

```java
@DisplayName("test copy jar")
@Test
void testCopyJar() throws IOException {
    File srcFile = getTempDirFile("createdJar.jar");
    File outFile = getTempDirFile("copiedJar.jar");
    try (JarInputStream jarInputStream = new JarInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
          JarOutputStream jarOutputStream = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)));) {
        // manifest
        Manifest manifest = jarInputStream.getManifest();
        if (manifest != null) {
            ZipEntry e = new ZipEntry(JarFile.MANIFEST_NAME);
            jarOutputStream.putNextEntry(e);
            manifest.write(new BufferedOutputStream(jarOutputStream));
            jarOutputStream.closeEntry();
        }
        // dir and file
        byte[] bytes = new byte[1024];
        JarEntry nextJarEntry;
        while ((nextJarEntry = jarInputStream.getNextJarEntry()) != null) {
            jarOutputStream.putNextEntry(nextJarEntry);
            int len;
            while((len = jarInputStream.read(bytes, 0, bytes.length)) != -1) {
                jarOutputStream.write(bytes, 0, len);
            }
            jarInputStream.closeEntry();
        }
        jarOutputStream.putNextEntry(new JarEntry("readme.md"));
        jarOutputStream.write("copied jar".getBytes());
        jarOutputStream.closeEntry();
    }
}
```

@tab 解压

```java
@DisplayName("test unpack jar")
@Test
void testUnpackJar() throws IOException {
    File srcFile = getTempDirFile("createdJar.jar");
    File destDir = getTempDirFile("unpack");
    destDir.mkdirs();
    byte[] bytes = new byte[1024];
    try (JarInputStream jarInputStream = new JarInputStream(new BufferedInputStream(new FileInputStream(srcFile)))) {
        // unpack files
        JarEntry nextJarEntry;
        while ((nextJarEntry = jarInputStream.getNextJarEntry()) != null) {
            File desTemp = new File(destDir, nextJarEntry.getName());
            if (nextJarEntry.isDirectory()) { // Jar 条目是空目录
                desTemp.mkdirs();
                log.info("MakeDir: {}", desTemp);
            } else { // Jar 条目是文件
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desTemp))) {
                    int len;
                    while((len = jarInputStream.read(bytes, 0, bytes.length)) != -1) {
                        bufferedOutputStream.write(bytes, 0, len);
                    }
                }
                log.info("Copied: {}", desTemp);
            }
        }
        // unpack manifest
        Manifest manifest = jarInputStream.getManifest();
        if (manifest != null) {
            File manifestFile = new File(destDir, JarFile.MANIFEST_NAME);
            manifestFile.getParentFile().mkdirs();
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(manifestFile))) {
                manifest.write(bufferedOutputStream);
            }
            log.info("Manifest: {}", manifestFile);
        }
    }
}
```

:::

### 签名 Jar 包

todo

### 验签 Jar 包

todo

`javapackager -signjar` （在 JDK 9 中已弃用，改为 `jarsigner` 工具）
