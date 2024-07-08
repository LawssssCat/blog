---
title: Java 事件实现
date: 2024-07-08
tag:
  - java
order: 1
---

事件/消息

<!-- more -->

## 事件驱动模型：生产者、消费者

### 观察者模式

工具： JDK Observable

解耦： 生产和消费的方法解耦

```
被观察者 - 观察者
```

### 发布订阅模式

工具： Guava EventBus

解耦： 生产和消费的关系解耦

```
发布者 - 消息总线 - 订阅者
```

## 框架：Guava EventBus

[link](../dev-java-commonsense/sdk-toolkit-guava.md)

## 框架：Spring Event

:::::: tabs

@tab Event

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEvent.java -->
```

@tab Publisher

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventPublisher.java -->
```

@tab Listener

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventListener.java -->
```

@tab AnnotationListener

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventAnnotationListener.java -->
```

::::::
