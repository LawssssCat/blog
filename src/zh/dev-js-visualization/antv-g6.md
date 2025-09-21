---
title: antv/g6 使用笔记
date: 2024-10-31
order: 1
---

::: warning
2025年1月12日，antv g6 有 v4 和 v5 两个主流版本，且两个版本在使用上有不少区别。
但很多文档没有表明其适用的版本。
因此使用时需要特别留意。
:::

官方文档：

- <https://g6.antv.vision/zh/docs/manual/tutorial/preface> （v5，2025年1月12日）
- <https://g6-v4.antv.vision/> （v4）

可参考笔记：

- [antv G6 4.x / 5.0-beta 版本 及 Graphin基本知识](https://juejin.cn/post/7315720126988238863)
- 书栈网 | [G6 API](https://www.bookstack.cn/read/antv-g6-api/42231065d3c7b97a.md) （v3） 

::: playground#vue 使用自定义导入的 Vue 案例

@file App.vue

```vue
<script setup>
import { ref, onMounted } from "vue";
import "@antv/g6"
const {Graph} = G6;

let mountNode = ref();

onMounted(() => {
  fetch("https://assets.antv.antgroup.com/g6/graph.json")
  .then((res) => res.json())
  .then((data) => {
    console.log(data)
    const graph = new Graph({
      container: mountNode.value,
      autoFit: "view",
      data,
      node: {
        style: {
          size: 10,
        },
        palette: {
          field: "group",
          color: "tableau",
        },
      },
      layout: {
        type: "d3-force",
        manyBody: {},
        x: {},
        y: {},
      },
      behaviors: ["drag-canvas", "zoom-canvas", "drag-element"],
    });

    graph.render();
  });
});
</script>

<template>
<div ref="mountNode" class="bg"></div>
</template>

<style scoped>
.bg {
  background-color: #fef
}
</style>
```

@import

```json
{
  "imports": {
    "vue": "https://sfc.vuejs.org/vue.runtime.esm-browser.js",
    "@antv/g6": "https://unpkg.com/@antv/g6@5/dist/g6.min.js"
  }
}
```

:::
