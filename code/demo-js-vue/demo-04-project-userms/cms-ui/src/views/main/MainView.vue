<template>
  <div>
    main
    {{ counterStore.count }} - {{ counterStore.doubleCount }} -
    <button @click="counterStore.increment">add</button>
    <el-button @click="logoutAction">Logout</el-button>
  </div>
</template>

<script setup lang="ts">
import { LOGIN_TOKEN } from '@/global/constants';
import router from '@/router';
import { useCounterStore } from '@/stores/counter';
import { localCache } from '@/utils/cache';

const counterStore = useCounterStore();

function logoutAction() {
  localCache.removeCache(LOGIN_TOKEN);
  router.push('/login');
}
</script>

<style scoped></style>
