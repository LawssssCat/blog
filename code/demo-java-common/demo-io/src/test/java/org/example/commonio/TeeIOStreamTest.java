package org.example.commonio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * 输入流分流
 */
@Slf4j
public class TeeIOStreamTest {
    private static final String INPUT = "This should go to the output." + System.lineSeparator() + "Two line content: xx";

    /**
     * TeeInputStream: 将输入流的内容输出到输出流
     */
    @Test
    void test_TeeInputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TeeInputStream tee = new TeeInputStream(in, out, true); // 当 in 关闭，同上将 out 关闭

        String s1 = out.toString();
        log.info("Output stream: {}", s1); // null —— InputStream 还没读
        Assertions.assertEquals("", s1);
        log.info("Input stream read: {}", IOUtils.readLines(tee).stream().collect(Collectors.joining("\n")));
        String s2 = out.toString();
        log.info("Output stream: {}", s2); // 和 InputStream 已读的一致
        Assertions.assertEquals(INPUT, s2);
    }

    /**
     * TeeOutputStream: 将输出流程分叉为两个输出流
     * 配合 TeeInputStream 则可: 将一个输入流的内容输出到两个输出流
     */
    @Test
    void test_TeeOutputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();

        TeeOutputStream out = new TeeOutputStream(out1, out2);
        TeeInputStream tee = new TeeInputStream(in, out, true);

        log.info("input stream read: {}", IOUtils.readLines(tee).stream().collect(Collectors.joining("\n")));
        log.info("Output stream: {}", out1.toString());
        log.info("Output stream: {}", out2.toString());
    }

    /**
     * Copy Stream —— 用于小内容流场景
     *
     * 问题：
     * load all data in memory
     */
    @Test
    void test() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(in, baos);
        byte[] bytes = baos.toByteArray();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));

        // Do some interleaved reads from them.
        log.info("One line from br1:");
        out.println(br1.readLine());
        log.info("Two lines from br2:");
        out.println(br2.readLine());
        out.println(br2.readLine());
        log.info("One line from br1:");
        out.println(br1.readLine());
    }

    /**
     * 该方法有问题
     *
     * 参考：
     * https://stackoverflow.com/a/12107486
     * https://stackoverflow.com/a/30262036 (better)
     */
    @Test
    void test_PipedInputStream() throws IOException {
        // Create the source input stream.
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes());
        // Create a pipe input stream for one of the readers.
        PipedInputStream pipIn = new PipedInputStream();
        // Create a tee-splitter for the other reader.
        TeeInputStream tee = new TeeInputStream(in, new PipedOutputStream(pipIn), true);

        // Create the two buffered readers.
        BufferedReader br1 = new BufferedReader(new InputStreamReader(tee));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(pipIn));

        // Do some interleaved reads from them.
        log.info("One line from br1:");
        out.println(br1.readLine());
        log.info("Two lines from br2:");
        out.println(br2.readLine());
        // out.println(br2.readLine()); // ❗block
        log.info("One line from br1:");
        out.println(br1.readLine());
    }

    /**
     * todo https://stackoverflow.com/a/5034653
     *
     * 可用于大内存流、无限流
     */
    @Test
    void test_Listener() {
        // todo
    }
}
