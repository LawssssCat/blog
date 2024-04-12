<!--
  生成指向站点网页的 <a> 链接：
  path —— 相对文件路径
  name —— （可选）连接展示名称
-->
<template>
  <RouteLink :to="url">{{ name }}</RouteLink>
</template>

<script setup lang="ts">
import { resolveLinkInfo } from "vuepress-theme-hope/client/utils/index";
import { normalizeUrl } from "../shared/utils.js";
import { usePageData } from "vuepress/client";

const props = defineProps({
  path: String,
  name: String,
});

const linkInfo = resolveLinkInfo(
  props.path.startsWith("/")
    ? props.path
    : normalizeUrl(
        "/" +
          usePageData().value.filePathRelative.replace(/\/[^/]+$/, "") +
          "/" +
          props.path.replace(/^\.\/?/, "")
      )
);

const url = linkInfo.link;
const name = props.name || linkInfo.text || linkInfo.link || props.path;
</script>
