<template>
  <div>
    ttt
    <div id="mountNode"></div>
  </div>
</template>

<script setup lang="ts">
import { Graph } from "@antv/g6";
import { onMounted } from "vue";

const data = {
  nodes: Array.from({ length: 11 }).map((_, i) => ({
    id: `node-${i}`,
    data: {
      category: i === 0 ? "central" : i % 2 === 0 ? "around-2" : "around-1",
    },
    // style: { x: 50 + i * 5, y: 50 + i * 5 }, // 通过布局自动设置位置
  })),
  edges: Array.from({ length: 10 }).map((_, i) => ({
    source: `node-0`,
    target: `node-${i + 1}`,
  })),
};

onMounted(() => {
  const graph = new Graph({
    container: "mountNode",
    data,
    node: {
      type: (datum) => (datum.id === "node-0" ? "rect" : "circle"),
      style: {
        // fill: "pink",
        size: 20,
      },
      palette: {
        field: "category",
        color: "tableau",
      },
    },
    edge: {
      style: {
        stroke: "lightgreen",
      },
    },
    // behaviors: ["drag-canvas", "zoom-canvas", "drag-element"], // 设置行为
    layout: {
      type: "d3-force", // 它是一种力导向布局算法，可以模拟节点之间的引力和斥力，使得节点自动调整到合适的位置
    },
  });

  graph.render();
});
</script>

<style scoped></style>
