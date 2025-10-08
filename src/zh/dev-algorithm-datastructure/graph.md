---
title: 图（Graph）
order: 11
---

todo https://www.youtube.com/watch?v=B5hxqxBL2d0

## 图的定义

图定义：

- 元素
  - 图 `G(V,E)`
  - 边 `E`（Edge）
  - 节点 `V`（Vertex）
- 有向（Directed）/无向（UnDirected）
- 有权（Weighted）/无权（UnWeighted）

概念：

- 桥/割边 —— 移除 “边” 后会增加连通分量的数量（白话：移除后会导致一个图变成两个图）
- 关节点/割点 —— 移除 “点” 后会增加连通分量的数量

## 图的问题

https://www.bilibili.com/video/BV1Cq421c7uL/

## 图的表示

关键词：邻接

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

### 邻接表

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-graph/src/test/java/org/example/test1/ListGraph.java -->
```

## 问题：图遍历（Graph Traversal）

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

### 问题：判断连通性

解决：遍历

## 问题：最短路径（Shortest Path）

两个点在图中的最短连线

算法：

- BFS（无权图）
- 迪杰斯特拉算法
- 贝尔曼-福特算法
- 弗洛伊德-沃舍尔算法
- `A*` 算法

### 问题：判断回路

### 问题：判断负权回路

算法：

- 贝尔曼-福特算法（`Bellman-Ford`）
- 弗洛伊德-沃舍尔算法（`Floyd-Warshall`）

## 问题：最小生成树（MST，minimum spanning tree）

可选算法

- 普里姆（Prim） Native implementation `O(V2)` 稠密图
- 普里姆（Prim） PQ implementation `O(ElogV)` 稀疏图
- 克鲁斯卡尔（Kruskal） UF implementation `O(ElogE)` （更大范围 `ElogV, E<=V2`） 稀疏图
- 博鲁夫卡（Boruvka）算法

> The Java API does not provide a Fibonacci heap. The `java.util.PriorityQueue` is a binary heap.

### Prim 算法

有优先级的 DFS

## 问题：强连通分量

强连通分量（SCCs）可以被视为有向图中的自包含循环，其中给定循环中的每个顶点都能到达同一循环中的其他顶点。

算法：

- 塔扬算法（Tarjan）
- 科萨拉朱算法（Kosaraju）

## 问题：旅行商问题

给定一个城市列表和每对城市之间的距离，求访问每个城市恰好一次且返回原始城市的最短路径。

算法：

- 赫尔德-卡普算法（Held-Karp）
- 分支定界算法（Branch and Bound）

> TSP 问题是 NP 难问题

## 问题：流网络最大流

图中 “源点” 和 “汇点” 之间，最大可连通流量。

算法：

- 福特-富尔克森算法（Ford-Fulkerson）
- 埃德蒙兹-卡普算法（Edmonds-Karp）
- 迪尼克算法（Dinic）
