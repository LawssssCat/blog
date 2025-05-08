---
title: Lodash使用
---

Lodash弥补ES5中对象、函数、数组API不足的问题，在ES6后依然有防抖、深层克隆等函数值得使用。

::: info

Lodash 对开发者最大的价值是有大量的现存优秀实践可以“参考”！

:::

## 常见函数

+ 数学
  + sum/max/mean
+ 字符串
  + truncate
+ 数组
  + forEach
  + sample/shuffle
  + uniq
  + range
+ 集合
  + intersection/union/xor/zip
+ 类型
  + `isXxx` 各种类型判断
+ 引用
  + cloneDeep
+ 函数
  + after/once
+ 性能
  + debounce/throttle —— 节流/防抖
  + memoize —— 结果缓存
  + attempt —— 异常处理

todo lodash/fp

---

扩展

+ xxxBy —— 传入函数，相当于map转换

### 数组/chunk

```js
_.chunk([1,2,3,4,5,6,7], 3)
// [[1,2,3], [4,5,6], [7]]
```

## 按需引入

todo
