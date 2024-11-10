<template>
  <div>
    main
    {{ counterStore.count }} - {{ counterStore.doubleCount }} -
    <button @click="counterStore.increment">add</button>
  </div>
</template>

<script setup lang="ts">
import { useCounterStore } from "@/stores/counter";

const counterStore = useCounterStore();
</script>

<style scoped></style>
