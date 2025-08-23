---
title: Rust 编译原理（入门）、项目结构
order: 99
---

## 语言结构

参考：

+ 标准库文档 —— <https://doc.rust-lang.org/stable/std/index.html>

::: tip

通过[Rust 源码仓库](https://github.com/rust-lang/rust)结构可以了解到：

```bash
compiler —— rust 编译器，已实现自构建
library —— 编译单元（crate）
  core —— 核心库，如基本类型
  std —— 标准库，在核心库基础上扩展通用功能 （嵌入式底层开发使用core库，其余场景一般用std库足够）
  alloc —— 内存分配相关
  ...
```

:::

## 编译过程

了解目的：理解 “宏（macros）”

```rust
// 构建步骤：
// 1、编译前端阶段（实现：RustC/gcc-rust(开发中，2022年)） —— 该阶段人类可读
//     + Token —— 处理宏
//     + AST（抽象语法树） —— 类型检查、...
//     + HIR（高级中间语言） —— 借用检查、代码优化/生成、...
//     + MIR（中级中间语言）（CFG，控制流图） —— （一般静态检查在该阶段进行，如使用Miri工具进行unsafe代码安全检查）
//     + LLVM IR
// 2、编译后端阶段（实现：LLVM(for release)/gcc(for release)/Cranelift(for debug)） —— 汇编
//     + LLVM

fn main() {
  println!("你好，🦀");
}
```

## 项目结构

### Cargo介绍

编译单元：
crate —— 编译单元，由编译项目中 `./Cargo.toml` 文件定义。

```toml
[package]
name = "rustc-main"
version = "0.0.0"
edition = "2021"
```

插件：

+ `cargo-clippy` —— 代码静态分析（lint）
+ `cargo-vet` —— 为开发者建立工作流，标记crate是否可信，保障供应链安全

### lemmeknow（简单小项目）

参考[lemmeknow](https://github.com/swanandx/lemmeknow)项目的最佳实践

```bash
src/
  data/       —— 处理数据功能
  identifier/ —— 处理匹配功能
  output/     —— 处理输出功能
  lib.rs      —— 对外接口，集成上述“功能”
  main.rs     —— cli入口
tests/ —— 测试代码，调用 src/lib.rs 进行测试
build.rs —— 构建脚本
Cargo.toml —— 项目 crate 定义
```

这种小项目，在项目配置中指定各目录的划分：

```toml
[package]
name = "lemmeknow"
version = "0.8.0"
edition = "2021"

# See more keys and their definitions at https://doc.rust-lang.org/cargo/reference/manifest.html

[lib]
name = "lemmeknow"
path = "src/lib.rs"
crate-type = ["lib"]

[features]
default = ["cli"]
cli = ["comfy-table", "clap"]

[[bin]]
name = "lemmeknow"
path = "src/main.rs"
required-features = ["cli"]
```

### rust本体（SDK项目）

参考[rust本体](https://github.com/rust-lang/rust)项目的最佳实践

```bash
compiler/ —— 编译器源码
  rustc/ —— 最小业务单元：编译器cli入口源码
    src/
      main.rs —— 编译器源码入口
    Cargo.toml
    build.rs
library/ —— 核心库
  core/ —— 核心库
  std/ —— 标准库
  alloc/
  ...
src/ —— 外围工具
  bootstrap/
  ci/
  ...
Cargo.toml
```

这种大项目，其 crate 在项目配置中使用 `workspace` 关键字管理：

```toml
[workspace]
members = [
  "compiler/rustc",
  "library/std",
  "library/test",
  ...
]
```

### wasmtime（SDK项目）

参考[wasmtime](https://github.com/bytecodealliance/wasmtime)项目的最佳实践

```bash
cranelift/ —— 主体，编译后端
crates/
  cache/     —— 提供缓存功能
  cranelift/ —— 提供主体对外暴露功能
  runtime/   —— 提供运行时功能
src/
  bin/
    wasmtime.ts —— 入口
  commands/ —— 其他cli命令
    compile.rs
    run.rs
    ...
scripts/ —— 构建脚本
tests/ —— 单元测试
fuzz/ —— 模糊测试
supply-chain/ —— 供应链说明
examples/
docs/ —— 说明文档
Cargo.toml
```

使用 `workspace` 关键字定义组件：

```toml
[workspace]
resolver = '2'
members = [
  "cranelift",
  "cranelift/egraph",
  "cranelift/isle/fuzz",
  ...
  "fuzz"
]
```

### Rust for Linux （混合项目）

参考[Rust-for-Linux](https://github.com/Rust-for-Linux/linux)项目的最佳实践

该项目主要是c项目，通过makefile进行统一编译。在这基础上增加rust代码实现的功能。

```bash
kernel/
rust/
  bindings/
  macros/
  kernel/
  helpers/
    clk.c
  ffi.rs
  Makefile
Makefile
```

### tauri （GUI项目）

参考[tauri](https://github.com/tauri-apps/tauri)项目的最佳实践

```bash
core/
  tauri-build/ —— 构建
  tauri-codegen/ —— 代码生成
  tauri-macros/
  tauri-runtime/ —— 运行时
  tauri-utils/
  tauri/
    script/
    src/
    test/
tools/ —— 跨平台工具
```

### slint （GUI项目）

参考[slint](https://github.com/slint-ui/slint)项目的最佳实践

```bash
internal/
  common/
  compiler/
  core/
    graphics/
api/ —— 对外(js/ts)暴露的接口
```
