<!-- 商家销售统计的横向柱状图 -->
<template>
  <div class="com-container">
    <div class="com-chart" ref="sellerRef">没有数据</div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

const sellerRef = ref()
const chartInstance = ref(null)
const allData = ref({
  title: '商家销售统计',
  data: null, // 服务器返回的数据
  currentPage: 1, // 当前显示的页数
  totalPage: 0, // 总页数
  pageSize: 5,
  timerId: null,
})

// 初始化echartInstance对象
function initChart() {
  chartInstance.value = echarts.init(
    sellerRef.value,
    //
    // 'mint',
    'dark',
  )
  const initOption = {
    title: {
      textStyle: {
        fontSize: 66,
      },
      left: 20,
      top: 20,
    },
    grid: {
      top: '13%',
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true, // 距离包含坐标轴文字
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'line',
        z: 0,
        lineStyle: {
          type: 'solid',
          width: 66,
          color: '#201c3a',
        },
      },
    },
    xAxis: {
      type: 'value',
    },
    yAxis: {
      type: 'category',
    },
    series: [
      {
        type: 'bar',
        barWidth: 66,
        label: {
          show: true,
          position: 'right',
        },
        itemStyle: {
          barBorderRadius: [0, 33, 33, 0],
          // 颜色渐变
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            {
              offset: 0,
              color: '#5052EE',
            },
            {
              offset: 1,
              color: '#AB6EE5',
            },
          ]),
        },
      },
    ],
  }
  chartInstance.value.setOption(initOption)
  // 对图表对象进行鼠标事件的监听
  chartInstance.value.on('mouseover', () => {
    endInterval()
  })
  chartInstance.value.on('mouseout', () => {
    startInterval()
  })
}

// 获取服务器的数据
async function getData() {
  const { data: ret } = await axios.get('/seller')
  // console.log(data)
  const dlist = ret.data
  allData.value.data = dlist?.sort((a, b) => {
    return b.value - a.value // 从小到大排序
  })
  allData.value.totalPage = Math.ceil(dlist?.length / allData.value.pageSize)
  // console.log(allData.value.data)
}

// 更新图表
function updateChart() {
  // console.log(allData.value.currentPage)

  const start = (allData.value.currentPage - 1) * allData.value.pageSize
  const end = allData.value.currentPage * allData.value.pageSize
  const showData = allData.value.data.slice(start, end).reverse()
  // console.log(showData)
  const sellerNames = showData.map((item) => item.name)
  const sellerValues = showData.map((item) => item.value)
  const dataOption = {
    title: {
      text: '▮ ' + allData.value.title,
    },
    yAxis: {
      data: sellerNames,
    },
    series: [
      {
        data: sellerValues,
      },
    ],
  }
  chartInstance.value.setOption(dataOption)
}

function startInterval() {
  if (allData.value.timerId) {
    endInterval()
  }
  allData.value.timerId = setInterval(() => {
    allData.value.currentPage %= allData.value.totalPage
    allData.value.currentPage += 1
    updateChart()
  }, 3000)
}
function endInterval() {
  clearInterval(allData.value.timerId)
}

// 当浏览器的大小发生变化，会调用方法完成屏幕分辨率的适配
function screenAdapter() {
  const titleFontSize = (sellerRef.value.offsetWidth / 100) * 3.6
  const adapterOption = {
    title: {
      textStyle: {
        fontSize: titleFontSize,
      },
    },
    tooltip: {
      axisPointer: {
        lineStyle: {
          width: titleFontSize,
        },
      },
    },
    series: [
      {
        barWidth: titleFontSize,
        itemStyle: {
          barBorderRadius: [0, titleFontSize / 2, titleFontSize / 2, 0],
        },
      },
    ],
  }
  chartInstance.value.setOption(adapterOption)
  chartInstance.value.resize()
}

onMounted(async () => {
  initChart()
  await getData()
  updateChart()
  startInterval()
  window.addEventListener('resize', screenAdapter)
  screenAdapter()
})

onBeforeUnmount(() => {
  endInterval()
  window.removeEventListener('resize', screenAdapter)
})
</script>

<style lang="scss" scoped>
:deep(canvas) {
  border-radius: 30px;
}
</style>
