package org.example.jar;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.jar.*;
import java.util.zip.ZipEntry;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateJarTest {
    @TempDir(cleanup = CleanupMode.NEVER)
    private static File tempDir;

    private File getTempDirFile(String filename) {
        File file = new File(tempDir, filename);
        log.warn("temp dir file: {}", file);
        return file;
    }

    @Order(1)
    @DisplayName("test create Jar: test JarOutputStream write \"Dir\" and \"File\".")
    @Test
    void testCreateJar() throws IOException {
        File file = getTempDirFile("createdJar.jar");
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest)) {
            /*
             * Jar 文件规定:
             * 1. 目录/文件不得以 "/" 开头
             * 2. 目录结尾必须是 "/"
             * 3. 路径分割必须使用 "/" 而不是 "\"
             */
            // 添加目录
            {
                JarEntry jarEntry = new JarEntry("some/path/");
                jarEntry.setTime(System.currentTimeMillis());
                jarOutputStream.putNextEntry(jarEntry);
                jarOutputStream.closeEntry();
            }
            // 添加文件
            {
                JarEntry jarEntry = new JarEntry("some/path/README.md");
                jarEntry.setTime(System.currentTimeMillis());
                jarOutputStream.putNextEntry(jarEntry);
                jarOutputStream.write("hello world!".getBytes()); // 写内容
                jarOutputStream.closeEntry();
            }
        }
    }

    @Order(2)
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

    @Order(2)
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
}
