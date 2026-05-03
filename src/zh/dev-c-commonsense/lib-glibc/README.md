---
title: glibc 使用笔记
---

glibc（GNU C Library）是标准C库的GNU实现，为C语言提供了标准的API以及POSIX（可移植操作系统接口）的扩展，其中标准库内容包括输入输出处理、字符串操作、内存管理等。

我们采用C/C++所写的程序，运行时基本都依赖与glibc。
如果我们想看当前机器glibc的源代码，首先需要知道当前机器glibc的版本号，然后到glibc的官网下载对应版本的源代码。

glibc官网地址: <https://www.gnu.org/software/libc/> \
glibc源代码包: <https://ftp.gnu.org/gnu/glibc/>

<!-- more -->

## 查看版本号

```bash
# 方法一：
# 使用命令ldd，查看可执行程序依赖libc的路径
$ ldd python
        linux-vdso.so.1 (0x00007fff40beb000)
        libm.so.6 => /lib/x86_64-linux-gnu/libm.so.6 (0x00007f9b44d70000)
        libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f9b44b47000)
        /lib64/ld-linux-x86-64.so.2 (0x00007f9b4552e000)
$ /lib/x86_64-linux-gnu/libc.so.6
GNU C Library (Ubuntu GLIBC 2.35-0ubuntu3.6) stable release version 2.35. # 💡
Copyright (C) 2022 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.
There is NO warranty; not even for MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.
Compiled by GNU CC version 11.4.0.
libc ABIs: UNIQUE IFUNC ABSOLUTE
For bug reporting instructions, please see:
<https://bugs.launchpad.net/ubuntu/+source/glibc/+bugs>.

# 方法二：
# ldd是glibc提供的命令，由此可知glibc的版本号
$ ldd --version
ldd (Ubuntu GLIBC 2.35-0ubuntu3.4) 2.35 # 💡
Copyright (C) 2022 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
Written by Roland McGrath and Ulrich Drepper.

# 方法三：
$ getconf GNU_LIBC_VERSION
glibc 2.35

# 方法四：
$ strings /lib/x86_64-linux-gnu/libc.so.6 | grep GLIBC
GLIBC_2.2.5
GLIBC_2.2.6
GLIBC_2.3
GLIBC_2.3.2
GLIBC_2.3.3
GLIBC_2.3.4
GLIBC_2.4
GLIBC_2.5
GLIBC_2.6
GLIBC_2.7
GLIBC_2.8
GLIBC_2.9
GLIBC_2.10
GLIBC_2.11
GLIBC_2.12
GLIBC_2.13
GLIBC_2.14
GLIBC_2.15
GLIBC_2.16
GLIBC_2.17
GLIBC_2.18
GLIBC_2.22
GLIBC_2.23
GLIBC_2.24
GLIBC_2.25
GLIBC_2.26
GLIBC_2.27
GLIBC_2.28
GLIBC_2.29
GLIBC_2.30
GLIBC_2.31
GLIBC_2.32
GLIBC_2.33
GLIBC_2.34
GLIBC_2.35 # 💡
GLIBC_PRIVATE
GNU C Library (Ubuntu GLIBC 2.35-0ubuntu3.6) stable release version 2.35.

# 方法五： 调用C函数
#include <stdio.h>
#include <gnu/libc-version.h>
int main(void)
{
    printf("glibc version:%s\n", gnu_get_libc_version());
    return 0;
}
```

### Musl libc

参考：

+ Musl libc：为什么我们会需要另一个 libc？ | <https://linuxstory.org/musl-libc-yet-another-libc/>

todo 整理理解

## API

### malloc

todo <https://www.bilibili.com/video/BV1fW9ZB8Eiv>
