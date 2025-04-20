<!--
展示组件
-->

<template>
  <ElForm
    ref="refForm"
    @submit.prevent="handleSubmit(refForm)"
    :model="formData"
    :rules="rules"
    status-icon
    label-width="80px"
    v-loading="loading"
  >
    <ElFormItem label="商品名词" prop="name">
      <ElInput v-model="formData.name"></ElInput>
    </ElFormItem>
    <ElFormItem label="商品价格" prop="price">
      <ElInputNumber v-model="formData.price"></ElInputNumber>
    </ElFormItem>
    <ElFormItem label="商品服务">
      <ElCheckboxGroup v-model="formData.services">
        <ElCheckbox v-for="[k, v] in services" :key="v" :label="v">{{ k }}</ElCheckbox>
      </ElCheckboxGroup>
    </ElFormItem>
    <ElFormItem>
      <ElButton native-type="submit" type="primary">
        {{ submitText }}
      </ElButton>
    </ElFormItem>
  </ElForm>
</template>

<script setup>
import { ref, defineEmits } from "vue";
import { formProps, services } from "./formUIData";

const refForm = ref();
const props = defineProps(formProps);
const emits = defineEmits(["finish"]);

const handleSubmit = async (form) => {
  try {
    await form.validate();
    emits("finish");
  } catch (e) {}
};
</script>
