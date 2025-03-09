---
title: echarts使用笔记
date: 2024-12-29
tag:
  - JavaScript
order: 1
---


## 直角坐标系

直角坐标系：
+ 柱状图
+ 折线图
+ 散点图

grid 网格 —— 坐标边框
axis 坐标轴
dataZoom 区域缩放

公共

+ dataTool

## 角度坐标系

### 饼图 pie

```json
[
  {"name":"产品1", "value":11231},
  {"name":"产品2", "value":6000},
  {"name":"产品3", "value":9203},
  {"name":"产品1", "value":6700},
  {"name":"产品5", "value":11231},
]
```

label
redius
roseType 兰丁格尔图 radius
selectedMode 选中效果 single/multiple
selectedOffset 选中偏移 30



## geo坐标系

### 地图

地图图表的使用方式：
+ 百度地图API：1.申请百度地图ak
+ 矢量地图：2.准备矢量地图数据

```json
{
  "type": "FeatureCollection",
  "UTF8Encoding": true,
  "features": [
    {
      "id": "710000",
      "type": "Feature",
      "geometry": {
        "type": "MultiPolygon",
        "coordinates": [],
        "encodeOffsets": []
      },
      "properties": {
        "cp": [121.509062,25.044332],
        "name": "xxx",
        "childNum": 6
      }
    }
  ]
}
```

```bash
$.get('json/map/xxx.json', function(json){})
echarts.registerMap('xxxMap', json)

var option = {
  geo: {
    type:'map'
    map:'xxxMap'
  }
}
```

roam 缩放拖动 true
label: {
  show: true 展示标签
}
zoom: 1 设置初始缩放
center: [87.61, 43.79] 设置初始中心

关联数据：

```js
var airData = [
  {name: '北京', value: 39.92},
  {name: '天津', value: 39.92},
  {name: '上海', value: 39.92},
  {name: '重庆', value: 39.92},
  {name: '河北', value: 39.92},
  {name: '河南', value: 39.92},
  {name: '辽宁', value: 39.92},
]

var option = {
  geo: {
    type: 'map',
    map: ....
  }
  series: [
    {
      data: airData,
      geoIndex: 0,
      type: 'map'
    }
  ],
  visualMap: {
    min: 0,
    max: 300,
    inRange: {
      color: ['white','red']
    },
    calculable: true, // 出现左侧滑块
  }
}
```

关联散点

```js
var scatterData = [
  {
    value: [117.28, 31.86]
  }
];
var option = {
  series: [
    {
      data: scatterData // 添加散点锁具
      type: 'effectScatter'
      coordinateSystem: 'geo' // 指明散点使用的坐标系统
      rippleEffect: {
        scale: 10 // 设置涟漪效果范围
      }
    }
  ]
}
```
