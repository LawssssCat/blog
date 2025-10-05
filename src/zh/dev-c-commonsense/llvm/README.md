---
title: LLVM 使用笔记
order: 11
---

LLVM项目是模块化、可重用的编辑器以及工具链技术集合。

::: tabs

@tab 传统编译器架构

+ Frontend（前端） —— 词法分析、语法分析、语义分析、中间代码生成
+ Optimizer（优化） —— 中间代码优化
+ Backend（后端） —— 机器码生成

<!-- 图 start -->
@startuml
interface "Source Code" as SourceCode
node "Machine Code" as MachineCode

package "Compiler" {
  [Frontend]
  [Optimizer]
  [Backend]
}

SourceCode --> [Frontend]
[Frontend] -> [Optimizer]
[Optimizer] -> [Backend]
[Backend] --> MachineCode

@enduml
<!-- 图 end -->

@tab LLVM编译器架构

<!-- 图 start -->
@startuml
interface C
interface Fortran
interface Haskell

package "Compiler" {
  [Clang C/C++/ObjC Frontend] as CFrontend
  [llvm-gcc Frontend] as FortranFrontend
  [GHC Frontend] as HaskellFrontend
  [LLVM Optimizer] as Optimizer
  [X86 Backend] as x86Backend
  [ARM Backend] as armBackend
  [PowerPC Backend] as ppcBackend
}

node x86
node ARM
node PowerPC

C --> [CFrontend]
Fortran --> [FortranFrontend]
Haskell --> [HaskellFrontend]
[CFrontend] --> [Optimizer]
[FortranFrontend] --> [Optimizer]
[HaskellFrontend] --> [Optimizer]
[Optimizer] --> [x86Backend]
[Optimizer] --> [armBackend]
[Optimizer] --> [ppcBackend]
[x86Backend] --> x86
[armBackend] --> ARM
[ppcBackend] --> PowerPC

@enduml
<!-- 图 end -->

:::
