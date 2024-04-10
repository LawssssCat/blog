<!--
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

let path: String = props.path;
if (path == "." || path == "./") {
  path = common.site.srcPath + "/" + pageData.value.filePathRelative;
} else if (path.startsWith("./")) {
  path =
    common.site.srcPath +
    ("/" + pageData.value.filePathRelative).replace(/\/[^/]+$/, "/") +
    path.replace(/^\.\//, "");
}

const url: String = normalizeUrl(
  pattern
    .replace(/:repo/u, common.github.repo)
    .replace(/:branch/u, "tree/master")
    .replace(/:path/u, path)
);
const name: String = props.name || url;
</script>
