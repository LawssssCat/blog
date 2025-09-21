export function getCurrentSize() {
  // window.top    —— 返回当前窗口的最顶层浏览器窗口。 【如果窗口本身是顶层窗口，parent属性返回的是对自身的引用】
  // window.parent —— 父窗口。【如果窗口本身是顶层窗口，parent属性返回的是对自身的引用】
  // window.self   —— 是对当前窗口自身的引用。它和window属性是等价的。
  const isIframe = window.top !== window.self
  // window概念：
  // outerWidth  —— 窗口宽度
  // innerWidth  —— 窗口内容宽度
  // el概念：
  // scrollWidth —— el盒子宽度，包含滚动条、margin、border、padding
  // clientWidth —— el盒子宽度，不包含滚动条，包含margin、border、padding
  // offsetWidth —— el盒子宽度，不包含滚动条、margin，包含border、padding
  const currentWidth = isIframe ? window.top.innerWidth : window.innerWidth
  const currentHeight = isIframe ? window.top.innerHeight : window.innerHeight
  return {
    currentWidth,
    currentHeight,
  }
}

export function getTargetSize({
  currentHeight,
  currentWidth,
  bili = 1920 / 1080,
  minWidth = 1280,
}) {
  const targetWidth = Math.max(currentWidth, minWidth)
  const targetHeight = Math.max(currentHeight, targetWidth / bili)
  return {
    targetWidth,
    targetHeight,
  }
}

// 计算屏幕的rem
export function calcRem({
  baseRem = 16, // px
  baseWidth = 1920,
  baseHeight = 1080,
} = {}) {
  const { currentWidth, currentHeight } = getCurrentSize()
  const { targetWidth, targetHeight } = getTargetSize({
    currentWidth: currentWidth,
    currentHeight: currentHeight,
    bili: baseWidth / baseHeight,
    minWidth: 1280,
  })
  const targetRem = baseRem * (targetWidth / baseWidth)
  // window —— 浏览器窗口
  // document —— 当前窗口浏览的文档对象
  // document.documentElement —— 文档el节点，即html节点
  document.documentElement.style.fontSize = targetRem + 'px'
}

const observer = new ResizeObserver(() => calcRem()) // 有且只有一个，避免多次修改
export function autoCalcRem() {
  return {
    start: () => observer.observe(document.documentElement),
    close: () => {
      observer.disconnect()
      document.documentElement.style.fontSize = null
    },
  }
}
