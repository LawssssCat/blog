package org.example.test1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

abstract class ListGraph {
    List<List<Integer>> graphs;
    public ListGraph(int size) {
        graphs = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            graphs.add(new ArrayList<>());
        }
    }

    public void addEdge(int start, int end) {
        graphs.get(start).add(end);
    }

    public void removeEdge(int start, int end) {
        graphs.get(start).remove(end);
    }

    abstract void traversal(Consumer<Integer> func);
}
