---
title: clang 使用笔记
order: 11
---

Clang是LLVM项目的一个子项目，基于LLVM架构的C/C++/Object-C编译器前端。

特点：

+ 错误提示易于理解
+ 跨平台支持好
+ mysys2

相比于GCC：

+ 编译速度快
+ 内存占用少
+ 易于重用，如IDE集成
+ 诊断信息可读性强

## clangd

LSP（语言服务器协议）是由微软主导定制的一套跨**编译器/IDE的标准化协议**，旨在归一化编程语言工具（如代码补全、语法检查、转跳定义等）的开发与集成。

## vscode clangd 插件

参考： <https://yill-z.github.io/2025/01/01/clangd/>

```json title=".vscode/.settings.json"
{
  "C_Cpp.intelliSenseEngine": "disabled",
  "clangd.path": "clangd",
  "clangd.arguments": [
    "--pretty",// 输出的 JSON 文件更美观
    "--compile-commands-dir=${workspaceFolder}/",
    "--query-driver=${workspaceFolder}/../toolchains/gcc-chushi-12.2.0-2023.06-x86_64_aarch64-linux-gnu/bin/aarch64-linux-gnu-",//指定编译器路径
    "--log=verbose",// 让 Clangd 生成更详细的日志
    "--background-index",//后台分析并保存索引
    "--all-scopes-completion", // 全局补全(补全建议会给出在当前作用域不可见的索引,插入后自动补充作用域标识符),例如在main()中直接写cout,即使没有`#include <iostream>`,也会给出`std::cout`的建议,配合"--header-insertion=iwyu",还可自动插入缺失的头文件
    "--clang-tidy", // 启用 Clang-Tidy 以提供「静态检查」
    "--clang-tidy-checks=performance-*, bugprone-*, misc-*, google-*, modernize-*, readability-*, portability-*",
    "--completion-parse=auto", // 当 clangd 准备就绪时，用它来分析建议
    "--completion-style=detailed", // 建议风格：打包(重载函数只会给出一个建议);还可以设置为 detailed
    // 启用配置文件(YAML格式)
    "--enable-config",
    "--fallback-style=Webkit", // 默认格式化风格: 在没找到 .clang-format 文件时采用,可用的有 LLVM, Google, Chromium, Mozilla, Webkit, Microsoft, GNU
    "--function-arg-placeholders=true", // 补全函数时，将会给参数提供占位符，键入后按 Tab 可以切换到下一占位符，乃至函数末
    "--header-insertion-decorators", // 输入建议中，已包含头文件的项与还未包含头文件的项会以圆点加以区分
    "--header-insertion=iwyu", // 插入建议时自动引入头文件 iwyu
    "--include-cleaner-stdlib", // 为标准库头文件启用清理功能(不成熟!!!)
      "--pch-storage=memory", // pch 优化的位置(Memory 或 Disk,前者会增加内存开销，但会提升性能)
      "--ranking-model=decision_forest", // 建议的排序方案：hueristics (启发式), decision_forest (决策树)
    "-j=12", // 同时开启的任务数量
  ],
  // 找不到编译数据库(compile_flags.json 文件)时使用的编译器选项,这样的缺陷是不能直接索引同一项目的不同文件,只能分析系统头文件、当前文件和被include的文件
  "clangd.fallbackFlags": [
    "-pedantic",
    "-Wall",
    "-Wextra",
    "-Wcast-align",
    "-Wdouble-promotion",
    "-Wformat=2",
    "-Wimplicit-fallthrough",
    "-Wmisleading-indentation",
    "-Wnon-virtual-dtor",
    "-Wnull-dereference",
    "-Wold-style-cast",
    "-Woverloaded-virtual",
    "-Wpedantic",
    "-Wshadow",
    "-Wunused",
    "-pthread",
    "-fuse-ld=lld",
    "-fsanitize=address",
    "-fsanitize=undefined",
    "-stdlib=libc++"
    //这里可以包含额外的头文件路径
  ],
  "clangd.checkUpdates": true, // 自动检测 clangd 更新
  "clangd.onConfigChanged": "restart", // 重启 clangd 时重载配置,具体方法: F1 + Fn 打开命令面板，然后搜索“clangd: restart"
  "clangd.serverCompletionRanking": true, // 借助网上的信息排序建议
  "clangd.detectExtensionConflicts": true, // 当其它拓展与 clangd 冲突时警告并建议禁用
}
```
