<!--
  path —— 相对代码仓库的源码路径
  name —— （可选）连接展示名称
-->
<template>
  <a :href="url">{{ name }}</a>
</template>

<script setup lang="ts">
import common from "../config/common";

const pattern: String = ":repo/:branch/:path";

const props = defineProps({
  path: String,
  name: String,
});

const url: String = pattern
  .replace(/:repo/u, common.github.repo.replace(/\/$/, ""))
  .replace(/:branch/u, "tree/master")
  .replace(/:path/u, props.path.replace(/^\/|\/$/, ""));
const name: String = props.name || url;
</script>
