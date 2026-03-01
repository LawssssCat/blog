---
title: 前端路径
order: 0
---

大佬：

+ https://github.com/sponsors/antfu

体系 todo

+ <https://www.bilibili.com/video/BV1JQQ7YXE53/>

方向：

+ 复杂业务
  + UI封装
  + 功能封装
    + 工具封装 e.g. lodash/moment.js/rx.js...
    + 基础能力抽象、封装 e.g. vue/react/...
    + 动态表单
    + Xx编辑器
      + 富文本
      + 3D建模
    + 可视化
      + 2D图表
      + 3D模型渲染
      + GIS
    + ...
+ 工程

美术：样式/绘图

+ HEX | RGB | HSL <https://www.bilibili.com/video/BV1yN4y1G7vm>

三板斧：

+ [js](./js.md)
+ [css](./css.md)
  + tailwindcss
  + UnoCss
  + css Generators —— “形状” 生成器
+ [svg](./svg.md)
+ canvas

框架：

+ [vue](../dev-js-vue/README.md)
+ react
+ ~~angular~~ —— 没了解，感觉生态差？

打包工具

+ vite
+ webpack
+ ~~gulp~~ ____，但国内商业项目不用

函数库

+ [lodash](./lodash.md) —— 如字符串、集合、防抖/节流等基础函数
+ VueUse —— 基于vue的函数优秀实践封装
+ rxjs —— 事件链式处理

图标库

+ todo element plus 入口
+ opentiny icon

动画库

+ **GSAP**
+ Animate CSS

表单库（动态）

+ [Alibaba Formily](https://www.bilibili.com/video/BV1d54y1H78S)
  + todo <https://www.bilibili.com/video/BV1Cg4y1873P>
  + todo <https://www.bilibili.com/video/BV1d54y1H78S>
+ Antd/Fusion Form
+ Fomik
+ React FinalForm
+ React Schema Form
+ React Hook Form
+ UForm

提示库（Tooltip）

+ [floating-ui](https://floating-ui.com/)
+ [popper.js](https://popper.js.org/)
+ Ballon.css

拖拽库

+ [原生](./js-drag-native.md)
+ gridstack.js 资料全、有生命周期（有公司使用）
+ View Regable Plus
+ View3DND
+ VueUse —— 不止拖拽，工具集合

图表库

+ echarts.js
+ antv

大屏库（[link_大屏适配方案](./feature-screen.md)）

+ datav —— 使用前，注意生命周期；建议参考源码手搓
<https://www.bilibili.com/video/BV1z1421t7gQ>

3D库

+ WebGL
+ three.js
  + 案例库
    + https://github.com/z2586300277/three-cesium-examples
    + https://z2586300277.github.io/three-cesium-examples/#/example
    + https://threehub.cn/#/example
    + http://babylonjsx.cn/home.html
    + https://github.com/blueRaining/Next3D
+ echarts-gl.js （配合echarts.js使用）

WebGIS（Geographic Information System，地理信息系统） —— todo https://webgis.cn/fundation-concept.html

+ 地图库（GeoJson）
  + [Turf.js](https://github.com/Turfjs/turf)
    + https://turfjs.org/
    + https://turfjs.fenxianglu.cn/
  + L7Editor
+ 工具
  + geojson数据下载器|阿里云DataV.GeoAtlas —— https://datav.aliyun.com/portal/school/atlas/area_selector
  + geojson数据下载器（cn，到省市区乡县） —— https://hxkj.vip/demo/echartsMap/ | [link_github](https://github.com/TangSY/echarts-map-demo),[link_gitee](https://gitee.com/HashTang/echarts-map-demo)
  + geojson编辑器|基于高德|Fine-GeoJSON-Editor —— https://geojson.finevis.cc/
  + ~~geojson编辑器（更懂cn宝宝） —— https://l7editor.antv.antgroup.com/~~
  + geojson编辑器 —— https://geojson.io/
  + OpenStreetMap|开源街道信息 —— https://www.openstreetmap.org/
  + json编辑器 —— https://www.bejson.com/jsoneditoronline/
