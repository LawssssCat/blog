<template>
  <div style="display: flex">
    <el-select clearable placeholder="请选择省份" v-model="selectedProvince">
      <el-option
        v-for="item in provinces"
        :key="item.code"
        :value="item.code"
        :label="item.name"
        >{{ item.name }}</el-option
      >
    </el-select>
    <el-select
      clearable
      :disabled="!selectedProvince"
      style="margin: 0 10px"
      placeholder="请选择城市"
      v-model="selectedCity"
    >
      <el-option
        v-for="item in cities"
        :key="item.code"
        :value="item.code"
        :label="item.name"
        >{{ item.name }}</el-option
      >
    </el-select>
    <el-select
      clearable
      :disabled="!selectedCity"
      placeholder="请选择区域"
      v-model="selectedArea"
    >
      <el-option
        v-for="item in areas"
        :key="item.code"
        :value="item.code"
        :label="item.name"
        >{{ item.name }}</el-option
      >
    </el-select>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from "vue";
import allAreas from "./lib/pca-code.json";

const selectedProvince = ref("");
const selectedCity = ref("");
const selectedArea = ref("");

const provinces = ref<any>(allAreas);
const cities = ref<any>([]);
const areas = ref<any>([]);

watch(
  () => selectedProvince.value,
  (val) => {
    if (!val) {
      cities.value = [];
    } else {
      cities.value = provinces.value.find(
        (item: { code: string }) => item.code === val
      )!.children;
    }
    selectedCity.value = "";
  }
);
watch(
  () => selectedCity.value,
  (val) => {
    if (!val) {
      areas.value = [];
    } else {
      areas.value = cities.value.find(
        (item: { code: string }) => item.code === val
      )!.children;
    }
    selectedArea.value = "";
  }
);
</script>

<style scoped></style>
