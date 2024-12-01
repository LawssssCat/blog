<template>
  <div>
    page{{ props.num }} - {{ counter.count }} and {{ counter.testCount }} and {{ message }} <!-- 3. 使用 -->
    -
    <button @click="touchStore">add</button>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  num: {
    type: String,
    default: "-1"
  }
})

// pinia
import { defineStore, storeToRefs } from "pinia";
import { testStore } from "../stores/TestStore"; // 1. 引入

const counter = testStore(); // 2. 实例化

const {message} = storeToRefs(counter) // 绑定

function touchStore() {
  // counter.count++; // 直接改变
  counter.increment(); // 通过方法改变
  message.value = (counter.count & 1) == 0 ? '张三' : "李四" // 通过绑定对象改变值
}

</script>

<style lang="scss" scoped>

</style>