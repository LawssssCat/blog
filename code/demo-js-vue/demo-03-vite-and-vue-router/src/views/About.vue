<template>
    <div>
        About
        <ul>
            <li v-for="(value, key, index) in route.query" :key="key">{{key}}, {{ value }}</li>
        </ul>
    </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute(); // 获取上层路由传递的参数

</script>

<style scoped>

</style>