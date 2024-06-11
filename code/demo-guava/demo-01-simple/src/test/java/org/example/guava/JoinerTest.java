package org.example.guava;

import com.google.common.base.Joiner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * 字符串拼接
 */
@Slf4j
public class JoinerTest {
    private final static String SP = "$";

    @DisplayName("Joiner.on")
    @Test
    void testJoin_ok() {
        List<String> strings = Arrays.asList("Google", "Guava", "Java", "NB");
        assertEquals("Google$Guava$Java$NB", Joiner.on(SP).join(strings));
        assertEquals("Google$Guava$Java$NB", String.join(SP, strings));
        assertEquals("Google$Guava$Java$NB", strings.stream().collect(Collectors.joining(SP)));
    }

    @DisplayName("Joiner.on + skipNulls")
    @Test
    void testJoin_nullHandle() {
        List<String> strings = Arrays.asList("Google", "Guava", "Java", "NB", null);
        assertThrowsExactly(NullPointerException.class, () ->
                assertEquals("Google$Guava$Java$NB$null", Joiner.on(SP).join(strings))); // 💡默认抛错
        assertEquals("Google$Guava$Java$NB", Joiner.on(SP).skipNulls().join(strings)); // 💡忽略
        assertEquals("Google$Guava$Java$NB$null", Joiner.on(SP).useForNull("null").join(strings)); // 💡null
        assertEquals("Google$Guava$Java$NB$null", String.join(SP, strings)); // null
        assertEquals("Google$Guava$Java$NB$null", strings.stream().collect(Collectors.joining(SP))); // null
        assertEquals("Google$Guava$Java$NB$null", strings.stream().map(s -> s == null ? "null" : s).collect(Collectors.joining(SP))); // null - custom
    }

    @DisplayName("Joiner.on + appendTo StringBuilder")
    @Test
    void testJoin_appendTo() {
        List<String> strings = Arrays.asList("Google", "Guava", "Java", "NB");
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppendTo = Joiner.on(SP).appendTo(sb, strings);
        assertEquals("Google$Guava$Java$NB", sbAppendTo.toString());
        assertEquals(sb, sbAppendTo); // 💡同一个对象
    }

    @SneakyThrows
    @DisplayName("Joiner.on + appendTo FileWriter")
    @Test
    void testJoin_appendTo2() {
        List<String> strings = Arrays.asList("Google", "Guava", "Java", "NB");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        try (FileWriter mock = mock(FileWriter.class)) {
            Joiner.on(SP).appendTo(mock, strings);
            verify(mock, Mockito.atLeast(4)).append(captor.capture());
            assertEquals("Google$Guava$Java$NB", String.join("", captor.getAllValues()));
        }
    }
}
