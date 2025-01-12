---
title: 变更日志
icon: log
article: false
---

记录站点的配置变更、变更计划。

<!-- more -->

## 2025 年 1 月 12 日 增强 Markdown 功能

高亮：!!高亮！!!

标记：==标记！==

属性（f12看id为hello）：属性 {#hello}

## 2025 年 1 月 8 日 demo

参考：
https://theme-hope.vuejs.press/zh/guide/markdown/code/demo.html

::: playground#vue 使用自定义导入的 Vue 案例

@file App.vue

```vue
<script setup>
import { ref, onMounted } from "vue";
import "@antv/g6"

let mountNode = ref()
// 定义数据源
const data = {
  // 点集
  nodes: [{
    id: 'node1',
    x: 100,
    y: 200
  },{
    id: 'node2',
    x: 300,
    y: 200
  }],
  // 边集
  edges: [
    // 表示一条从 node1 节点连接到 node2 节点的边
    {
      source: 'node1',
      target: 'node2'
    }
  ]
};

onMounted(() => {
  // 创建 G6 图实例
  const graph = new G6.Graph({
    container: mountNode.value,
    // 画布宽高
    width: 800,
    height: 500
  });
  // 读取数据
  graph.data(data);
  // 渲染图
  graph.render();
});
</script>

<template>
<div ref="mountNode" class="bg"></div>
</template>

<style scoped>
.bg {
  background-color: #e1e
}
</style>
```

@import

```json
{
  "imports": {
    "vue": "https://sfc.vuejs.org/vue.runtime.esm-browser.js",
    "@antv/g6": "https://gw.alipayobjects.com/os/antv/pkg/_antv.g6-3.2.3/build/g6.js"
  }
}
```

:::

## 2024 年 4 月 9 日 新增 site info / url 组件

`<SiteLink path="/path/to/link/" />` —— 生成站点链接，以标题名显示

链接 `<SiteLink path="./" />`：
<SiteLink path="./" />
链接 `<SiteLink path="./intro.md" />`：
<SiteLink path="./intro.md" />
链接 `<SiteLink path="/zh/changelog.md" />`：
<SiteLink path="/zh/changelog.md" />

## 2024 年 4 月 6 日 代码高亮 （todo 维护）

引入来自 shiki 的 transformers。

`transformerMetaHighlight` —— 缺陷：1. 无法高亮 todo 修复 2. 左侧高亮 todo 原因

```js {1,3-4}
console.log("1");
console.log("2");
console.log("3");
console.log("4");
```

`transformerNotationHighlight` —— 缺陷： 同上

```ts {3,4}
console.log("hewwo"); // [!code highlight]
console.log("hello");
console.log("hello");
console.log("hello");
```

`transformerNotationDiff`

```ts
console.log("hewwo"); // [!code --]
console.log("hello"); // [!code ++]
```

`transformerNotationFocus`

```ts
console.log("Not focused");
console.log("Focused"); // [!code focus]
console.log("Not focused");
```

## 2024 年 4 月 4 日 新增 url 组件

`<RepoLink path="/path/to/src/" />` —— 生成路由到代码仓库源码的地址

链接 `<RepoLink path="/src/zh/changelog.md" />`：
<RepoLink path="/src/zh/changelog.md" />
链接 `<RepoLink path="./" />`：
<RepoLink path="./" />
链接 `<RepoLink path="./intro.md" />`：
<RepoLink path="./intro.md" />
