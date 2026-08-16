package org.example.commonio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.example.io.SplittableInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * 输入流分流
 */
@Slf4j
public class TeeIOStreamTest {
    // private static final String CN = "US-ASCII";
    private static final String CN = "UTF-16";
    private static final String INPUT = "This should go to the output." + System.lineSeparator() + "Two line content: 你好啊";

    /**
     * TeeInputStream: 将 in 流的内容输出到 out 流
     */
    @Test
    void test_TeeInputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes(CN)); // 从byteArray中读入数据
        ByteArrayOutputStream out = new ByteArrayOutputStream(); // 将数据写入byteArray内存空间

        TeeInputStream tee = new TeeInputStream(in, out, true); // 当 in 关闭，同上将 out 关闭

        assertEquals("", out.toString()); // 返回空，因为 InputStream 还没读
        IOUtils.readLines(tee); // 读入数据
        assertEquals(INPUT, new String(out.toByteArray(), CN)); // 和 InputStream 已读的一致
        // 编码问题
        assertNotEquals(INPUT, new String(out.toByteArray()));
        assertEquals(new String(out.toByteArray()), out.toString());
    }

    /**
     * TeeOutputStream: 将一个 out 流分叉成两个 out 流。配合 TeeInputStream 可将一个 in 流的内容输出到两个地方
     */
    @Test
    void test_TeeOutputStream() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes(CN));
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();

        TeeOutputStream out = new TeeOutputStream(out1, out2);
        TeeInputStream tee = new TeeInputStream(in, out, true);

        IOUtils.readLines(tee); // 读入数据
        assertEquals(INPUT, out1.toString(CN));
        assertEquals(INPUT, out2.toString(CN));
    }

    /**
     * 将输入写到byteArray内存
     */
    @Test
    void test_copy() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes(CN));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        assertEquals(INPUT, out.toString(CN));
    }

    /**
     * 将 in 流输入到另一个 in 流
     * <br>
     * 参考：
     * <ul>
     *     <li>
     *         <del>https://stackoverflow.com/a/12107486</del> —— 存在问题：
     This pattern can cause your program to unexpectedly block. br2 is only able to read past br1 because br1's buffer is long enough to fit both lines. If you comment out the first br1.readLine() the program hangs.
     *     </li>
     *     <li>
     *         https://stackoverflow.com/a/30262036 —— 上面问题的修复  (better)
     *     </li>
     * </ul>
     */
    @Test
    void test_PipedInputStream() throws IOException {
        InputStream in = new ByteArrayInputStream(INPUT.getBytes(CN));

        // Create the two buffered readers.
        SplittableInputStream is1 = new SplittableInputStream(in);
        SplittableInputStream is2 = is1.split();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(is1, CN));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2, CN));

        assertEquals(INPUT, br2.lines().collect(Collectors.joining(System.lineSeparator())));
        assertEquals(INPUT, br1.lines().collect(Collectors.joining(System.lineSeparator())));
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
