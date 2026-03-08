package org.example.guava;

import com.google.common.base.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 字符串常用工具
 */
public class StringsTest {
    @DisplayName("Strings.isNullOrEmpty")
    @Test
    void testEmptyOrNull() {
        // null or empty
        assertTrue(Strings.isNullOrEmpty(null));
        assertTrue(Strings.isNullOrEmpty(""));
        assertFalse(Strings.isNullOrEmpty("hello"));
        // null or empty
        assertEquals(null, Strings.emptyToNull(""));
        assertEquals("", Strings.nullToEmpty(null));
        // normal
        assertEquals("hello", Strings.emptyToNull("hello"));
        assertEquals("hello", Strings.nullToEmpty("hello"));
    }

    @DisplayName("Strings.commonXxxfix")
    @Test
    void testCommon() {
        assertEquals("h", Strings.commonPrefix("hello", "hi"));
        assertEquals("d", Strings.commonSuffix("world", "md"));
    }

    @DisplayName("Strings.repeat")
    @Test
    void testRepeat() {
        assertEquals("hello,hello,hello,", Strings.repeat("hello,", 3));
        // 去掉末尾的 ','
        String repeat = Strings.repeat("hello,", 3);
        Iterable<String> split = Splitter.on(",").omitEmptyStrings().split(repeat);
        String join = Joiner.on(",").join(split);
        assertEquals("hello,hello,hello", join);
    }

    /**
     * 补全长度
     */
    @DisplayName("Strings.pad")
    @Test
    void testPad() {
        // end
        assertEquals("hello", Strings.padEnd("hello", 5, 'X'));
        assertEquals("helloX", Strings.padEnd("hello", 6, 'X'));
        assertEquals("helloXX", Strings.padEnd("hello", 7, 'X'));
        // start
        assertEquals("hello", Strings.padStart("hello", 5, 'X'));
        assertEquals("Xhello", Strings.padStart("hello", 6, 'X'));
        assertEquals("XXhello", Strings.padStart("hello", 7, 'X'));
    }

    /**
     * 已有 jdk 标准库替代
     */
    @Test
    @Deprecated
    void testCharsets() {
        assertEquals(StandardCharsets.UTF_8, Charsets.UTF_8);
        assertEquals(StandardCharsets.ISO_8859_1, Charsets.ISO_8859_1);
        // ...
    }

    @Test
    void testCharMatcher() {
        // digit
        assertTrue(CharMatcher.javaDigit().matches('1'));
        assertTrue(CharMatcher.javaDigit().matches('᭓')); // 3
        assertTrue(CharMatcher.javaDigit().matches('᮰'));
        assertTrue(CharMatcher.javaDigit().matches('꘠'));
        assertTrue(CharMatcher.javaDigit().matches('꩑'));

        // count
        assertEquals(2, CharMatcher.is('a').countIn("Guava"));
        assertEquals(3, CharMatcher.anyOf("Ga").countIn("Guava"));
        assertEquals(4, CharMatcher.javaLowerCase().countIn("Guava8"));
        assertEquals(5, CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).countIn("Guava8"));
        assertEquals(6, CharMatcher.javaLetterOrDigit().countIn("Guava8"));
        assertEquals(7, CharMatcher.breakingWhitespace().countIn("       "));

        // collapse
        assertEquals(" Hello world ", CharMatcher.breakingWhitespace().collapseFrom("       Hello     world   ", ' '));
        assertEquals("Hello world", CharMatcher.breakingWhitespace().trimAndCollapseFrom("       Hello     world   ", ' '));

        // remove
        assertEquals("helloworld", CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello world 123"));
        // retain （保留）
        assertEquals("  123", CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello world 123"));
    }
}
