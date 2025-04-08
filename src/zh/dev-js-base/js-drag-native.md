---
title: 拖拽效果
order: 66
---

:::::: vue-demo

```vue
<!--
参考：
https://www.bilibili.com/video/BV1p6vhe6E5X
 -->
<template>
  <div ref='xxsafsf' class="list">
    <div draggable='true' class="list-item">1</div>
    <div draggable='true' class="list-item">2</div>
    <div draggable='true' class="list-item">3</div>
    <div draggable='true' class="list-item">4</div>
    <div draggable='true' class="list-item">5</div>
  </div>
</template>

<script>
export default {
  mounted() {
    const list = this.$refs.xxsafsf;
    let sourceNode;

    // 1. 监听拖拽事件，改变样式
    // 由于事件委托，可以直接监听父元素
    list.ondragstart = (e) => {
      const el = e.target; // todo 为何 setTimeout 中 target 变成父元素？
      sourceNode = e.target;
      e.dataTransfer.effectAllowed = 'move' // 鼠标显示+号（默认是禁止符号）
      setTimeout(() => {
        console.log('拖拽开始', e, el)
        // 通过异步，让拖拽开始时，样式不变，从而使鼠标所在的el样式不变
          el.classList.add('moving')
      }, 0)
    }

    // 1. 监听拖入事件
    list.ondragenter = (e) => {
      // fix：元素不允许拖动到自身
      e.preventDefault();

      // 拖入父元素或者自身不做变化
      if (e.target === list || e.target === sourceNode) {
        return ;
      }
      // 判断拖动方向
      const children = [...list.children];
      const sourceIndex = children.indexOf(sourceNode);
      const targetIndex = children.indexOf(e.target);
      if (sourceIndex < targetIndex) {
        console.log('ondragenter 向下', e)
        list.insertBefore(sourceNode, e.target.netElementSibling);
      } else {
        console.log('ondragenter 向上', e)
        list.insertBefore(sourceNode, e.target);
      }
    }
    list.ondragover = (e) => {
      // fix：元素不允许拖动到自身，默认行为会复位元素
      e.preventDefault();
    }
    // 1. 监听放手事件
    list.ondragend = (e) => {
      e.target.classList.remove('moving')
      // todo 恢复 e.dataTransfer.effectAllowed = 'move' // 鼠标显示+号（默认是禁止符号）
    }

    // todo flip.js 动画
  },
  unmounted() {
    console.log('unmounted')
  }
}
</script>

<style scoped>
.list {
}
.list .list-item {
  background-color: aquamarine;
  border-radius: 4px;
  margin-top: 4px;
  padding: 0 16px;

  cursor: move;
  user-select: none;
}
.list .list-item.moving {
  background: transparent;
  color: transparent;
  border: 1px dashed #ccc;
}
</style>
```

::::::
