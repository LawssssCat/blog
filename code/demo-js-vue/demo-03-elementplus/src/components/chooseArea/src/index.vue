<template>
  <div style="display: flex;">
    <el-select placeholder="请选择省份" v-model="selectedProvince">
      <el-option v-for="item in provinces" :key="item.code" :value="item.code" :label="item.name">{{ item.name }}</el-option>
    </el-select>
    <el-select :disabled="!selectedProvince" style="margin: 0 10px;" placeholder="请选择城市" v-model="selectedCity">
      <el-option v-for="item in cities" :key="item.code" :value="item.code" :label="item.name">{{ item.name }}</el-option>
    </el-select>
    <el-select :disabled="!selectedCity" placeholder="请选择区域" v-model="selectedArea">
      <el-option v-for="item in areas" :key="item.code" :value="item.code" :label="item.name">{{ item.name }}</el-option>
    </el-select>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import allAreas from './lib/pca-code.json'

const selectedProvince = ref('')
const selectedCity = ref('')
const selectedArea = ref('') 

const provinces = ref(allAreas)
const cities = computed(() => {
  if (!selectedProvince.value) {
    return [];
  } else {
    return provinces.value.find(item => item.code === selectedProvince.value)!.children;
  }
})
const areas = computed(() => {
  if (!selectedCity.value) {
    return []
  } else {
    return cities.value.find(item => item.code === selectedCity.value)?.children
  }
})
</script>

<style scoped></style>
