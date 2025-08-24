---
title: Rust 语法
order: 33
---

checklist

::: tabs

@tab 标准系统

+ 练气
  + 概念
    + 所有权（了解）
    + 类型系统（了解）
  + 工程
    + cargo工作流（了解）
    + 常用三方库
    + 常见错误处理
    + 编码风格（了解）
    + 官方文档查阅（了解）
+ 筑基
  + 概念
    + 内存管理机制（所有权/生命周期管理）
    + 类型系统（深入）
    + 宏机制
    + Unsafe使用和安全抽象机制（了解）
    + 多线程并发安全机制
    + 异步并发机制（了解）
  + 工程
    + cargo工作流（深入）
    + 官方文档查阅（深入：core/std）
    + 熟练编写宏和FFi
    + 编码风格（深入）
    + 熟练编写单元测试/集成测试
    + 熟练编写多线程和异步并发
+ 接单
  + 概念
    + 类型系统（深刻理解其在代码架构设计的应用）
    + 异步并发机制（深入）
    + Unsafe使用和安全抽象机制（深入）
    + 理解编译过程，通过Mir理解代码行为
    + 高级特性，如：常量泛型/GAT等
  + 工程
    + 理解 Rust 编程范式并应用
    + 利用 Rust 语言类型系统和语言特性进行系统架构设计
    + 熟练使用第三方库，具有查阅源码识别代码质量的能力

@tab 嵌入式系统

嵌入式：指资源受限的裸机环境或者非通用标准的操作系统环境。在rust语言中用 `no_std` 环境来表示。

+ 练气
  + 概念
    + Rust编译器特性（了解）
    + 内存管理机制（所有权/生命周期管理）
    + Unsafe使用和安全抽象机制（了解）
  + 工程
    + `no_std`工具链（了解）
    + 使用Unsafe和FFi（了解）
    + 基于core定制自己的std库
+ 筑基
  + 概念
    + 略
  + 工程
    + 略
    + 熟练使用常用 `no_std` 第三方库
+ 接单
  + 概念
    + 略
  + 工程
    + 略

:::

<!-- more -->

> 语法参考：
>
> + Rust Documentation
>   + [Rust by Example](https://doc.rust-lang.org/stable/rust-by-example/index.html)
>   + [Rust by Example（中文版：通过例子学 Rust）](https://rustwiki.org/zh-CN/rust-by-example/index.html)
>
> 环境：
>
> + 在线 <https://play.rust-lang.org/>

## 注释&文档

<https://doc.rust-lang.org/stable/rust-by-example/hello/comment.html>

```rust
// 注释
/* 注释 */

//! 文档（放在文件头部） —— 支持markdown（支持内部链接）
/// 文档（放在方法上方）
```

## 打印

<https://doc.rust-lang.org/stable/rust-by-example/hello/print.html>

```rust
// 打印宏
// format!: write formatted text to String
// print!: same as format! but the text is printed to the console (io::stdout).
// println!: same as print! but a newline is appended.
// eprint!: same as print! but the text is printed to the standard error (io::stderr).
// eprintln!: same as eprint! but a newline is appended.

// 打印trait
fmt::Debug
fmt::Display
```

## 基础数据类型

指引：
<https://rustwiki.org/zh-CN/rust-by-example/primitives.html>

文档：
<https://rustwiki.org/zh-CN/std/#primitives>

基本数据类型特点：

+ 有暴露的接口方法
+ 有实现好的 trait —— 基本类型的核心特点
+ 有内存安全检查

```rust
// fn 声明函数
fn main() {
  // let 声明变量
  // i32 是数据类型
  let a: i32 = 1;
  // 如果没有指定数据类型，会推断
  let b = 2;
  // Rust 变量默认不可变。添加 mut 修饰的变量才可改变
  let mut y = 1;
  y = 2;

  // println!() 是宏，用于打印输出，并在末尾添加换行符
  println!("{}", a);

  // assert_eq!() 是宏，用于判断两个参数是否相等
  // 如果相等没有输出信息，如果不相等则报错
  // 更多： https://doc.rust-lang.org/std/index.html?search=assert#macros
  assert_eq!(2, b);
}
```

### 标量类型（scalar type）

+ 整型
  + 无符号整型
    + `u8` 1bytes
    + `u16` 2bytes
    + `u32` 3bytes
    + `u64` 4bytes
    + `u128` 5bytes
  + 有符号整型
    + `i8` 1bytes
    + `i16` 2bytes
    + `i32` 3bytes
    + `i64` 4bytes
    + `i128` 5bytes
+ 浮点型
  + `f32` 4bytes
  + `f64` 8bytes
+ 布尔型
  + `bool` 1bytes
+ 字符型
  + `char` 4bytes
  + `u8`

#### 标量列表

```rust
fn main() {
  // 整型
  let a: i32 = 42;
  let b: u32 = 42u32; // 无符号
  let c: i32 = -42i32; // 有符号
  let d: i32 = 98_222; // 十进制
  let e: i32 = 0xff; // 十六进制
  let f: i32 = 0o77; // 八进制
  let g: i32 = 0b1111_0000; // 二进制
  let h: i32 = b'A'; // 单字节字符
  // 浮点型
  let a: f64 = 1.23;
  let b: f32 = 1.23f32;
  // 字符
  let a: char = 'a'
  let b: u8 = b'a'; // 表示字符 a 的 ASCII 值
  // 布尔值
  let a:bool = true;
}
```

#### 标量声明方式

```rust
fn main() {
  // 变量可以给出类型说明。
  let logical: bool = true;

  let a_float: f64 = 1.0;  // 常规说明
  let an_integer   = 5i32; // 后缀说明

  // 否则会按默认方式决定类型。
  let default_float   = 3.0; // `f64`
  let default_integer = 7;   // `i32`

  // 类型也可根据上下文自动推断。
  let mut inferred_type = 12; // 根据下一行的赋值推断为 i64 类型
  inferred_type = 4294967296i64;

  // 可变的（mutable）变量，其值可以改变。
  let mut mutable = 12; // Mutable `i32`
  mutable = 21;

  // 报错！变量的类型并不能改变。
  mutable = true;

  // 但可以用遮蔽（shadow）来覆盖前面的变量。
  let mutable = true;
}
```

#### 标量运算

```rust
fn main() {
  // 整数相加
  println!("1 + 2 = {}", 1u32 + 2);

  // 整数相减
  println!("1 - 2 = {}", 1i32 - 2);
  // 试一试 ^ 尝试将 `1i32` 改为 `1u32`，体会为什么类型声明这么重要

  // 短路求值的布尔逻辑
  println!("true AND false is {}", true && false);
  println!("true OR false is {}", true || false);
  println!("NOT true is {}", !true);

  // 位运算
  println!("0011 AND 0101 is {:04b}", 0b0011u32 & 0b0101);
  println!("0011 OR 0101 is {:04b}", 0b0011u32 | 0b0101);
  println!("0011 XOR 0101 is {:04b}", 0b0011u32 ^ 0b0101);
  println!("1 << 5 is {}", 1u32 << 5);
  println!("0x80 >> 2 is 0x{:x}", 0x80u32 >> 2);

  // 使用下划线改善数字的可读性！
  println!("One million is written as {}", 1_000_000u32);
}
```

### 复合类型（compound type）

+ 数组（array）：如 `[1, 2, 3]` —— 必须相同类型
+ 元组（tuple）：如 `(1, true)` —— 可以包含不同类型

### 自定义类型

+ `struct`： 定义一个结构体（structure）
+ `enum`： 定义一个枚举类型（enumeration）

而常量（constant）可以通过 `const` 和 `static` 关键字来创建。

#### 结构体（structure，缩写成 struct）

https://rustwiki.org/zh-CN/rust-by-example/custom_types/structs.html

结构体（structure，缩写成 struct）有 3 种类型，使用 struct 关键字来创建：

+ 元组结构体（tuple struct），事实上就是具名元组而已。
+ 经典的 C 语言风格结构体（C struct）。
+ 单元结构体（unit struct），不带字段，在泛型中很有用。

```rust
// 元组结构体
struct Pair(i32, f32);

// C语言风格结构体
struct Point {
    x: f32,
    y: f32,
}

// 单元结构体
struct Unit;

// 结构体可以作为另一个结构体的字段
#[allow(dead_code)]
struct Rectangle {
    // 可以在空间中给定左上角和右下角在空间中的位置来指定矩形。
    top_left: Point,
    bottom_right: Point,
}
```

#### 枚举（enumeration）

https://rustwiki.org/zh-CN/rust-by-example/custom_types/enum.html

```rust
// 创建一个 `enum`（枚举）来对 web 事件分类。注意变量名和类型共同指定了 `enum`
// 取值的种类：`PageLoad` 不等于 `PageUnload`，`KeyPress(char)` 不等于
// `Paste(String)`。各个取值不同，互相独立。
enum WebEvent {
    // 一个 `enum` 可以是单元结构体（称为 `unit-like` 或 `unit`），
    PageLoad,
    PageUnload,
    // 或者一个元组结构体，
    KeyPress(char),
    Paste(String),
    // 或者一个普通的结构体。
    Click { x: i64, y: i64 }
}

// 此函数将一个 `WebEvent` enum 作为参数，无返回值。
fn inspect(event: WebEvent) {
    match event {
        WebEvent::PageLoad => println!("page loaded"),
        WebEvent::PageUnload => println!("page unloaded"),
        // 从 `enum` 里解构出 `c`。
        WebEvent::KeyPress(c) => println!("pressed '{}'.", c),
        WebEvent::Paste(s) => println!("pasted \"{}\".", s),
        // 把 `Click` 解构给 `x` and `y`。
        WebEvent::Click { x, y } => {
            println!("clicked at x={}, y={}.", x, y);
        },
    }
}

fn main() {
    let pressed = WebEvent::KeyPress('x');
    // `to_owned()` 从一个字符串切片中创建一个具有所有权的 `String`。
    let pasted  = WebEvent::Paste("my text".to_owned());
    let click   = WebEvent::Click { x: 20, y: 80 };
    let load    = WebEvent::PageLoad;
    let unload  = WebEvent::PageUnload;

    inspect(pressed);
    inspect(pasted);
    inspect(click);
    inspect(load);
    inspect(unload);
}
```

#### 常量

Rust 有两种常量，可以在任意作用域声明，包括全局作用域。它们都需要显式的类型声明：

+ `const`：不可改变的值（通常使用这种）。
+ `static`：具有 'static 生命周期的，可以是可变的变量（译注：须使用 static mut 关键字）。

```rust
// 全局变量是在所有其他作用域之外声明的。
static LANGUAGE: &'static str = "Rust";
const  THRESHOLD: i32 = 10;
```

## 类型转换

```rust
#![allow(overflowing_literals)]
fn main() {
  let decimal = 65.4321_f32;
  // 错误：不提供隐式转换
  let integer: u8 = decimal; // ERROR
  // 使用as显式转换
  let integer = decimal as u8; // 该转换会丢失数值精度
  let character = integer as char;
  assert_eq!(65, integer);
  assert_eq!('A', character);

  // 小范围的类型转换到大范围的类型，没有数据丢失
  assert_eq!(65, 65_i8 as i32);

  // 使用as转换如果发生溢出，从最低有效位开始保留8位，然后剩余位置，直到最高有效位都被抛弃
  // 1000 - 256 -256 - 256 = 232
  assert_eq!(232, 1000 as u8);

  // 1000 as u8 -> 232
  assert_eq!(232, 1000 as u8);
}
```
