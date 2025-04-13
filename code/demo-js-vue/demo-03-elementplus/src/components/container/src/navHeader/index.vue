<template>
  <div class="header">
    <span @click="toggle">
      <el-icon-expand v-if="isCollapse"></el-icon-expand>
      <el-icon-fold v-else></el-icon-fold>
    </span>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  isCollapse: boolean;
}>();
let emits = defineEmits(["update:is-collapse"]);
let toggle = () => {
  emits("update:is-collapse", !props.isCollapse);
};
</script>

<style scoped lang="scss">
.header {
  height: 60px;
  display: flex;
  padding: 0 20px;
  align-items: center;
}
</style>
