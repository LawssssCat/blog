import * as echarts from 'echarts'
import _ from 'lodash'

export default class ResiableChart {
  #instance
  #observer
  #resize

  constructor() {
    // 为什么用防抖：节流虽然能“及时”响应变化，但是最终只看最后一次resize结果，中间的渲染并不带来收益
    // debounce
    // throttle
    this.#resize = _.debounce(() => {
      this.#instance.resize()
      console.log('resize', this.#instance)
    }, 200)
  }

  init(element, options) {
    this.#instance = echarts.init(element)
    this.draw(options)
    this.#observer = new ResizeObserver(this.#resize)
    // 监听窗口大小变化
    // window.addEventListener('resize', resizeChart)
    this.#observer.observe(element)

    // const isFinish = ref(false)
    // todo 必要性
    // chartInstance.value.on('finished', () => {
    //   if (!isFinish.value) {
    //     isFinish.value = true
    //   }
    // })
  }

  destroy() {
    // window.removeEventListener('resize', resizeChart)
    if (this.#observer) {
      this.#observer.disconnect()
    }

    if (this.#instance) {
      this.#instance.dispose()
      this.#instance = null
    }
  }

  draw(options) {
    this.#instance.setOption(options)
  }
}
