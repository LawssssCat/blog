<template>
  <div class="trend">
    <div class="text">
      <template v-if="slots.default">
        <slot></slot>
      </template>
      <template v-else>
        {{ props.text }}
      </template>
    </div>
    <div class="icon">
      <component :is="`el-icon-${props.upIcon}`"
        v-if="isUp"
        :style="{
          color: !props.isColorReverse ? props.upColor : props.downColor,
        }"
      ></component>
      <component :is="`el-icon-${props.downIcon}`"
        v-else
        :style="{
          color: props.isColorReverse ? props.upColor : props.downColor,
        }"
      ></component>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, useSlots } from "vue";

const slots = useSlots();

const props = defineProps({
  // 标记当前趋势是上升up/下降down
  type: {
    type: String,
    default: "up",
  },
  // 趋势显示的文字
  // 1. 父组件传递过来的数据
  // 2. 插槽
  text: {
    type: String,
    default: "文字",
  },
  // 上升趋势图标颜色
  upIcon: {
    type: String,
    default: 'arrowup'
  },
  upColor: {
    type: String,
    default: "#f5222d",
  },
  // 下降趋势图标颜色
  downIcon: {
    type: String,
    default: 'arrowdown'
  },
  downColor: {
    type: String,
    default: "#52c41a",
  },
  isColorReverse: {
    type: Boolean,
    default: false,
  },
});

const icons = ["arrowup", "arrowdown"];

const isUp = computed(() => props.type === "up");
</script>

<style scoped lang="scss">
.trend {
  display: flex;
  align-items: center;
  .text {
    font-size: 12px;
    margin-right: 4px;
  }
  .icon {
    svg {
      width: 1em;
      height: 1em;
    }
  }
}
</style>
