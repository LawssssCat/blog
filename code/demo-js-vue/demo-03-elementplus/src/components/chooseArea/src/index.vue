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
import { ref, unref, computed, watch } from "vue";
import allAreas from "./lib/pca-code.json";

export interface AreaItem {
  name: string;
  code: string;
  children?: AreaItem[];
}

export interface Data {
  name: string;
  code: string;
}

// 分发事件给父组件
const emits = defineEmits(["change"]);

const selectedProvince = ref("");
const selectedCity = ref("");
const selectedArea = ref("");

const provinces = ref<AreaItem[]>(allAreas);
const cities = ref<AreaItem[]>([]);
const areas = ref<AreaItem[]>([]);

watch(
  () => selectedProvince.value,
  (val) => {
    if (!val) {
      cities.value = [];
    } else {
      cities.value = provinces.value.find(
        (item) => item.code === val
      )!.children!;
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
      areas.value = cities.value.find((item) => item.code === val)!.children!;
    }
    selectedArea.value = "";
  }
);
watch(
  () => selectedArea.value,
  () => {
    // console.log(selectedProvince.value, selectedCity.value, selectedArea.value);
    const fn = (arr: AreaItem[], code: any): Data => {
      const { code: c, name: n } = unref(arr).find(
        (item) => item.code === code
      )!;
      return {
        code: c,
        name: n,
      };
    };
    let province: Data = fn(provinces.value, selectedProvince.value);
    let city: Data = fn(cities.value, selectedCity.value);
    let area: Data = fn(areas.value, selectedArea.value);
    // console.log(province, city, area);
    emits("change", {
      province,
      city,
      area,
    });
  }
);
</script>

<style scoped></style>
