<template>
  <el-container>
    <!-- 侧边栏 -->
    <el-aside width="auto">
      <nav-side :is-collapse="isCollapse"></nav-side>
    </el-aside>
    <el-container>
      <!-- 头部 -->
      <el-header>
        <nav-header v-model:is-collapse="isCollapse"></nav-header>
      </el-header>
      <!-- 主体 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref } from "vue";
import NavHeader from './navHeader/index.vue'
import NavSide from './navSide/index.vue'

const isCollapse = ref(false);
</script>

<style scoped>
.el-header {
  padding: 0;
  border-bottom: 1px solid #eee;
}
</style>
