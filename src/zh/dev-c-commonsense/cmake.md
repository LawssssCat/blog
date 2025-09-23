---
title: cmake 使用笔记
order: 20
---

CMake是一个跨平台的构建系统，它可以根据用户编写`CMakeLists.txt`文件生成适用于不同编译器和操作系统的构建文件。
如生成 Visual Studio 17 的 `.sln`、XCode 的 `.xcodeproj`、Ninja 的 `.ninja`、make 的 `Makefile`文件。
进而生成可执行文件、静态库、动态库等目标文件。

关键字：跨编译器、跨平台

::: info

关于cmake版本：

+ 在cmake的早期版本（2.xx）中是使用`directory-oriented`的方式来管理“属性”的传递性的。当你定义了一个属性，就意味着当前文件夹和子文件夹会使用这些属性。这使得你必须按照实际目录的方式来管理cmake的依赖关系。
+ 在cmake的新版本（2015年左右，称“modern cmake”）中使用`target-oriented`的方式来管理“属性”的传递性。对属性的传递性做了抽象，剥离了与文件实际目录的耦合。

本文不做说明则以新版本管理方式记录cmake的使用。
上述概念区别在后面有[章节](todo)分析。

:::

```bash
$ cmake --version
cmake version 3.31.6

CMake suite maintained and supported by Kitware (kitware.com/cmake).
```

<!-- more -->

## Getting Start

配置文件：
`CMakeLists.txt`

代码仓：
<RepoLink path="/code/code/demo-c-base/demo-03-cmake/wk/CmakeLists.txt" />

最简例子：

::: tabs

@tab 生成Makefile

```bash
# 生成Makefile
$ cmake -B build
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
# 生成目标文件
$ cmake --build build
gmake: Warning: File 'Makefile' has modification time 1.9 s in the future
gmake[1]: Warning: File 'CMakeFiles/Makefile2' has modification time 1.8 s in the future
gmake[2]: Warning: File 'CMakeFiles/hello.dir/build.make' has modification time 1.8 s in the future
gmake[2]: warning:  Clock skew detected.  Your build may be incomplete.
gmake[2]: Warning: File 'CMakeFiles/hello.dir/build.make' has modification time 1.7 s in the future
[ 50%] Building CXX object CMakeFiles/hello.dir/hello.cc.o
[100%] Linking CXX executable hello
gmake[2]: warning:  Clock skew detected.  Your build may be incomplete.
[100%] Built target hello
gmake[1]: warning:  Clock skew detected.  Your build may be incomplete.
gmake: warning:  Clock skew detected.  Your build may be incomplete.
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

常用参数：

参数名 | 作用
--- | ---
`--target <target>` | 执行目标，如Makefile中的目标
`--config <profile>` | todo

## 基础概念

### 生成器（Generator）

生成如 makefile、VS2017 等不同构建工具需要的文件的生成器。

```bash
# 查看生成器
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

### 属性

参考：

+ 彻底弄懂cmake中的 INTEFACE 可见性/传递性 问题 <https://chunleili.github.io/cmake/understanding-INTERFACE>

从 modern cmake(>=3.0) 开始，使用的范式从 director-oriented 转换到了 target-oriented。 这其中最重要的有三个概念：

1. **target** —— 编译的目标，一般就三种：
    + 静态库: 使用`add_library()`
    + 动态库: 使用`add_library()` 指定SHARED关键字
    + 可执行文件: 使用`add_executable()`
1. **properties** —— “编译目标”的“属性”
    + 编译标志：使用 `target_compile_option`
      + `COMPILE_DEFINITIONS`
    + 预处理宏标志：使用 `target_compile_definitions`
      + `COMPILE_OPTIONS`
    + 头文件目录：使用 `target_include_directories`
      + `INCLUDE_DIRECTORIES`
      + `SOURCES`
    + 链接库：使用 `target_link_libraries`
      + `LINK_LIBRARIES`
      + `INTERFACE_LINK_LIBRARIES`
      + `STATIC_LIBRARY_OPTIONS`
    + 链接标志：使用 `target_link_options`
      + `LINK_OPTIONS`
      + `STATIC_LIBRARY_FLAGS`
1. **可见性** —— “编译目标属性”的在不同“编译目标”之间的“传递性”
    + PRIVATE = 依赖自己使用，但不向上传递 （实现：`INCLUDE_DIRECTORIES`）
    + PUBLIC = 依赖自己使用，且向上传递 （实现：`INCLUDE_DIRECTORIES` + 下游可见）
    + INTERFACE = 依赖自己不使用，但向上传递 （实现：`INTERFACE_INCLUDE_DIRECTORIES` + 下游可见）

      ```makefile
      # 使用Interface的例子：
      add_library(Eigen INTERFACE)   # Engine是header-only库，只有头文件和依赖关系，没有代码实现。
      target_sources(Eigen INTERFACE # 由于自身没有代码实现，需要使用的依赖文件，但是需要将依赖向下传递，因此使用Interface可见性。
        FILE_SET HEADERS
          BASE_DIRS src
          FILES src/eigen.h src/vector.h src/matrix.h
      )
      add_executable(exe1 exe1.cpp)
      target_link_libraries(exe1 Eigen)
      ```
