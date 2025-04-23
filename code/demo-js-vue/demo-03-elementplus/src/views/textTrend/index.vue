<template>
  <h4>默认</h4>
  <div class="flex">
    <m-text-trend text="营业额"></m-text-trend>
    <m-text-trend text="营业额" type="down"></m-text-trend>
    <m-text-trend text="营业额" up-color="blue"></m-text-trend>
  </div>
  <h4>取反</h4>
  <div class="flex">
    <m-text-trend :is-color-reverse="true" text="营业额"></m-text-trend>
    <m-text-trend :is-color-reverse="true" text="营业额" type="down"></m-text-trend>
    <!-- 指定颜色，则忽略 is-color-reverse 设置 -->
    <m-text-trend :is-color-reverse="true" text="营业额" type='down' up-color="blue"></m-text-trend>
  </div>
  <h4>插槽</h4>
  <div class="flex">
    <m-text-trend>Hello <span style="color: red;">World</span> ~</m-text-trend>
  </div>
  <h4>换图标</h4>
  <div class="flex">
    <m-text-trend up-icon="carettop" down-icon="caretbottom" text="营业额"></m-text-trend>
    <m-text-trend up-icon="carettop" down-icon="caretbottom" text="营业额" type="down"></m-text-trend>
    <m-text-trend up-icon="carettop" down-icon="caretbottom" text="营业额" up-color="blue"></m-text-trend>
  </div></template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.flex {
  display: flex;
  div {
    margin-right: 10px;
  }
}
</style>
