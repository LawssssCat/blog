package org.exampke.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {
    @Test
    void testStreamOf() {
        { // 可增删
            List<String> collect = Stream.of("a").collect(Collectors.toList());
            collect.add("b");
            Assertions.assertEquals(2, collect.size());
        }
        { // 不可为空
            Stream<Object> stream = Stream.of();
            NullPointerException nullPointerException = Assertions.assertThrowsExactly(NullPointerException.class, () -> {
                Stream.of(null); // java.lang.NullPointerException: Cannot read the array length because "array" is null
            });
            List<Object> collect = stream.collect(Collectors.toList());
            Assertions.assertEquals(0, collect.size());
        }
        { // 可部分为空
            List<String> collect = Stream.of("a", null).collect(Collectors.toList());
            Assertions.assertEquals(2, collect.size());
            Assertions.assertEquals("a", collect.get(0));
            Assertions.assertEquals(null, collect.get(1));
        }
    }

    @Test
    void testAsList() {
        { // 不可增删
            List<String> strings = Arrays.asList("a");
            Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> strings.add("b"));
            Assertions.assertEquals(1, strings.size());
        }
        { // 不可为空
            Assertions.assertThrowsExactly(NullPointerException.class, () -> {
                List<Object> list = Arrays.asList(null);
            });
        }
    }

    @Test
    void testListOf() {
        { // 不可增删
            List<String> strings = List.of("a");
            Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> strings.add("b"));
            Assertions.assertEquals(1, strings.size());
        }
        { // 不可为空
            Assertions.assertThrowsExactly(NullPointerException.class, () -> {
                List.of(null); // java.lang.NullPointerException: Cannot read the array length because "elements" is null
            });
        }
    }

    @Test
    void testCollections() {
        { // 不可增删
            List<String> a = Collections.singletonList("a");
            Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> {
                a.add("b");
            });
            // stream collect 后为新的可增删 list
            List<String> collect = a.stream().collect(Collectors.toList());
            collect.add("b");
            Assertions.assertEquals(2, collect.size());
        }
        { // 可以为空
            List<String> a = Collections.singletonList(null);
            Assertions.assertEquals(1, a.size());
            Assertions.assertEquals(null, a.get(0));
        }
    }

    @Test
    void testMerge() {
        
    }
}
