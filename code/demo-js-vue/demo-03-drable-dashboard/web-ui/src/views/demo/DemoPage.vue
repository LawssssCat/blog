<template>
  <div class="demo-wrapper">
    <div class="demo-header">
      <h1>{{ props.title }}</h1>
    </div>
    <div class="demo-body">
      <div v-for="(item, index) in props.demoList" :key="index" class="demo-item">
        <div
          class="demo-item-title"
          @click="({ target: { parentNode: ele } }) => toggleFullScreen(ele)"
        >
          {{ item }}
        </div>
        <hr />
        <component :is="calcComponent(item)"></component>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineAsyncComponent } from 'vue'
import { toggleFullScreen } from '@/utils/fullScreen'

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
console.log(modules)
function calcComponent(componentId) {
  return defineAsyncComponent(modules[`./modules/${componentId}.vue`])
}
function handleDemoItemTitleClick(e) {
  e.target.parentNode.requestFullscreen()
}
</script>

<style scoped lang="scss">
.demo-wrapper {
  display: flex;
  flex-direction: column;
  .demo-body {
    flex-grow: 1;

    display: grid;
    grid-template-columns: repeat(v-bind('props.columnNum'), 1fr);
  }
}

.demo-wrapper {
  padding: 5px 10px;
  .demo-body {
    border: 2px dotted yellow;
    padding: 5px;
    gap: 5px;

    .demo-item {
      border: 2px dotted yellowgreen;
      padding: 1px 2px;
      height: 300px;
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
