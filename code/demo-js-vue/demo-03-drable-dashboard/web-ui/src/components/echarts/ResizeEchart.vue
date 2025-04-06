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
import * as echarts from 'echarts'
import _ from 'lodash'

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
const chartInstance = ref()
const isFinish = ref(false)

function drawChart(options) {
  chartInstance.value.setOption(options, true)
}

// 为什么用防抖：节流虽然能“及时”响应变化，但是最终只看最后一次resize结果，中间的渲染并不带来收益
// debounce
// throttle
const resizeChart = _.debounce(() => {
  chartInstance.value.resize()
  console.log('resize', chartElementRef.value)
}, 200)

defineExpose({
  chartElementRef,
  chartInstance,
})

// 数据可能是异步的
watch(
  () => props.options,
  (newVal) => {
    drawChart(newVal)
  },
  {
    deep: true,
  },
)

onMounted(() => {
  if (!chartInstance.value) {
    chartInstance.value = echarts.init(chartElementRef.value)
  }
  // 监听窗口大小变化
  window.addEventListener('resize', resizeChart)
  // 绘制图形
  drawChart(props.options)
  // todo 必要性
  // chartInstance.value.on('finished', () => {
  //   if (!isFinish.value) {
  //     isFinish.value = true
  //   }
  // })
})
onUnmounted(() => {
  window.removeEventListener('resize', resizeChart)
  if (chartInstance.value) {
    chartInstance.value.dispose()
    chartInstance.value = null
  }
})
</script>

<style lang="scss" scoped>
.echart-chart {
  height: 100%;
}
</style>
