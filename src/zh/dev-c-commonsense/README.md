---
title: C/C++ 开发
order: 1
---

## 开发环境配置

::: tip

入门C/C++环境搭建是最重要的，因为这关乎到开发过程中的关键词提示、调试、编译/交叉编译、用例测试/基线测试/覆盖测试、打包发布等各个流程的具体操作。
但是，搭建环境又不是最基础的，因为个人如下原因：

1. **选择多** —— 语言历史悠久，最初没有方便好用的ide工具，后面又有太多自成体系的好工具而没有一套统一的ide工具集。
1. **需求不一样** —— 不同阶段不同场景对开发环境需求不一样，阶段不同：有的只需要能编译、有的还需要调试、有的需要测试打包发布甚至自动化；开发环境需求不同：做CUDA开发、做Qt开发、做STM32开发、做Web开发、等等。
1. **前置知识多** —— 要真正了解开发环境全貌，可能需要对编译原理、cmake、gtest、llvm等有住够的了解。

基于这些原因，很多指导是零散的、自相矛盾的、易出问题的。

一般情况下，我们希望直接处理业务问题。
因此本人在经历各种折磨后，决定把用过觉得顺手的或者目前正在使用的开发环境搭建流程进行记录，希望需要时候能起作用。

:::

开发系统

+ Linux
  + 直接在主系统开发 —— ❗不推荐，因为编译产物可能会污染到主系统
+ Windows
  + 直接在主系统开发 —— 只是ide把东西隐藏了，实际还会用下面的技术
  + 切换Linux环境：模拟
    + MinGW
  + 切换Linux环境：容器
    + WSL
  + 切换Linux环境：虚拟机
    + vmware
    + vagrant
+ macos

开发工具

+ Jetbrain Clion —— JetBrains公司提供的（著名产品Java的Idea），目前免费（支持ja-netfilter），产品化无需太多配置
  + 工具链：vcpkg + cmake + gcc/clang,llvm + gdb/lldb,gtest
    + 适合：Idea过来，且了解c/c++工具链各工具作用
    + 插件：
      + 中文语言包
      + clang-format
    + todo 指导 <https://www.bilibili.com/video/BV18R4y127UV>
  + 工具链：xx + qt
    + 适合：QT开发
    + todo <https://www.bilibili.com/video/BV18q4y1i7kV/>
  + 工具链：xx
    + 适合：STM32开发
    + todo <https://zhuanlan.zhihu.com/p/145801160>

+ VSCode
  + 工具链：vcpkg + cmake + gcc/clang,llvm + lldb,gtest
    + 适合：有跨平台构建需求，且了解c/c++工具链各工具作用
    + Demo：<RepoLink path="/code/demo-c-base/README.md" />
    + 插件
      + ~~C/C++ Extension Pack (ms-vscode.cpptools-extension-pack)~~ —— ❗别下载这个，我们用clang编译，不用gcc编译
      + [clangd (llvm-vs-code-extensions.vscode-clangd)](https://clangd.llvm.org/)
      + Clang-Format
      + CodeLLDB (vadimcn.vscode-lldb) —— 测试
      + [CMake Tools (ms-vscode.cmake-tools)](https://github.com/microsoft/vscode-cmake-tools/blob/main/docs/cmake-settings.md#command-substitution)
      + C++ TestMate (matepek.vscode-catch2-test-adapter) —— 处理 CMake Tools 插件提供用例清单的两个问题：1、用例清单无法跳转用例代码，巨麻烦；2、用例代码左侧无启动按钮，要到用例清单找到用例执行，麻烦
    + 测试：gtest

+ Visual Studio —— todo 未了解。听说有开发工具版本兼容问题，如vs2012项目到vs2017不能直接编译等问题

+ Eclipse IDE for C/C++ Developers —— 25年 Java 都不用 Eclipse ide 了...

## 文档

+ 语法
  + ~~C++教程 <https://www.runoob.com/cplusplus/cpp-useful-resources.html>~~

+ 接口
  + 中文
    + C/C++参考（API Reference Document） —— <https://www.apiref.com/cpp-zh/cpp/utility/functional/bind.html>
    + C/C++参考 —— <https://cppreference.cn/w/>
  + 英文
    + C/C++参考 —— <https://en.cppreference.com/w/cpp/header.html>
    + cstdlib —— <https://cplusplus.com/reference/cstdlib/>

## 编译&语法

<!-- todo
详解三大编译器：gcc、llvm 和 clang | <https://zhuanlan.zhihu.com/p/357803433>
-->

编译链：

+ [gcc](./gcc.md)
+ LLVM
  + [clang](./llvm/clang.md)

+ [makefile](./makefile.md)
+ [cmake](./cmake.md)
+ 包管理

  习惯：

  + 简单的用 FetchContent 管理
  + 复杂的用 vcpkg + find_packages 管理

  选项：

  + 源码下载、`./configure`、`make`、`make install` （推荐）
  + cmake `FetchContent`
  + xrepo
  + bazel
  + meson
  + ...

+ [MinGW](./mingw.md)
+ cygwin


## 调试

+ [gdb](./gdb.md)
+ CodeLLDB

## 三方件

通用

+ [glibc](./lib-glibc/README.md) —— C标准库

+ [perl](./lib-perl/README.md)
+ [openssl](./lib-openssl/README.md)

+ fmt —— 格式化库
+ zlib —— 压缩库

Web

+ drogon —— web服务器

测试

+ [gtest](./lib-gtest/README.md)
+ doctest
+ catch2 —— 也是一个测试框架

+ googlebench
+ nanobench —— 性能基线测试
