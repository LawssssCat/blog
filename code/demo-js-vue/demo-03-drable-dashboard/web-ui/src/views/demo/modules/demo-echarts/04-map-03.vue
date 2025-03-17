<!--
地图的使用方式:
1. 百度地图API —— 需要申请百度地图ak（accesskey）
1. 矢量地图
https://datav.aliyun.com/portal/school/atlas/area_selector

步骤：
1. ECharts 最基本的代码结构
2. 准备中国地图的矢量数据
3. 使用Ajax获取矢量地图数据
4. 在Ajax回调函数中注册地图矢量数据 echarts.registerMap('chinaMap', 矢量赌徒数据)
5. 配置geo的type为'map'，map为'chinaMap'
-->

<style scoped lang="scss">
.echart-container {
  width: 100%;
  height: 100%;
}
</style>

<template>
  <div class="echart-container" ref="echartsRef">xxx</div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts/lib/echarts'
import chianGeoJson from '@/assets/geo/china.json'

const airData = [
  {
    name: '北京市',
    value: 58.57080647907313,
  },
  {
    name: '天津市',
    value: 23.317169880335044,
  },
  {
    name: '河北省',
    value: 24.320945337511752,
  },
  {
    name: '山西省',
    value: 29.603344061344266,
  },
  {
    name: '内蒙古自治区',
    value: 2.3953991670907104,
  },
  {
    name: '辽宁省',
    value: 11.4564962477799,
  },
  {
    name: '吉林省',
    value: 25.687146240467218,
  },
  {
    name: '黑龙江省',
    value: 24.04105234669229,
  },
  {
    name: '上海市',
    value: 2.6991892016154106,
  },
  {
    name: '江苏省',
    value: 29.348445390866626,
  },
  {
    name: '浙江省',
    value: 18.17213223888888,
  },
  {
    name: '安徽省',
    value: 29.755559962390024,
  },
  {
    name: '福建省',
    value: 12.170691505935572,
  },
  {
    name: '江西省',
    value: 15.973323842004865,
  },
  {
    name: '山东省',
    value: 20.324525576738058,
  },
  {
    name: '河南省',
    value: 14.536908918685468,
  },
  {
    name: '湖北省',
    value: 15.956244524024077,
  },
  {
    name: '湖南省',
    value: 17.276023877103263,
  },
  {
    name: '广东省',
    value: 2.4214699109088844,
  },
  {
    name: '广西壮族自治区',
    value: 23.033324227904405,
  },
  {
    name: '海南省',
    value: 27.419958923094494,
  },
  {
    name: '重庆市',
    value: 5.131884907390358,
  },
  {
    name: '四川省',
    value: 27.389831853201876,
  },
  {
    name: '贵州省',
    value: 8.701595085667238,
  },
  {
    name: '云南省',
    value: 22.30376285031127,
  },
  {
    name: '西藏自治区',
    value: 5.746571194886176,
  },
  {
    name: '陕西省',
    value: 15.568560522545596,
  },
  {
    name: '甘肃省',
    value: 18.356001956011383,
  },
  {
    name: '青海省',
    value: 22.49116893279259,
  },
  {
    name: '宁夏回族自治区',
    value: 16.734772960313318,
  },
  {
    name: '新疆维吾尔自治区',
    value: 10.688507846737231,
  },
  {
    name: '台湾省',
    value: 11.578533824636146,
  },
  {
    name: '香港特别行政区',
    value: 12.9666650579844,
  },
  {
    name: '澳门特别行政区',
    value: 2.967828808667523,
  },
]
const scatterData = [
  {
    value: [117.283, 32.86],
  },
]

const echartsRef = ref()
const echartsInstance = ref()

function screenInit() {
  echartsInstance.value = echarts.init(echartsRef.value)
  echarts.registerMap('chinaMap', chianGeoJson)
  echartsInstance.value.setOption({
    geo: {
      type: 'map',
      map: 'chinaMap',
      roam: true,
      center: scatterData[0].value,
      zoom: 4,
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}<br/>{c} (p / km2)',
    },
    series: [
      {
        data: airData,
        type: 'map',
        geoIndex: 0,
      },
      {
        // 增加涟漪坐标
        data: scatterData,
        type: 'effectScatter',
        coordinateSystem: 'geo', // 指明使用geo坐标系统
        rippleEffect: {
          // 调整涟漪效果
          color: '#f9f9f9',
          scale: 10,
        },
      },
    ],
    visualMap: {
      min: 0,
      max: 50,
      inRange: {
        color: ['white', 'red'],
      },
      calculable: true,
    },
  })
}

function screenAdapter() {
  echartsInstance.value?.resize()
}

onMounted(() => {
  screenInit()
  // window.addEventListener('resize', screenAdapter)
})
onUnmounted(() => {
  // window.removeEventListener('resize', screenAdapter)
})
</script>
