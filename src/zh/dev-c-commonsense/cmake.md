---
title: cmake 使用笔记
order: 20
---

CMake可以读入工程源码文件、解析配置、输出编译所需的makefile文件。
从而解决逐句编写/修改makefile的业务/跨平台繁琐问题，在复杂项目中非常有用。

是的：
**CMake支持跨平台**！

```bash
$ cmake --version
cmake version 3.31.6

CMake suite maintained and supported by Kitware (kitware.com/cmake).
```

<!-- more -->

## Getting Start

配置文件：
`CMakeLists.txt`

最简例子：

::: tabs

@tab 生成Makefile

```bash
$ cmake .
-- The CXX compiler identification is GNU 15.2.1
-- Detecting CXX compiler ABI info
-- Detecting CXX compiler ABI info - failed
-- Check for working CXX compiler: /usr/bin/c++
-- Check for working CXX compiler: /usr/bin/c++ - works
-- Detecting CXX compile features
-- Detecting CXX compile features - done
-- Configuring done (1.9s)
-- Generating done (0.1s)
-- Build files have been written to: /home/vagrant/wk2
```

@tab CMake文件

```txt title="CMakeLists.txt"
# 最小要求CMake版本
# cmake_minimum_required(VERSION major.minor[.patch])
cmake_minimum_required(VERSION 3.10)

# 项目信息
# project(项目名
#   [VERSION 版本号]
#   [LANGUAGES 编程语言 ...] # 不填写，默认 LANGUAGES C CXX
# )
project(Helloworld VERSION 0.1 LANGUAGES CXX)

# add_executable(目标名称 源文件1 [源文件2...])
add_executable(helloworld hello.cpp)
```

@tab 源文件

```cpp
#include <iostream>

int main() {
  std::cout << "Hello, World!" << std::endl;
  return 0;
}
```

:::

## 生成器（Generator）

```bash
$ cmake -G
CMake Error: No generator specified for -G

Generators
  Green Hills MULTI            = Generates Green Hills MULTI files
                                 (experimental, work-in-progress).
* Unix Makefiles               = Generates standard UNIX makefiles. # --- 💡前面带*号，默认生成器
  Ninja                        = Generates build.ninja files.
  Ninja Multi-Config           = Generates build-<Config>.ninja files.
  Watcom WMake                 = Generates Watcom WMake makefiles.
  CodeBlocks - Ninja           = Generates CodeBlocks project files
                                 (deprecated).
  CodeBlocks - Unix Makefiles  = Generates CodeBlocks project files
                                 (deprecated).
  CodeLite - Ninja             = Generates CodeLite project files
                                 (deprecated).
  CodeLite - Unix Makefiles    = Generates CodeLite project files
                                 (deprecated).
  Eclipse CDT4 - Ninja         = Generates Eclipse CDT 4.0 project files
                                 (deprecated).
  Eclipse CDT4 - Unix Makefiles= Generates Eclipse CDT 4.0 project files
                                 (deprecated).
  Kate - Ninja                 = Generates Kate project files (deprecated).
  Kate - Ninja Multi-Config    = Generates Kate project files (deprecated).
  Kate - Unix Makefiles        = Generates Kate project files (deprecated).
  Sublime Text 2 - Ninja       = Generates Sublime Text 2 project files
                                 (deprecated).
  Sublime Text 2 - Unix Makefiles
                               = Generates Sublime Text 2 project files
                                 (deprecated).
```
