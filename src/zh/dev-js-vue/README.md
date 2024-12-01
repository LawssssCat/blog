---
title: Vue 程序设计
date: 2024-10-31
order: 1
---

参考：

- 官网 —— https://cn.vuejs.org/guide/introduction.html
- ~~4 小时快速入门前端 Vue3+Vite+Pinia —— https://www.bilibili.com/video/BV1aa1NYxECK/~~ （vue2 语法）

## 基本使用

### 模板语法

- 渲染变量（`{{ val }}`,`v-html="val"`）,变量计算（`{{ val1 + val2 }}`）
- 属性变量（`v-bind:attr="val`/`:attr="val`）
- 动态属性变量（`v-bind:[val1,val2,...]="valn`/`:[val1,val2,...]="valn"`）
- 事件绑定（`v-on:click="count++"`/`@click="func1"`）,按键修饰符（`keyup`/`keydown`/...）,系统修饰符（`.ctrl`/`.alt`/...）,精确修饰符（`.exact`）,鼠标按键修饰符（`.left`/`.right`/`.middle`）
- 条件渲染（`v-if`/`v-else-if`/`v-else`,`v-show`）
- 循环渲染（`v-for="(item,index) in items" :key="item.id"`/`v-for="(value,key,index) in obj" :key="key"`,`v-for="(value,key) of obj" :key="key"`）
- 模板标签（`<template>`）

```html
<!-- @include: @project/code/demo-js-vue/demo-01-simple/index.html -->
```

### 自定义组件

**语法**

::: tabs

@tab vue2

script:

- 数据（`data(){return{val1:data1,...}}`）
- 计算数据（`computed:{data1(){return val1},...}`）
- 方法（`methods:{func1(){...},...}`）
- 生命周期（`beforeCreate`/`created`/`beforeMount`/`mounted`/`beforeUpdate`/`updated`/`beforeUnmount`/`unmounted`） [link](https://www.vueframework.com/docs/v3/cn/guide/composition-api-lifecycle-hooks.html)
- 变更监听（`watch: {val:{handler:(newVal,oldVal)=>{...},deep:true}}`）
- 组件（`components:{Component1,...}`）
- 上层变量（`props:{prop1:{type:String,default:""},...}`）

component:

- 变量传递（`<Component1 val1="data1">`,`props:{val1}`,`{{val1}}`）
- 变量绑定（`<Component1 v-model="val1">`,`props:{modelValue}`,`this.$emit('update:modelValue',value)`）
- 事件传递（`<Component1 @event1="func1">`,`this.$emit("event1")`）
- 匿名插槽（`<Component1><slot>`）
- 具名插槽（`<Component1><slot name="namedSlot" :val1="val1">`）

:::

**例子**

```html
<!-- @include: @project/code/demo-js-vue/demo-02-vite/src/App.vue -->
```

::: tabs

@tab vue2

```html
<!-- @include: @project/code/demo-js-vue/demo-02-vite/src/components/MyDialog.vue -->
```

@tab vue3

组合式 API

```html
<!-- @include: @project/code/demo-js-vue/demo-02-vite/src/components/MyDialog2.vue -->
```

:::

### 路由: Vue Router

[Vue Router](https://router.vuejs.org/)

- 不同的历史模式 （[link](https://router.vuejs.org/zh/guide/essentials/history-mode.html)）
  - createWebHashHistory —— `#` 请求未发送服务器 无法 SEO 捕获
  - createWebHistory —— （常见）适合浏览器场景，路径看起来 “正常” （e.g. `https://example.com/user/id`），但是路径无法被直接访问
  - createMemoryHistory —— 不记录历史记录，适合 node 环境和 SSR，不适合浏览器场景

```bash
npm install vue-router@4
```

::: tabs

@tab 路由配置

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/main.ts -->
```

@tab 路由显示

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/App.vue -->
```

@tab 二级路由

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/views/Home.vue -->
```

:::

### 状态管理

- bus
- vuex —— 【过时】 2022 年
- pinia —— 2019 年 ~ 2024 年 [`issue271`](https://github.com/vuejs/rfcs/pull/271)

https://pinia.vuejs.org/

```bash
npm install pinia
```

::: tabs

@tab 引入

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/main.ts -->
```

@tab 定义

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/components/TestA.vue -->
```

@tab 使用

```html
<!-- @include: @project/code/demo-js-vue/demo-03-vite-and-vue-router/src/views/page01.vue -->
```

:::

## VSCode 插件

- Vue - Official —— 开发环境监测、高亮
- Vue VSCode Snippets —— 提示
