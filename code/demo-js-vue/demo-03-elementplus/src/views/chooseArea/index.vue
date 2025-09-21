<template>
  <div>
    <m-choose-area @change="handleAreaChange"></m-choose-area>
  </div>
</template>

<script setup lang="ts">
import { type Data } from "@/components/chooseArea/src/index.vue";

function handleAreaChange({
  province,
  city,
  area,
}: {
  province: Data;
  city: Data;
  area: Data;
}) {
  console.log(province, city, area);
}
</script>

<style scoped></style>
