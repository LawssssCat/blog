<template>
  <div>首页</div>
  <el-button>按钮</el-button>
  <!-- <el-icon-edit style="width: 1em; height: 1em; margin-right: 8px;" /> -->
  <el-icon-edit />
  <MyInput ref="myInputRef" v-model="data" placeholder="地址">
    <template #prepend>
      <ElSelect placeholder="Select" style="width: 115px;">
        <ElOption label="Restaurant" value="1"></ElOption>
        <ElOption label="Order No." value="2"></ElOption>
        <ElOption label="Tel" value="3"></ElOption>
      </ElSelect>
    </template>
    <template #append>
      <ElButton :icon="Search"></ElButton>
    </template>
  </MyInput>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import MyInput from '@/components/MyInput.vue';
import { Search } from '@element-plus/icons';

const data = ref('');
const myInputRef = ref();

onMounted(() => {
  console.log(myInputRef.value) // 组件实例绑定了ElInput的所有方法和属性
  myInputRef.value.focus();
});
</script>

<style scoped></style>
