---
title: Mockito 使用
date: 2024-04-24
tag:
  - java
  - test
order: 33
---

Mockito 是 Java 的模拟测试框架，通过 Mockito 可以创建和配置 Mock 对象，简化外部依赖的类的测试。

<!-- more -->

<SiteInfo
  name="Mocikto 教程"
  url="https://baeldung-cn.com/mockito-series"
  preview="/assets/images/cover3.jpg"
/>

```xml title="pom.xml"
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

## 初始化

::: tabs

@tab 注解 + ExtendWith

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Init01Test.java -->
```

```xml title="pom.xml"
<!-- 提供 MockitoExtension.class 对象 -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

@tab 注释 + BeforeEach

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Init02Test.java -->
```

@tab 手动初始化

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Init03Test.java -->
```

:::

::: tip

- Mock —— 从类型创建，不调用实际方法
- Spy —— 对现有实例的封装，调用被封装实例的实际方法

:::

## 基本使用

- Mock —— 根据类名创建一个 “空对象”，可以对其返回值进行定制，若无定制返回默认值
- Spy —— 根据实际对象创建一个 “代理对象”，
- Captor —— 捕获传入的参数值
- InjectMock —— 自动注入相关引用

### mock

对于一个 mock 对象，我们可以指定返回值和执行特定的动作。
当然，也可以不指定。

如果不指定返回值的话，一个 mock 对象的所有非 void 方法都将返回默认值：

- int、long 类型方法将返回 0
- boolean 方法将返回 false
- 对象方法将返回 null 等等

而 void 方法将什么都不做。

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Mock01Test.java -->
```

### spy

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Spy01Test.java -->
```

### captor

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/Captor01Test.java -->
```

### 区别 @Mock/@Spy/@Captor/@InjectMock

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/DiffTypesTest.java -->
```

### verify

通过 verify 来判断方法内部实现是否符合预想。

通过对 `verify(T mock)`/`verify(T mock, VerificationMode mode)` 的返回值的方法调用，验证某些行为是否至少发生过一次/确切的次数/从未发生过。

| 方法调用次数校验                       | 说明                               |
| -------------------------------------- | ---------------------------------- |
| `times(int wantedNumberOfInvocations)` | 允许验证调用的确切次数。           |
| `atLeast(int minNumberOfInvocations)`  | 允许至少 x 次调用的验证。          |
| `atMost(int maxNumberOfInvocations)`   | 允许最多 x 次调用的验证。          |
| `atLeastOnce()`                        | 允许至少一次调用的验证。           |
| `atMostOnce()`                         | 允许最多一次调用的验证。           |
| `never()`                              | times(0)的别名，见 times(int) 。   |
| `only()`                               | 允许检查给定的方法是否只调用一次。 |

| 类是否被调用校验                            | 说明                                                                         |
| ------------------------------------------- | ---------------------------------------------------------------------------- |
| `verifyNoInteractions(Object... mocks)`     | 验证给定的 mock 对象上没有发生交互。                                         |
| `verifyNoMoreInteractions(Object... mocks)` | 检查任何给定的 mock 对象上是否有任何未经验证的交互。                         |
| `validateMockitoUsage()`                    | 验证测试代码中是否有书写错误的地方。如是否有漏写 return、verifyMethod 等等。 |

```java
<!-- @include: @project/code/demo-java-test/usage-mockito/src/test/java/org/example/VerifyTest.java -->
```

## 原理

todo 原理

```java

  @Test
  public void testInit() {
    Mockito.mockingDetails(mockUserService).isMock(); // true
    Mockito.mockingDetails(spyUserService).isSpy(); // true
    Mockito.mockingDetails(spyUserService).isMock(); // true —— ❗Spy是Mock的子类
  }
```
