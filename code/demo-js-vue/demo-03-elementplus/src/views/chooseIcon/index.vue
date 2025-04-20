<template>
  <div>
    <mChooseIcon title="选择图标" v-model:visible="visible">
      xxx
    </mChooseIcon>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import mChooseIcon from "@/components/chooseIcon/src/index.vue";

const visible = ref(false);
</script>

<style scoped></style>
