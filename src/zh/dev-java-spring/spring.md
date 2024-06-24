---
title: Spring 介绍
date: 2024-06-24
tag:
  - spring
order: 1
---

## spring 扩展点

- https://www.bilibili.com/video/BV1Mt42177MK/

::: tip
区别：

- Instantiation 实例化，对应 bean 的 new 过程
- Initialization 初始化，对应 bean 初始化和销毁的 3 对方法

:::

Spring 提供多种 spring bean 类初始化/销毁时的扩展点：

- configuration 类实现 BeanPostProcessor 接口 —— 在每个 spring bean 初始化前/后调用接口方法
  - 应用
    - ApplicationContextAwareProcessor（ApplicationContextAware 的原理）
    - AutowiredAnnotationBeanProcessor（使用 `@Autowire` 注入 spring bean 的原理）
    - CommonAnnotationBeanPostProcessor（使用 jsr250 的原理，如 `@PostConstruct`/`@PreDestroy`/... 等注解的原理）
    - aop 原理（`@EnableAspectJAutoProxy`）
- configuration 类方法上添加 `@Bean` 注解，返回值就是创建的 spring bean 对象
  - initMethod —— 初始化方法
  - destroyMethod —— 销毁方法
- spring bean 类方法上添加 `@PostConstruct` 注解
- spring bean 类实现 InitializingBean 接口（创建后）、实现 DisposableBean 接口（销毁前）
