<template>
  <div class="login">
    <LoginPanelView></LoginPanelView>
  </div>
</template>

<script setup lang="ts">
import LoginPanelView from "./cpns/LoginPanelView.vue";
</script>

<style scoped>
.login {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  /* background: url(""); */
}
</style>
