---
title: 实现SysY编译器
order: 1
---

参考：

+ Rust编译器开源社区 | 2025秋季Rust编译器训练营 | 导学阶段-Rust基础知识
+ Rust编译器开源社区 | 2025秋季Rust编译器训练营 | 基础阶段-编译器基本原理 —— https://opencamp.ai/rustcompiler/camp/2025fall/stage/2?tab=video
+ Rust编译器开源社区 | 2025秋季Rust编译器训练营 | 专业阶段-Rust编译器架构
+ 项目一：RISC-V从Tier2升级Tier1
+ 项目二：Rust编译器分布式构建和测试

```text
目标：
使用Rust语言实现SysY语言的编译器
+ 支持解析SysY源代码，进行词法分析、语法分析、语义分析、中间代码生成、中间代码优化等工作
输入：
Rust编译器的输入是一个包含SysY源代码的文件
输出：
Rust编译器解析SysY源代码后，生成语义等价的LLVM IR
+ IR执行不报错且输出与SysY源代码输出一致
```

::: info

SysY定义

+ SysY语言是编译系统设计赛中常用的编程语言。由C语言的一个子集扩展而成。
+ 每个SysY程序的源码存储在一个扩展名为sy的文件中
  + 该文件中有且仅有一个名为main的主函数定义，还可以包含若干全局变量声明、常量声明和其他函数定义
+ SysY语言支持int/float类型和元素为int/float类型且按行优先存储的多维数组类型
  + 其中int型整数位32位有符号数
  + float位32位单精度浮点数
  + const修饰符用于声明常量
+ SysY支持int和float之间的隐式类型，但是无显式的强制类型转化支持

:::
