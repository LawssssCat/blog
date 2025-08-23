---
title: Rust 语法
order: 33
---

## 基础数据类型

知识点

+ 基本数据类型
+ 基础复合类型
+ 常量、变量声明

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

原生类型：

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
  + `char`
  + `u8`

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
