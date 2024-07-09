---
title: 图（Graph）
date: 2024-07-08
tag:
  - datastructure
order: 1
---

图定义：

- 元素
  - 图 `G(V,E)`
  - 边 `E`（Edge）
  - 节点 `V`（Vertex）
- 有向（Directed）/无向（UnDirected）
- 有权（Weighted）/无权（UnWeighted）

表示：

- 邻接表（Adjacency List）

  | Vertex | Neighbors |
  | ------ | --------- |
  | A      | B         |
  | B      | A,C       |
  | C      | B         |

- 邻接矩阵（Adjacency Matrix）

问题：

- 拓扑排序 —— 有向无环图/拓扑序列
- 最短路径

## 邻接表

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-graph/src/test/java/org/example/test1/ListGraph.java -->
```

图遍历（Graph Traversal）

- 深度优先（DFS，Depth First Search）
- 广度优先（BFS，Breadth First Search）

::: tabs

@tab DFS

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-graph/src/test/java/org/example/test1/DfsListGraph.java -->
```

@tab BFS

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-graph/src/test/java/org/example/test1/BfsListGraph.java -->
```

@tab 遍历测试用例

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-graph/src/test/java/org/example/test1/ListGraphTravelTest.java -->
```

:::
