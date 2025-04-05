---
title: svg使用
order: 0
---

SVG（Scalable Vector Graphics，可伸缩矢量图）

::: info

参考：

+ 文档 ——  <https://developer.mozilla.org/en-US/docs/Web/SVG>（官方资料）
+ 入门指导 —— <https://www.bilibili.com/video/BV1Nz411b7sQ>

:::

工具：

+ [SVGEdit](https://github.com/SVG-Edit/svgedit?tab=readme-ov-file) —— 开源
    online
    + https://svgedit.netlify.app/editor/index.html
    + https://unpkg.com/svgedit@7.3.6/dist/editor/index.html
+ ~~pencil —— 开源~~
+ figma —— 新生态
+ illustrator —— 专业

## 语法

### svg标签

 标签 | 分类 | 作用
--- | --- | ---
xmlns |  | xml namespace 防止标签命名冲突
viewBox | 视图 | 视图区域
preserveAspectRatio | 视图 | “对齐模式”和“填充模式” <br> 对齐模式：xMinYMin/.../xMidyMid（默认）/.../xMaxYMax <br> 填充模式：slice/meet（默认） 
x | 位置 | （少用）嵌套svg中，子svg标识原点位置 
y | 位置 | 
width | 形状 | svg（默认）宽度
height | 形状
fill | 颜色 | svg图形（默认）颜色
stroke | 颜色 | svg图形边线（默认）颜色
stroke-width | 
opacity | 
transform | 

::: tip
上述标签，除了 viewBox/preserveAspectRatio/xmlns 外都能通过 css 控制
（如果都设置了值，css优先级更高）
:::

:::::: normal-demo 图像 

```html
<svg
  width='200px' height='200px' 绘图区域
  viewBox='0 0 100 100'        视图区域
  style='background:white'
  xmlns="http://www.w3.org/2000/svg">
  <!-- 矩形 -->
  <rect x='10' y='20' width='20' height='10' fill='#66a' stroke='red' stroke-width='1px' />
  <!-- 文字 -->
  <text x='30' y='20' fill='red' stroke='#aa6'>hello</text>
  <!-- 转换 -->
  <svg x='10' y='30' width='80' height='80' viewBox="-40 0 150 100">
    <g
      fill="grey"
      transform="rotate(-10 50 100)
                 translate(-36 45.5)
                 skewX(40)
                 scale(1 0.5)">
      <path
        id="heart"
        d="M 10,30 A 20,20 0,0,1 50,30 A 20,20 0,0,1 90,30 Q 90,60 50,90 Q 10,60 10,30 z" />
    </g>
    <use href="#heart" fill="none" stroke="red" />
  </svg>
  <!-- 嵌套 -->
  <svg x='80' y='80'
  width='20px' height='20px' 
  viewBox='0 0 10 10' 只显示上半园
   fill='#933'
   xmlns="http://www.w3.org/2000/svg">
    <circle cx='10' cy='10' r='10' opacity='0.6'/>
  </svg>
</svg>
```

::::::

### svg图形

+ 基础图形
+ 布尔运算
+ 描边效果

#### 基础图形

:::::: vue-demo 图形

```vue
<template>
<div class='demo-box'>
  <div class='demo-line'><span>rect | 矩形</span>
    <svg viewBox='0 0 100 40'>
        <rect x='10' y='10' width='80' height='20'/>
    </svg>
  </div>
  <div class='demo-line'><span>circle | 正圆形</span>
    <svg viewBox='0 0 100 40'>
      <circle cx='20' cy='20' r='10'/>
    </svg>
  </div>
  <div class='demo-line'><span>ellipse | 椭圆</span>
    <svg viewBox='0 0 100 40'>
      <ellipse cx='30' cy='20' rx='20' ry='10'/>
    </svg>
  </div>
  <div class='demo-line'><span>line | 直线</span>
    <svg viewBox='0 0 100 40'>
      <line x1='10' y1='10' x2='90' y2='20' stroke-width='4'/>
    </svg>
  </div>
  <div class='demo-line'><span>polyline | 折线</span>
    <svg viewBox='0 0 100 40'>
      <polyline points='10,10 40,30 60,30 90,20' fill='none' stroke-width='4'/>
    </svg>
  </div>
  <div class='demo-line'><span>polygon | 多边形</span>
    <svg viewBox='0 0 100 40'>
      <polygon points='10,10 40,30 60,30 90,20' stroke-width='4'/>
    </svg>
  </div>
  <div class='demo-line'><span><a href='https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorials/SVG_from_scratch/Paths'>path | 路径</a></span>
    <svg viewBox='0 0 100 40'>
      <!-- 
       M 10 10 —— Move to 移动到 10 10 点
       H 90 —— Horizontal line to 水平线到 x=90 的位置
       V 90 —— Vertical line to 垂直线到 y=90 的位置
       L 20 30 —— Line To 直线到 x=20 y=30 的位置
       Z —— Close path 关闭路径 = 连线开始点和结束点，形成一个闭合图形
        -->
      <path d='M 10 20 V 30 H 30 L 60 10 C 90 0, 90 40, 60 30 Z' fill='none' stroke-width='4'/>
    </svg>
  </div>
</div>

</template>
<style scoped>
.demo-box {
  display: flex;
  flex-direction: column;
}
.demo-box .demo-line {
  display: flex;
  align-items: center;
}
.demo-box .demo-line svg {
  fill: #f36363;
  stroke: #6363f3;
  width: 100px;
  height: 40px;
}
</style>
```

::::::

#### 布尔运算
todo

#### 描边

todo
https://www.bilibili.com/video/BV1rZ421T7sz


:::::: vue-demo 图形

```vue
<template>
<div style='background:#f9f9f9'>
<h4>线条样式</h4>
<h5>原始样式：</h5>
<svg viewBox="0 0 200 100" width='200px' height='100px' xmlns="http://www.w3.org/2000/svg">
  <!-- Example of a polyline with the default fill -->
  <polyline points="0,100 50,25 50,75 100,0" />
  <!-- Example of the same polyline shape with stroke and no fill -->
  <polyline points="100,100 150,25 150,75 200,0" fill="none" stroke="black" />
</svg>
<h5>修改线条样式：</h5>
<svg viewBox="0 0 200 100" width='200px' height='100px' xmlns="http://www.w3.org/2000/svg">
  <!-- Example of a polyline with the default fill -->
  <polyline points="0,100 50,25 50,75 100,0" stroke="red" stroke-dasharray="4 1" />
  <!-- Example of the same polyline shape with stroke and no fill -->
  <polyline points="100,100 150,25 150,75 200,0" fill="none" stroke="black" stroke-dasharray="4 1" />
</svg>
</div>
</template>
```

::::::


### svg动画

主要标签
+ animate
+ animateTransform
+ animateMotion
+ set

#### animate

基础变化设置：
attributeName/from/to/by/dur/repeatCount/repeatDur/fill/href
start/end/restart

高级变化设置：
begin/end

关键帧相关：
keyTimes/values/calcMode（linear/discrete/paced/spline）/keySplines
accumulate/additive

##### 基础设置/高级联动

:::::: vue-demo 基础变化

```vue
<template>
<svg width='300' height='300' viewBox='0 0 300 300'
xmlns="http://www.w3.org/2000/svg">
  <circle id='myCircle' cx='150' cy='150' r='50' fill='blue' />
  <animate 
    href='#myCircle'
    attributeName="r"
    from='50' 
    to='150'
    dur='1s' 
    repeatCount='indefinite'
    fill='freeze'
  />
</svg>
</template>
```

::::::

问题：

1. path d 平滑过渡动画需要符合规则，可以引入 js 库简化 d 起始值限制
    + flubber
    + Amine.js 的 SVG morphing 能力 （⭐推荐⭐）
    + GSAP 的 MorphSVG （但收费）


:::::: vue-demo 动画交互

```vue
<template>
<h4>高级变化：鼠标事件（移入/移出/点击）</h4>
<div id='myCircle2btn'>点击我</div>
<svg width='300' height='300' viewBox='0 0 300 300'
xmlns="http://www.w3.org/2000/svg">
  <circle id='myCircle2' cx='150' cy='150' r='50' fill='blue' />
  <animate 
    href='#myCircle2'
    attributeName="r"
    to='150'
    dur='1s' 
    fill='freeze'
    begin='mouseover'
  />
  <animate 
    href='#myCircle2'
    attributeName="r"
    to='50'
    dur='1s' 
    fill='freeze'
    begin='mouseout'
  />
  <animate 
    href='#myCircle2'
    attributeName="fill"
    to='red'
    dur='1s' 
    fill='freeze'
    begin='mousedown'
  />
  <animate 
    href='#myCircle2'
    attributeName="fill"
    to='blue'
    dur='1s' 
    fill='freeze'
    begin='mouseup'
  />
  <animate 
    href='#myCircle2'
    attributeName="cx"
    by='100'
    dur='0.5s' 
    begin='myCircle2btn.click'
  />
</svg>

<hr/>
<h4>高级变化：动画联动</h4>
<div id='myCircle3btn'>点击我</div>
<svg width='300' height='300' viewBox='0 0 300 300'
xmlns="http://www.w3.org/2000/svg">
  
  <circle id='myCircle31' cx='150' cy='100' r='50' fill='blue' />
  <circle id='myCircle32' cx='150' cy='200' r='50' fill='blue' />
  <animate 
    id='ani001'
    href='#myCircle31'
    attributeName="cx"
    by='100'
    dur='1s' 
    fill='freeze'
    begin='myCircle3btn.click'
  />
  <animate 
    href='#myCircle32'
    attributeName="cx"
    by='100'
    dur='1s' 
    fill='freeze'
    begin='ani001.end - 0.5s;click'
  />
</svg>

<hr/>
<h4>高级变化：JS控制</h4>
<div @click='handleClick'>点击（看代码区别）</div>
<svg width='300' height='300' viewBox='0 0 300 300'
xmlns="http://www.w3.org/2000/svg">
  <circle id='myCirclejs' cx='150' cy='100' r='50' fill='blue' />
  <animate 
    ref='ani'
    href='#myCirclejs'
    attributeName="cx"
    by='100'
    dur='1s' 
    fill='indefinite'
    begin='click'
  />
</svg>
</template>

<script>
export default {
  methods: {
    handleClick() {
      this.$refs.ani.beginElement();
    }
  }
}
</script>
```

::::::

##### 关键帧

:::::: vue-demo 动画关键帧

```vue
<template>
<h4>高级变化：关键帧</h4>
<svg width='300' height='300' viewBox='0 0 300 300'
xmlns="http://www.w3.org/2000/svg">
  <!-- 一般动画 -->
  <circle cx='100' cy='50' r='50' fill='blue'>
    <animate 
      attributeName="cx"
      dur='1s'
      to='210'
      fill='freeze'
      repeatCount='indefinite'
    />
  </circle>
  <!-- 关键帧动画 -->
  <circle cx='100' cy='150' r='50' fill='blue'>
    <animate 
      attributeName="cx"
      dur='1s'
      keyTimes='0; 0.5; 1'
      values="100; 200;210 " 
      fill='freeze'
      repeatCount='indefinite'
    />
  </circle>
  <!-- 曲线动画 -->
  <circle cx='100' cy='250' r='50' fill='blue'>
    <animate 
      attributeName="cx"
      dur='1s'
      keyTimes='0; 1'
      values="100; 210 " 
      calcMode='spline'
      keySplines='0.25 0.1 0.25 1'
      fill='freeze'
      repeatCount='indefinite'
    />
  </circle>
</svg>
</template>

<script>
export default {
  methods: {
  }
}
</script>
```

::::::

#### animateMotion 沿线动画

+ 基础设置：
path/mpath/rotate

+ 关键帧：
keyPoints

##### 基础设置/关键设置

:::::: vue-demo 沿线动画

```vue
<template>
<div id='sfasfsfsfaffas'>
<h4>原始动画</h4>
<svg width="165" height="28"
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
 <!-- Created with SVG-edit - https://github.com/SVG-Edit/svgedit-->
   <path d="m2.5,14.05 c80,-40 80,40 160,0" fill="none" id="svg_6" stroke="#58c4a3" stroke-linecap="round" stroke-width="5"/>
  <circle r='10' fill='red' opacity='0.6'>
    <animateMotion dur='2s' repeatCount='indefinite'>
      <mpath href='#svg_6' />
    </animateMotion>
  </circle>
</svg>

<hr/>
<h4>视图范围调整</h4>
<svg width="180" height="80"
viewBox='0 -20 180 60'
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
   <path d="m10,20.01c80,-40 80,40 160,0" fill="none" id="svg_62" stroke="#58c4a3" stroke-linecap="round" stroke-width="5"/>
  <circle r='10' fill='red' opacity='0.6'>
    <animateMotion dur='2s' repeatCount='indefinite'>
      <mpath href='#svg_62' />
    </animateMotion>
  </circle>
</svg>

<hr/>
<h4>调整中心点</h4>
<svg width="180" height="80"
viewBox='0 -20 180 60'
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
   <path d="m10,20.01c80,-40 80,40 160,0" fill="none" id="svg_63" stroke="#58c4a3" stroke-linecap="round" stroke-width="5"/>
  <text fill='red' opacity='0.6'>
    hello~
    <animateMotion dur='2s' repeatCount='indefinite' rotate='auto'>
      <mpath href='#svg_63' />
    </animateMotion>
  </text>
</svg>
<br>
<svg width="180" height="80"
viewBox='0 -20 180 60'
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
   <path d="m10,20.01c80,-40 80,40 160,0" fill="none" id="svg_66" stroke="#58c4a3" stroke-linecap="round" stroke-width="5"/>
  <g id='svg_66g'>
    <text
      text-anchor="middle"
      dominant-baseline="middle"
      x='0' y='0' fill='red' opacity='0.6'>hello~</text>
  </g>
  <animateMotion dur='2s' repeatCount='indefinite' href='#svg_66g' rotate='auto'>
    <mpath href='#svg_66' />
  </animateMotion>
</svg>
</div>
</template>

<style>
#sfasfsfsfaffas svg {
  border: 1px solid #393;
}
</style>
```

::::::

::: tip

如何找到中心点：
+ 对于text标签
    1. 使用 `text-anchor="middle"`（水平居中）、`dominant-baseline="middle"`（垂直居中） 使文字 “居中”
+ 对于组合标签
    1. 将图形用 `g` 标签包裹
    1. 用 `transform='translate(-100, -100)'` 偏移图片宽高的一半

:::

##### 关键帧

:::::: vue-demo

```vue
<template>
<div id='sfasfsfsfaffas2'>
<h4>匀速</h4>
<svg width="165" height="28"
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
  <circle r='10' fill='red' opacity='0.6'>
    <animateMotion dur='2s' repeatCount='indefinite' path='m2.5,14.05 c80,-40 80,40 160,0'>
    </animateMotion>
  </circle>
</svg>

<h4>关键帧调整</h4>
<svg width="165" height="28"
xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
  <circle r='10' fill='red' opacity='0.6'>
    <animateMotion dur='2s' repeatCount='indefinite' path='m2.5,14.05 c80,-40 80,40 160,0'
    keyPoints='0;0.1;0.9;1'
    keyTimes='0;0.4;0.6;1'
    >
    </animateMotion>
  </circle>
</svg>
</div>
</template>

<style>
#sfasfsfsfaffas2 svg {
  border: 1px solid #393;
}
</style>
```

::::::

::: tip

配合 GASP 的 ScrollTrigger 和 MotionPath 两个插件可以实现页面滚动时炫酷的联动效果！

:::

#### animateTransform
缩放/旋转

:::::: vue-demo

```vue
<template>
<div id='sfasfsfsfaffas3'>

<svg width='600' height='200' viewBox='0 0 600 200'>
<!-- 第一个矩形：平移动画 -->
<rect x='50' y='50' width='100' height='100' fill='blue'>
<animateTransform attributeName='transform'
type='translate' 
from='0 0'
to='200 0'
dur='3s'
repeatCount='indefinite'/>
</rect>

<!-- 第二个矩形：缩放边高 -->
<rect x='250' y='50' width='100' height='100' fill='green' 
transform-origin='300 100' 注意设置中心点
>
<animateTransform attributeName='transform'
type='scale' 
from='1'
to='2'
dur='3s'
repeatCount='indefinite'/>
</rect>

<!-- 第三个矩形：缩放边高 -->
<rect x='450' y='50' width='100' height='100' fill='red'>
<animateTransform attributeName='transform'
type='rotate' 
from='0 500 100'
to='360 500 100'
dur='3s'
repeatCount='indefinite'/>
</rect>

</svg>

</div>
</template>

<style>
#sfasfsfsfaffas3 svg {
  border: 1px solid #393;
}
</style>
```

::::::

::: tip

转换一般需要使用 `transform-origin: 100px 100px;` 设置图形的中心点

:::

#### set

和动画类似，但是没有动画的变化过程，直接改变！

## 示例

### 路径生长效果

:::::: vue-demo

```vue
<template>
  <svg width='300' height='400' viewBox='0 0 300 600' style='background:#f9f9f9'>
    <!-- 直线 -->
    <path d='M 10 20 H 200'
      stroke='black'
      stroke-width='20'
      stroke-dasharray='200'
      stroke-dashoffset='200'
    >
      <animate attributeName='stroke-dashoffset'
        to='-200'
        dur='3s'
        fill='freeze'
        repeatCount='indefinite'
      />
    </path>
    <!-- 形状边线 -->
    <rect x='10' y='50' width='200' height='200' fill='none' 
    stroke='black' 
    stroke-width='20'
    stroke-dasharray='800'
    stroke-dashoffset='800'
    stroke-linecap='square'>
      <animate attributeName='stroke-dashoffset' 
      to='-800'
      dur='3s'
      fill='freeze'
      repeatCount='indefinite'/>
    </rect>

    <!-- 曲线 -->
    <path d='M10 400 Q 80 200, 150 400 Q 220 600, 290 400'
    stroke-linecap='round'
    stroke='#9f9f9f'
    stroke-width='20'
    fill='none'
    vertor-effect='non-scaling-stroke'
    />
    <path d='M10 400 Q 80 200, 150 400 Q 220 600, 290 400'
    stroke-linecap='round'
    stroke='black'
    stroke-width='20'
    fill='none'
    stroke-dasharray='200 520'
    stroke-dashoffset='200'
    vertor-effect='non-scaling-stroke'
    >
      <animate attributeName='stroke-dashoffset'
      to='-520' dur='3s' repeatCount='indefinite' />
    </path>
  </svg>
</template>
```

::::::

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
