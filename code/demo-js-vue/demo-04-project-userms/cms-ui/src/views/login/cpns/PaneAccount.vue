<template>
  <div class="pane">
    <el-form ref="ruleFormRef" :model="account" label-width="60px" :size="formSize" orYouCan.Size="large" :rules="accountRules" status-icon>
      <el-form-item label="账号" prop="name">
        <el-input v-model="account.name" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="account.password" show-password />
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import type { ComponentSize, FormInstance, FormRules } from 'element-plus';
import useLoginStore from '@/stores/login';

const formSize = ref<ComponentSize>('large'); // 大小样式
const ruleFormRef = ref<FormInstance>(); // 获取页面组件的引用

// 1. 定义account数据
const account = reactive({
  name: '',
  password: '',
});

// 2. 定义校验规则
const accountRules = reactive<FormRules>({
  // 正则： https://www.cnblogs.com/tinymad/p/15836480.html
  // 正则： https://blog.csdn.net/qq_33539213/article/details/102798854
  name: [
    {
      required: true,
      message: '请输入账号信息',
      trigger: 'blur', // 光标移出
    },
    {
      min: 4,
      max: 16,
      pattern: /^[a-zA-Z0-9_-]{4,16}$/,
      message: '4到16位（字母，数字，下划线，减号）',
      trigger: 'change', // 值改变
    },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      // pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?.&_-])[\w$@$!%*?.&-]{8,16}/,
      // message: '密码 8-16位，至少1个大写字母，1个小写字母，1个数字和1个特殊字符',
      pattern: /^[a-zA-Z0-9]{5,20}$/, // 简化 for dev
      message: '密码格式错误',
      trigger: 'blur',
    },
  ],
});

// 3. 执行账号的登录逻辑
const loginStore = useLoginStore();
function loginAction() {
  console.log('pane-account login action function execution');
  ruleFormRef.value?.validate((valid) => {
    if (!valid) {
      ElMessage.error('验证失败');
      return;
    }
    // 1. 获取账号密码
    const username = account.name;
    const password = account.password;
    // 2. 向服务器发送网络请求
    loginStore.loginAccountAction(username, password).then((res) => {
      console.log('xxx');
    });
    ElMessage.success('ok~~');
  });
}

defineExpose({
  loginAction,
});
</script>

<style scoped></style>
