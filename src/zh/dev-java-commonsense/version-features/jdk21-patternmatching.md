---
title: JDK21功能特性：模式匹配
date: 2025-03-15
order: 99
---

## “模式匹配”是什么？

模式匹配（Pattern Matching）是检查某个值（value）/对象（object）是否匹配某一个模式的机制。

```bash
输入：匹配模式⭐、匹配值/匹配对象
输出：是/否匹配
程序：根据是/否匹配，做对应处理
```

更具上述定义，可以找到多种实现：
+ `if`语句 —— `if(1 == 1) {...}`
+ `switch`语句 —— `switch(value) {case 1: ...; break; ...}`
+ 正则语句 —— `sed 's/oldValue/newValue/g'`
+ ...

## JDK8~21的模式匹配

功能演进方向：

+ 增加/增强可以使用的模式匹配的地方：`if`/**`switch`**
+ 增加新的匹配模式：`==`/**`instanceof`**/**`null`**

### JDK12

<https://openjdk.org/projects/jdk/12/>

#### JEP 325: Switch Expressions (Preview)

### JDK 13

<https://openjdk.org/projects/jdk/13/>

#### JEP 354: Switch Expressions (Preview)

### JDK14

<https://openjdk.org/projects/jdk/14/>

#### JEP 361: Switch Expressions

<https://openjdk.org/jeps/361>

switch 表达式支持

:::::: details 表达式块及其返回值
::: tabs

@tab 之前

```java
int numLetters;
switch (day) {
    case MONDAY:
    case FRIDAY:
    case SUNDAY:
        numLetters = 6;
        break;
    case TUESDAY:
        numLetters = 7;
        break;
    case THURSDAY:
    case SATURDAY:
        numLetters = 8;
        break;
    case WEDNESDAY:
        numLetters = 9;
        break;
    default:
        throw new IllegalStateException("Wat: " + day);
}
```

@tab JDK14

```java
int numLetters = switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> 6;
    case TUESDAY                -> { var r = 7; yield r; }
    case THURSDAY, SATURDAY     -> { yield 8; }
    case WEDNESDAY              -> { 9; };
};
```

重点：

1. 直接返回值
1. 无需 `break` 关键字
1. 相同处理的匹配条件可写在同一个 `case` 上，通过 “,” 分割

:::
::::::

#### JEP 305: Pattern Matching for instanceof (Preview)

模式匹配 for `instanceof`，see JDK16

#### JEP 359: Records (Preview)

记录类型，see JDK16

### JDK15

<https://openjdk.org/projects/jdk/15/>

#### JEP 375: Pattern Matching for instanceof (Second Preview)

模式匹配 for `instanceof`，see JDK16

#### JEP 384: Records (Second Preview)

记录类型，see JDK16

### JDK16

<https://openjdk.org/projects/jdk/16/>

#### JEP 394: Pattern Matching for instanceof

<https://openjdk.org/jeps/394>

对于 instance 的模式匹配

:::::: details
::: tabs

@tab 之前

```java
if (obj instanceof String) {
    String value = (String) obj;
    if ("str".equals(value)) {
        System.out.println("value = " + value);
    }
}
```

@tab JDK16

```java
if (obj instanceof String value && "str".equals(value)) {
    // 1.末尾加上变量名；
    // 2.变量可以在“&&”判断中使用（❗即不可在“||”中使用）
    System.out.println("value = " + value);
}
```

:::
::::::

#### JEP 395: Records

<https://openjdk.org/jeps/395>

新增 record 类型，作为 “数据” 的容器

[link](./jdk16-recordtype.md)

:::::: details
::: tabs

@tab 之前

手写 get/set equal hashcode toString
or lombok `@Data`

```java
@Data
class Point {
    private final int x;
    private final int y;
}
```

@tab JDK16

```java
record Point(int x, int y) {
    // Implicitly declared fields
    private final int x;
    private final int y;

    // Other implicit declarations elided ...

    // Implicitly declared canonical constructor
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

:::
::::::

::: warning

1. record 类型没有 build/set 方法，其值在 new 时就需要确定，后续无法修改
1. record 类型统一被 final 修复，无法被继承

:::

### JDK17

<https://openjdk.org/projects/jdk/17/>

#### JEP 406: Pattern Matching for switch (Preview)

### JDK18

<https://openjdk.org/projects/jdk/18/>

#### JEP 420: Pattern Matching for switch (Second Preview)

### JDK19

<https://openjdk.org/projects/jdk/19/>

#### JEP 405: Record Patterns (Preview)

#### JEP 427: Pattern Matching for switch (Third Preview)

### JDK20

<https://openjdk.org/projects/jdk/20/>

#### JEP 432: Record Patterns (Second Preview)

#### JEP 433: Pattern Matching for switch (Fourth Preview)

### JDK21

<https://openjdk.org/projects/jdk/21/>

#### JEP 440: Record Patterns

<https://openjdk.org/jeps/440>

支持 record 在模式匹配中解构（Destructuring）、嵌套解构

:::::: details
::: tabs

@tab JDK16

```java
record Point(int x, int y) {}

static void printSum(Object obj) {
    if (obj instanceof Point p) {
        int x = p.x();
        int y = p.y();
        System.out.println(x+y);
    }
}
```

@tab JDK21

```java
static void printSum(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println(x+y);
    }
}
```

嵌套

```java
static void printXCoordOfUpperLeftPointWithPatterns(Rectangle r) {
    if (r instanceof Rectangle(ColoredPoint(Point(var x, var y), var c), var lr)) {
        // 注意：进来的条件需要Rectangle、ColoredPoint、Point对象均非空
        System.out.println("Upper-left corner: " + x);
    }
}
```

💡上述写法在 `switch` 中同理。

注意：

+ 解构中的类型也是判断条件，~~如果确定无需作为条件，则建议写成 `var` 类型~~
+ 父类/子类的判断同 `instanceof` 惯例
+ 如果嵌套的解构中出现中间对象为空的情况，则不符合判断条件，也就不会继续解构了

问题：
1. 取的字段不能太深：如果需要的字段在比较深的地方，取出来需要把record整个结构翻出来，可能导致维护问题。
参考处理：[egison](https://github.com/egison/egison)

:::
::::::

#### JEP 441: Pattern Matching for switch

<https://openjdk.org/jeps/441>

:::::: details 匹配语法变更
::: tabs

@tab JDK21前

```java
String formatted = "unknown";
if (obj instanceof Integer i) {
    formatted = String.format("int %d", i);
} else if (obj instanceof Long l) {
    formatted = String.format("long %d", l);
} else if (obj instanceof Double d) {
    formatted = String.format("double %f", d);
} else if (obj instanceof String s) {
    formatted = String.format("String %s", s);
}
```

@tab JDK21

```java
var formatted = switch (obj) {
    case Integer i -> String.format("int %d", i);
    case Long l    -> String.format("long %d", l);
    case Double d  -> String.format("double %f", d);
    case String s  -> String.format("String %s", s);
    default        -> "unknown"; // obj.toString();
};
```

卫语句（guard code/guard clause：额外的检查条件）

```java
// JDK19前，用 && 后加条件
// JDK19以后，用 when 后加条件
var formatted = switch (obj) {
    case Integer i  when i > 1-> String.format("int %d > 1", i);
    case Integer i -> String.format("int %d", i);
    case Long l    -> String.format("long %d", l);
    case Double d  -> String.format("double %f", d);
    case String s  -> String.format("String %s", s);
    default        -> "unknown"; // obj.toString();
};
```

优点：

1. 减少 `obj instanceof` 的重复书写

限制：

1. 只能对 obj 进行模式匹配了 （好处：减少可能性，增加可读性）
1. 必须加上 `default` 关键字/`case Object` 匹配条件，以覆盖全部条件（todo 不需要返回值时，也要求写default关键字）
```java
Object o = null;
    switch (o) {
    case null -> {}
    default -> throw new IllegalStateException("Unexpected value: " + o);
};
```
1. 范围更精确的判断，需要放在前面，以获得更高的优先级（编译时也会检查）
    e.g.
    + `case Integer i when i > 1` 需要在 `case Integer i` 前
    + `case Person p` 需要在 `case Object o` 前

:::
::::::

:::::: details 处理`NullPointerException`情况
::: tabs

@tab 以前

```java
if (obj == null) {
    System.out.println("Oops!");
    return;
}
var formatted = switch (obj) {
    case Integer i  when i > 1-> String.format("int %d > 1", i);
    default        -> "unknown"; // obj.toString();
};
```

@tab JDK21

```java
var formatted = switch (obj) {
    case null -> { /* ignore */ } // 处理空指针问题
    case Integer i  when i > 1-> String.format("int %d > 1", i);
    default        -> "unknown"; // obj.toString();
};
```
也可以
```java
var formatted = switch (obj) {
    case Integer i  when i > 1-> String.format("int %d > 1", i);
    case null, default        -> "unknown"; // 处理空指针问题
};
```

:::
::::::

### JDK23

<https://openjdk.org/projects/jdk/23/>

#### JEP 455: Primitive Types in Patterns, instanceof, and switch (Preview)

允许在模式匹配中使用Java基本类型

```java
// 基本类型与instanceof
if (getPopulation() instanceof float pop) {
    ... pop ...
}
if (i instanceof byte b) {
    ... b ...
}
// 基本类型与switch
long v = ...;
switch (v) {
    case 1L              -> ...;
    case 10_000_000_000L -> ...;
    case long x          -> ... x ...;
}
float v = ...;
switch (v) {
    case 0f -> 5f;
    case float x when x == 1f -> 6f + x;
    case float x -> 7f + x;
}
boolean v = ...;
switch (v) {
    case true -> ...;
    case false -> ...;
    // Alternatively: case true, false -> ...;
}
```

### JDK24

<https://openjdk.org/projects/jdk/23/>

#### JEP 488: Primitive Types in Patterns, instanceof, and switch (Second Preview)

## checklist

1. switch表达式增强
支持模式匹配，代码更简洁高效。
1. instanceof类型测试模式
提供更灵活的类型判断方式。
1. 解构模式
简化数据提取，提高代码可读性。
1. when守卫条件
增强条件判断，提高逻辑严谨性。
1. 多重模式匹配 ？？？
支持多个模式匹配，优化处理逻辑。
1. 模式匹配异常处理
增强异常处理机制，提高程序稳定性。
1. 模式匹配与lambda结合
简化lambda模式，提高编码效率。

## 总结

在 JDK21 版本中，关键字 `instanceof` 和 `switch` 都支持了模式匹配。
同时，模式匹配中：

+ 允许通过 `when` 关键字添加卫（guard）语句
+ 允许对 `record` 类型进行解构（Destructuring）和嵌套解构

此外，关键字 `switch` 功能得到加强：

+ 允许使用lambda块编写处理方法，简化代码，同时提高可读性
+ 通过当行值/`yield`关键字方式，新增返回值功能，减少重复的赋值语句；
