package org.example.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CollectionsTest {
    private List<String> build() {
        return Lists.newArrayList("hello", "world");
    }
    /**
     * 集合（可遍历的）公共能力
     */
    @DisplayName("测试 FluentIterable")
    @Test
    void testFluentIterable() {
        // of ❌JDK 替代
        assertArrayEquals(new Integer[] {1,2,3}, FluentIterable.of(1,2,3).toArray(Integer.class));
        assertArrayEquals(new Integer[] {1,2,3}, Stream.of(1,2,3).toArray()); // JDK 替代
        assertArrayEquals(new Integer[] {1,1,1}, Stream.generate(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        }).limit(3).toArray()); // 自定义生成

        // concat ❌JDK 替代
        assertArrayEquals(new Integer[] {1,2,2,3},
                FluentIterable.concat(FluentIterable.of(1,2), FluentIterable.of(2,3)).toArray(Integer.class));
        assertArrayEquals(new Integer[] {1,2,2,3}, Stream.concat(Stream.of(1,2), Stream.of(2,3)).toArray());

        // filter ❌JDK 替代
        assertTrue(FluentIterable.from(build()).filter(e -> "hello".equals(e)).contains("hello"));
        assertTrue(Iterables.removeIf(FluentIterable.from(build()), s -> !"hello".equals(s))); // removeIf
        assertTrue(Iterators.removeIf(FluentIterable.from(build()).iterator(), s -> !"hello".equals(s))); // removeIf

        // append
        assertTrue(FluentIterable.from(build()).append("haha").contains("haha"));
        assertTrue(Stream.concat(build().stream(), Stream.of("haha")).anyMatch(s -> "haha".equals(s)));

        // match
        assertTrue(FluentIterable.from(build()).allMatch(StringUtils::isNotBlank)); // all match
        assertTrue(FluentIterable.from(build()).anyMatch(s -> "hello".equals(s))); // any match
        assertEquals("hello", FluentIterable.from(build()).firstMatch(StringUtils::isNotBlank).get()); // first match

        // first / last / limit  ❌JDK 替代
        assertEquals("hello", FluentIterable.from(build()).first().get());
        assertEquals("world", FluentIterable.from(build()).last().get());
        assertArrayEquals(new String[] {"hello"}, FluentIterable.from(build()).limit(1).toArray(String.class));

        // 💡copyInto —— addAll （是否深拷贝/浅拷贝，由传入的 collection 决定。并且返回的就是传入的 collection 对象）
        assertTrue(FluentIterable.from(build()).copyInto(Lists.newArrayList("haha")).contains("haha"));

        // 💡cycle —— 循环
        FluentIterable<String> cycle = FluentIterable.from(build()).cycle();
        assertEquals("[hello, world] (cycled)", cycle.toString());
        assertThrowsExactly(TimeoutException.class, () -> new FutureTask<>(() -> cycle.size()) // 死循环
                .get(1L, TimeUnit.SECONDS));
        assertArrayEquals(new String[] {"hello", "world", "hello"}, cycle.limit(3).toArray(String.class));

        // transform ❌JDK 替代
        assertArrayEquals(new Integer[] {"hello".length(), "world".length()},
                FluentIterable.from(build()).transform(String::length).toArray(Integer.class));

        // 💡consuming —— （消费的）遍历：next + remove
        FluentIterable<String> old = FluentIterable.from(build());
        Iterables.consumingIterable(old).forEach(e -> log.info("consuming: {}", e));
        assertEquals(0, old.size()); // has been consumed
    }

    /**
     * 列表能力
     */
    @DisplayName("测试 Lists")
    @Test
    void testLists() {
        // new ❌JDK 替代
        assertEquals("A,B,C", Joiner.on(",").join(Lists.newArrayList("A", "B", "C"))); // 可修改
        assertEquals("A,B,C", Joiner.on(",").join(Lists.newLinkedList(FluentIterable.of("A", "B", "C")))); // 可修改
        assertArrayEquals(new String[] {"A", "B"}, Lists.asList("A", new String[] {"B"}).toArray()); // 不可修改
        assertArrayEquals(new Character[] {'A', 'B', 'C'}, Lists.charactersOf("ABC").toArray()); // 拆分，不如 Spliter

        // 💡COW
        Lists.newCopyOnWriteArrayList(Lists.newArrayList("A")); // 💡用于 “读多写少的并发场景”，具体参考 COW

        // 💡笛卡尔积
        List<List<String>> cartesianProduct = Lists.cartesianProduct(
                Lists.newArrayList("A", "B", "C"),
                Lists.newArrayList("1", "2")
        );
        log.info("cartesianProduct={}", cartesianProduct);
        assertEquals("[[A, 1], [A, 2], [B, 1], [B, 2], [C, 1], [C, 2]]", cartesianProduct.toString());

        // 💡partition
        assertEquals("[[John, Jane], [Adam, Tom], [Viki]]",
                Lists.partition(Lists.newArrayList("John","Jane","Adam","Tom","Viki"), 2).toString());

        // 💡反转
        assertArrayEquals(new String[] {"C", "B", "A"}, Lists.reverse(Arrays.asList("A", "B", "C")).toArray());
    }

    /**
     * 非重复集合能力
     */
    @DisplayName("测试 Sets")
    @Test
    void testSets() {

    }

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
}
