<template>
  <div class="wrapper">
    <ResizeEchart :options="options" class="PolarGauge"></ResizeEchart>
    <!-- <div id="main" class="PolarGauge"></div> -->
  </div>
</template>

<script setup>
import ResizeEchart from '@/components/echarts/ResizeEchart.vue'

const options = {
  title: [
    {
      text: 'Tangential Polar Bar Label Position (middle)',
    },
  ],
  polar: {
    radius: [30, '80%'],
  },
  angleAxis: {
    max: 4,
    startAngle: 75,
  },
  radiusAxis: {
    type: 'category',
    data: ['a', 'b', 'c', 'd'],
  },
  tooltip: {},
  series: {
    type: 'bar',
    data: [2, 1.2, 2.4, 3.6],
    coordinateSystem: 'polar',
    label: {
      show: true,
      position: 'middle',
      formatter: '{b}: {c}',
    },
  },
}
// import { onMounted } from 'vue'
// import * as echarts from 'echarts'

// onMounted(() => {
//   var chartDom = document.getElementById('main')
//   var myChart = echarts.init(chartDom)
//   var option

//   option = {
//     title: [
//       {
//         text: 'Tangential Polar Bar Label Position (middle)',
//       },
//     ],
//     polar: {
//       radius: [30, '80%'],
//     },
//     angleAxis: {
//       max: 4,
//       startAngle: 75,
//     },
//     radiusAxis: {
//       type: 'category',
//       data: ['a', 'b', 'c', 'd'],
//     },
//     tooltip: {},
//     series: {
//       type: 'bar',
//       data: [2, 1.2, 2.4, 3.6],
//       coordinateSystem: 'polar',
//       label: {
//         show: true,
//         position: 'middle',
//         formatter: '{b}: {c}',
//       },
//     },
//   }

//   myChart.setOption(option)
//   myChart.setOption(option)
//   myChart.resize()
// })
</script>

<style scoped>
.wrapper {
  background-color: #aaa;
  height: 100vh;
  overflow-y: auto;
}
.PolarGauge {
  height: 100%;
  border: 2px solid #f33;
}
</style>
