package org.example.guava.collection;

import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/**
 * 排序
 */
public class OrderingTest {
    @Test
    void testSortNullException() {
        List<Integer> arr = Arrays.asList(1, 3, null, 2); // 💡null

        // throw
        assertThrowsExactly(NullPointerException.class, () -> {
            arr.sort(Comparable::compareTo);
        });

        // null first
        Collections.sort(arr, Ordering.natural().reverse().nullsFirst());
        assertEquals("[null, 3, 2, 1]", arr.toString()); // 💡nullFirst 时，尽管倒叙，null 依然在 fist
        Collections.sort(arr, Ordering.natural().nullsFirst());
        assertEquals("[null, 1, 2, 3]", arr.toString());

        // isOrder
        assertEquals(true, Ordering.natural().nullsFirst().isOrdered(arr));
        assertEquals(false, Ordering.natural().nullsLast().isOrdered(arr));
    }
}
