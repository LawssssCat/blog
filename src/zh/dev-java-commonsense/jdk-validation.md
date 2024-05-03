---
title: JDK validation 功能
date: 2024-04-29
tag:
  - java
  - jdk
order: 33
---

Java EE 规范中用接口定义了 Java Bean 的校验方式，即 [Java Bean Validation](https://beanvalidation.org/)。

::: info

常用的 Java EE 规范接口在 jdk 的 javax 包下，如：

- javax.sql —— 数据库访问接口。实现厂商有 mysql/sqlserver/oracle/...
- javax.servlet —— tomcat/jetty
- java.xml —— jaxp（java api for xml processing）/jaxb
- javax.persistence —— hibernate
- javax.transaction —— 分布式实务
- javax.jms —— activemq

:::

<!-- more -->

## 使用案例

### 一：引入校验接口

Java Bean Validation 接口不默认包含在 JDK 中，需要主动引入：

::: code-tabs#Validation

@tab Java Bean Validation

```xml title="pom.xml"
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
```

@tab Jakarta Bean Validation

```xml title="pom.xml"
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
    <version>2.0.1</version>
</dependency>
```

:::

::: tip

**Java 与 Jakarta 的区别？**

Java EE 规范由 [JCP（Java Community Process）](https://jcp.org/) 通过 JSR（Java Specification Requests，Java 规范提案） 规定。
但 Oracle 收购 Java 后，虽然将 Java EE 规范捐献给 eclipse 基金会管理，但要求更改 java 相关的命名，其中包括 Java EE 代码所在的 javax 包名。
因此，后续 javax 包下的代码统一移动到了 jakarta 下。
所以，可以说 java 与 jakarta 没太大区别，或者说 jakarta 是新版的 java。

参考： <https://blogs.oracle.com/theaquarium/opening-up-java-ee>

:::

::: info

规范版本：

| 提案   | 版本               |
| ------ | ------------------ |
| jsr303 | beanvalidation 1.0 |
| jsr349 | beanvalidation 1.1 |
| jsr380 | beanvalidation 2.0 |

:::

### 二：引入校验实现（hibernate-validator）

引入 `validation-api` 的具体实现之一 [hibernate-validator](http://hibernate.org/validator/)：

::: code-tabs#Validation

@tab Java Bean Validation

```xml title="pom.xml"
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.0.Final</version>
</dependency>
<!-- fix: javax.validation.ValidationException: HV000183: Unable to initialize 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath, or use ParameterMessageInterpolator instead -->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-el</artifactId>
    <version>9.0.65</version>
</dependency>
```

@tab Jakarta Bean Validation

```xml title="pom.xml"
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.1.Final</version>
</dependency>
```

:::

::: warning

`validation-api`/`jakarta.validation-api` 属于接口，没有具体实现，不能直接运行，否则出现如下告警：

```java
javax.validation.NoProviderFoundException: Unable to create a Configuration, because no Bean Validation provider could be found. Add a provider like Hibernate Validator (RI) to your classpath.

	at javax.validation.Validation$GenericBootstrapImpl.configure(Validation.java:291)
	at javax.validation.Validation.buildDefaultValidatorFactory(Validation.java:103)
```

:::

### 三：使用校验规则

::: tabs

@tab 类限定

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-javax/src/main/java/org/example/entity/User.java -->
```

@tab 测试方法

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-javax/src/test/java/org/example/entity/UserTest.java -->
```

:::

## 常用注解

### Bean Validation Constraint

| 注解                                                                                                                                   | 说明 |
| -------------------------------------------------------------------------------------------------------------------------------------- | ---- |
| `@Null`/`@NotNull`/`@NotEmpty`/`NotBlank`                                                                                              | 非空 |
| `@AssertTrue`/`@AssertFalse`                                                                                                           | 布尔 |
| `@Min(value)`/`@Max(value)`/`@DecimalMin(value)`/`@DecimalMax(value)`/`@Size(max, min)`/`@NegativeOrZero`/`@Digits(integer, fraction)` | 数值 |
| `@Past`/`@PastOrPresent`/`@Future`                                                                                                     | 时间 |
| `@Pattern(value)`/`@Email`                                                                                                             | 正则 |

### Hibernate Validation Constraint

| 注解      | 说明 |
| --------- | ---- |
| `@Length` | 长度 |
| `@Range`  | 范围 |
| `@URL`    | 正则 |

## 实现原理分析

有多种 bean validation 实现，下面以 hibernate 为例。

### 一：校验实现的加载

上面例子中，我们业务类上只调用了校验接口（`javax.validation.Validator`），运行时就自动调用了校验器的实现（`org.hibernate.validator.internal.engine.ValidatorImpl`）。
有这种现象是因为 beanvalidation 使用了 SPI 技术。

> SPI（service provider interface，服务提供接口） 是 JDK 提供的一种服务发现机制。
> 同样使用 SPI 的有 jdbc/slf4j/...

在 beanvalidation 这里具体有两个关键的配置点：

1.  在接口中调用服务接口的类加载方法 —— `ServiceLoader<ValidationProvider> loader = ServiceLoader.load( ValidationProvider.class, classloader );`
1.  在实现中配置服务接口的实现类名 —— 在 `META-INF/services/javax.validation.spi.ValidationProvider` 中写入 `org.hibernate.validator.HibernateValidator`

这样，运行时就可以获取到服务接口的具体实现了。

### 二：校验实现的绑定

校验注解会绑定一个校验器实现。当要校验值时，会找到校验器进行校验。

如： `@NotBlank` 由 `org.hibernate.validator.internal.constraintvalidators.hv.NotBlankValidator` 实现

绑定过程在 `org.hibernate.validator.internal.metadata.core.ConstraintHelper` 中：

```java
putConstraint( tmpConstraints, NotBlank.class, NotBlankValidator.class );

List<Class<? extends ConstraintValidator<NotEmpty, ?>>> notEmptyValidators = new ArrayList<>( 11 );
notEmptyValidators.add( NotEmptyValidatorForCharSequence.class );
notEmptyValidators.add( NotEmptyValidatorForCollection.class );
notEmptyValidators.add( NotEmptyValidatorForArray.class );
notEmptyValidators.add( NotEmptyValidatorForMap.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfBoolean.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfByte.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfChar.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfDouble.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfFloat.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfInt.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfLong.class );
notEmptyValidators.add( NotEmptyValidatorForArraysOfShort.class );
putConstraints( tmpConstraints, NotEmpty.class, notEmptyValidators );

putConstraint( tmpConstraints, NotNull.class, NotNullValidator.class );
putConstraint( tmpConstraints, Null.class, NullValidator.class );
```

## 自定义注解

::: tabs

@tab 注解

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-javax/src/main/java/org/example/entity/validation/MyUrl.java -->
```

@tab 校验器

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-javax/src/main/java/org/example/entity/validation/MyUrlValidator.java -->
```

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-validation/demo-usage-validation-javax/src/test/java/org/example/entity/validation/MyUrlValidatorTest.java -->
```

:::

---

参考：

- 参数校验 Jakarta Bean Validation 学习 - <https://blog.csdn.net/csdn_mrsongyang/article/details/106115243>
