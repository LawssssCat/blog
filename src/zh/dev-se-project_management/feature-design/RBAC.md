---
title: 权限设计
order: 0
---

RBAC （Role Based Access Control） 访问控制 —— 基于角色的访问控制。
通过 “用户、角色、权限” 三个概念，实现用户分配角色，角色分配权限的权限管理方式。

## 权限

有三个级别：

+ 页面级 —— 路由守卫 —— 显示不显示
+ 组件级 —— permissions 匹配 —— 显示不显示/样式区别
+ 函数级 —— 入侵硬编码 —— 不同逻辑/不同处理 —— 通过设计优化，减少这类需求

权限组件封装：

::: tabs

@tab 权限组件使用

```vue
<!-- 权限 -->
<Authority permission="sys:user:view">
  <a-button type="primary">查询用户</a-button>
</Authority>
<!-- 多权限 -->
<Authority permission="['sys:user:view','sys:user:update']">
  <a-button type="primary" danger>禁用用户</a-button>
</Authority>
<!-- 逻辑插槽：不只限制是否可见 -->
<Authority>
  <template #default="{ userPermissions }">
    <a-button
      :disabled="!userPermissions.includes('sys:user:add')"
      type="primary"
    >
      新增用户
    </a-button>
  </template>
</Authority>
```

@tab 权限组件编写

```vue
<template>
  <slot v-if="showSlot" :userPermissions="permissions"></slot>
</template>

<script setup>
import { computed } from 'vue';
import { userAuth } from './useAuth';
const props = defineProps({
  permission: {
    type: [String, Array],
  },
});

const { permissions } = useAuth();
const showSlot = computed(() => {
  if (!props.permission) {
    // 没有传入权限，直接显示
    return true;
  }
  if (!permissions) {
    return false;
  }
  if (Array.isArray(props.permission)) {
    return props.permission.every((p) => permissions.value.includes(p));
  } else {
    return permissions.value.includes(props.permission);
  }
});
</script>
```

:::

## 角色

todo 参考：[link1](https://www.cnblogs.com/KingJA/p/16188877.html)

## 多租户

多租户可以实现多个租户共享系统实例，并且租户间能实现数据与行为的隔离。

用户数据存储主要分为3种方式：

+ 独立数据库
+ 共享数据库但隔离数据架构（不同schema）
+ 共享数据库且共享数据架构（通过租户id进行逻辑隔离）

概念设计：

+ 用户 —— 身份验证
+ 角色 —— 关联用户和资源/权限
+ 用户组/机构 —— 针对特定业务，复用角色、资源的分配
+ 租户 —— 屏蔽下层的资源管理，实现计费/计量。给不同团队分配同一套业务资源


todo 《基于Mybatis-plus多租户实现方案》
todo 《金融级别大数据平台的多租户隔离实践》
todo 《多租户用户管理数据模型设计》

todo https://www.bilibili.com/video/BV1Ns4y1U7dr
