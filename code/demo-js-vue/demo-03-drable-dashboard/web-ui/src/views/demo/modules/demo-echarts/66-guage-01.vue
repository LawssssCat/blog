<!-- 参考：https://www.jianshu.com/p/9af1fbc63447 -->
<template>
  <div class="wrapper">
    <ResizeEchart :options="options" class="PolarGauge"></ResizeEchart>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ResizeEchart from '@/components/echarts/ResizeEchart.vue'

const options = ref({
  title: [
    {
      text: 'AQI指数',
      top: '80%',
      x: 'center',
      borderColor: '#fff',
      borderWidth: 1,
      borderRadius: 12,
      padding: [7, 14],
      textStyle: {
        fontWeight: 'normal',
        fontSize: '1rem',
        color: '#fff',
      },
    },
  ],
  angleAxis: {
    show: false,
    type: 'value',
    max: 100, // 最大值
    endAngle: -45,
    startAngle: 225, //极坐标初始角度
    splitLine: {
      show: false,
    },
  },
  barMaxWidth: 14, //圆环宽度
  radiusAxis: {
    show: false,
    type: 'category',
  },
  //圆环位置和大小
  polar: {
    center: ['50%', '50%'],
    radius: '100%',
  },
  series: [
    {
      type: 'bar',
      data: [
        {
          //上层圆环，显示数据
          value: 75,
          itemStyle: {
            color: '#80B8FF',
          },
        },
      ],
      // barGap: '-100%', //柱间距离,上下两层圆环重合
      coordinateSystem: 'polar',
      roundCap: true, //顶端圆角
      z: 2, //圆环层级，同zindex
    },
    {
      //下层圆环，显示最大值
      type: 'bar',
      data: [
        {
          value: 100,
          itemStyle: {
            color: {
              //图形渐变颜色方法，四个数字分别代表，右，下，左，上，offset表示0%到100%
              type: 'linear',
              x: 0,
              y: 0,
              x2: 1, //从左到右 0-1
              y2: 0,
              colorStops: [
                {
                  offset: 0,
                  color: '#FF6666',
                },
                {
                  offset: 0.7,
                  color: '#FFFF66',
                },
                {
                  offset: 1,
                  color: '#00FFFF',
                },
              ],
            },
          },
        },
      ],
      // barGap: '-100%',
      coordinateSystem: 'polar',
      roundCap: true,
      z: 1,
    },
    //仪表盘
    {
      name: 'AQI',
      type: 'gauge',
      startAngle: 225, //起始角度，同极坐标
      endAngle: -45, //终止角度，同极坐标
      axisLine: {
        show: false,
      },
      splitLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
      axisLabel: {
        show: false,
      },
      splitLabel: {
        show: false,
      },
      pointer: {
        show: false,
      },
      title: {
        offsetCenter: [0, 50],
        color: '#FFFFFF',
        fontSize: 60,
        fontWeight: 500,
        rich: {
          a: {
            fontWeight: 'normal',
            fontSize: 16,
            color: '#FFF',
            padding: [0, 0, 0, 40],
          },
        },
      },
      detail: {
        formatter: function (e) {
          return '良'
        },
        color: '#fff',
        fontSize: 16,
        offsetCenter: [0, -50],
        backgroundColor: '#FCC841',
        borderRadius: 8,
        padding: [4, 12],
      },
      data: [
        {
          value: 75,
          name: 75 + '\n' + '{a|' + '首要污染物：PM10' + '}',
        },
      ],
    },
  ],
})
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
