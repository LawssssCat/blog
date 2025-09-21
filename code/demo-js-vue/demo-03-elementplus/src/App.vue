<template>
  <RouterView></RouterView>
</template>

<script setup lang="ts">
import { RouterView } from "vue-router";
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
}

svg {
  height: 1em;
  width: 1em;
}

html,
body,
#app,
.el-container,
.el-menu {
  height: 100%;
}
</style>
