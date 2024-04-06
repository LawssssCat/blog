<template>
  <a :href="url">{{ url }}</a>
</template>

<script setup>
import common from "../config/common";

const props = defineProps(["path"]);
const url =
  common.github.repo.replace(/\/$/, "") +
  "/tree/master/" +
  props.path.replace(/^\//, "");
</script>
