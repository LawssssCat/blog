package org.example.guava.collection;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class MapsTest {
    /**
     * TreeMultiset —— 有序（自然顺序）可重复
     * HashMultiset —— 无序可重复
     */
    @DisplayName("测试 Multiset")
    @Test
    void testMultiset() {
        TreeMultiset<Integer> treeMultiset = TreeMultiset.create(FluentIterable.of(3,2,1,2));
        Assertions.assertArrayEquals(new Integer[] {1,2,2,3}, treeMultiset.stream().collect(Collectors.toList()).toArray());
    }

    /**
     * 键值对
     */
    @DisplayName("测试 Maps")
    @Test
    void testMaps() {
        // 💡new
        ImmutableMap<String, Integer> mapUniqueIndex = Maps.uniqueIndex(Lists.newArrayList(1, 2, 3), v -> "key_" + v);
        assertEquals("{key_1=1, key_2=2, key_3=3}", mapUniqueIndex.toString());

        // 💡transform
        Map<String, String> mapTransform = Maps.transformValues(mapUniqueIndex, new Function<Integer, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Integer input) {
                return "value_" + input;
            }
        });
        assertEquals("{key_1=value_1, key_2=value_2, key_3=value_3}", mapTransform.toString());
    }

    /**
     * Map&lt;Object, List&lt;Object&gt;&gt;
     */
    @DisplayName("测试 MultiMap")
    @Test
    void testMultiMap() {
        Function<Object, Object> funcPutValue = (map) -> {
            if (map instanceof Map) {
                ((Map) map).put("key1", "1");
                ((Map) map).put("key1", "2");
                ((Map) map).put("key1", "3");
            } else if (map instanceof Multimap) {
                ((Multimap) map).put("key1", "1");
                ((Multimap) map).put("key1", "2");
                ((Multimap) map).put("key1", "3");
            }
            return map;
        };
        Assertions.assertEquals("{key1=3}", funcPutValue.apply(Maps.newHashMap()).toString());
        Assertions.assertEquals("{key1=[1, 2, 3]}", funcPutValue.apply(LinkedListMultimap.create()).toString());
    }

    /**
     * 可以将 Key 和 Value 对换
     *
     */
    @DisplayName("测试 BiMap")
    @Test
    void testBiMap() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("1", "1");
        biMap.put("1", "66"); // 💡允许 put 重复 key
        assertThrowsExactly(IllegalArgumentException.class, () -> biMap.put("2", "66")); // 💡默认不允许 put 重复值
        biMap.forcePut("2", "66"); // 💡强制 put 重复值
        biMap.put("3", "11"); // 💡key 和 value 均不一样，则无影响
        Assertions.assertEquals("{2=66, 3=11}", biMap.toString());
        Assertions.assertEquals("{66=2, 11=3}", biMap.inverse().toString()); // 💡键值反转
    }
}
