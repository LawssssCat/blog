---
title: 变更日志
icon: log
article: false
---

记录站点的配置变更、变更计划。

<!-- more -->

## 2024 年 4 月 6 日 代码高亮

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
