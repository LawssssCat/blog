---
title: GCC使用笔记
---

GCC（GNU Compiler Collection，GNU编译程序集合）是GNU项目的一个开放源码产品，用于完成C语言代码的编译。如Python就是由C语言开发，由GNU编译程序编译的！

GCC官网地址： <https://gcc.gnu.org/> \
GCC官方文档： <https://gcc.gnu.org/onlinedocs/>

<!-- more -->

参考：

+ [x] B站|李呵欠|GNU Makefile编译C/C++教程（Linux系统、VSCODE） - <https://www.bilibili.com/video/BV1EM41177s1/><br> 配套文档 - <https://github.com/WohimLee/GNC-Tutorial>

## 基础概念

### 常见文件、软件

项目常见组成部分：

模块 | 说明 | 备注
--- | --- | ---
`configure` | GCC源码树根目录中的一个脚本，用于设置配置值和创建GCC编译程序必须的make程序文件。 | &nbsp;
`gcc` | 该驱动程序等同于执行编译程序和链接程序以产生需要的输出。 | 只能编译c语言
`c++` | gcc的一个版本，默认语言为C++，而且在链接的时候自动包含标准C++库，这和g++一样。 | &nbsp;
`g++` | gcc的一个版本，默认语言为G++，而且在链接的时候自动包含标准C++库，这和c++一样。 | 向下兼容，同时能编译C/C++语言 <br> （<span style="background:yellow">一般选择此作为编译工具</span>）
`libgcc` | 该库包含的例程段被作为编译程序的一部分，是因为它们可以被链接到实际的可执行程序中。它们是例程，链接到可执行程序，来执行基本的任务，例如浮点运算。这些库中的例程通常都是平台相关的。 | &nbsp;
`libstdc++` | 运行时库，包括定义为标准语言一部分的所有的C++类和函数。 | &nbsp;

编译常见软件：

工具 | 说明 | 备注
--- | --- | ---
`ar` | 这是一个程序，可通过文档添加、删除和析取文件来维护库文件。通常使用该文件是为了创建和管理链接程序使用的目标库文档。该程序是binutils包的一部分。 | 编译静态库是用到
`as` | GNU汇编器。实际上它是一族汇编器，因为它可以被编译或者能够在各种不同平台上工作。该程序是binutils包的一部分。 | 简单说：将C/C++源文件转换成汇编语言
`autoconf` | 产生的shell脚本自动配置源代码包取编译某个特定版本的UNIX。 | &nbsp;
`gdb` | GNU调试器。可用于检查程序运行时的值和行为。 | 各种IDE（vscode/visualstudio/...）底层调用该工具
GNATS（GNU Bug Tracking System） | 一个跟踪GCC和其他GNU程序问题的在线系统。 | 经常在别人工程中下载库编译安装时经常看到这东西
`gprof` | 该程序会监督编译程序的执行过程，并报告程序中各个函数的运行时间，可以根据所提供的配置车文件来优化程序。该程序是binutils包的一部分。 | 静态库/动态库都用到 <br> 新手经常碰到的问题大概率与此库相关，如 ld error ...
`ld` | GNU连接程序。该程序将目标文件的集合组成可执行程序。该程序是binutils包的一部分。 | &nbsp;
`libtool` | 一个基本库，支持make程序的描述文件使用的简化共享库用法的脚本。 | &nbsp;
`make` | 一个工具程序，它会读makefile脚本来确定程序中哪部分需要编译和连接，然后发布必要的命令。它读出的脚本（makefile或Makefile）定义了文件关系和依赖关系。 | make工具的支持库

### 编译过程

简单来说：首先把源代码编译成目标文件， 然后把目标文件链接起来。

#### 制作可执行文件

```bash
↓
↓ hello.c （源文件，文本）
↓
预处理器（cpp） —— 注释、宏定义 —— e.g. gcc -E hello.c -o hello.i
↓
↓ hello.i （修改了的源程序，文本）
↓
编译器（ccl） —— e.g. gcc -S hello.c -o hello.s
↓
↓ hello.s （汇编程序，文本）
↓
汇编器（as） —— e.g. gcc -c hello.c -o hello.o
↓
↓ hello.o （目标程序，二进制）（可重定位程序，relocatable object program）
↓
链接器（ld） —— e.g. gcc hello.c -o hello
↓
↓ ←←← prinf.o （链接二进制）
↓
↓ hello （目标程序，二进制）（可执行文件）
```

#### 制作库文件

::: tabs

@tab 制作静态库

静态库（static library）是在链接可执行文件时，代码段和数据段直接拷贝到可执行文件中，以便代码复用。

制作：

```bash
# gcc -c lib1.c lib2.c ...
# gcc -c add.c -o add.o
# gcc -c tfunc.c -o tfunc.o
gcc -c add.c tfunc.c
# 库文件命名规范 lib<库名>.a
ar -r liboperation.a add.o tfunc.o
```

链接：

```bash
# gcc [.c] [.a] -o [.o]
# gcc [.c] -o [.o] -l[库名] -L[库路径]
gcc calc.c liboperation.a -o calc

# 也可以直接使用.o文件链接
gcc calc.o tfunc.o add.o -o calc
```

@tab 制作动态库

> 动态链接的具体用法参考： [here](#title-dynamic-link)

动态库（shared library）是在业务运行时才从文件系统中加载到运行环境中的，因此无法在编译和链接阶段获取代码段的符号地址（代码段的符号包括引用的全局数据，调用的函数等）。

> 动态链接执行复杂，执行时间比静态链接长。
> 但是，极大的节省了 size。
> 其中用到的 PIC 和动态链接技术是计算机发展史上非常重要的一个里程碑。

制作：

```bash
# gcc -c -fpic [.c/.cpp] [.c/.cpp] ...
# -fpic/-fPIC
#       PIC（position independent code）
#       -fPIC 与生成动态链接可以说没有直接关系，不用fPIC依然可以编译出so文件。
#       但是如果不加 -fPIC 则加载 .so 文件的代码段时，代码段引用的数据对象需要重定位。
#       重定位会修改代码段的内容，这就造成每个使用这个 .so 文件代码段的进程在内核里都会生成这个 .so 文件代码段的copy。
#       由于于这个 .so 文件代码段和数据段内存映射的位置不一样，每个copy都不一样。
#       💡一般用fPIC来生成so，而生成a时则不用fPIC
#
#       不使用fPIC编译so的情况：（满足以下4个条件）
#       1. 该库可能需要经常更新
#       2. 该库需要非常高的效率（尤其是有很多全局量的使用时）
#       3. 该库并不很大
#       4. 该库基本不需要被多个应用程序共享
gcc -c -fpic tfunc.c add.c
# gcc -shared [.o] [.o] ... -o [lib库名.so]
# -shared —— 生成共享目标文件。通常用在建立共享库时。
#            💡是否添加 -fPIC 的问题：
#            从GCC来看，shared应该是包含fPIC选项的，但似乎不是所以系统都支持，所以最好显式加上fPIC选项。
gcc -shared tfunc.o add.o -o liboperation.so
```

链接：

```bash
# gcc [.c/.cpp] -o [可执行文件名] -l[库名] -L[库路径] -Wl,-rpath=[库路径]
# -Wl.option —— 此选项传递option给链接
#               多个option中间用逗号","分割
# -rpath              —— 运行时动态库路径
# -l[库名] -L[库路径]  —— 编译时动态库路径
gcc calc.c -o calc -loperation -L$(pwd) -Wl,-rpath=$(pwd)
```

:::

#### 文件后缀

::: tabs

@tab C语言

后缀（Suffix） | 说明（File Contains）
--- | ---
`.c` | 源文件 <br> C source code that is to be preprocessed.
`.h` | 头文件 <br> C or C++ header file.
`.i` | 预处理文件 <br> C source code that is not to be preprocessed. <br> This type of file is produced as an intermediate step in compilation.
`.s` | 汇编语言文件 <br> Assembly language code. <br> this type of file is produced as an intermediate step in compilation.
`.o` | 目标文件 <br> An object file in a format appropriate to be supplied to the linker. <br> This type of file is produced as an intermediate step in compilation.
`.a` | 静态库文件 <br> Static object library (archive).
`.so` <br>`.lib`/`.dll` (for windows) | 动态库/共享库/运行时库文件 <br> Shared object library.

@tab C++语言

后缀（Suffix） | 说明（File Contains）
--- | ---
`.C`/`.c++`/`.cc`/`.cp`/`.cpp`/`.cxx` | 源文件 <br> C++ source code that is to be preprocessed.
`.h` | 头文件 <br> C or C++ header file.
`<none>` | The standard C++ system header files have no suffix
`.ii` | 预处理文件 <br> C++ source code that is not to be preprocessed. <br> This type of file is produced as an intermediate step in compilation.
`.s` | 汇编语言文件 <br> Assembly language code. <br> this type of file is produced as an intermediate step in compilation.
`.o` | 目标文件 <br> An object file in a format appropriate to be supplied to the linker. <br> This type of file is produced as an intermediate step in compilation.
`.a` | 静态库文件 <br> Static object library (archive).
`.so` <br>`.lib`/`.dll` (for windows) | 动态库/共享库/运行时库文件 <br> Shared object library.

:::

#### 常用编译选项

编译选项 | 说明 | 备注
--- | --- | ---
`-m64` | 指定编译为64位应用 | &nbsp;
`-std=` | 指定编译标准，例如： `-std=c++11`/`-std=c++14` | &nbsp;
`-g` | 包含调试信息 | &nbsp;
`-w` | 不显示告警 | &nbsp;
`-O` | 优化等级，通常使用 `-O3` | &nbsp;
`-I` | 加载头文件路径前 | &nbsp;
`-fPIC` <br> （Position-Independent Code） | 产生二进制文件没有绝对地址，使用全部相对地址。二进制可以被加载到内存任意位置，且可以正确的执行 | 共享库必加

链接选项 | 说明 | 备注
--- | --- | ---
`-l` | 加在库名前面 | &nbsp;
`-L` | 加在库路径前面 | &nbsp;
`-Wl,<选项>` | 将`<选项>`传递给链接器 | &nbsp;
`-Wl,-rpath="共享库路径"` | 指定运行时共享库（.so文件）路径所在的目录 | &nbsp;

#### 查看ELK信息

Linux的可执行文件一般是elf格式（Executable and Linking Format，可执行可连接格式）的，在这个可执行文件的头部包含了很多重要的信息：如文件格式，加载地址，符号表等。

```bash
####################################
# 命令： file <some-file>
####################################
$ file hello.o
hello.o: ELF 64-bit LSB relocatable, x86-64, version 1 (SYSV), not stripped
# ELF ——————————————— 💡Executable and Linking Format，可执行可连接格式
# 64-bit
# LSB ——————————————— 💡Least Significant Bit，最小标识位/最低位有效。表示时是小端模式的程序
# relocatable ——————— 💡可重定位，运行时才指定内存位置？
# x86-64 ———————————— 💡cpu架构
# version 1 (SYSV) —— 💡System V | 程序初始化方案？
# stripped/not stripped 
#   + stripped     —— 💡将程序中的符号表的信息剔除掉了，优点： 1. 可执行文件体积减少； 2. 程序更难以被调试/逆向/破解
#   + not stripped —— 💡保留了上述信息，便于调试

####################################
# 命令： readelf -h <elk-file> —— 💡查看二进制文件头文件
####################################
$ readelf -h hello.o 
ELF Header:
  Magic:   7f 45 4c 46 02 01 01 00 00 00 00 00 00 00 00 00
  Class:                             ELF64
  Data:                              2's complement, little endian
  Version:                           1 (current)
  OS/ABI:                            UNIX - System V
  ABI Version:                       0
  Type:                              REL (Relocatable file) # ———————— 💡文件类型 ❗可重定位，运行时才指定内存位置？
  Machine:                           Advanced Micro Devices X86-64 # — 💡适配的cpu架构
  Version:                           0x1
  Entry point address:               0x0 # ——————————————————————————— 💡程序入口地址 ❗无指定
  Start of program headers:          0 (bytes into file)
  Start of section headers:          616 (bytes into file)
  Flags:                             0x0
  Size of this header:               64 (bytes)
  Size of program headers:           0 (bytes)
  Number of program headers:         0
  Size of section headers:           64 (bytes)
  Number of section headers:         14
  Section header string table index: 13
$ ./hello.o # 未可执行
-bash: ./hello.o: cannot execute binary file: Exec format error 
```

### 头文件

C/C++ 中 `#include` 头文件有两种形式，一种是使用尖括号`<>`，一种是使用双引号`""`。

+ `#include`/`#define`/`#if` 为预处理语句 —— 宏，由预处理器处理
  + `#include` 用于在编译前将指定的头文件内容插入到源文件中
+ `#include "xxx.h"` 适合用于指定**项目内部或者开发者本地自定义的头文件**路径
  查找顺序：
  1. 当前文件所在的目录 —— 编译器首先查找与当前源文件同目录的头文件。
  1. 引用该文件的的文件夹 —— 如果该源文件被其他文件通过`#include`引用，编译器会在最先引用该文件的文件夹内查找。
  1. 指定的项目包路径 —— 略
  1. 全局或系统路径 —— 略
+ `#include <xxx.h>` 仅在全局或系统或者指定的项目包路径中查找这些头文件，**忽略当前目录，且优先级顺序不同**。
  这是为了更高效地查找**标准库和第三方库**中的头文件。（当然也便于读代码时候区分是软件自身的代码还是三方依赖的代码）
  查找顺序：
  1. C++标准库路径 —— 即如`/usr/lib64/libstdc++.so.6`等路径。
    这些路径可能包含`<iostream>`/`<vector>`/`<string>`等标准库文件。
  1. 系统库路径 —— 这些路径通常是操作系统或者IDE预先配置好的系统路径。
    在Linux系统中常见为`/usr/include`,`/usr/local/include`等路径（环境变量：`LD_LIBRARY_PATH`）；
    在Windows系统中可能是`C:\Program Files (x86)\Microsoft Visual Studio\`路径。
  1. 指定的项目包路径 —— 通过编译器参数指定的路径，如`-I /path/to/include`选项（环境变量：`CPLUS_INCLUDE_PATH`）。
    这个路径可以是多个目录，依赖于构建系统的设置。
    这些路径一般存有开发者指定的第三方库的头文件，例如Boost、Poco、QT等。
      + GCC/Clang: `g++ -I /path/to/headers main.cpp`
      + CMake: `include_directories()`/`target_include_directories()`

::::: tip

可以通过命令 `echo | gcc -x c -v -E -` 查看具体的搜索路径

::: tabs

@tab C++编译器（GCC）

```bash
# gcc：表示使用GCC编译器进行编译。
# -x c++：表示指定编译的语言为C++。
# -v：表示显示详细的编译过程信息。
# -E：表示仅执行预处理阶段，不进行编译和链接。
# -：表示从标准输入读取源代码。
$ echo | gcc -x c++ -v -E -
...
ignoring nonexistent directory "/usr/lib/gcc/x86_64-redhat-linux/15/include-fixed"
ignoring nonexistent directory "/usr/lib/gcc/x86_64-redhat-linux/15/../../../../x86_64-redhat-linux/include"
#include "..." search starts here:
#include <...> search starts here:
 /usr/lib/gcc/x86_64-redhat-linux/15/../../../../include/c++/15
 /usr/lib/gcc/x86_64-redhat-linux/15/../../../../include/c++/15/x86_64-redhat-linux
 /usr/lib/gcc/x86_64-redhat-linux/15/../../../../include/c++/15/backward
 /usr/lib/gcc/x86_64-redhat-linux/15/include
 /usr/local/include
 /usr/include
...
```

@tab C++编译器（Clang）

```bash
# clang：表示使用GCC编译器进行编译。
# -x c++：表示指定编译的语言为C++。
# -v：表示显示详细的编译过程信息。
# -E：表示仅执行预处理阶段，不进行编译和链接。
# -：表示从标准输入读取源代码。
$ echo | clang -x c++ -v -E -
```

:::

:::::

>
> 总结：
>
> + `#include <...>` 优先查找标准库和外部库文件，用于引用系统文件和第三方库等开发者一般不可修改的头文件。
> + `#include "..."` 优先查找开发者本地文件，用于引用开发团队项目内部或者开发者本地定义的，开发者可以修改的头文件。
>

### configure

`configure` 是一个脚本文件，定义了执行时可以传入的必要参数，告知配置项目。
`configure` 程序它会根据传入的配置项目检查程序编译时所依赖的环境以及对程序编译安装进行配置，最终生成编译所需的 `Makefile` 文件供程序 `make` 读入使用进而调用相关编译程式来编译最终的二进制程序。

参考：

+ 简述configure、pkg-config、pkg_config_path三者的关系 | <https://www.cnblogs.com/wliangde/p/3807532.html>

### pkg-config 链接库配置生成器

而configure脚本在检查相应依赖环境时(例：所依赖软件的版本、相应库版本等)，通常会通过pkg-config的工具来检测相应依赖环境。

一般来说，如果库的头文件不在 `/usr/include` 目录中，那么在编译的时候需要用 `-I` 参数指定其路径。
但由于编译的环境和编译后程序运行的环境大概率不同，通过 `-I` 指定的文件路径大概率也不一样，这是需要编译后的 `lib/pkgconfig` 目录中添加 `.pc` 文件来指定库的各种必要信息，包括版本信息、编译和连接需要的参数等。
这样，不管库文件安装在哪，通过库对应的 `.pc` 文件就可以准确定位，可以使用相同的编译和连接命令，使得编译和连接界面统一。

```txt
prefix=/opt/gtk/
exec_prefix=${prefix}
libdir=${exec_prefix}/lib
includedir=${prefix}/include
 
glib_genmarshal=glib-genmarshal
gobject_query=gobject-query
glib_mkenums=glib-mkenums
 
Name: GLib
Description: C Utility Library
Version: 2.12.13
Libs: -L${libdir} -lglib-2.0
Cflags: -I${includedir}/glib-2.0 -I${libdir}/glib-2.0/include
```

```bash
# 列出所有可使用的包
# 位置在：
# + /usr/lib/pkgconfig —— 此目录下都是各种.pc文件。
# + /usr/local/lib/pkgconfig —— 新软件一般都会安装.pc文件
# + 没有可以自己创建，并且设置环境变量 PKG_CONFIG_PATH 寻找 .pc 文件路径。
$ pkg-config –-list-all

# 给出在编译时所需要的选项
$ gcc -c `pkg-config --cflags glib-2.0` sample.c
# 给出连接时的选项
$ gcc sample.o -o sample `pkg-config --libs glib-2.0`
```

```bash
export PKG_CONFIG_PATH=/opt/gtk/lib/pkgconfig:$PKG_CONFIG_PATH
export LD_LIBRARY_PATH=/opt/gtk/lib:$LD_LIBRARY_PATH
```

### 动态链接 {#title-dynamic-link}

Linux支持动态连接库，不仅节省了磁盘、内存空间，而且可以提高程序运行效率。不过引入动态连接库也可能会带来很多问题，例如动态连接库的调试、升级更新和潜在的安全威胁。

> 参考：
>
> + 动态符号链接的细节 - <https://www.w3cschool.cn/cbook/ojay2ozt.html> 【非常详细】

为了让动态链接器能够进行符号的重定位，必须把动态链接库的相关信息写入到可执行文件当中：

```bash
# 通过 readelf -d 可以打印出该文件直接依赖的库
$ readelf -d test | grep NEEDED
 0x00000001 (NEEDED)                     Shared library: [libc.so.6]
# 通过 ldd 命令则可以打印出所有依赖或者间接依赖的库
$ ldd test
      linux-gate.so.1 =>  (0xffffe000) # 在文件系统中并没有对应的库文件，它是一个虚拟的动态链接库，对应进程内存映像的内核部分。参考： http://www.linux010.cn/program/Linux-gateso1-DeHanYi-pcee6103.htm
      libc.so.6 => /lib/libc.so.6 (0xb7da2000)
      /lib/ld-linux.so.2 (0xb7efc000) # 动态链接器 | 绝对路径 | readelf -x .interp test | interpeter
      # 程序的加载过程 - 动态链接器
      # 当 Shell 解释器或者其他父进程通过exec启动我们的程序时，系统会先为ld-linux创建内存映像，然后把控制权交给ld-linux，
      # 之后ld-linux负责为可执行程序提供运行环境，负责解释程序的运行，因此ld-linux也叫做dynamic loader（或intepreter）
      # 参考： http://www.ibm.com/developerworks/cn/linux/l-elf/part1/index.html
```

程序有两种方式使用库

+ 编译时通过 `-l`,`-L` 参数隐式使用： `gcc -o test test.c -lmyprintf -L./ -I./`
+ 运行时通过 `LD_LIBRARY_PATH` 环境变量显示使用： `LD_LIBRARY_PATH=$PWD`

::: tip
**指定动态库位置**：

通过 `LD_LIBRARY_PATH` 参数，它类似 Shell 解释器中用于查找可执行文件的 `PATH` 环境变量，也是通过冒号分开指定了各个存放库函数的路径。

该变量实际上也可以通过 `/etc/ld.so.conf` 文件来指定，一行对应一个路径名。 （一般需要管理员权限） <br>
为了提高查找和加载动态链接库的效率，系统启动后会通过 `ldconfig` 工具创建一个库的缓存 `/etc/ld.so.cache`。
如果用户通过 `/etc/ld.so.conf` 加入了新的库搜索路径或者是把新库加到某个原有的库目录下，最好是执行一下 `ldconfig` 以便刷新缓存。

更多参考： `man ld-linux`, `/lib/ld-linux.so.2`
:::

#### 动态链接器（dynamic linker/loader）

Linux 下 elf 文件的动态链接器是 ld-linux.so，即 `/lib/ld-linux.so.2`。
通过 `man ld-linux` 可以获取与动态链接器相关的资料，包括各种相关的环境变量和文件都有详细的说明。

如：

+ `LD_LIBRARY_PATH`
+ `LD_BIND_NOW`
+ `LD_PRELOAD` 指定预装载一些库，以便替换其他库中的函数，从而做一些安全方面的处理
+ `LD_DEBUG` 用来进行动态链接的相关调试

+ `ld.so.conf`
+ `ld.so.cache`
+ `/etc/ld.so.preload` 指定需要预装载的库

#### 运行时库的连接 `LD_LIBRARY_PATH`

库文件在连接（静态库和共享库）和运行（仅限于使用共享库的程序）时被使用，其搜索路径是在系统中进行设置的。

一般Linux系统把 `/lib` 和 `/usr/lib` 两个目录作为默认的库搜索路径，所以使用这两个目录中的库是不需要进行设置搜索路径即可直接使用。
对于处于默认库搜索路径之外的库，需要将库的位置添加到 库的搜索路径之中。
设置库文件的搜索路径有下列两种方式，可任选其一使用：

1. 会话生效 —— 在环境变量 `LD_LIBRARY_PATH` 中指明库的搜索路径。

  `export LD_LIBRARY_PATH=/opt/gtk/lib:$LD_LIBRARY_PATH`

1. 永久生效 —— 在 `/etc/ld.so.conf` 文件中添加库的搜索路径（绝对路径）。 ⚠️一般需管理员权限

  e.g.

  ```bash
  /usr/X11R6/lib
  /usr/local/lib
  /opt/lib
  ```

  ::: tip
  另外，为了加快程序执行时对共享库的定位速度，避免使用搜索路径查找共享库的低效率，会有 `/etc/ld.so.cache` 库列表文件可以直接读取、搜索。
  `/etc/ld.so.cache` 是一个非文本的数据文件，不能直接编辑，它是根据 `/etc/ld.so.conf` 中设置的搜索路径由 `/sbin/ldconfig` 命令将这些搜索路径下的共享库文件集中在一起而生成的( `ldconfig` 命令要以 root 权限执行)。

  如当安装完一些库文件(例如刚安装好glib)，或者修改 `ld.so.conf` 增加新的库路径后，需要运行一下 `/sbin/ldconfig` 使所有的库文件都被缓存到 `ld.so.cache` 中。
  否则 `ld.so.conf` 的更改不生效。
  :::

### 静态编译

一般在“裸机”上跑的程序会考虑使用静态链接编译，避免动态链接缺失的情况

::: tip

**Linux 和 裸机（baremetal） 区别**：

+ Linux —— 在已安装操作系统的机器上跑。一般：个人pc、公司服务器
+ baremetal —— 直接由硬件调起。一般：嵌入式

:::

编译参数：

+ gcc —— `-static`

::: tip

静态编译的二进制通过 `ldd` 可以看到 "not a dynamic executable" 的字样。
而 ~~`-nostdlib`~~ 通过 `ldd` 看到的是 "statically linked"，这表示 “它恰好没有链接到任何库，但在其他方面与普通 PIE 相同，指定了 ELF 解释器。”

todo What's the difference between "statically linked" and "not a dynamic executable" from Linux ldd? | <https://stackoverflow.com/questions/61553723/whats-the-difference-between-statically-linked-and-not-a-dynamic-executable>

:::

### 交叉编译

“交叉编译” 指在一个平台上生成另一个平台上的可执行文件。

一般要用到如下编译工具（todo 各工具作用）

+ gcc
+ ld
+ objcopy
+ objdump

交叉编译工具链

+ `arm-linux-gcc`
+ `arm-none-linux-gnueabi-gcc-ld`

e.g. 交叉编译脚本

```bash
#!/bin/bash

install_arm32() {
  sudo apt install gcc make gcc-arm-linux-gnueabi binutils-arm-linux-gnueabi
}

install_arm64() {
  sudo apt install gcc make gcc-aarch64-linux-gun binutils-aarch64-linux-gnu
}
gen_hello() {
  cat > helloworld.c << EOF
#include<stdio.h>
int main()
{
  printf("Hello World!\n");
  return 0;
}
EOF
}

compile_64() {
  gcc helloworld.c -o helloworld-x86_64
}

compile_arm32() {
  arm-linux-gnueabi-gcc helloworld.c -o helloworld-arm -static
}

compile_arm64() {
  aarchi64-linux-gnu-gcc helloworld.c -o helloworld-aarch64 -static
}

# Main.
# install_arm32
# install_arm64
gen_hello
compile_64 || exit 1
compile_arm32
compile_arm64
```

## 编译原理

todo

## 安全加固

todo

`nm` 可以查看 elf 文件的符号信息

```bash
$ gcc -c test.c
$ nm test.o
00000000 B global
00000000 T main
          U printf
```
