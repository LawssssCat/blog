---
title: Rust 语言通用概念
date: 2024-10-31
order: 1
---

todo

https://rustwiki.org/zh-CN/book/ch02-00-guessing-game-tutorial.html

## 文档

记录 Rust 相关的有用链接：

- Rust 官方文档中文翻译 —— <https://rustwiki.org/zh-CN/>

  - [Rust 程序设计语言（中文）](https://rustwiki.org/zh-CN/book/ch01-03-hello-cargo.html)
  - [Cargo 手册（中文）](https://rustwiki.org/zh-CN/cargo/)

- ? Rust 培训 —— <https://letsgetrusty.com/bootcamp/>

有用的视频：

- 配置 VSCode 的 rust 开发环境 —— <https://www.youtube.com/watch?v=ZhedgZtd8gw>

## 环境配置

### 一键配置：rustup

```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

### 手动配置

- rustc —— 编译器 (类 gcc)
- cargo —— 构建器、包管理器 (类 makefile)

```bash
cargo new hello_cargo # 创建项目
cargo new --vcs=git hello_cargo # 创建项目，创建版本管理器
cd hello_cargo

# 开发构建 + 运行
cargo build # 构建
./target/debug/hello_cargo # .\target\debug\hello_cargo

# 发布构建 （构建更慢、运行更快）
cargo build --release
./target/release/hello_cargo # .\target\release\hello_cargo

cargo run # 构建并运行

cargo check # 检查语法
```

### vscode

参考：

- https://www.bilibili.com/video/BV1A4mjYjE6C/

插件

- rust-analyzer —— 语法提示
- CodeLLDB —— 调试工具
- Even Better TOML —— 配置高亮
- Dependi —— 依赖版本自动检索
- error lens —— 提示高亮
- todo tree —— 高亮并收集 `TODO/FIXME/...`
- ~~GitHub Copilot~~ —— 代码 AI 生成
