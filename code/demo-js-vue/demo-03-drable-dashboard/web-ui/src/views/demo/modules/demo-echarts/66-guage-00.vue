<template>
  <div class="wrapper">
    <div class="w-item">
      <ResizeEchart class="PolarGauge" :options="buildOptions()"></ResizeEchart>
    </div>
    <div class="w-item">
      <ResizeEchart class="PolarGauge" :options="buildOptions()"></ResizeEchart>
    </div>
    <div class="w-item">
      <ResizeEchart class="PolarGauge" :options="buildOptions()"></ResizeEchart>
    </div>
    <div class="w-item">
      <ResizeEchart class="PolarGauge" :options="buildOptions()"></ResizeEchart>
    </div>
    <div class="w-item">
      <ResizeEchart class="PolarGauge" :options="buildOptions()"></ResizeEchart>
    </div>
  </div>
</template>

<script setup>
import ResizeEchart from '@/components/echarts/ResizeEchart.vue'

const gaugeData = [
  {
    name: 'xx',
    value: 90,
  },
]

function buildOptions() {
  const options = {
    series: [
      {
        type: 'gauge',
        startAngle: -120,
        endAngle: -60,
        pointer: {
          show: false,
        },
        progress: {
          show: true,
          overlap: false,
          roundCap: true,
          clip: true,
          itemStyle: {
            // opacity: 0.6,
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                {
                  offset: 0,
                  color: 'red', // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: 'blue', // 100% 处的颜色
                },
              ],
              global: false, // 缺省为 false
            },
          },
        },
        axisLine: {
          roundCap: true,

          lineStyle: {
            width: 40, // rem?
          },
        },
        splitLine: {
          show: false,
          distance: 0, // rem?
          length: 10,
        },
        axisTick: {
          show: false,
        },
        axisLabel: {
          show: false,
          distance: 50, // rem?
        },
        data: gaugeData,
        title: {
          fontSize: '3em',
        },
        detail: {
          width: '70',
          height: '70',
          fontSize: '',
          color: 'inherit',
          borderColor: 'inherit',
          borderRadius: 99,
          borderWidth: 1,
          formatter: '{value}%',
        },
      },
    ],
  }
  return options
}
</script>

<style scoped lang="scss">
.wrapper {
  background-color: #aaa;
  height: 100vh;
  display: grid;
  grid-template-columns: 50% 50%;
  grid-template-rows: repeat(4, 500px);
  overflow-y: auto;
}

.PolarGauge {
  height: 100%;
}
</style>
