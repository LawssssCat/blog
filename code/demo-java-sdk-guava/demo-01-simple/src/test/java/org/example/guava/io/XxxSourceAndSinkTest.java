package org.example.guava.io;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class XxxSourceAndSinkTest {
    private final String SOURCE = "testSourceAndSinkFiles.txt";

    /**
     * 读
     */
    @SneakyThrows
    @Test
    void testCharSource() {
        String msg = "hello world";

        // 读方法
        CharSource charSource = CharSource.wrap(msg);
        assertEquals(msg, charSource.read());
        assertEquals(1, charSource.readLines().size());
        assertEquals(false, charSource.isEmpty());
        assertEquals(true, CharSource.empty().isEmpty());
        assertEquals(true, CharSource.wrap("").isEmpty());
        assertEquals(msg.length(), charSource.length());
        assertEquals(msg.length(), charSource.lengthIfKnown().get());

        // 字节读
        ByteSource wrap = ByteSource.wrap(msg.getBytes(StandardCharsets.UTF_8));
        HashCode hash = wrap.hash(Hashing.sha256());
        log.info(hash.toString());
        assertEquals("b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9", hash.toString()); // 二次计算结果不变
        assertEquals(msg, wrap.asCharSource(StandardCharsets.UTF_8).read());

        // 读合并
        CharSource concat = CharSource.concat(
                CharSource.wrap(msg),
                CharSource.wrap(msg)
        );
        assertEquals(msg + msg, concat.read());
        assertEquals(1, concat.readLines().size());
    }

    /**
     * 写
     */
    @SneakyThrows
    @Test
    void testCharSink() {
        File file = new File(XxxSourceAndSinkTest.class.getResource("").getFile(), SOURCE);

        String msg = "你好 ！";

        CharSink charSink = Files.asCharSink(file, StandardCharsets.UTF_8);
        charSink.write(msg);

        Files.readLines(file, StandardCharsets.UTF_8).forEach(log::info);
        assertEquals(msg, Files.asCharSource(file, StandardCharsets.UTF_8).read());
    }
}
