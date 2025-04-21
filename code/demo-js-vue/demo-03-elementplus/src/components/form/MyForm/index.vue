<template>
  <ElForm :label-width="labelWidth">
    <!-- 通过数据配置生成表单 -->
    <template v-for="(item, index) in formItem" :key="index">
      <ElFormItem :label="item.label">
        <!-- 开关按钮 -->
        <!--
        <ElFormItem label="新增为会员">
          <ElSwitch></ElSwitch>
        </ElFormItem>
        -->
        <template v-if="item.type === 'switch'">
          <ElSwitch></ElSwitch>
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
        <template v-else-if="item.type === 'input'">
          <ElInput :placeholder="item.placeholder"></ElInput>
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
          <ElSelect :placeholder="item.placeholder">
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
            :type="item.data"
            :placeholder="item.placeholder"
          ></ElDatePicker>
        </template>
      </ElFormItem>
    </template>
  </ElForm>
</template>

<script setup>
defineProps({
  labelWidth: {
    type: String,
  },
  formItem: {
    type: Array,
  },
});
</script>
