<template>
  <div class="demo-wrapper">
    <div class="demo-header">
      <h1 style="font-size: 2rem">{{ props.title }}</h1>
    </div>
    <div class="demo-body">
      <div v-for="(item, index) in props.demoList" :key="index" class="demo-item">
        <div
          class="demo-item-title"
          @click="({ target: { parentNode: ele } }) => toggleFullScreen(ele)"
        >
          <span style="font-size: 1rem">{{ item }}</span>
        </div>
        <hr />
        <component :is="calcComponent(item)"></component>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineAsyncComponent, onMounted, onUnmounted } from 'vue'
import { toggleFullScreen } from '@/utils/fullScreen'
import { autoCalcRem } from '@/utils/rem.js'

const props = defineProps({
  title: {
    default: 'Demo',
  },
  demoList: {
    default: [],
  },
  columnNum: {
    default: 2,
  },
})

const modules = import.meta.glob('./modules/**/*.vue')
console.log('modules', modules)
function calcComponent(componentId) {
  return defineAsyncComponent(modules[`./modules/${componentId}.vue`])
}

onMounted(() => {
  autoCalcRem().start()
})
onUnmounted(() => {
  autoCalcRem().close()
})
</script>

<style scoped lang="scss">
.demo-wrapper {
  display: flex;
  flex-direction: column;
  .demo-header {
    flex-shrink: 0;
    height: 3rem;
  }
  .demo-body {
    flex-grow: 1;

    display: grid;
    grid-template-columns: repeat(v-bind('props.columnNum'), 1fr);
  }
}

.demo-wrapper {
  padding: 0.25rem 0.625rem;
  .demo-body {
    border: 0.125rem dotted yellow;
    padding: 0.25rem;
    gap: 0.25rem;

    .demo-item {
      border: 0.125rem dotted yellowgreen;
      padding: 0.125rem;
      height: 21rem;
      overflow: hidden;
    }
    .demo-item:hover {
      border-style: solid;
      // box-shadow: 8px 4px 4px black;
    }
    .demo-item-title:hover {
      font-weight: bold;
      cursor: pointer;
    }
  }
}
</style>
