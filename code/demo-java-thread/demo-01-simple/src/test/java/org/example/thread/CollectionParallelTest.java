package org.example.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CollectionParallelTest {
    @Test
    void testStreamParallel() {
        assertEquals("ABCDE", Stream.of("A", "B", "C", "D", "E").collect(Collectors.joining()));
        assertEquals("ABCDE", Stream.of("A", "B", "C", "D", "E").parallel().collect(Collectors.joining())); // 并行但有序
            assertEquals("1A1B1C1D1E", Stream.of("A", "B", "C", "D", "E").parallel().map(s -> "1" + s).collect(Collectors.joining())); // 并行但有序
        assertEquals("1A1B1C1D1E", Stream.of("A", "B", "C", "D", "E").parallel().map(s -> {
            log.info(s); // 大概率非有序
            return "1" + s;
        }).collect(Collectors.joining()));
    }
}
