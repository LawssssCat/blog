<script setup lang="ts"></script>

<template>
  <div class="app">
    <h2>App</h2>
    <RouterLink to="/main">首页</RouterLink>
    /
    <RouterLink to="/login">登录</RouterLink>
    <RouterView />
  </div>
</template>

<style scoped>
.app {
  width: 100vh;
  height: 100vh;
  background-color: aquamarine;
}
</style>
