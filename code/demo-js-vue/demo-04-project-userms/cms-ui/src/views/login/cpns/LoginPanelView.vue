<template>
  <div class="login-panel">
    <!-- 顶部标题 -->
    <h1 class="title">后台管理系统</h1>

    <!-- 中间tabs切换 -->
    <div class="tabs">
      <el-tabs type="border-card" stretch v-model="activeTab">
        <el-tab-pane name="account">
          <template #label>
            <div class="label">
              <el-icon><UserFilled /></el-icon>
              <span class="text">账号登录</span>
            </div>
          </template>
          <PaneAccount ref="accountRef"></PaneAccount>
        </el-tab-pane>
        <el-tab-pane name="phone">
          <template #label>
            <div class="label">
              <el-icon><Cellphone /></el-icon>
              <span class="text">手机登录</span>
            </div>
          </template>
          <PanePhone></PanePhone>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 底部区域 -->
    <div class="controls">
      <el-checkbox label="记住密码" v-model="isRemPwd" size="large" />
      <el-link type="primary" size="large">忘记密码</el-link>
    </div>
    <el-button class="login-btn" type="primary" @click="handleLoginBtnClick"
      >立即登录</el-button
    >
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import PaneAccount from "./PaneAccount.vue";
import PanePhone from "./PanePhone.vue";

const isRemPwd = ref(false);
const activeTab = ref("account");
const accountRef = ref<InstanceType<typeof PaneAccount>>(); // 固定写法: PaneAccount = 构造器 | typeof PaneAccount = 构造器类型 | InstanceType<typeof PaneAccount> = 构造器构造的实例类型

function handleLoginBtnClick() {
  if (activeTab.value === "account") {
    // 1. 获取子组件的实例
    // 2. 调用方法
    accountRef.value?.loginAction();
  } else {
    console.log("手机登录");
  }
}
</script>

<style lang="less" scoped>
.login-panel {
  width: 330px;
  margin-bottom: 150px;

  .title {
    text-align: center;
    margin-bottom: 15px;
  }

  .label {
    display: flex;
    align-items: center;
    justify-content: center;
    .text {
      margin-left: 10px;
    }
  }

  .controls {
    margin-top: 12px;
    display: flex;
    justify-content: space-between;
  }

  .login-btn {
    margin-top: 10px;
    width: 100%;
    // --el-button-size: 50px;
  }
}
</style>
