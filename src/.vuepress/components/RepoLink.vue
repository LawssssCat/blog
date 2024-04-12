<!--
  生成指向代码仓库的 <a> 链接：
  path —— 相对代码仓库的源码路径
  name —— （可选）连接展示名称
-->
<template>
  <a :href="url">{{ name }}</a>
</template>

<script setup lang="ts">
import common from "../config/common";
import { normalizeUrl } from "../shared/utils.js";
import { usePageData } from "vuepress/client";

const pattern: String = ":repo/:branch/:path";

const pageData = usePageData();
const props = defineProps({
  path: String,
  name: String,
});

const url: String = normalizeUrl(
  pattern
    .replace(/:repo/u, common.github.repo)
    .replace(/:branch/u, "tree/master")
    .replace(
      /:path/u,
      props.path.startsWith("/")
        ? props.path
        : normalizeUrl(
            common.site.srcPath +
              "/" +
              usePageData().value.filePathRelative.replace(/\/[^/]+$/, "") +
              "/" +
              props.path.replace(/^\.\/?/, "")
          )
    )
);
const name: String = props.name || url;
</script>
