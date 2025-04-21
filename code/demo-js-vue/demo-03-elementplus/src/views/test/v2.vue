<!--
表单封装
1. 标签
1. 提示
1. 数据源
-->

<template>
  <div>
    <!-- v-bind 比 : 好，可在组件内声明类型 -->
    <MyForm v-bind="LoginData" v-model="form">
      <template #header>
        <h1>新增客户</h1>
      </template>
      <template #footer>
        <ElFormItem>
          <ElButton type="primary">创建</ElButton>
          <ElButton>取消</ElButton>
        </ElFormItem>
      </template>
    </MyForm>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyForm from "@/components/form/MyForm/index.vue";
import LoginData from "./data";

const formItem = LoginData.formItem ?? [];
const obj = {};
formItem.map((item) => {
  obj[item.field] = "";
});
const form = ref(obj);
</script>
