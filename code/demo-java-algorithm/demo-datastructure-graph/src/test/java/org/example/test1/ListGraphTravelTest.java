package org.example.test1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ListGraphTravelTest {
    @Test
    void testBfs() {
        ListGraph listGraph = new BfsListGraph(10);
        for (int i = 1; i < listGraph.graphs.size(); i++) {
            listGraph.addEdge(i-1, i);
        }
        listGraph.addEdge(2, 8);
        listGraph.addEdge(3, 6);
        //
        assertTravel(listGraph);
    }

    @Test
    void testDfs() {
        ListGraph listGraph = new DfsListGraph(10);
        for (int i = 1; i < listGraph.graphs.size(); i++) {
            listGraph.addEdge(0, i);
        }
        listGraph.addEdge(2, 6);
        listGraph.addEdge(6, 3);
        listGraph.addEdge(6, 9);
        //
        assertTravel(listGraph);
    }

    private static void assertTravel(ListGraph listGraph) {
        AtomicInteger count = new AtomicInteger();
        listGraph.traversal(v -> {
            log.info("{} cur: {}", listGraph.getClass().getName(), v);
            count.getAndIncrement();
        });
        Assertions.assertEquals(listGraph.graphs.size(), count.get());
    }
}
