<!-- 参考：
https://www.bilibili.com/video/BV1sEGCzVEir
-->

<template>
  <div class="demo-wrapper" ref="demoWrapperRef">
    <div class="cursor">
      <span v-for="index of 36" :key="index" class="box" :style="{
        '--i': index,
        'filter': `hue-rotate(${index * 10}deg)` /* 颜色渐变 */
      }">

      </span>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import gsap from 'gsap';

const demoWrapperRef = ref();

onMounted(() => {
  demoWrapperRef.value.addEventListener('mousemove', e => {
    gsap.to('.box', {
      x: e.clientX,
      y: e.clientY,
      rotate: (index) => index * 10, // 定位在周边
      stagger: 0.05, // 动画延迟
    })
  })
})
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.demo-wrapper {
  height: 100%;
  background: #ec9d91;
  overflow: hidden; // ??
}
.cursor {
  position: relative;
  pointer-events: none; // ??

  .box {
    position: absolute;
    top: -80px;
    width: 10px;
    height: 5px;
    background: #00ff9a;
    box-shadow: 0 0 15px #00ff9a,
      0 0 50px #00ff9a;
    transform-origin: center 50px; // 动画
  }
}
</style>
