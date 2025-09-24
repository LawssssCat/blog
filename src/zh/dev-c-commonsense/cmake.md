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

参考：

+ <https://www.bilibili.com/video/BV1MXX3YQEia>

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

### 属性（Properties）

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

### 变量（Variables）

#### 普通变量

命令：

+ 定义变量值
  + `set(varName value... [PARENT_SCOPE])`
+ 取消变量值
  + `set(varName)` —— 注意：这种方式和`set(varName "")`有区别，参考“[缓存变量](#id-value-cache)”
  + `unset(varName)`

特点：

+ 所有变量值都是“字符窜”！
+ 区分大小写

规范：

+ 变量名建议只使用字母、数字、`_`、`-`
+ 不要用`CMAKE_`（cmake变量前缀）开头定义自己的变量

数据类型：
cmake中只有字符串一种数据类型，但是表现形式有字符串、数组两种。
其中数组是用`;`符号分割的字符串，本质上还是字符串。

数据类型 | 声明方式
--- | ---
字符串 | `set(myVar hello)` <br> `set(myVar "hello\;world")`
数组/列表 | `set(myVar hello world)` <br> `set(myVar hello;world)` <br> `set(myVar "hello;world")`

变量打印：

+ `${...}` —— 这种方式会递归解析内部变量名。 e.g. `message("myVar = ${myVar}")`、`set(myVar2 ${myVar}2)`、`message(hello ${myVar}) # hello xxx`
+ `[[...]]` —— 这种方式不会解析内部变量名。 e.g. `message([[myVar]])`、`message([=[myVar]=])`、`message([[hello ${myVar}]]) # 'hello ${myVar}'`

#### 环境变量（Env Variable）

只改变cmake文件内命令的环境变量，不会改变执行会话的环境变量

命令：

+ 定义环境变量
  + `set(ENV{varName} value)` —— e.g. `set(ENV{PATH} "$ENV{PATH}:/opt/myDir")`

#### 缓存变量（Cache Variable） {id=id-value-cache}

缓存变量会存放在CMakeCache.txt文件中

::: tip

对比普通变量和缓存变量：

+ 缓存变量优先级比较低。
如果同时定义了相同名称的普通变量、缓存变量，则使用普通变量。
+ 缓存变量的清空需使用 `set(myVar "")` 而非普通变量的 `unset(myVar)`
+ 作用域区别：
  + 缓存变量作用域是“全局”
  + 普通变量作用域是“函数”或“子目录” （具体区别在[作用域](#id-properties-scope)章节详细分析。）

规范：

应避免使用相同名称的普通变量和缓存变量。
可以通过命名规则加以区分：
如缓存变量可以使用全大写字母（e.g. `MY_CACHE_VARIABLE`）；
普通变量使用驼峰命名（e.g. `normalVariable`）

:::

命令：

+ 定义缓存变量

  + 命令行声明

  `cmake -D "MY_VAR:STRING=hello world" .`

  + 文档内部声明

  ```makefile
  set(
    varName
    value...
    CACHE
    type # 实际都是字符串，但在图形界面（cmake-gui）有区分：BOOL、FILEPATH、PATH、STRING、INTERNAL
    "helpstring"
    [FORCE] # 当false时，缓存中已有值，则新值不写入
  )
  ```

  e.g.
  `set(DEMO_IS_PARALLEL off CACHE BOOL "Is using parallel algorithm")`

  ::: info

  BOOL取值支持多种格式：

  + 真：true/yes/y/1 （及其大写）
  + 假：false/no/n/0

  另外，BOOL类型支持`option(optVal helpString [初始值])`方式定义

  :::

+ 删除缓存变量

  + 命令行声明

  `cmake -U "MY*" .`

### 流程控制

::: warning

需要注意的是BOOL值的判断：

+ 当取值为 1/非零数值/ON/YES/Y/TRUE （不区分大小写） 时，均判断为真
+ 当取值为 0/OFF/NO/N/FALSE/IGNORE/NOTFOUND/`*-NOTFOUND`/`""` （不区分大小写） 时，判断为假

规范：

更值得注意的是：（历史问题）当判断条件为字符串或变量时，判断结果会不一样

+ 字符串 —— 不为`"TRUE"`均为假
+ 变量值 —— 取值不为`FALSE`均为真

e.g.

```makefile
set(v "hello")
if(v)
  message("${v} is true") # here，原因：变量值，非空
else()
endif()
if(${v})
else()
  message("'${v}' is false") # here，原因：字符串，非TRUE
endif()
```

应对这种问题，建议人为规定统一用哪种写法。

:::

#### if/elseif/else/endif

略

e.g.

```makefile
if(expr)
  # 1
elseif(expr2)
  # 2
else()
  # 3
endif()
```

#### option

略

e.g.

```makefile
option(BUILD_MYLIB "构建MyLib目标")
if(BUILD_MYLIB)
  add_library(MyLib mylib.cpp) # here if "cmake . -DBUILD_MYLIB=on"
else()
  message("忽略构建MyLib目标")
endif()
```

#### foreach/IN/ITEMS/LISTS/ZIP_LISTS/RANGE

```makefile
foreach(<loop_var> <items>)
  <commands>
endforeach()
```

e.g.

IN ITEMS

```makefile
# foreach(v IN ITEMS a b c)
foreach(v a b c) # 简写
  message("v:${v}")
endforeach()
# 打印：
# v:a
# v:b
# v:c
```

IN LISTS

```makefile
set(list1 2 4 6 8)
set(list2 1 3 5 7)
foreach(v IN LISTS list1 list2)
  message("v=${v}")
endforeach()
```

IN ZIP_LISTS

```makefile
set(list1 2 4 6 8)
set(list2 1 3 5 7)
foreach(v IN ZIP_LISTS list1 list2)
  message("v:(${v_0},${v_1})")
endforeach()
# 打印
# v:(2,1)
# v:(4,3)
# v:(6,5)
# v:(8,7)
```

RANGE

```makefile
foreach(v RANGE 1 8 2) # start end step
  message("v=${v}")
endforeach()
```

#### while

```makefile
while(<condition>)
  <commands>
endwhile()
```

#### break/continue

略

#### 关键字

##### AND/OR/NOT

略

##### 比较运算符

数值 | 字符串 | 版本号 | 路径
--- | --- | --- | ---
LESS | STRLESS | VERSION_LESS | &nbsp;
GREATER | STRGREATER | VERSION_GREATER | &nbsp;
EQUAL | STREQUAL | VERSION_EQUAL | PATH_EQUAL
LESS_EQUAL | STRLESS_EQUAL | VERSION_LESS_EQUAL | &nbsp;
GREATER_EQUAL | STRGREATER_EQUAL | VERSION_GREATER_EQUAL | &nbsp;
&nbsp; | MATCHS | &nbsp; | &nbsp;

e.g.

```makefile
if("/path//to/myfile" PATH_EQUAL "/path/to/myfile")
  # here，原因：路径相同
else()
endif()

if("/path//to/myfile" STREQUAL "/path/to/myfile")
else()
  # here，原因：字符串内容不一样
endif()

# 正则
if("My phone number is 12345" MATCHES "My phone number is ([0-9]+)")
  # 注意，匹配上可以用CMAKE_MATCH_<N>调用（作用域：当前分支）
  message("Phone number:${CMAKE_MATCH_1}") # 输出： Phone number:12345
endif()
```

##### 文件操作

+ EXISTS
+ IS_DIRECTORY
+ IS_SYMLINK
+ IS_ABSOLUTE
+ IS_READABLE
+ IS_WRITABLE
+ IS_EXECUTABLE
+ IS_NEWER_THAN

##### 存在性测试

+ COMMAND 是否可调用
+ POLICY 是否存在策略
+ TARGET 是否存在目标
+ TEST 是否存在测试
+ DEFINED 是否定义变量
+ IN_LIST 是否在列表中

e.g.

```makefile
set(list1 a b c d e f g)
set(a "f")
if (a IN_LIST list1)
  message("a is in list1")
endif()
```

##### 预定义变量

```makefile
if(MSVC)
  message("build with MSVC")
elseif(MINGW)
  message("build with MINGW")
elseif(XCODE)
  message("build with XCODE")
else()
  message("build with other compiler")
endif()
```

下面语句常用于CMakeLists.txt文件开头

```makefile
if("${CMAKE_SOURCE_DIR}" STREQUAL "${CMAKE_BINARY_DIR}")
  message(FATAL_ERROR # 在打印提示信息后终止后面命令
"错误：禁止源码内编译。
    请为构建文件创建一个单独的目录。
")
endif()
```

### 函数（Function）

```makefile
function(function_name args...)
  # do something
endfunction()
```

#### 命名参数、未命名参数

关键字：

+ ARGC 参数数量
+ ARGV 参数列表
+ ARGN 未命名参数列表
+ ARGV`<N>` 位置N上的参数值

e.g.

```makefile
function(my_function1 a b)
  message("a:${a},b:${b}")  # a:1,b:2
  message("argc:${ARGC}")   # argc:5
  message("argv:${ARGV}")   # argv:1;2;3;4;5
  message("argv2:${ARGV2}") # argv2:3
  message("argn:${ARGN}")   # argn:3;4;5
endfunction()
my_function1(1 2 3 4 5)
```

#### 关键字参数

```makefile
function(my_function2 targetName)
  message("targetName:${targetName}") # targetName:myTarget
  # cmake_parse_arguments(
  #    <prefix>
  #    <options:选项关键字列表>
  #    <one_value_keywords:单值关键字列表>
  #    <multi_value_keywords:多值关键字列表>
  #    <args>...)
  cmake_parse_arguments(arg_myfunction2 "USE_MYLIB" "MYLIB_PATH" "SOURCES;INCLUDES" ${ARGN})
  message("USE_MYLIB:${arg_myfunction2_USE_MYLIB}")   # USE_MYLIB:FALSE
  message("MYLIB_PATH:${arg_myfunction2_MYLIB_PATH}") # MYLIB_PATH:/usr/local/lib
  message("SOURCES:${arg_myfunction2_SOURCES}")       # SOURCES:main.cpp
  message("INCLUDES:${arg_myfunction2_INCLUDES}")     # INCLUDES:include;include2
  # <prefix>_UNPARSED_ARGUMENTS       # 未解析的关键字变量
  # <prefix>_KEYWORDS_MISSING_VALUES  # 调用时，未提供值的关键字变量
endfunction()
my_function2(myTarget
  USE_MYLIB                   # 选项关键字（Option Keywords）：传入值则为TRUE
  MYLIB_PATH "/usr/local/lib" # 单值关键字：后面跟一个值
  SOURCES "main.cpp"
  INCLUDES "include" "include2" # 多值关键字：后面跟多个值
)
```

#### 返回值

有两种方式：

1、

```makefile
function(my_function3 returnValue)
  set(${returnValue} "hello world" PARENT_SCOPE)
endfunction()
my_function3(result)
message("result:${result}") # result:hello world2
```

2、

```makefile
function(my_function4 returnValue)
  set(${returnValue} "hello world2")
  return(PROPAGATE ${returnValue})
endfunction()
my_function4(result)
message("result:${result}") # result:hello world2
```

#### 内置函数

##### list命令

`list(操作关键字 <listvar> [其他参数...])`

e.g.

LENGTH：获取数组长度

```makefile
set(listVal a b c)
list(LENGTH listVar listLength)
message("listLength: ${listLength}") # listLength: 3
```

GET：获取指定位置

```makefile
set(listVar a b c)
list(GET listVar 0 firstItem)
message("firstItem: ${firstItem}") # firstItem: a
```

JOIN、APPEND

```makefile
set(listVar a b c)
list(APPEND listVar d)
list(JOIN listVar "-" outStr)
message("outStr: ${outStr}") # outStr: a-b-c-d
```

##### string命令

`string(操作关键字 <list> [其他参数...])`

e.g.

FIND

```makefile
string(FIND "interesting" "in" fIndex)
string(FIND "interesting" "in" rIndex REVERSE)
message("fIndex = ${fIndex}") # 0
message("rIndex = ${rIndex}") # 8
```

##### math命令

`math(EXPR <variable> "<expr>" [OUTPUT_FORMAT <format:HEXADECIMAL/DECIMAL>])`

运算符： `+-*/%|&^~.` `<<>>()`

e.g.

```makefile
set(myVar 1+2*6/2)
message("myVar = ${myVar}") # myVar = 1+2*6/2
math(EXPR a 1+2*6/2)
math(EXPR b ${a}*3)
message("a = ${a}") # a = 7
message("b = ${b}") # b = 21
```

##### file命令

`file(操作关键字 <var> [其他参数...])`

+ 读
  + READ
  + STRINGS
  + HASH
  + TIMESTAMP
+ 写
  + WRITE
  + TOUCH
  + GENERATE
  + GONFIGURE
+ 文件系统
  + GLOB —— 获取文件列表
  + MAKE_DIRECTORY
  + REMOVE
  + COPY
  + COPY_FILE
  + CHMOD
+ 路径转换
  + REAL_PATH
  + RELATIVE_PATH
+ 传输
  + DOWNLOAD
  + UPLOAD

e.g.

GLOB：列出目录文件 💡自动添加文件

```makefile
file(GLOB varFileList
  LIST_DIRECTORIES false
  CONFIGURE_DEPENDS RELATIVE ${CMAKE_SOURCE_DIR} "*.cpp")
message("varFileList: ${varFileList}") # varFileList: main.cpp;shape.cpp
add_executable(MyApp ${varFileList})
```

```makefile
file(GENERATE
  OUTPUT generatorExample.txt
  CONTENT "platform = $<PLATFORM_ID>"
  TARGET myApp)
```

### 宏（Macro）

```makefile
macro(myMacro arg...)
  # command
endmacro()
```

::: tip

宏和函数类似，但是函数有自己的栈（Stack），而宏相当于将内容插入到上下文。

宏和函数还有其他区别，如：

+ 宏的参数是值，函数的参数是变量，这在逻辑判断上会有分歧

    ```makefile
    function(my_function a) # 这里a是变量
      if(a) # 变量判断非""，结果为真
        message("true")
      endif()
    endfunction()
    macro(my_macro a) # 这里a是字符串"hello"
      if(a) # 字符串判断非"TRUE"，结果为假
      else()
        message("false")
      end()
    endmacro()
    my_function(hello) # true
    my_macro(hello) # false
    ```

+ 宏用return命令会导致调用者的上下文退出

简单来说：
优先使用函数，而不是使用宏。

:::

### 作用域（Scope） {id=id-properties-scope}

```makefile
# 默认范围
set(myVar cat)
block()
  set(myVar dog)
  message("${myVar}") # dog
endblock()
message("${myVar}") # cat

# PARENT_SCOPE
set(myVar cat)
block()
  set(myVar dog PARENT_SCOPE) # 修改外层变量值
  message("${myVar}") # cat
endblock()
message("${myVar}") # dog

# PROPAGATE —— 传递
set(myVar cat)
block(SCOPE_FOR VARIABLES PROPAGATE myVar) # 内部的修改可直接传递到外面
  set(myVar dog)
  message("${myVar}") # dog
endblock()
message("${myVar}") # dog
```

### 生成器表达式（Generator Expression）

格式：
`$<...>`

取值时机：
编译时（`cmake --build .`）才确定取值

```makefile
add_custom_target(MyTarget
  ALL
  COMMAND ${CMAKE_COMMAND} -E echo "platform = $<PLATFORM_ID>")
```

常见：

+ `$<PLATFORM_ID>` —— 生成的平台，如windows、linux、macos
+ `$<CXX_COMPILER_ID>` —— 所使用的编译器编号，如GNU、MSVC、
+ `$<CXX_COMPILER_VERSION>` —— 所使用的编译版本号

+ `$<condition:trueValue>` —— 判断
+ `$<IF:condition,trueValue,falseValue>` —— 三元运算符
+ `$<BOOL:value>` —— 将Y/off/no/...等转换为0/1
+ `$<AND:...>`
+ `$<OR:...>`
+ `$<NOT:value>`

e.g.

```makefile
set(myVar 0)
file(GENERATE OUTPUT generatorExample1.txt CONTENT "res = $<IF:${myVar},hello,bye>" TARGET myApp) # res = bye
```

```makefile
target_compile_options(MyApp
PRIVATE
  $<$<AND:$<CXX_COMPILER_ID:GNU>,$<CONFIG:Debug>>:-Og>)
  $<$<AND:$<CXX_COMPILER_ID:GNU>,$<CONFIG:Release>>:-O2>)
  $<$<AND:$<CXX_COMPILER_ID:MSVC>,$<CONFIG:Debug>>:/Od>)
  $<$<AND:$<CXX_COMPILER_ID:MSVC>,$<CONFIG:Release>>:/O2>)
```

## 案例

### 分层目录Demo

代码仓：
<RepoLink path="/code/code/demo-c-base/demo-03-cmake/wk2/CmakeLists.txt" />

包含：

+ [x] 分层目录组织方式
+ [ ] 定义接口库（只有接口，没有实现）
+ [ ] 配置和使用第三方库
+ [ ] 集成Google测试框架
+ [ ] 包的安装
+ [ ] 配置和使用自定义包
