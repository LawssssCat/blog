package org.example.guava.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * 不可变集合
 */
public class ImmutableXxxTest {
    @Test
    void testListAddException() {
        List<Integer> integers = ImmutableList.of(1, 2, 3);
        assertThrowsExactly(UnsupportedOperationException.class, () -> integers.add(4));
    }

    @Test
    void testListNew() {
        assertEquals("[1, 2, 3]", ImmutableList.copyOf(new Integer[] {1,2, 3}).toString()); // clone
        assertEquals("[1, 2, 3, 4, 5]", ImmutableList.builder().add(1).add(2, 3).add(Arrays.asList(4, 5)).build().toString());
    }

    @Test
    void testMapNew() {
        ImmutableMap<Object, Object> immutableMap = ImmutableMap.builder().put("Oracle", "12C").put("Mysql", "7.5").build();
        assertEquals("{Oracle=12C, Mysql=7.5}", immutableMap.toString());
        assertThrowsExactly(UnsupportedOperationException.class, () -> immutableMap.put("Scala", "2.3.0"));
    }
}
