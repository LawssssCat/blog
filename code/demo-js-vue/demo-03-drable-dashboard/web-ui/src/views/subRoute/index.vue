<template>
  <div>
    <div :key="index" v-for="(item, index) in ['a', 'a', 'b']">
      {{ item }} | <RouterView :name="item"></RouterView> |
    </div>
  </div>
</template>

<script setup></script>
