package org.example.guava;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * 字符串分割
 */
public class SplitterTest {
    private final static String SP = "|";
    private final static String SP_REGEX = "\\" + SP;

    @DisplayName("Splitter.on(SP)")
    @Test
    void testSplitter() {
        String str = "hello|world";
        assertArrayEquals(new String[] {"hello", "world"}, Splitter.on(SP).splitToList(str).toArray());
        assertArrayEquals(new String[] {"hello", "world"}, Splitter.onPattern(SP_REGEX).splitToList(str).toArray()); // 💡regex
        assertArrayEquals(new String[] {"hello", "world"}, Arrays.stream(str.split(SP_REGEX)).toArray()); // 💡regex
    }

    @DisplayName("Splitter.on(SP).trimResults()")
    @Test
    void testSplitter_trimResults() {
        String str = " hello | world ";
        assertArrayEquals(new String[] {" hello ", " world "}, Splitter.on(SP).splitToList(str).toArray());
        assertArrayEquals(new String[] {"hello", "world"}, Splitter.on(SP).trimResults().splitToList(str).toArray());
        assertArrayEquals(new String[] {"hello", "world"}, Arrays.stream(str.split(SP_REGEX)).map(String::trim).toArray()); // 💡regex
    }

    @DisplayName("Splitter.on(SP).omitEmptyStrings()")
    @Test
    void testSplitter_omitEmpty() {
        String str = "hello||world|";
        assertArrayEquals(new String[] {"hello", "", "world", ""}, Splitter.on(SP).splitToList(str).toArray());
        assertArrayEquals(new String[] {"hello", "world"}, Splitter.on(SP).omitEmptyStrings().splitToList(str).toArray());
        assertArrayEquals(new String[] {"hello", "world"}, Arrays.stream(str.split(SP_REGEX)).filter(StringUtils::isNotBlank).toArray()); // 💡regex
    }

    @DisplayName("Splitter.fixedLength(4)")
    @Test
    void testSplitter_fixLength() {
        String str = "aaaabbbbccccdddd";
        assertArrayEquals(new String[] {"aaaa", "bbbb", "cccc", "dddd"}, Splitter.fixedLength(4).splitToList(str).toArray());
        assertArrayEquals(new String[] {"aaaa", "bbbb", "cccc", "dddd"}, splitFixLength(str).toArray());
    }

    private static List<String> splitFixLength(String str) {
        List<String> result = new ArrayList<>();
        for (int i = 0, next; i < str.length(); i = next) {
            next = Math.min(str.length(), i + 4);
            result.add(str.substring(i, next));
        }
        return result;
    }
}
