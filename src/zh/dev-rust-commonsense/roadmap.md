---
title: Rust 资料
order: 66
---

## 在线工具

Playground：

- <https://play.rust-lang.org/?version=stable&mode=debug>
- <https://replit.com/languages/rust> （Replit，支持cargo和crate，功能比较丰富）
- <https://godbolt.org/> （Godbolt，支持Rust，可以查看编译后汇编，适合深入学习）

练习：

- Rust by example —— <https://doc.rust-lang.org/rust-by-example>
- Rustlings（Rust 知识检测） —— <https://github.com/rust-lang/rustlings>
- Rust Quiz（Rust 知识检测） —— <https://github.com/dtolnay/rust-quiz>

## 文档/资料

记录 Rust 相关的有用链接：

- Rust 语言之旅 —— <https://tourofrust.com/TOC_zh-cn.html>
- Rust 语言参考 【官方】 —— <https://doc.rust-lang.org/reference/>
- Rust The Book 【官方】 —— <https://doc.rust-lang.org/book/>
- Rust 官方文档中文翻译 —— <https://rustwiki.org/zh-CN/>

  - [通过例子学 Rust（中文）](https://rustwiki.org/zh-CN/rust-by-example) ⭐⭐⭐⭐⭐
  - [Rust 程序设计语言（中文）](https://rustwiki.org/zh-CN/book/ch01-03-hello-cargo.html) ⭐⭐⭐⭐⭐
  - [Rust 程序设计语言 std库文档（中文）](https://rustwiki.org/zh-CN/std/)
  - [Cargo 手册（中文）](https://rustwiki.org/zh-CN/cargo/)

- FLS（Rust 语言规范） —— <https://spec.ferrocene.dev/>

有用的视频：

- 配置 VSCode 的 rust 开发环境 —— <https://www.youtube.com/watch?v=ZhedgZtd8gw>

系统/编译原理：

todo 2025 秋季 Rust 编译器训练营 —— https://opencamp.ai/rustcompiler/camp/2025fall
todo 开源操作系统训练营

https://os.educg.net/
https://github.comm/learningos
https://github.com/rcore-os
https://github.com/arceos-org
https://github.com/kern-crates

## 介绍

官网：

- <https://www.rust-lang.org/>
- <https://github.com/rust-lang/rust>

口号：
“构建可靠且软件语言能力的语言”

路线/RFC流程/工作组：
<https://www.rust-lang.org/zh-CN/governance>

历史（发布版本、特性）：
<https://releases.rs>

## 设计哲学

面向用户（Rust语言使用者）

- 可靠性（Reliable） —— 如果编译成功，则可以工作
- 高性能（Performant） —— 高效率、小内存 for “零成本抽象” in 所有权、闭包、...
- 支持性（Supportive） —— 语言、工具和社区随时位用户提供帮助
- 生产力（Productive） —— 让工作事半功倍
- 透明性（Transparent） —— 让用户可以预测和控制底层细节
- 多样性（Versatile） —— 可以用 Rust 做任何事

面向社区（Rust语言开发者）

略

金发姑娘原则 —— 语言没有银弹，适合的才是最好的

## 项目

### 开发工具

- `mdbook` —— 文档生成（一般Rust项目在`./book`目录中存放该文档）
- `wasmtime` —— 目标是提供一个轻量级、高效且安全的运行时环境，使得Wasm应用程序能够在各种设备和平台上顺利运行

### 生态

#### 基础库

参考 [crates.io](https://crates.io/) 网站

- rand —— 随机数
- syn —— 宏相关
- libc —— 系统接口调用
- serde —— 各种格式序列化/反序列化框架

#### 跨平台客户端

##### tauri-apps/tauri

##### wishawa/async_ui

##### 飞书App客户端（部分开源）

AppFlowy-IO/AppFlowy

#### 游戏与艺术

##### bevyengine/bevy

面向数据架构（ECS）的游戏引擎

##### https://arewegameyet.rs/

##### googleforgames/quilkin

可通过udp代理隐藏ip地址

##### FyroxEngine/Fyrox

##### nannou-org/nannou

#### 操作系统

##### tockos

##### Theseus OS

https://www.theseus-os.com/

实验性质，希望系统组件可被安全替换

##### Rust for Linux

#### 物联网边缘计算

##### eclipse-zenoh/zenoh

##### bytecodealliance/wasmtime

#### 数据库

##### TiKV

##### https://surrealdb.com/

### 练手

- 猜数字 https://doc.rust-lang.org/book/ch02-00-guessing-game-tutorial.html
- lemmeknow —— 正则识别web项目，适合初学者的小项目 （todo 复刻）
