package org.example.test1;

import java.util.function.Consumer;

public class BfsListGraph extends ListGraph {
    public BfsListGraph(int size) {
        super(size);
    }

    @Override
    void traversal(Consumer<Integer> func) {
        BfsTraversal traversal = new BfsTraversal(this);
        for (int i = 0; i < graphs.size(); i++) {
            traversal.traversal(i, func);
        }
    }

    /**
     * 广度优先遍历
     */
    private static class BfsTraversal {
        ListGraph graph;
        boolean[] visited;

        BfsTraversal(ListGraph graph) {
            this.graph = graph;
            visited = new boolean[graph.graphs.size()];
        }

        void traversal(int v, Consumer<Integer> func) {
            if (visited[v]) {
                return;
            }
            visited[v] = true;
            func.accept(v);
            // next
            graph.graphs.get(v).forEach(neighbor -> {
                traversal(neighbor, func);
            });
        }
    }
}
