---
title: Java 功能特性
date: 2025-03-15
order: 0
---

## JDK10

### 局部变量的类型推断

[link](https://java.didispace.com/java-features/java10/jep286-local-variable-type-inference.html)

::: tabs

@tab JDK7之前

```java
List<String> list = new ArrayList<String>();
Map<String, String> map = new HashMap<String,String>();
```

@tab JDK7 简化

```java
List<String> list = new ArrayList<>();
Map<String, String> map = new HashMap<>();
```

@tab JDK10

```java
var list = new ArrayList<String>();
var map = new HashMap<String, String>();
```

对于 `var` 的使用还有几点要注意的：

1. 定义的时候必须初始化
1. 只能用于定义局部变量
1. 不能用于定义成员变量、方法参数、返回类型
1. 每次只能定义一个变量，不能复合声明变量
 
:::

## JDK14

### JEP 361：增强 switch 语法

> 这里的JEP 361特性，经历了JDK 12、JDK 13两个预览版本之后才在JDK 14中定稿，所以部分功能在JDK 12和JDK 13中也会看到，但真正使用，还是建议在JDK 14之后的版本中应用

```java
public class DemoTest {
    @Test
    void test() {
        doSome(1);
    }

    private void doSome(int flag) {
        switch (flag) {
            case 1 -> {
                System.out.println("1:flag = " + flag);
                flag++;
                // 无需 break
            }
            case 2 -> System.out.println("2:flag = " + flag); // 单行无需 {...}
            // 可以不写default分支
        }
        System.out.println("e:flag = " + flag);
    }
}
```

## JDK15

## JDK16

### 增强 instanceof 语法

> 该功能经历了2个Preview版本（JDK 14中的JEP 305、JDK 15中的JEP 375），最终定稿于JDK 16中的JEP 394

```java
public class DemoTest {
    @Test
    void test() {
        doSome("str");
    }

    private void doSome(Object obj) {
        if (obj instanceof String value) { // 末尾加上变量名
            // String value = (String) data.get("key1); // 可以省略这行
            System.out.println("value = " + value);
        }
    }
}
```

### 增加 record 类型

[link](./jdk16-recordtype.md)

## JDK17

## JDK17

## JDK18

## JDK19

## JDK20

## JDK21

OpenJDK21
https://openjdk.org/projects/jdk/21/

2023年9月20日，Oracle公司宣布Java 21正式发布。该版本是继JDK 17之后最新的长期支持版本（LTS），**将获得至少8年的支持**！

Java 21 号称具有数千项性能、稳定性和安全性改进。新的 JDK 21 包括对 15 项改进的抢先体验，这些增强功能是在 Oracle CloudWorld 2023 会议上宣布的，包括支持虚拟线程以提高整体吞吐量，以及增加对向量应用编程接口（API）的支持，从而更轻松地构建涉及人工智能 AI 模型的 Java 应用。

### 新增API

+ HTTP Client API
+ 向量 API
+ 注解 API
+ Thread API

### 模式匹配

[link](./jdk21-patternmatching.md)



## 附录

### 名词

+ JCP（Java Community Process，Java 社区流程）
+ JSL（Java Language Specification，Java 语言规范） —— 被实现的语言特性
+ JVMS（Java Virtual Machine Specification，Java 语言规范） —— 被实现的JVM特性
+ JSR（Java Specification Requests，Java 规范提案） —— 被接纳的特性。内容：专家组、JSL/JVMS变更说明、投票、计划（schedule）
+ JBS（JDK Bug System，JDK 问题跟踪系统） —— 通过issue跟踪BUG/JEP的实现
+ JEP（JDK Enhancement Proposal，JDK 增强建议） —— 被提出特性想法。内容：任何人、想法

### 参考

+ Java 9 - 21：新特性解读 ⭐⭐⭐
https://java.didispace.com/java-features/
+ JDK 21新特性全解析：从JEP 400到JEP 420的35个要点
https://www.w3cschool.cn/hycig/hycig-bpvq3zff.html
+ JDK17 新特性（视频）
https://www.bilibili.com/video/BV1k34y1U74K/
+ JDK18 新特性（视频）
https://www.bilibili.com/video/BV1m5411U7ii/
+ JDK19 新特性（视频）
https://www.bilibili.com/video/BV1ZV4y1N7eH/
+ JDK20 新特性（视频）
https://www.bilibili.com/video/BV1JK411z78J/
+ JDK21 新特性（视频）
https://www.bilibili.com/video/BV1Ah4y1X7wo/
+ JDK22 新特性（视频）
https://www.bilibili.com/video/BV1DK4y1i7wM
+ JDK23 新特性（视频）
https://www.bilibili.com/video/BV1Zi421v7E6/
+ JDK24 新特性（视频）
https://www.bilibili.com/video/BV1j8S3YoE4X/
