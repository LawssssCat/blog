---
title: Rust 编译原理（深入）
order: 99
---

## 编译流程

### GCC（GNU Compiler Collection）

GCC（GNU Compiler Collection） 将编译分成多个步骤，通过替换其中步骤，使得 GCC 支持 c 语言以外其他语言的编译。

> GCC stands for "GNU Compiler Collection". GCC is an integrated distribution of compilers for several major programming languages. These languages currently include C, C++, Objective-C, Objective-C++, Fortran, Ada, D, and Go.

具体步骤如下：（todo 具体步骤）

::: tabs

@tab C语言

```bash
main.c
-> C Pre-Processor
main.i
-> C Compiler
main.o
-> Assembler
main.s
-> Linker
main
```

@tab C++语言

```bash
main.c
-> C++ Pre-Processor
main.i
-> C++ Compiler
main.o
-> Assembler
main.s
-> Linker
main
```

@tab Objective C

```bash
main.c
-> Objective C Pre-Processor
main.i
-> Objective-C Compiler
main.o
-> Assembler
main.s
-> Linker
main
```

@tab Gfortran

```bash
main.c
-> Gfortran Fortran Pre-Processor
main.o
-> Assembler
main.s
-> Linker
main
```

跨语言链接：

```bash
# 编译和汇编Fortrant文件
gfortran -c is_prime.f90
# 编译和汇编c文件
gcc -c count_primes.c
# 链接成一个可执行文件
gfortran count_primes.o is_prime.o -o count_primes
```

@tab GNAT

```bash
main.c
-> GNAT Ada Pre-Processor
main.o
-> Assembler
main.s
-> Linker
main
```

:::

> GCC 起初全称 “GNU C Compiler”，现在全称 “GNU Compiler Collection”，大概多亏其支持多语言编译功能。

### Rust

Rust 有一套与 C 完全不同的工具链：不同的编译器、不同的构建系统、不同的整体理念。

```bash
-> Parser-Expand-Resolve
-> Compiler(HIR)
-> Type Check
-> Compiler(MIR)
-> Borrow Checking
-> CodeGen(LLVM)
-> Assembler(LLVM)
-> Linker
```

## 调用其他语言

参考：

+ <https://www.bilibili.com/video/BV1AShuzfEzX/>

### 重点：ABI

ABI（Application Binary Interface， 应用二进制接口）定义二进制代码的不同组件如何通过硬件相互交互。

当我们混合使用不同语言时，语言间交互的部分需要遵循ABI规则，否则会出现如寄存器存取错误等底层规则异常。

不同语言声明ABI的方式：

::: tabs

@tab C语言

关键字 `extern` 声明一个外部函数。

e.g. `extern int is_prime(int n);`

@tab rust

使用 `extern` 关键字和 `no_mangle` 属性声明外部函数。

e.g.

```rust
#[no_mangle]
pub extern "C" fn is_prime(n: i32) -> i32;
```

@tab Go语言

在 `import "C"` 的正上方使用一段特殊的注释块来包含C头文件

e.g.

```go
package main

/*
#cgo CFLAGS: -I
#cgo LDFLAGS: -L, -ladd
#include "add.h"
*/
import "C"
import "fmt"

func main() {
  result := C.add(3, 4)
  fmt.Println("Result:", result)
}
```

甚至能直接在Go源文件中写内联C代码

e.g.

```go
package main

/*
int add(int a, int b) {
  return a + b;
}
*/
import "C"
import "fmt"

func main() {
  result := C.add(3, 4)
  fmt.Println("Result:", result)
}
```

@tab Fortran

使用 `bind` 属性

e.g.

```fortran
function add(a, b) bind(C, name="add")
```

:::

### 例子：C调用Rust代码

::: tabs

@tab 编译脚本

```bash
# 编译和汇编Rust文件
rustc --crate-type=staticlib is_prime.rs
# 编译和汇编c文件
gcc main.c libis_prime.a -o count_primes
```

@tab main.c

```c
#include <stdio.h>
#include <stdbool.h>

// Declare Rust function using C-compatible name and signature
extern int is_prime(int n);

int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("Usage: %s <positive_integer>\n", argv[0]);
    return 1;
  }
  int n = atoi(argv[1]);
  if (n < 2) {
    printf("Number of prime numbers between 0 and %d id: 0\n", n)
    return 0;
  }
  int count = 0;
  for (int i = 2; i <= n; i++) {
    if (is_prime(i)) {
      count__;
    }
  }
  printf("Number of prime numbers between 0 and %d is: %d\n", n, count);
  return 0;
}
```

@tab is_prime.rs

```rust
// Tell Rust to use C-compatible function export
#[no_mangle]
pub extern "C" fn is_prime(n: i32) -> i32 {
  if n < 2 {
    return 0;
  }
  for i in 2..=((n as f64).sqrt() as i32) {
    if n % i == 0 {
      return 0;
    }
  }
  1
}
```

:::

### 例子：Rust调用C代码

C语言历史悠久，许多成熟的库和系统API都是用C编写的。
Rust开发者经常需要接入这个现有的生态系统，尤其时在图形、加密和操作系统API等领域。

::: tabs

@tab 编译脚本

@tab is_prime.c

```c
// Simple C implementation of is_prime
int is_prime(int n) {
  if (n < 2) return 0;
  for (int i = 2; i * i <= n; i++) {
    if (n % i == 0)
      return 0;
  }
  return 1;
}
```

@tab count_primes.rs

```rust
extern "C" {
  fn is_prime(n: i32) -> i32;
}

fn main() {
  let n = 50;
  let mut count = 0;

  for i in 2..=n {
    // SAFETY: We're calling a well-behaved external C function
    let result = unsafe { is_prime(i) };
    if result != 0 {
      count += 1;
    }
  }

  println!("Number of prime numbers between 0 and {} is: {}", n, count);
}
```

:::
