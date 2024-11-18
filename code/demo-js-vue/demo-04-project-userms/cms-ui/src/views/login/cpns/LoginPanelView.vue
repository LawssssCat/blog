<template>
  <div class="login-panel">
    <!-- 顶部标题 -->
    <h1 class="title">后台管理系统</h1>

    <!-- 中间tabs切换 -->
    <div class="tabs">
      <el-tabs type="border-card" stretch>
        <el-tab-pane label="账号登录">User</el-tab-pane>
        <el-tab-pane label="手机登录">User</el-tab-pane>
      </el-tabs>
    </div>

    <!-- 底部区域 -->
    <div class="controls">
      <el-checkbox label="记住密码" v-model="isRemPwd" size="large" />
      <el-link type="primary" size="large">忘记密码</el-link>
    </div>
    <el-button class="login-btn" type="primary">Primary</el-button>
  </div>
</template>

<script setup lang="ts">
import { ElTabPane } from "element-plus";
import { ref } from "vue";

const isRemPwd = ref(false);
</script>

<style lang="less" scoped>
.login-panel {
  width: 330px;
  margin-bottom: 150px;

  .title {
    text-align: center;
    margin-bottom: 15px;
  }

  .icon {
    display: flex;
    align-items: center;
    justify-content: center;
    .text {
      margin-left: 5px;
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
