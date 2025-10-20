<template>
  <div>
    hello
  </div>

  <hr/>
    <ElSpace direction="vertical">
    <div>
      opener: {{ openerContent }}
    </div>
    <div>
      count: {{ countContent }}
    </div>
  </ElSpace>

  <hr/>

  <ul>
    <li>
      <ElLink @click="handleOpen('/demo-01-openxss', '_blank')">unsafe open()</ElLink>
    </li>
    <li>
      <ElLink href="/demo-01-openxss" target="_blank" rel="opener referrer">unsafe __target</ElLink>
    </li>
  </ul>

  <hr/>

  <ul>
    <li>
      <ElLink @click="handleOpen('/demo-01-openxss', '_blank', 'noopener=yes,noreferrer=yes')">safe open()</ElLink>
    </li>
    <li>
      <ElLink href="/demo-01-openxss" target="_blank">safe __target</ElLink>
    </li>
  </ul>

</template>

<script setup>
import { ref, computed } from 'vue';

const openerContent = computed(() => {
  if (!window) {
    return 'window is null';
  }
  if (!window.opener) {
    return 'window.opener is null'
  }
  return window.opener;
})

window.count = 1;

const countContent = computed(() => {
  return window?.opener?.count + window?.count;
})

function handleOpen(...xxx) {
  window.open(...xxx);
}
</script>

<style lang="scss" scoped>

</style>