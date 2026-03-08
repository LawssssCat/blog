package org.example.guava.io;

import com.google.common.io.CharSink;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CloserTest {
    private final File SOURCE = new File(CloserTest.class.getResource("").getFile(), "testCloser.txt");

    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        CharSink charSink = Files.asCharSink(SOURCE, StandardCharsets.UTF_8);
        charSink.write("hello world");
    }

    @Test
    void testCloser() throws IOException {
        Closer closer = Closer.create();
        try {
            BufferedReader inputStream = closer.register(new BufferedReader(new InputStreamReader(new FileInputStream(SOURCE))));
            log.info("------- read start -------");
            inputStream.lines().forEach(line -> log.info("X: {}", line));
            log.info("------- read end -------");
        } catch (Throwable t) {
            closer.rethrow(t); // 将 finally 的异常也 add 到这里，避免异常丢失
        } finally {
            closer.close();
        }
    }

    /**
     * JDK 替代
     */
    @Test
    void testJDK() throws IOException {
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(SOURCE)))) {
            log.info("------- read start -------");
            inputStream.lines().forEach(line -> log.info("X: {}", line));
            log.info("------- read end -------");
        }
    }
}
