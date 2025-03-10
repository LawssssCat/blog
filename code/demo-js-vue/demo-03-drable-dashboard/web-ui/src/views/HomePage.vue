<template>
  <h1>Home</h1>
  <ul class="info-list">
    <li
      class="info-item"
      :key="routeInfo.path"
      v-for="routeInfo in router.getRoutes()"
      @click="toRoute(routeInfo)"
    >
      <div class="info-link">
        path: "<span>{{ routeInfo.path }}</span
        >"
      </div>
      <div class="info-raw">{{ routeInfo }}</div>
    </li>
  </ul>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()

function toRoute(routeInfo) {
  console.log(routeInfo)
  router.push(routeInfo)
}
</script>

<style lang="scss" scoped>
.info-item {
  display: flex;
  flex-direction: column;
  .info-link {
    cursor: pointer;
    &:hover {
      color: #f9f9f9;
      background-color: #9f9f9f;
    }
  }

  .info-raw {
    font-size: 10px;
    border: 1px dotted yellow;
  }
  .info-raw:hover {
    border-radius: 3px;
    border-style: solid;
  }
}
</style>
