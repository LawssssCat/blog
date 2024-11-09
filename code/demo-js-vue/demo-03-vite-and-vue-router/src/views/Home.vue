<template>
    <div>
        Home
        <ul>
            <li>
                <!-- 路由触发 -->
                <button @click="toAbout">/about</button>
            </li>
            <!-- 二级路由 -->
            <li>
                <button @click="toPage(1)">/page01</button>
            </li>
            <li>
                <button @click="toPage(2)">/page02</button>
            </li>
        </ul>
        <!-- 二级路由显示 -->
        <RouterView></RouterView>
    </div>
</template>

<script setup lang="ts">

import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute(); // 获取上层路由传递的参数

function toAbout() {
    // 路由转跳
    router.push({
        path: '/about',
        query: {
            name: '张三',
            id: 1
        }
    })
}

function toPage(num: number) {
    router.push({
        path: '/page' + num
    })
}
</script>

<style scoped>

</style>