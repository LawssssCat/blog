<!-- 组合式API（vue3语法） -->

<template>
  <div>
    <p>{{ props.title }}</p>
    <p>{{ a }}</p>
    <button v-on:click="testClick">ClickMe!</button>
    <ul>
      <li v-for="(item,index) in b">
        {{ index }} , {{ item.id }} , {{ item.name }}
      </li>
    </ul>
    <button @click="testClick2">ClickMe!</button>
    <p>{{ c }}</p>
    <button @click="cancel">收缩内容</button>
  </div>
</template>

<!-- 注意：有setup标签 -->
<script setup lang="ts">
import { ref,reactive } from "vue";
import { onMounted, watch, computed } from "vue";


let a = ref("张三") // 变量定义
function testClick() {
  a.value = "翠花"; // 变量值改变
}

let b = reactive([{id:1,name:'翠花'},{id:2,name:'王五'}]); // 深度变量绑定
function testClick2() {
  b[1].name = "张三"
}

// 变量监听
watch(b, (oldVal, newVal) => {
  console.log("watch in vue", oldVal, newVal)
})
// 计算属性
const c = computed(() => {return a.value + " - " + b[0].name})

// 生命周期
onMounted(() => {
  console.log("onMounted in vue3")
})

// 上层传入的变量
const props = defineProps({
  title: {
    type: String,
    default: ""
  }
})

// 发送消息（向上层传变量）
const emit = defineEmits({})
function cancel() {
  emit("close");
}

</script>

<style scoped>

</style>