---
title: JDK validation 功能（spring）
date: 2024-05-03
tag:
  - java
  - spring
order: 34
---

对于 JDK validation，Spring 做了封装 `spring-boot-starter-validation`，引用于如 `spring-boot-starter-web` 项目中。

<SiteInfo
  name="Validation with Spring Boot - the Complete Guide"
  url="https://reflectoring.io/bean-validation-with-spring-boot"
  preview="/assets/images/cover3.jpg"
/>

## 校验捕获

在 web 项目中，通过接收 BindingResult 参数来处理请求中的参数校验异常。

::: tabs

@tab 处理类

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-springboot-web/src/main/java/org/example/handler/UserHandler.java -->
```

@tab 校验类

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-springboot-web/src/main/java/org/example/entity/UserParam.java -->
```

:::

## 全局拦截校验异常

在 web 项目中，通过 `@ControllerAdvice` 和 `@ExceptionHandler({BindException.class})` 注解可以拦截校验异常。

但对于不同请求、不同参数接受方式，拦截异常不同：

- 当 `@NotNull`/`Positive` 这些注解直接修饰参数时，抛出 `ConstraintViolationException` 异常。 （💡 这些注解直接修饰的话，需要在类上添加 `@Validated` 注解，校验才会工作）
- 当 `@Valid`/`@Validated` 修饰 `@RequestBody` 参数时，抛出 `MethodArgumentNotValidException` 异常。
- 当 `@Valid`/`@Validated` 修饰非 `@RequestBody` 参数时，抛出 `BindException` 异常。

::: tabs

@tab 处理类

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-springboot-web/src/main/java/org/example/handler/TaskHandler.java -->
```

@tab 校验类

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-springboot-web/src/main/java/org/example/entity/TaskParam.java -->
```

:::
