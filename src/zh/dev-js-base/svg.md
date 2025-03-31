---
title: svg使用
order: 0
---

::: info

参考：

+ <https://www.bilibili.com/video/BV1Nz411b7sQ>

:::

图像 todo

```html
<svg
  width='500px' height='500px' 绘图区域
  viewBox='0 0 100 100'        视图区域
  style='background:white'>
  <!-- 矩形 -->
  <rect x='10' y='10' width='20' height='10'/>
  <!-- 文字 -->
  <text x='10' y='50'>hello</text>
</svg>
```

颜色

todo

缩放

todo

动画

todo

```html
<style>
#r1 {
  animation: move 1s ease 1s infinite alternate
}
@keyframes move{
  0%{transform:scale(1)}
  100%{transform:scale(1,5);x:50;fill:red}
}
</style>
<svg width='500px' height='500px'
  viewBox='0 0 100 100'
  style='background:white'>
  <rect id='r1' x='10' y='10' width='20' height='10'/>
</svg>
```

复用

todo

工程

todo 引入
