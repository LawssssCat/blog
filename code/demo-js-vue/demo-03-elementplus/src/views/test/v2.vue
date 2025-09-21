<!--
表单封装
1. 标签
1. 提示
1. 数据源
-->

<template>
  <div>
    <!-- v-bind 比 : 好，可在组件内声明类型 -->
    <MyForm ref="formRef" v-bind="LoginData" v-model="form">
      <!-- 头部 -->
      <template #header>
        <h1>新增客户</h1>
      </template>
      <!-- 尾部 -->
      <template #footer>
        <ElSpace>
          <ElButton type="primary" @click="handleCreateXxx">创建</ElButton>
          <ElButton @click="handleReset">重置</ElButton>
        </ElSpace>
      </template>
    </MyForm>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyForm from "@/components/form/MyForm/index.vue";
import LoginData from "./data";

const formRef = ref();

const formItem = LoginData.formItem ?? [];
const obj = {};
formItem.map((item) => {
  obj[item.field] = "";
});
const form = ref(obj);

function handleCreateXxx() {
  console.log('保存', form.value)
}

function handleReset() {
  console.log('重置', formRef.value.resetForm)
  formRef.value.resetForm();
}
</script>
