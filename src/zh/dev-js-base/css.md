---
title: 前端样式
date: 2025-03-01
tag:
  - CSS
order: 1
---

网页样式

参考：

+ https://www.bilibili.com/video/BV1nM411T77W
+ https://www.bilibili.com/video/BV1w84y1r7X8

todo 布局
https://www.bilibili.com/video/BV1am4y1E7DM/
https://www.bilibili.com/video/BV1EM411m71m/

todo 动画
https://www.bilibili.com/video/BV11X4y1V7ZZ
https://www.bilibili.com/video/BV1Ki421S7WB 黑白切换⚪扩散

todo 侧边栏
https://www.bilibili.com/video/BV1uL41157hj
https://www.bilibili.com/video/BV1kb4y1J7L9/
https://www.bilibili.com/video/BV1kN4y1M7Gq/
https://www.bilibili.com/video/BV1yh411a78Z/ ⭐

todo 顶部菜单
https://www.bilibili.com/video/BV15Y411P7Rd/

todo 项目
https://www.bilibili.com/video/BV1pq4y1c7oy
https://www.bilibili.com/video/BV1nw411Q7sS/
 https://www.bilibili.com/video/BV1nj411K7ME/ ⭐

## 基础样式

```html
<!-- @include: @project/code/demo-js-css/demo-01-layout/index-00-base.html -->
```

## 布局

div —— 容器

容器类型：（通过 `display: xxx` 让 div 变成 xxx 容器）

+ flex （CSS3）： 先出现，好。侧重：todo
+ grid （CSS3）： 后出现，更好。侧重：todo

### flex

概念：

+ 坐标轴
  + `main axis` 左右
    + `main start`
    + `main end`
  + `cross axis` 上下
    + `cross start`
    + `cross end`
+ 排布： `flex-direction: xxx`
  + `row`/`row-reverse` 基于 `main axis` 横向排布
  + `column`/`column-reverse` 基于 `cross axis` 横向排布
+ 宽度（在item中指定）：
  + `flex-basis` 提供基础宽度
  + `flex-grow`/`flex-shrink`/`flex` 提供宽度比例
    + `flex-grow: 1` 扩展剩余宽度（e.g. 容器width=500px 元素5个width=10px 元素实际width=`10px+(500px-10px*5)/5=500px/5=100px`）
    + `flex-shrink: 1` 缩减多余宽度（e.g. 容器width=500px 元素5个width=200px 元素实际width=`200px-(200px*5-500px)/5=500px/5=100px`）
    + `flex: 1 2 30px` = `grow 1; shrink 2; basis 30px` 
+ 对齐
  + `align-items: stretch/flex-start/flex-end/center/baseline` 交叉轴元素对齐
  + `justify-items: ` 主轴元素对齐
  + `justify-content: flex-start/flex-end/center/space-between/space-aroundspace-evenly` 主轴内容对齐
  + `align-content: 同上` 交叉轴内容对齐
  + `flex-wrap: nowrap/wrap` 换行


```html
<!-- @include: @project/code/demo-js-css/demo-01-layout/index-01-flex.html -->
```

### grid

参考

+ todo https://www.webhek.com/post/interactive-guide-to-grid/
+ todo https://ruanyifeng.com/blog/2019/03/grid-layout-tutorial.html

grid = 网格

概念：

+ 行/列 
  + `grid-template-columns: 200px/1fr/repeat(12, 50px)/repeat(auto-fill, 150px)/repeat(auto-fill, minmax(150px, 1fr))` 列宽
    + fr = fraction
  + `grid-template-rows` 行高
  + `grid-auto-rows` 默认行高 
+ 空隙
  + `grid-row-gap`
  + `grid-column-gap`
  + `gap` 简写
+ 布局
  + `grid-column-start/grid-column-end` 简写 `grid-column: start/end` （tip:跨度1则可忽略end）
  + `grid-row-start/grid-row-end` 简写 `grid-row: start/end`
  + 可视化简写 `grid-template-areas` 加 `grid-area: xxx`


```html
<!-- @include: @project/code/demo-js-css/demo-01-layout/index-01-grid.html -->
```

## 动画

todo
https://www.bilibili.com/video/BV1nM411T77W?
