function getPropertyName(names, target) {
  return names.find((name) => name in target)
}

const enterFullScreenName = getPropertyName(
  ['requestFullscreen', 'mozRequestFullScreen', 'webkitRequestFullscreen', 'msRequestFullscreen'],
  document.documentElement,
)
// 进入全屏
export function enterFullScreen(ele) {
  console.log('enterFullScreen', ele)
  return enterFullScreenName && ele[enterFullScreenName]()
}

const exitFullScreenName = getPropertyName(
  ['exitFullscreen', 'mozCancelFullScreen', 'webkitExitFullscreen', 'msExitFullscreen'],
  document,
)
// 退出全屏
export function exitFullScreen() {
  exitFullScreenName && document[exitFullScreenName]()
}

const fullEleName = getPropertyName(
  ['fullscreenElement', 'mozFullScreenElement', 'msFullScreenElement', 'webkitFullscreenElement'],
  document,
)
// 获取全屏元素
export function getFullScreenEle() {
  return fullEleName && document[fullEleName]
}

// 是否全屏
export function isFullScreen() {
  return !!getFullScreenEle()
}

export function toggleFullScreen(ele) {
  isFullScreen() ? exitFullScreen() : enterFullScreen(ele)
}
