package org.example.guava.collection;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 图
 */
@Slf4j
public class GraphTest {
    @Test
    void test() {
        // 一个有向图形
        MutableGraph<Integer> graph = GraphBuilder.directed().build();
        // 包含三个顶点（1, 2, 3, 4）
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        // 包含两条边（1->2，2->3）
        graph.putEdge(1, 2);
        graph.putEdge(2, 3);

        // show
        showGraph(graph);

        // 点
        assertArrayEquals(new Integer[] {1,2,3,4}, graph.nodes().toArray());
        // 边
        assertArrayEquals(new String[] {
                    "1 -> 2",
                    "2 -> 3"
                },
                graph.edges().stream().map(edge -> edge.source() + " -> " + edge.target()).toArray()
        );

        // 获取邻接点
        assertArrayEquals(new Integer[] {}, graph.adjacentNodes(4).toArray());
        assertArrayEquals(new Integer[] {1, 3}, graph.adjacentNodes(2).toArray());

        // 判断链接
        assertTrue(graph.hasEdgeConnecting(1, 2)); // true, 1 -> 2
        assertFalse(graph.hasEdgeConnecting(1, 3)); // false, 1 -> 2 -> 3
        assertFalse(graph.hasEdgeConnecting(3, 2)); // false, 1 x-> 2 （有向）
        assertFalse(graph.hasEdgeConnecting(1, 4)); // false, 1 ?-? 4
    }

    private static void showGraph(MutableGraph<Integer> graph) {
        // 点
        log.info("node: {}", Arrays.toString(graph.nodes().toArray()));
        // 边
        graph.edges().forEach(edge -> {
            log.info("edge: {} -> {}", edge.source(), edge.target());
        });
    }
}
