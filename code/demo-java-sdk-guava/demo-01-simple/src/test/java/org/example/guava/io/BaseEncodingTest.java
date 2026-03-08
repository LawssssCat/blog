package org.example.guava.io;

import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BaseEncodingTest {
    @Test
    void testBase64Encode() {
        String msg = "hello world +  !";
        BaseEncoding baseEncoding = BaseEncoding.base64();

        // encode
        String encode = baseEncoding.encode(msg.getBytes());
        log.info(encode);
        assertEquals("aGVsbG8gd29ybGQgKyAgIQ==", encode);

        // decode
        assertArrayEquals(msg.getBytes(), baseEncoding.decode(encode));
    }

}
