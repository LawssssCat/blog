<template>
  <h2>例子：</h2>
  <ul>
    <li v-for="routeObj in router.getRoutes()">
      <router-link :to="routeObj.path">{{ routeObj.path }}</router-link>
    </li>
  </ul>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

console.log(router.getRoutes())

</script>

<style scoped>

</style>