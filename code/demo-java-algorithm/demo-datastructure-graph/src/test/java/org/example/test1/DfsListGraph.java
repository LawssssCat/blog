package org.example.test1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class DfsListGraph extends ListGraph {
    public DfsListGraph(int v) {
        super(v);
    }

    @Override
    public void traversal(Consumer<Integer> func) {
        DfsTraversal traversal = new DfsTraversal(this);
        for (int i = 0; i < graphs.size(); i++) {
            traversal.traversal(i, func);
        }
    }

    /**
     * 深度优先遍历
     */
    private static class DfsTraversal {
        ListGraph graph;
        boolean[] visited;
        // Deque<Integer> queue = new ArrayDeque<>(); // 数组，方便中间增删
        Queue<Integer> queue = new LinkedList<>(); // 链表，方便前后增删

        DfsTraversal(ListGraph graph) {
            this.graph = graph;
            visited = new boolean[graph.graphs.size()];
        }

        void traversal(int v, Consumer<Integer> func) {
            offer(v);
            while (!queue.isEmpty()) {
                func.accept(queue.poll());
                // add
                graph.graphs.get(v).forEach(neighbor -> {
                    offer(v);
                });
            }
        }

        private void offer(int v) {
            if (!visited[v]) {
                queue.offer(v);
                visited[v] = true;
            }
        }
    }
}
