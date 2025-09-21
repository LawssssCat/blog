---
title: Makefile 使用笔记
order: 11
---

Makefile是`make`工具使用，用于描述工程如何构建的文件，从而进行源码的批量编译。

基本格式：

```makefile
target : prerequisties
[tab键]command # ⚠️command前需要使用tab键，而非空格xN

# target： 目标文件，可以是ObjectFile、可执行文件、标签（Label，此概念将在“伪目标”中叙述）
# prerequistie： 要生成那个target所需要的文件或者目标
# command： 是make要执行的命令
```

基本执行流程：

+ `make`会在当前目录下找到一个名字叫`Makefile`或`makefile`的脚本文件
+ 如果找到，他会找脚本文件中第一个目标（target），把目标实现，即生成目标文件
  + 如果目标文件不存在，或者目标文件依赖的.o文件（prerequities）更新（即依赖文件修改时间比目标文件修改时间更新），则会执行目标（target）后面所定义的命令（command）来生成目标文件
  + 如果目标文件依赖的.o文件（prerequities）不存在，则make会找到目标为该.o文件的规则，生成该.o文件，然后重新生成目标文件

::: tabs

@tab 构建命令

```bash
$ make # 构建
g++ -c -g -o hello.o hello.cc
g++ hello.o -o hello.out
chmod +x hello.out
$ ./hello.out
Hello, VS Code!
$ make clean # 清理
rm -f *.o
```

@tab Makefile

<!-- markdownlint-disable MD010 -->
```makefile
hello:hello.o # 依赖hello.o文件，所以先执行它的命令
	g++ hello.o -o hello.out
	chmod +x hello.out
hello.o:hello.cc
	g++ -c -g -o hello.o hello.cc
clean:
	rm -f *.o
```
<!-- markdownlint-enable MD010 -->

:::

<!-- more -->

文档：

+ GNU Make 官网网站： <https://www.gnu.org/software/make/>
+ GNU Make 官方文档地址： <https://www.gnu.org/software/make/manual/>
+ Makefile Tutorial: <https://makefiletutorial.com/>

参考：

+ [ ] B站|无限十三年|从零开始学Makefile - <https://www.bilibili.com/video/BV1Bv4y1J7QT> todo 看完+笔记

## 基本概念

### 伪目标

“伪目标” 不是一个具体的文件，而是一个标签。

💡这个“伪目标”的取名不要和文件名重名，否则可能会不执行命令
为了避免和文件重名，可以添加特殊标记 `.PHONY` 来显式的指定一个目标为一个“伪目标”。

e.g.

```makefile
clean :
  @rm -rfv objs

debug :
  @echo hello

.PHONY: clean
```

### 变量

变量在声明时需要给予初始值，而且在使用时需要使用 `${variable}`/`$(variable)` 格式。

e.g.

```makefile
cpp := src/main.cpp
obj := objs/main.o

$(obj) : ${cpp}
  @g++ -c $(cpp) -o $(obj)

compile : $(obj)
```

#### 预定义变量/自动变量

变量 | 说明
--- | ---
`$@` | 目标（target）的完整名称
`$*` | 目标（target）的主干部分（即：不包括后缀）
`$%` | 如果目标不是归档文件，则为空；<br>如果目标是归档文件成员，则为对应的成员文件名
`$<` | 第一个依赖文件（prerequisties）的名称
`$^` | 所有的依赖文件（prerequisties）。用空格分开，不包含重复的依赖文件
`$^` | 同 `$^` 但包含重复的文件名
`$?` | 依赖中修改时间晚于目标修改时间的所有文件名。用空格分开
`$|` | 所有order-only依赖文件名

e.g.

```makefile
cpp := src/main.cpp
obj := objs/main.o

$(obj) : ${cpp}
  @g++ -c $^ -o $@

compile : $(obj)

clean :
  @rm -rfv $(obj)

.PHONY : compile clean
```

```bash
$ make compile 
$ make clean 
removed 'objs/main.o'
```

#### 环境变量

变量 | 说明
--- | ---
`MAKE` | 值："make"
`CURDIR` | 项目根路径
`MAKECMDGOALS` | 命令行中输入的目标值

### 隐式变量名

业界约定俗成的规则，如什么场景的变量一般用什么变量名。
**但具体作用完全由makefile文件内容决定**。

变量名 | 说明
--- | ---
`MAKEFLAGS` | `make -f1 -f2 ....` 中的 `-f1 -f2 -f3 ...`
`CC` | 指定C编译程序 <br> Program for compiling C programs; default gcc
`CXX` | 指定C++编译程序 <br> Program for  compiling C++ programs; default g++
`CFLAGS` | 传递给C编译程序的额外参数 <br> Extra flags to give to the C compiler
`CXXFLAGS` | 传递给C++编译程序的额外参数 <br> Extra flags to give to the C++ compiler
`CPPFLAGS` | 传递给C处理器的额外参数 <br> Extra flags to give to the C preprocessor
`LDFLAGS` | 传递给链接器的额外参数 <br> Extra flags to give to compiler when they are supposed to invoke the linker

#### 变量范围

Makefile中的变量一般时全局变量，也就是说定义后可以在Makefile中的任意位置使用。
但也可以将变量指定在某个目标范围内。

```makefile
target ... : variable-assignment
target ... : prerequisites
  recipes
  ...
```

e.g.

```makefile
var1 = Global Var

%.c: var2 = Some Target Var # 全部 .c 目标能访问

.PHONY: test.c

test.c: var3 = Specific Target Var # 只有该目标能访问
test.c:
  @echo $(var1)
  @echo $(var2)
  @echo $(var3)
```

### 常用符号

#### `=` 链接

+ 链接赋值运算符
+ 用于将右边的值分配给左边的变量
+ **如果后面的语句重新定义了该变量，则将使用新的值**

```makefile
HOST_ARCH   = aarch64
TARGET_ARCH = $(HOST_ARCH)

# 改变变量值
HOST_ARCH   = amd64

debug:
  @echo $(TARGET_ARCH) # amd64
```

#### `:=` 赋值

+ 立即赋值运算
+ 用于在定义变量时立即求值
+ 该值在定义后不再更改
+ 即使在后面的语句中重新定义了该变量

```makefile
HOST_ARCH   := aarch64
TARGET_ARCH := $(HOST_ARCH)

# 改变变量值
HOST_ARCH   := amd64

debug:
  @echo $(TARGET_ARCH) # aarch64
```

#### `?=` 默认

+ 默认赋值运算符
+ 如果该变量已经定义，则不进行任何操作
+ 如果该变量尚未定义，则求值并分配

```makefile
HOST_ARCH    = aarch64
HOST_ARCH   ?= amd64

debug:
  @echo $(HOST_ARCH) # aarch64
```

#### `+=` 累加

```makefile
CXXFLAGS := -m64 -fPIC -g -O0 -std=c++11 -w -fopenmp
CXXFLAGS += $(include_paths)
```

#### `\` 续行

```makefile
LDLIBS := cudart opencv_core \
          gomp nvinfer protobuf cudnn pthread \
          cublas nvcaffe_parser nvinfer_plugin
```

### 常用函数

语法： `$(fn arguments)` or `${fn arguments}`

+ `fn` 函数名
+ `arguments` 函数参数
  + 函数名与参数间以空格 ` ` 分割
  + 参数间以逗号 `,` 分割

#### shell

```makefile
$(shell <command> <arguments>)

# 功能： 调用 shell 命令
# 返回： 函数返回 shell 命令执行结果
```

e.g.

```makefile
cpp_srcs := $(shell find src -name "*.cpp")
```

#### subst

```makefile
$(subst <from>,<to>,<text>)

# 功能： 字符串替换函数。
#       把 <text> 中的 <from> 替换成 <to>
# 返回： 替换结果
```

```makefile
cpp_srcs := $(shell find src -name "*.cpp")
cpp_objs := $(subst src/,objs/,$(cpp_srcs))
```

#### patsubst

```makefile
$(patsubst <pattern>,<replacement>,<text>)

# 功能： 模式字符串替换函数。 
#       从 <text> 中 <pattern> 替换成 <replacement> 
#       支持通配符% —— 表示任意长度的字符串
```

```makefile
cpp_srcs := $(shell find src -name "*.cpp")
cpp_objs := $(patsubst src/%.cpp,objs/%.o,$(cpp_srcs))
```

#### foreach

```makefile
$(foreach <var>,<list>,<text>)

# 功能： 循环函数。
#        把字符串 <list> 中的元素逐一去除，执行 <text> 包含的表达式
# 返回： <text> 所返回的每个字符串组成的字符串（以空格分割）

# 伪代码（python）
# res = ""
# for var in list:
#   res = res + " " + text + var
```

```makefile
library_paths := /datav/shared/100_du/03.08/lean/protobuf-3.11.4/lib \
                 /usr/local/cuda-10.1/lib64

library_paths := $(foreach item,$(library_paths),-L$(item))

# 结果：
# g++ *.cpp -o main -L/datav/shared/100_du/03.08/lean/protobuf-3.11.4/lib -L/usr/local/cuda-10.1/lib64 -L...
```

同等效果

```makefile
I_flag := $(I_flag:%=-I%)
```

#### dir

```makefile
$(dir <names...>)

# 功能： 取目录函数。
#       从文件名序列中取出目录部分。
#       目录部分是指最后一个反斜杠 "/" 之前的部分
#       如果没有反斜杠，则返回 "./"
# 返回： 返回文件名序列的目录部分
```

```makefile
$(dir src/foo.c hacks) # 返回值 "src/ ./"
```

```makefile
cpp_srcs := $(shell find src -name "*.cpp")
cpp_objs := $(patsubst src/%.cpp,objs/%.o,$(cpp_srcs))

objs/%.o : src/%.cpp
  @mkdir -p $(dir $@) # 创建目录
  @g++ -c $^ -o $@

compile : $(cpp_objs)

debug :
  @echo $(cpp_srcs)
  @echo $(cpp_objs)

.PHONY : debug compile
```

#### notdir

```makefile
$(notdir <names...>)
```

```makefile
libs := $(notdir $(shell find /usr/lib -name lib*))
```

#### filter/filter-out

```makefile
$(filter <names...>) # 包含
$(filter-out <names...>) # 排除
```

```makefile
libs    := $(notdir $(shell find /usr/lib -name lib*))
a_libs  := $(filter %.a,$(libs))
so_libs := $(filter %.so,$(libs))
```

#### basename

```makefile
$(basename <names...>)
```

```makefile
libs    := $(notdir $(shell find /usr/lib -name lib*))
a_libs  := $(subst lib,$(basename $(filter %.a,$(libs))))
so_libs := $(subst lib,$(basename $(filter %.so,$(libs))))
```

#### origin

返回变量出处

```makefile
ifeq ("$(origin M)", "command line")
  KBUILD_EXTMOD := $(M)
endif

# command line
# file
```

#### if

```makefile
#if 函数的语法是:
#$(if <condition>,<then-part> )
#或
#$(if <condition>,<then-part>,<else-part> )
#<condition>参数是if的表达式，如果其返回的为非空字符串，那么这个表达式就相当于返回真，于是，<then-part>会被计算，否则<else-part>会被计算
#
#if函数的返回值是，
#     如果<condition>为真（非空字符串），那个<then-part>会是整个函数的返回值，
#     如果<condition>为假（空字符串），那么<else-part>会是整个函数的返回值，此时如果<else-part>没有被定义，那么，整个函数返回空字串。

SRC_DIR := src

#if函数---设置默认值
#如果变量SRC_DIR的值不为空,则将SRC_DIR指定的目录作为SUBDIR子目录;否则将/home/src作为子目录
SUBDIR += $(if $(SRC_DIR),$(SRC_DIR),/home/src)

all:
    @echo $(SUBDIR)

# 例子：
KBUILD_OUTPUT := $(shell cd $(KBUILD_OUTPUT) && /bin/pwd)
$(if $(KBUILD_OUTPUT),, \
     $(error output directory "$(saved-output)" does not exist))
```

### 逻辑判断

是否定义

```makefile
ifdef V
  ifeq ("$(origin V)", "command line")
    KBUILD_VERBOSE = $(V)
  endif
endif
ifndef KBUILD_VERBOSE
  KBUILD_VERBOSE = 0
endif
```

是否相等

<!-- markdownlint-disable MD010 -->
```makefile
defconfig: $(obj)/conf
ifeq ($(KBUILD_DEFCONFIG),)
	$< -d Config.in
else
	@echo *** Default configuration is based on '$(KBUILD_DEFCONFIG)'
	$(Q)$< -D $(KBUILD_DEFCONFIG) Config.in
endif
	$(MTIME_IS_COARSE) && sleep 1
```
<!-- markdownlint-enable MD010 -->

## kbuild/kconfig

todo

Menuconfig配置原理：
在Linux里面我们所看到的menuconfig界面是通过配置内核顶层的Kconfig产生的，而当输入make menuconfig命令的时候系统会读取Makefile来解析Kconfig。

## 例子

### 例子：编译参数链接/问题：解决缺少头文件问题

代码：
<RepoLink path="/code/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-fail-01-header" />

::: tabs

@tab 错误：缺少头文件路径参数

```bash
$ make # 缺失头文件问题
src/add.cpp:1:10: fatal error: add.hpp: No such file or directory
    1 | #include <add.hpp>
      |          ^~~~~~~~~
compilation terminated.
make: *** [Makefile:6: objs/add.o] Error 1
```

```java
<!-- @include: @project/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-fail-01-header -->
```

@tab 正确

```makefile
<!-- @include: @project/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile -->
```

:::

### 例子：静态库编译

代码：
<RepoLink path="/code/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-staticlib" />

```makefile
<!-- @include: @project/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-staticlib -->
```

### 例子：动态库编译

代码：
<RepoLink path="/code/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-dynamiclib" />

```makefile
<!-- @include: @project/code/demo-c-base/demo-02-makefile/demo-make-03/Makefile-dynamiclib -->
```

### 例子：嵌套编译

大项目中会分出多个小项目，这时需要使用嵌套（递归）make构建。

具体做法为：

1. 每个子项目都写一个Makefile
1. 写一个总的Makefile调用各子项目Makefile

e.g.

将main.cpp外的其他cpp存为一个子项目，编译为一个库文件。然后将main.cpp作为另一个子项目，编译为.o文件。最后再链接库文件成可执行文件。

todo 完成例子
