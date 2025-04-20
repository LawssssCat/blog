<template>
  <div>
    <m-choose-icon title="选择图标" v-model:visible="visible">
      xxx
    </m-choose-icon>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';

const visible = ref(false);
</script>

<style scoped></style>
