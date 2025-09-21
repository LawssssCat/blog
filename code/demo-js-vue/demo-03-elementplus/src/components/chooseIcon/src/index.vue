<template>
  <div>
    <ElButton @click="handleClick" type="primary">
      <slot></slot>
    </ElButton>
    <ElDialog
      :title="props.title"
      v-model="isDialogVisible"
      body-class="el-dialog-box"
    >
      <div class="container">
        <div
          v-for="(val, key) of ElIcons"
          :key="key"
          class="item"
          @click="handleCopy(key)"
          @mousedown="mousedown"
          @mouseup="mouseup"
          @mouseleave="mouseup"
        >
          <div class="icon">
            <Component :is="val"></Component>
          </div>
          <div class="text">{{ key }}</div>
        </div>
      </div>
    </ElDialog>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";

import { ElMessage } from "element-plus";
import * as ElIcons from "@element-plus/icons";

import { useCopy } from "@/hooks/useCopy";
import {toLine} from '@/utils/index';

const props = defineProps<{
  // 弹出框的标题
  title: string;
  // 控制弹出框的显示与隐藏
  visible: boolean;
}>();

const emits = defineEmits(["update:visible"]);

const isDialogVisible = computed({
  get() {
    return props.visible;
  },
  set(newVal) {
    // 单向数据流
    emits("update:visible", newVal);
  },
});

const handleClick = () => {
  isDialogVisible.value = true;
};

//  @close="handleDialogClose"
// const handleDialogClose = () => {
//   emits('update:visible', false)
// }

const handleCopy = (key: string) => {
  useCopy(`<el-icon-${toLine(key)} />`);
  isDialogVisible.value = false;
  ElMessage({
    message: "复制成功",
    type: "success",
    duration: 1000,
  });
};

function mouseup(e: Event) {
  (e.currentTarget as HTMLElement).classList.remove('mousedown')
}

function mousedown(e: Event) {
  (e.currentTarget as HTMLElement).classList.add('mousedown')
}
</script>

<style scoped lang="scss">
:deep(.el-dialog-box) {
  height: 500px;
  overflow-y: auto;
}

.container {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  padding: 4px;
}

.item {
  width: 25%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  height: 70px;

  cursor: pointer;
  &:hover {
    box-shadow: 3px 3px 6px rgba(0, 0, 0, 0.3);
  }
  &.mousedown {
    box-shadow: inset 3px 3px 6px rgba(0, 0, 0, 0.3);
  }

  .icon {
    font-size: 14px;
  }

  .text {
    flex-shrink: 0;
  }

  svg {
    width: 2em;
    height: 2em;
  }
}
</style>
