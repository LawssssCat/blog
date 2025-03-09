<!-- 商家销售统计的横向柱状图 -->
<template>
  <div class="com-container">
    <div class="com-chart" ref="sellerRef">没有数据</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

const sellerRef = new ref(null)
const allData = new ref({})
const chartInstance = new ref(null)

// 初始化echartInstance对象
function initChart() {
  chartInstance.value = echarts.init(sellerRef.value)
}

// 获取服务器的数据
async function getData() {
  const { data: ret } = await axios.get('/seller')
  // console.log(data)
  allData.value = ret.data
}

// 更新图表
function updateChart() {
  const sellerNames = allData.value.map((item) => item.name)
  const sellerValues = allData.value.map((item) => item.value)
  const option = {
    xAxis: {
      type: 'value',
    },
    yAxis: {
      type: 'category',
      data: sellerNames,
    },
    series: [
      {
        type: 'bar',
        data: sellerValues,
      },
    ],
  }
  chartInstance.value.setOption(option)
}

onMounted(async () => {
  initChart()
  await getData()
  updateChart()
})
</script>

<style lang="scss" scoped></style>
