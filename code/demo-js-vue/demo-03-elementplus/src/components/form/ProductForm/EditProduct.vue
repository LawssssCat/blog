<!--
逻辑组件：编辑逻辑
-->

<template>
  <ProductForm
    :formData="formData"
    submitText="提交修改"
    :loading="loading"
    @finish="handleFinish"
  ></ProductForm>
</template>

<script setup>
import { ref, reactive, defineProps } from "vue";
import { ElMessage } from "element-plus";

import ProductForm from "./FormUIView.vue";
// import {} from "./apis";

const props = defineProps({
  id: String,
});

let formData = reactive({
  name: "",
  price: 0,
  services: [],
});

const loading = ref(true);
(async () => {
  // const res = await getProductById(props.id);
  // formData = res.data;
  // 模拟数据
  formData = reactive({
    name: "商品1",
    price: 100,
    services: [2, 3],
  });
  loading.value = false;
})();

const handleFinish = () => {
  loading.value = true;
  console.log("编辑商品", formData);
  setTimeout(() => {
    loading.value = false;
    ElMessage({
      message: "编辑商品成功",
      type: "success",
      duration: 1000,
    });
  }, 1000);
}
</script>
