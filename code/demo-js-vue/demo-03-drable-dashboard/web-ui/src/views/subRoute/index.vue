<template>
  <div>
    <div :key="index" v-for="(item, index) in ['a', 'a', 'b']">
      {{ item }} | <router-view :name="item"></router-view>
    </div>
  </div>
</template>

<script setup></script>
