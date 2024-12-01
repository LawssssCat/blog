---
title: JDK Jar 包操作
date: 2024-10-24
tag:
  - java
order: 66
---

JAR 文件是一种 Java 归档文件，可以包含多个 Java 类文件、资源文件和其他文件。

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

## 创建 Jar 包

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

## 加载 Jar 包

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

## 签名 Jar 包

todo

## 验签 Jar 包

todo

`javapackager -signjar` （在 JDK 9 中已弃用，改为 `jarsigner` 工具）
