---
title: JS 调试/反调试
date: 2024-10-01
tag:
  - JavaScript
order: 1
---

## 反调试

原理

1. 控制台特征判断控制台是否被打开，显示正常内容是否显示
1. 通过 js 的内置函数进行无限 debugger，干扰调试过程

可用操作

- 页面窗口大小检测
- `console.log` 打印检测
- `toString()` 检测
- `setInterval` 和 `setTimeout` 事件判断
- `eval` 执行 `debugger`
- `constructor` 构造函数执行 `debugger`
- `Function` 构造函数执行 `debugger`
- 调用 `windows` 的内置方法如 `window.close()` 关节界面和 `window.history.back()` 页面回退

e.g.

```js
// 检测时间差
function consoleOpenCallback2() {
  window.close();
}

setInterval(function () {
  const before = new Date();
  /* 新版 chrome 禁用了这种命令调用方式：
   * Uncaught EvalError:
   * Refused to evaluate a string as JavaScript because 'unsafe-eval' is not an allowed source of script in the following Content Security Policy directive:
   * "script-src chrome://resources chrome://webui-test 'self'".
   */
  (function () {}).constructor("debugger")();
  // debugger;
  const after = new Date();
  const cost = after.getTime() - before.getTime();
  if (cost > 100) {
    // consoleOpenCallback2();
    console.log("window.close()");
  }
}, 1000);
```

```java
// 进行定时器加构造器，达到无限 debugger
$(function() {
  setInterval(function() {
    a();
  }, 4E3);
  var a = function() {
    function a(c) {
      (function() {}).constructor("debugger")();
      // a(++c); // 放开后，浏览器会卡死！
    }
    try {
      a(0);
    } catch(d) {}
  }
})
```
