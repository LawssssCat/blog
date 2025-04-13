<!--
参考：
https://juejin.cn/post/6958979078594494495

思路：
1. 在组件内进行监听窗口 window.addEventListener('resize', fn)
2. 组件响应图表视图chart.resize()
-->
<template>
  <div class="echart-chart-wrapper">
    <div ref="chartElementRef" class="echart-chart"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import ResiableChart from './ResizableChart'

const props = defineProps({
  options: {
    type: Object,
    default: () => {},
  },
  loading: {
    type: Boolean,
    default: false,
  },
})

const chartElementRef = ref()

const chartInstance = new ResiableChart()

// 数据可能是异步的
watch(
  () => props.options,
  (newVal) => {
    chartInstance.draw(newVal)
  },
  {
    deep: true,
  },
)

onMounted(() => {
  chartInstance.init(chartElementRef.value, props.options)
})
onUnmounted(() => {
  chartInstance.destroy()
})

defineExpose({
  chartElementRef,
  chartInstance,
})
</script>

<style lang="scss" scoped>
.echart-chart {
  height: 100%;
}
</style>
