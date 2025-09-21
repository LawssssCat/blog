---
title: C/C++开发
order: 1
---

代码：
<RepoLink path="/code/demo-c-base/" />

开发环境配置：

+ <https://www.cnblogs.com/lenmom/p/9193388.html>

头文件：
C++ 中 `#include` 头文件有两种形式，一种是使用尖括号`<>`，一种是使用双引号`""`。

+ `#include`/`#define`/`#if` 为预处理语句 —— 宏，由预处理器处理
  + `#include` 用于在编译前将指定的头文件内容插入到源文件中
+ `#include "xxx.h"` 适合用于指定**项目内部或者开发者本地自定义的头文件**路径
  查找顺序：
  1. 当前文件所在的目录 —— 编译器首先查找与当前源文件同目录的头文件。
  1. 引用该文件的的文件夹 —— 如果该源文件被其他文件通过`#include`引用，编译器会在最先引用该文件的文件夹内查找。
  1. 指定的项目包路径 —— 通过编译器参数指定的路径，如`-I /path/to/include`选项。这个路径可以是多个目录，依赖于构建系统的设置。这些路径一般存有开发者指定的第三方库的头文件，例如Boost、Poco、QT等。
      + GCC/Clang: `g++ -I /path/to/headers main.cpp`
      + CMake: `include_directories()`/`target_include_directories()`
  1. 全局或系统路径 —— 这些路径通常是操作系统或者IDE预先配置好的系统路径，比如`/usr/include`（Linux系统）或者`C:\Program Files\`（Windows系统）。
+ `#include <xxx.h>` 仅在全局或系统或者指定的项目包路径中查找这些头文件，**忽略当前目录，且优先级顺序不同**。这是为了更高效地查找**标准库和第三方库**中的头文件。（当然也便于读代码时候区分是软件自身的代码还是三方依赖的代码）
  查找顺序：
  1. C++标准库路径 —— 即如`/usr/lib64/libstdc++.so.6`等路径。这些路径可能包含`<iostream>`/`<vector>`/`<string>`等标准库文件。
  1. 系统库路径 —— 在Linux系统中常见为`/usr/include`,`/usr/local/include`等路径；在Windows系统中可能是`C:\Program Files (x86)\Microsoft Visual Studio\`路径。
  1. 指定的项目包路径 —— 同上

>
> 总结：
>
> + `#include <...>` 优先查找标准库和外部库文件，用于引用系统文件和第三方库等开发者一般不可修改的头文件。
> + `#include "..."` 优先查找开发者本地文件，用于引用开发团队项目内部或者开发者本地定义的，开发者可以修改的头文件。
>
