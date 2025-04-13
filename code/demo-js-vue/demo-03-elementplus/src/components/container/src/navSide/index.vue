<template>
  <el-menu
    :collapse="isCollapse"
    default-active="1"
    class="el-menu-vertical-demo"
  >
    <el-menu-item :index="index" :key="index" v-for="(item, index) in menuList">
      <el-icon-menu></el-icon-menu>
      <span>{{ item.title }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup lang="ts">
import { ref } from "vue";

const props = defineProps<{
  isCollapse: boolean
}>()

const menuList = ref([
  {
    title: "导航1",
  },
  {
    title: "导航2",
  },
  {
    title: "导航3",
  },
]);
</script>

<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
</style>
