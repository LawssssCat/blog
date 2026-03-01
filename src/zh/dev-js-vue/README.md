---
title: Vue 程序设计
date: 2024-10-31
order: 1
---

参考：

- 官网 —— https://cn.vuejs.org/guide/introduction.html
- ~~4 小时快速入门前端 Vue3+Vite+Pinia —— https://www.bilibili.com/video/BV1aa1NYxECK/~~ （vue2 语法）

## 项目初始化

```bash
$ npm init vite@latest project-name
Need to install the following packages:
create-vite@6.1.1
Ok to proceed? (y) y


> npx
> create-vite .

√ Select a framework: » Vue
√ Select a variant: » TypeScript

Scaffolding project in xxx\blog\code\demo-js-antv\demo-01-g6...

Done. Now run:

  npm install
  npm run dev
```

## 基本使用

### 模板语法

- 渲染变量（`{{ val }}`,`v-html="val"`）,变量计算（`{{ val1 + val2 }}`）

  ::: vue-demo

  ```vue
  <template>
    <!-- 渲染变量（不解析标签） -->
    <p>{{message}}</p>
    <!-- 渲染变量（渲染标签） -->
    <p v-html="message"></p>
    <!-- 双向绑定 -->
    <p>
      <input v-model="message" placeholder="edit me!"/> <br>
      <textarea v-model="message" placeholder="add multiple lines"></textarea>
    </p>
    <!-- 变量计算 -->
    <p>
      {{ number + 1 }}, {{ ok ? 'yes' : 'no' }},
      {{ message.split(' ').reverse().join(' ') }}
    </p>
  </template>

  <script>
  export default {
    data() {
      return {
        ok: true,
        message: "hello <span style='color:red'>vue</span>!!",
        number: 14,
      }
    }
  }
  </script>
  ```

  :::

- 属性变量（`v-bind:attr="val`/`:attr="val`）\
  动态属性变量（`v-bind:[val1,val2,...]="valn"`/`:[val1,val2,...]="valn"`）

  ::: vue-demo

  ```vue
  <template>
    <p>
      <!-- 属性变量 -->
      <a v-bind:href="href01">超链接</a> -
      <a :href="href01">超链接（简写）</a> -
      <a :href="href01 + '/calc'" :class="{ cssasfsfasfs01: true }" :style="{fontSize: number + 'px'}">超链接（计算）</a> -
      <!-- 动态属性变量 -->
      <a v-bind:[attributename]="href01">超链接（动态属性）</a> -
    </p>
  </template>

  <script>
  export default {
    data() {
      return {
        number: 14,
        href01: "http://example.org/",
        attributename: "href", // 不能有大写（attributeName），否则解析失败（且没有报错日志 “坑”）
      }
    }
  }
  </script>

  <style>
  .cssasfsfasfs01 {
    color: red;
  }
  </style>
  ```

  :::

- 事件绑定（`v-on:click="count++"`/`@click="func1"`）,按键修饰符（`keyup`/`keydown`/...）,系统修饰符（`.ctrl`/`.alt`/...）,精确修饰符（`.exact`）,鼠标按键修饰符（`.left`/`.right`/`.middle`）

  ::: vue-demo

  ```vue
  <template>
    <!-- 事件触发 -->
    按键修饰符：
    <a href="https://vueframework.com/docs/v3/cn/guide/migration/keycode-modifiers.html">link</a><br>
    <ul style='font-size: 12px;'>
      <li>
        系统修饰键：
        <ul>
          <li>.ctrl</li>
          <!-- @click.ctrl = Ctrl + Click -->
          <li>.alt</li>
          <!-- @keyup.alt.enter = Alt + Enter -->
          <li>.shift</li>
          <li>.meta —— 精确修饰组合：允许你精确的控制组合触发事件</li>
          <li>.exact</li>
          <!-- @click.ctrl = 即使 Alt 或 Shift 被同时按下也会触发 -->
          <!-- @click.ctrl.exact = 只有 Ctrl 被按下才会触发 -->
          <!-- @click.exact = 没有任何系统修饰符被按下时才会触发 -->
        </ul>
      </li>
      <li>
        鼠标按钮修饰符：
        <ul>
          <li>.left</li>
          <li>.right</li>
          <li>.middle</li>
        </ul>
      </li>
    </ul>
    <hr>

    鼠标/点击事件：<br>
    <!-- 注意：零参数默认传event事件，否则需要$event注入事件 -->
    <button v-on:click="count++">点击</button>
    /
    <button v-on:click="addOne(2,$event)">点击+2</button>
    /
    <button @click="addOne(3,$event),console.log('touch')">点击+3</button>
    :
    {{ count }}
    <hr />

    键盘/输入事件：
    （keyup = 按下后的释放事件）<br>
    <input
      style='width:100%;'
      placeholder='输入回车/下一页按钮，然后观察控制台'
      v-model='msg'
      @keyup.enter="console.log('enter')"
      @keyup.page-down="console.log('page-down')"
    />
    <br>
    <span v-html='msg'></span>
    <br>
  </template>

  <script>
  export default {
    data() {
      return {
        count: 1,
        msg: '',
        addOne: (val, event) => {
          console.log(event);
          this.count += val ? val : 1;
        },
      }
    },
    methods: {
      // addOne
    }
  }
  </script>
  ```

  :::

- 条件渲染（`v-if`/`v-else-if`/`v-else`,`v-show`）

  ::: vue-demo

  ```vue
  <template>
    <p><button @click="awesome++">变</button> : {{awesome}}</p>
    <p>
      <!-- 是否渲染 -->
      <!-- 注意：template标签是vue自创的，作用就是不会被vue渲染 -->
      <template v-if="awesome%3==1">Vue is awesome!</template>
      <template v-else-if="awesome%3===2">Oh no 😨 xxx</template>
      <template v-else>Oh no 😨 ???</template>
    </p>
    <p>
      <!-- 是否显示 -->
      <!-- 注意： 1. v-show 不支持 <template> 2. v-show 不支持 v-else-show / v-else -->
      <span v-show="awesome%2==1">Vue is awesome!</span>
      <span v-show="awesome%2===0">Oh no 😨 xxx</span>
    </p>
  </template>

  <script>
  export default {
    data() {
      return {
        awesome: 1,
      }
    },
  }
  </script>
  ```

  :::

- 循环渲染（`v-for="(item,index) in items" :key="item.id"`/`v-for="(value,key,index) in obj" :key="key"`,`v-for="(value,key) of obj" :key="key"`）

  ::: vue-demo

  ```vue
  <template>
  <p><span v-for="n in 10" :key="n">{{ n }}</span></p>
  <p>
    <!-- 数组循环 -->
    <!-- 注意：key的使用 -->
    <span v-for="(item,index) in items" :key="item.id"
      >{{ index }} - {{ item }}, </span
    ><br />
    <!-- 两参数，数组 -->
    <span v-for="(value,key) in obj" :key="key"
      >{{ key }} - {{ value }}, </span
    ><br />
    <!-- 两参数，对象 -->
    <span v-for="(value,key,index) in obj" :key="key"
      >{{ index }} - {{ key }} - {{ value }}, </span
    ><br />
    <!-- 三参数，对象 -->
  </p>
  <p>
    <!-- 对象循环 -->
    <!-- 注意：key的使用 -->
    <span v-for="(value,key) of items" :key="key"
      >{{ key }}: {{ value }}</span
    >,
  </p>
  </template>

  <script>
  export default {
    data() {
      return {
        items: [
          { id: 1, message: "Foo" },
          { id: 2, message: "Bar" },
        ],
        obj: {
          f1: "v1",
          f2: "v2",
        },
      }
    },
  }
  </script>
  ```

  :::

- 模板标签（`<template>`）

  ::: vue-demo

  ```vue
  <template>
  <div>
    <!-- v-for 和 v-if 同时使用 -->
    <!-- 注意：不要写在同一个标签上，否则可能有意外现象 -->
    <template v-for="item in items" :key="item.id">
      <li v-if="!(item.id==1)">{{item.id}} - {{item.message}}</li>
    </template>
  </div>
  </template>

  <script>
  export default {
    data() {
      return {
        items: [
          { id: 1, message: "Foo" },
          { id: 2, message: "Bar" },
          { id: 3, message: "Cert!" },
        ],
      }
    },
  }
  </script>
  ```

  :::

### API

> 详细分析重点接口函数
>
> 分类方式：
> 按功能划分（和vue官网一样），更便于定位

官网：

- <https://cn.vuejs.org/api/>

:::::: tabs

@tab 初始化

- createApp —— 指定根组件
- createSSRApp
- app.mount —— 指定根组件被挂在到哪个dom内部
- app.unmount
- app.config
- app.config.errorHandler
- app.config.warnHandler
- app.config.throwUnhandledErrorInProduction —— 在生产模式下，错误只会被记录到控制台以尽量减少对最终用户的影响。然而，这可能会导致只在生产中发生的错误无法被错误监控服务捕获。通过该选项让生产环境的未处理错误被抛出。
- app.config.performance —— 性能监控，仅在开发环境生效，可通过浏览器开发工具查看
- ~~app.config.compilerOptions~~ —— 一般没用，直接看vite的配置
- ~~app.config.globalProperties —— 设置全局属性~~ （在vue3中废弃）
- app.config.optionMergeStrategies —— 配置合并设置
- app.config.idPrefix —— 为 `useId()` 配置前缀 （只有在有多个 Vue App 的场景有意义）

@tab 组件

- app.component —— 注册自定义组件（Components） <https://cn.vuejs.org/guide/components/registration>
- app.directive —— 注册自定义指令（Directives） <https://cn.vuejs.org/guide/reusability/custom-directives>
- app.use —— 安装插件 (Plugins)  <https://cn.vuejs.org/guide/reusability/plugins>
- ~~app.mixin —— 注册“混入”（公共逻辑/配置的提取）~~ （过时技术，用“组合式函数”替代）
- app.provide —— 注册提供给子组件的变量/方法
- app.runWithContext —— 执行方法，并传递返回值（可以调用当前上下文设置，如通过inject获取provide值）
- app.version
- defineComponent todo
- defineAsyncComponent

@tab 生命周期

- nextTick —— 等待下一次 DOM 更新刷新的工具方法
- app.onUnmount

::::::

#### SSR

todo createSSRApp

#### 对比：组件、指令、插件

todo 对比

#### 对比：mixin、组合式函数

定义：

- mixin：公共逻辑/配置的提取（不包含html和css内容） —— 基于vue2的选项式API做的配置混入，本质上是“对象的合并”
- 组合式函数： 负责有状态逻辑的封装

mixin问题：

- 数据源不清晰
- 数据相互覆盖（需要精心维护脆弱的优先级）

例子：

::::::::: tabs

@tab mixin

:::::: vue-demo

```vue
<template>
mixin实现：
<div ref='devRef'>{{ count }}</div>
<button @click='handleClick'>add</button>
</template>

<script>
const myMixin = {
  data() {
    return {
      count: 2,
    }
  },
  mounted() {
    console.log('myMixin', this.$refs.devRef);
  },
  methods: {
    handleClick() {
      this.count += 2;
      console.log('jia 2');
    }
  }
}

export default {
  data() {
    return {
      count: 1,
    }
  },
  mixins: [
    myMixin
  ],
  methods: {
    handleClick() {
      this.count++;
      console.log("jia 1")
    }
  },
  mounted() {
    console.log('mounted')
  }
}
</script>
```

::::::

@tab 组合式函数

:::::: vue-demo

```vue
<template>
组合式函实现数：
</template>

<script>
import { ref, reactive, onMounted } from 'vue';


export default {
  setup() {
   

    return {
    }
  }
}
</script>
```

```json
// 配置 (可选)
{
  "jsLib": [
    {
      
    }
  ],
  "cssLib":[
  ]
}
```

::::::

:::::::::

#### 对比：引用reactive/ref/shallowRef

todo reactive/ref/shallowRef

todo weakmap

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

## 实战

todo 后台管理

todo 权限管理 https://www.bilibili.com/video/BV126DpYrES7/

todo ？？？ https://www.bilibili.com/video/BV1Xh411V7b5
