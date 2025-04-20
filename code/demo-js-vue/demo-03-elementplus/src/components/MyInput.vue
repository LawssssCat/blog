<!--
封装组件常见问题：
1. 使用 v-bind="$attrs" 将所有属性传递给 ElInput 组件
2. 使用 <template v-for="(value, name) in $slots" #[name]="slotData"> 将所有插槽、作用域插槽传递给 ElInput 组件
3. 将 ElInput 方法挂载到组件实例上（在 vue 上无法像 react 一样直接替换 ref 指向，只能使用这种折衷方法，能实现效果！）
-->

<template>
  <div class="my-input">
    <ElInput ref="inpRef" v-bind="$attrs">
      <template v-for="(value, name) in $slots" #[name]="slotData">
        <slot :name="name" v-bind="slotData ?? {}"></slot>
      </template>
    </ElInput>
  </div>
</template>

<script>
export default {
  mounted() {
    const entries = Object.entries(this.$refs.inpRef);
    for (const [key, value] of entries) {
      this[key] = value;
    }
  },
};
</script>

<style scoped lang="scss">
.my-input {
  transition: 0.3s;
  &:hover,
  &:focus-within {
    filter: drop-shadow(0 0 3px rgba(0, 0, 0, 0.3));
  }
}
</style>
