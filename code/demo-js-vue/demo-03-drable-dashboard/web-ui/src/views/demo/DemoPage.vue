<template>
  <div class="demo-wrapper">
    <div class="demo-header">
      <h1>{{ props.title }}</h1>
    </div>
    <div class="demo-body">
      <div v-for="(item, index) in props.demoList" :key="index" class="demo-item">
        <component :is="calcComponent(item)"></component>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineAsyncComponent } from 'vue'
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
    }
    .demo-item:hover {
      border-style: solid;
      // box-shadow: 8px 4px 4px black;
    }
  }
}
</style>
