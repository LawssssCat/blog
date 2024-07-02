package org.example.guava.io;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FilesTest {
    private final String SOURCE = "testFiles.txt";
    private final String SOURCE_COPY = "testFilesCopy.txt";

    @AfterEach
    void tearDown() {
        Optional.ofNullable(FilesTest.class.getResource(SOURCE_COPY))
                .map(new Function<URL, URI>() {
                    @SneakyThrows
                    @Override
                    public URI apply(@Nullable URL url) {
                        return url.toURI();
                    }
                })
                .map(File::new)
                .filter(File::exists)
                .ifPresent(File::delete);
    }

    /**
     * Guava Files 读取（默认 + 自定义） && 复制 && hash
     */
    @SneakyThrows
    @Test
    void testFiles_Guava() {
        File file = new File(FilesTest.class.getResource(SOURCE).toURI());

        // read
        String content = Joiner.on("\r\n").join(Files.readLines(file, StandardCharsets.UTF_8));
        log.info("read file: {}\n{}", file.getAbsolutePath(), content);
        // read  （方式二）
        String content2 = Files.toString(file, StandardCharsets.UTF_8); // ❌Deprecated
        String content3 = Files.asCharSource(file, StandardCharsets.UTF_8).read(); // ✅ 新用法
        {
            CharMatcher charMatcher = CharMatcher.breakingWhitespace();
            assertEquals(charMatcher.trimFrom(content), charMatcher.trimFrom(content2));
            assertEquals(charMatcher.trimFrom(content), charMatcher.trimFrom(content3));
        }
        // copy
        File fileCopy = new File(file.getParent(), SOURCE_COPY);
        Files.copy(file, fileCopy);
        assertTrue(fileCopy.exists());

        // read with custom
        String content4 = Files.asCharSource(file, StandardCharsets.UTF_8).readLines(new LineProcessor<String>() {
            Collection<String> list = Lists.newArrayList();

            @Override
            public boolean processLine(String line) throws IOException {
                if (CharMatcher.whitespace().trimFrom(line).isEmpty()) {
                    // 过滤空行
                } else {
                    list.add(line);
                }
                return true;
            }

            @Override
            public String getResult() {
                return Joiner.on("\n").join(list);
            }
        });
        log.info("read file(custom): {}\n{}", file.getAbsolutePath(), content4);
        {
            CharMatcher charMatcher = CharMatcher.breakingWhitespace();
            assertEquals(charMatcher.trimAndCollapseFrom(content, ' '), charMatcher.trimAndCollapseFrom(content4, ' '));
        }

        // 判断 hash 是否一致
//        Assertions.assertEquals("8c5cbd4af6688e412026d6211a2fc32e",
//                Files.hash(file, Hashing.goodFastHash(128)).toString()); // 💡Deprecated 每次都会变
        assertEquals("c10763621db4698452b574f60e49d87f03c7c085568e5e27bd1597e509eaf481",
                Files.asByteSource(file).hash(Hashing.sha256()).toString());
        HashCode hash = Files.asByteSource(file).hash(Hashing.sha256());
        HashCode hashCopy = Files.asByteSource(fileCopy).hash(Hashing.sha256());
        assertEquals(hash, hashCopy);
    }

    /**
     * JDK Files 读取 && 复制
     */
    @SneakyThrows
    @Test
    void testFiles_JDK() {
        File file = new File(FilesTest.class.getResource(SOURCE).toURI());
        // read
        log.info("read file: {}\n{}", file.getAbsolutePath(), Joiner.on("\n").join(java.nio.file.Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)));
        // copy
        File fileCopy = new File(file.getParent(), SOURCE_COPY);
        java.nio.file.Files.copy(
                file.toPath(),
                fileCopy.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
        assertTrue(fileCopy.exists());
    }

    /**
     * 遍历文件目录
     */
    @SneakyThrows
    @Test
    void testTreeFileTraverser() {
        File file = new File(FilesTest.class.getResource(SOURCE).toURI());
        Files.fileTreeTraverser().breadthFirstTraversal(file).forEach(f -> log.info("breadthFirst: {}", f)); // 广度遍历
        Files.fileTreeTraverser().preOrderTraversal(file).forEach(f -> log.info("preOrder: {}", f)); // 深度、先序遍历
        Files.fileTreeTraverser().postOrderTraversal(file).forEach(f -> log.info("postOrder: {}", f)); // 深度、后序遍历
        Files.fileTreeTraverser().children(file).forEach(f -> log.info("children: {}", f)); // 遍历一层
    }
}
