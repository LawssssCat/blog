package org.example.guava.collection;

import com.google.common.collect.BoundType;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangeTest {
    @Test
    void test() {
        assertEquals("[2..+∞)", Range.atLeast("2").toString());
        assertEquals("[2..+∞)", Range.downTo("2", BoundType.CLOSED).toString());
        assertEquals("(2..+∞)", Range.greaterThan("2").toString());
        assertEquals("(-∞..10)", Range.lessThan("10").toString());
        assertEquals("(-∞..10)", Range.upTo("10", BoundType.OPEN).toString());
        assertEquals("(-∞..10]", Range.atMost("10").toString());
        assertEquals("(-∞..+∞)", Range.all().toString());
    }

    /**
     * x|a<=x<=b
     */
    @Test
    void testRangeClose() {
        Range<Integer> closed = Range.closed(0, 9);
        assertEquals(true, closed.contains(5));
        assertEquals(0, closed.lowerEndpoint());
        assertEquals(9, closed.upperEndpoint());
    }

    /**
     * x|a<x<b
     */
    @Test
    void testRangeOpen() {
        Range<Integer> closed = Range.open(0, 9);
        assertEquals(true, closed.contains(5));
        assertEquals(0, closed.lowerEndpoint()); // 💡端点没变！
        assertEquals(9, closed.upperEndpoint()); // 💡端点没变！
        assertEquals(false, closed.contains(0));
        assertEquals(false, closed.contains(9));
    }

    @Test
    void testMapRange() {
        // range select
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();
        treeMap.put("Scala", 1);
        treeMap.put("Java", 2);
        treeMap.put("Kafka", 3);
        treeMap.put("Guava", 4);
        assertEquals("{Guava=4, Java=2, Kafka=3, Scala=1}", treeMap.toString());
        assertEquals("{Java=2, Kafka=3}", Maps.subMap(treeMap, Range.openClosed("Guava", "Kafka")).toString());

        // range map
        TreeRangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0, 60), "A");
        rangeMap.put(Range.closedOpen(60, 80), "B");
        rangeMap.put(Range.closedOpen(80, 100), "C");
        assertEquals("B", rangeMap.get(60));
    }
}
