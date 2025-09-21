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

const echartsRef = ref()
const echartsInstance = ref()

function screenAdapter() {
  echartsInstance.value?.resize()
}

onMounted(() => {
  echartsInstance.value = echarts.init(echartsRef.value)
  echarts.registerMap('chinaMap', chianGeoJson)
  echartsInstance.value.setOption({
    geo: {
      type: 'map',
      map: 'chinaMap',
      roam: true, // 可拖动、滚轮放大缩小
      label: {
        // show: true, // 标签常显式
      },
      zoom: 2, // 初始放大比例
      center: [87.6, 43.79], // 初始中心点
    },
  })
  window.addEventListener('resize', screenAdapter)
})
onUnmounted(() => {
  window.removeEventListener('resize', screenAdapter)
})
</script>
