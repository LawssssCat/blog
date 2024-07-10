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

特性：

- 支持异步 —— `@Async`
- 支持条件配置 —— `@EventListener(condition = "...")`

### 基本使用

:::::: tabs

@tab Event

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEvent.java -->
```

@tab Publisher

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventPublisher.java -->
```

:::: info

按 Spring 的设计，提供了两个接口注入 或者 ApplicationContext。

::: tabs

@tab Publisher

```java
@Service
public class XxxEventService implements ApplicationEventPublisherAware {
  private ApplicationEventPublisher applicationEventPublisher;
  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
  public void publishEvent() {
    applicationEventPublisher.publishEvent(new XxxEvent("xxx"));
  }
}
```

@tab Context

Context 实现了 Publisher 接口，把 Context 当作 Publisher 用即可。

```java
@Service
public class XxxEventService implements ApplicationContextAware {
  private ApplicationContext applicationContext;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
  public void publishEvent() {
    applicationContext.publishEvent(new XxxEvent("xxx"));
  }
}
```

:::

::::

@tab Listener

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventListener.java -->
```

@tab AnnotationListener

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/event/SimpleEventAnnotationListener.java -->
```

::::::

### 自定义事件，封装实现按标识处理

特性：

- 监听事件可按 Topic 区分处理
- 监听器统一配置

:::::: tabs

@tab Event

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/eventTpoic/TopicEvent.java -->
```

@tab Publisher

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/eventTpoic/TopicEventPublisher.java -->
```

@tab ListenerEngine

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/eventTpoic/TopicListenerEngine.java -->
```

@tab ListenerEngineConfig

```java
<!-- @include: @project/code/demo-java-event/demo-01-spring/src/main/java/org/example/eventTpoic/TopicListenerEngineConfig.java -->
```

::::::

### 内置事件

| 事件                  | 描述                                                                                                    |
| --------------------- | ------------------------------------------------------------------------------------------------------- |
| ContextRefreshedEvent | 容器实例被实例化或者 refreshed（触发 `refresh()` 方法）时触发事件。                                     |
| ContextStartedEvent   | 容器启动时（触发 `start()` 方法）触发事件。                                                             |
| ContextStoppedEvent   | 容器停止时（触发 `stop()` 方法）触发事件。此时所有 bean 收到 stop 信号。可通过 `start()` 方法重启容器。 |
| ContextClosedEvent    | 容器关闭时（触发 `close()` 方法）触发事件。此时所有 bean 已经销毁。容器无法重启。                       |
| RequestHandledEvent   | Spring Web 中 DispatcherServlet 处理完一个请求后触发事件。                                              |
