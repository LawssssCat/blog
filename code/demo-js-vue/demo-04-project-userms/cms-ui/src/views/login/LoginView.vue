<template>
  <div>login</div>
</template>

<script setup lang="ts">
import { xRequest2 } from "@/service";

xRequest2
  .get({
    url: "/login",
    interceptors: {
      requestSuccessFn: (config) => {
        console.log("login 请求成功拦截");
        return config;
      },
      // requestFailureFn: (error) => {
      //   console.log("login 请求失败拦截");
      //   Promise.reject(error);
      // },
      responseSuccessFn: (resp) => {
        console.log("login 响应成功拦截");
        return resp;
      },
      responseFailureFn: (err) => {
        console.error("login 响应失败拦截");
        return Promise.reject(err);
      },
    },
  })
  .then((res) => {
    console.log(res);
  })
  .catch((err) => {
    console.error(err);
  });
</script>

<style scoped></style>
