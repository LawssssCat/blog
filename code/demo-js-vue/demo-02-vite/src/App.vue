<template>
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <div>hello world!</div>
  <div>{{ dayInfo }}</div>
  <div>{{ dayInfo }}</div>
  <div>{{ count }}</div>
  <div>{{ obj }}</div>
  <button @click="clickMe">clickMe</button>
  <hr>
  <MyDialog></MyDialog>
</template>

<script lang="ts">
import MyDialog from "./components/MyDialog.vue";
function toString(obj: any) {
  return JSON.stringify(obj);
}
export default {
  components: {MyDialog},
  data() { // 数据
    return {
      count: 0,
      obj: {
        name: "计数器",
        count: 0
      },
      weather: "晴天",
      x: 0
    }
  },
  methods: { // 方法
    clickMe() {
      this.count++;
      this.obj.count++;
    },
  },
  watch: { // 监听：值改变的回调
    count(newVal, oldVal) { // 值监听
      console.log(`count 更新: '${oldVal}' -> '${newVal}'`)
    },
    obj: { // 深度监听
      handler: (newVal, oldVal) => {
         console.log(`obj 更新: '${toString(oldVal)}' -> '${toString(newVal)}'`)
      },
      deep: true // 配置深度监听启动
    }
  },
  computed: { // 计算属性
    // 注意：不要在计算属性中更改 this 的属性值，否则结果会不合预期。
    // 异步计算 https://my.minecraft.kim/tech/845/%E5%A6%82%E4%BD%95%E5%9C%A8-vue3-%E4%B8%AD%E5%BC%82%E6%AD%A5%E4%BD%BF%E7%94%A8-computed-%E8%AE%A1%E7%AE%97%E5%B1%9E%E6%80%A7/
    dayInfo() {
      return ( // 语法解析时，括号没啥用
        "今天的天气是：" + this.weather + "，时间是：" + new Date().toISOString() + "，Counter：" + this.x++ // 每次引用都会重新计算一遍（留意x的值）
      );
    }
  },
  // ==== 启动时必然触发 ====
  beforeCreate() {
    console.log("beforeCreate")
  },
  created() {
    console.log("created")
  },
  beforeMount() {
    console.log("beforeMound")
  },
  mounted() {
    console.log("mounted")
  },
  // ==== 数据变化时触发 ====
  beforeUpdate() {
    console.log("beforeUpdate")
  },
  updated() {
    console.log("update")
  },
  // ==== 组件卸载时触发 ====
  beforeUnmount() {
    console.log("beforeUnmount")
  },
  unmounted() {
    console.log("unmounted")
  },
}
</script>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
