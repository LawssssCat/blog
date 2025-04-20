<!--
逻辑组件：新增逻辑
-->

<template>
  <ProductForm
    :formData="formData"
    :loading="loading"
    submitText="新增商品"
    @finish="handleFinish"
  >
  </ProductForm>
</template>

<script setup>
import { ref, reactive } from "vue";
import {ElMessage} from "element-plus";

import ProductForm from "./FormUIView.vue";

const formData = reactive({
  name: '',
  price: 0,
  services: [],
})
const loading = ref(false)
const handleFinish = () => {
  loading.value = true;
  console.log('新增商品', formData);
  setTimeout(() => {
    loading.value = false;
    ElMessage({
      message: '新增商品成功',
      type: 'success',
      duration: 1000,
    });
  }, 1000);
}
</script>
