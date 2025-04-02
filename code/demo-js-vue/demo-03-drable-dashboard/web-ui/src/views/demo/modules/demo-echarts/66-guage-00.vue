<template>
  <div class="wrapper">
    <div ref="polarGaugeRef" class="PolarGauge"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'

// https://www.cnblogs.com/HelloWorld-Yu/p/12560805.html
// https://www.jianshu.com/p/9af1fbc63447
// https://echarts.apache.org/zh/option.html#color

// https://www.jianshu.com/p/9af1fbc63447

const polarGaugeRef = ref()
const echartInstance = ref()

const gaugeData = [
  {
    name: 'xx',
    value: 90,
  },
  //   {
  //   value: 10,
  // }
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

function init() {
  echartInstance.value = echarts.init(polarGaugeRef.value)
  echartInstance.value.setOption(buildOptions())
}

let observer

onMounted(() => {
  init()
  observer = new ResizeObserver(() => echartInstance.value.resize())
  observer.observe(polarGaugeRef.value)
})

onUnmounted(() => {
  if (observer) {
    observer.disconnect()
  }
})
</script>

<style scoped>
.wrapper {
  background-color: #aaa;
  height: 100vh;
}

.PolarGauge {
  height: 100%;
}
</style>
