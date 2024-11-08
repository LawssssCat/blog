<!-- Vue2 语法 -->

<template>
  <div class="dialog-bg">
    <div class="dialog">
      <!-- props 传值 -->
      <div>{{ title }}</div>
      <div v-html="content"></div>
      <!-- slot 传值 -->
      <div>
        <!-- 默认插槽 -->
        <slot>
          插槽默认值
        </slot>
      </div>
      <div>
        <!-- 具名插槽 -->
        <slot name="namedSlot"></slot>
      </div>
      <div>
        <!-- 具名插槽，向上层传值 -->
        <slot :val1="val1" val2="myVal2" name="namedSlot2"></slot>
      </div>
      <div>
        <!-- 与上层v-model双向绑定 -->
         <!-- 问题：https://www.mintimate.cn/2024/01/17/vModelVue/ -->
        <input type="text" v-bind:value="modelValue"
        v-on:input="updateValue($event.target.value)"
         @keydown.enter="submit" @keydown.esc="cancel"></div>
      <div class="btn-group">
        <button @click="submit">确定</button>
        <button @click="cancel">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: { // 2. 上层传入的属性
    title: {
      type: String,
      default: ""
    },
    content: {
      type: String,
      default: ""
    },
    modelValue: { // 接收上层的 v-model 传值，名字固定只能是 "modelValue"
      type: String,
      default: ""
    }
  },
  data() {
    return {
      val1: "myVal1"
    }
  },
  methods: {
    // 1. 取消事件向上层传递，在上层决定如何处理事件
    submit() {
      // this.$emit('update:model-value', this.modelValue)
      this.$emit("close")
    },
    cancel() {
      this.$emit("close")
    },
    updateValue(value) {
      this.$emit('update:modelValue', value)
    }
  },
  beforeUnmount() { // 事件：取消挂载前
    console.log("beforeUnmount");
  },
  unmounted() { // 事件：取消挂载
    console.log("unmounted");
  }
}
</script>

<style scoped>
.dialog-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  text-align: center;
}
.dialog {
  width: 300px;
  padding: 20px;
  margin: 0 auto;
  margin-top: 200px;
  background-color: #e4e4e4;
}
.btn-group {
  margin-top: 50px;
  display: flex;
  justify-content: space-around;
}
</style>