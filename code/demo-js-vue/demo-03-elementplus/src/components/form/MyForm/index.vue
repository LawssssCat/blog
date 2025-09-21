<template>
  <div>
    <slot name="header"></slot>
  </div>
  <ElForm ref="formRef" :label-width="labelWidth" :model="modelValue">
    <!-- 通过数据配置生成表单 -->
    <template v-for="(item, index) in formItem" :key="index">
      <ElFormItem :label="item.label" :prop="item.field">
        <!-- 开关按钮 -->
        <!--
        <ElFormItem label="新增为会员">
          <ElSwitch></ElSwitch>
        </ElFormItem>
        -->
        <template v-if="item.type === 'switch'">
          <ElSwitch v-model="modelValue[item.field]"></ElSwitch>
        </template>

        <!-- 输入框 -->
        <!--
        <ElFormItem label="用户名">
          <ElInput placeholder="请输入用户名"></ElInput>
        </ElFormItem>
        <ElFormItem label="备注名">
          <ElInput placeholder="请输入备注名"></ElInput>
        </ElFormItem>
        -->
        <template v-else-if="item.type === 'input' || item.type === 'password'">
          <ElInput
            :placeholder="item.placeholder"
            :show-password="item.type === 'password'"
            v-model="modelValue[item.field]"
          ></ElInput>
        </template>

        <!-- 下拉框 -->
        <!--
        <ElFormItem label="客户来源">
          <ElSelect placeholder="请选择客户来源">
            <ElOption label="手工录入" value="0"></ElOption>
            <ElOption label="自动导入" value="1"></ElOption>
          </ElSelect>
        </ElFormItem>
        -->
        <template v-else-if="item.type === 'select'">
          <ElSelect
            :placeholder="item.placeholder"
            v-model="modelValue[item.field]"
          >
            <ElOption
              v-for="option in item.options"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            >
            </ElOption>
          </ElSelect>
        </template>

        <!-- 日期选择 -->
        <!--
        <ElFormItem label="过期时间">
          <ElDatePicker type="date" placeholder="Pick a date"></ElDatePicker>
        </ElFormItem>
        -->
        <template v-else>
          <ElDatePicker
            v-model="modelValue[item.field]"
            type="date"
            :placeholder="item.placeholder"
          ></ElDatePicker>
        </template>
      </ElFormItem>
    </template>
  </ElForm>

  <div>
    <slot name="footer"></slot>
  </div>
</template>

<script setup lang="ts">
import { ref, withDefaults } from "vue";

const formRef = ref();

interface FormItem {
  type: string;
  field: string;
  label: string;
  placeholder?: string;
  options?: Array<{
    label: string;
    value: string;
  }>;
}

withDefaults(
  defineProps<{
    labelWidth: string;
    formItem: FormItem[];
    modelValue: { [key: string]: any };
  }>(),
  {
    labelWidth: "160px",
  }
);

const resetForm = () => {
  formRef.value.resetFields();
};

defineExpose({
  resetForm,
});
</script>
