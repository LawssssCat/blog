---
title: rustc
order: 1
---

## 编译原理

todo 《自己动手构造编译系统》 <https://github.com/fanzhidongyzby/cit>

编译系统 = 编译 + 汇编 + 链接 ....

```bash
源代码 hello.c
-> 编译器
汇编代码 hello.s
-> 汇编器
二进制目标文件 hello.o
-> 链接器
可执行文件（ELF） hello
```

![image.png](https://s2.loli.net/2025/08/24/VKs1ajqWrUXGCol.png)

编译系统设计：\
![image.png](https://s2.loli.net/2025/08/24/djKmiZUOgAEofXB.png)

### 编译器（Compiler）

![image.png](https://s2.loli.net/2025/08/24/ZTwOvqS3jYioP7u.png)

#### 词法分析

关键设施：

+ 扫描器 —— 源文件 to 字符
+ 解析器（with 有限自动机） —— 字符 to 词法记号

![image.png](https://s2.loli.net/2025/08/24/qcilIx74XS8onmF.png)

词法分析：有限自动机\
![image.png](https://s2.loli.net/2025/08/24/xumzHW7aJ3M5Ecp.png)

#### 语法分析

语法单元拆解 —— 语言支持哪些特性\
![image.png](https://s2.loli.net/2025/08/24/5xoidszpNOFS4e7.png)

LL1文法定义

符号表

#### 语义分析

todo 相对简单，纯if-else

#### 中间代码生成

todo IR设计

todo 运行时内存组织

todo 目标代码生成

### 优化器（Optimizer）

todo 优化器设计

todo 控制流图（CFG）

### x86指令 & ELF文件

汇编到操作码

todo x86指令，RISC架构

### 汇编器（Assembler）

todo

### 链接器（Linker）

todo
