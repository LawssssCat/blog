---
title: C/C++基础语法
order: 6
---

<!-- more -->

参考：

+ C++ 基础概念 <https://www.bilibili.com/video/BV1ot4JezEAF>
+ C++ 必知必会 <https://www.bilibili.com/video/BV13nxqeLEYK/>

## GNU C

随时代发展，语言也需要有新的特性、标准。
GNU C 通常指的就是 GNU 项目的C语言编程标准，其中包括ANSI C（通常指C89或C90）的所有特性，以及对C99、C11等更现代C标准的支持和一些GNU特有的扩展。

我们编写好代码后，用gcc编译时会基于某个语言标准进行代码解析，因此了解不同标准可以帮助我们定位编译问题、也能借此了解编程语言设计者对当前新增的开发需求给出了哪些解决方案。

todo gcc版本和支持的语言标准对照表

## 基础概念

### 变量（Variable）

概念：

+ 变量用“标识符（Identifier）”表示 —— abc,a_bc,啊吧车,... (Case Sensitive)
+ 变量不能是“关键字（keyword）” —— bool,auto,for,class,struct,const,and,...
+ 使用变量前需要“定义（definition）”数据类型和“声明（declaration）”标识符 —— `数据类型 标识符` （“数据类型”与“编译器内存分配”有关）
+ 常量（Constant） —— 值不可变
  + 字面常量（Literal）： `1,2,3` `'a','b','c'` `true,false`
    + “整数进制”表示方式： `26`（十进制）、`0x1A`（十六进制）、`032`（八进制）、`0b111010`（二进制）
    + “浮点数”表示方式： `2.6` `2.6f` `0.026e2`（科学计数法） `26e-1` `.26E1f`
    + “字符”表示方式： `'a'` `97` `'\x60'` `'\100'`
  + 字符串：
    + c语言风格
      + `const char* hello = "hello";`
      + `char hello2[] = "hello";` （❗注意：它的长度是6，因为会有`'\0'`字符隐藏在了结尾）
      + `char hello3[] = {'H','e','l','l','o','\0'};`
    + c++风格
      + `string hello4 = "Hello"; // #include <string> using namespace std;`

代码：

```cpp
// ==========
// 1字节
// ==========
// 字符型变量
char c; // -128~127
unsigned char c; // 0~255
bool b; // true=1 false=0 （❗逻辑判断时，不为0则为True）
// ==========
// 2字节
// ==========
// 短整型
short int c; // -32768~32767
short c;
unsigned short c;
// ==========
// 4字节
// ==========
// 整型
int a; // -2147483648~2147483647
// 无符号整型
unsigned int d;
// 长整型
long c;
// 浮点数
float b; // 1.2E-38~3.4E+38
// ==========
// 8字节
// ==========
double b; // 1.7E-308~1.7E+308
// ==========
// 2字节/4字节
// ==========
// 宽字符
wchar_t b;
```

::: info
不同系统/CPU类型数据类型大小可能不一样，可以通过`sizeof(var)`宏方法获取具体大小。
:::

```cpp
// 定义方式
int a,b,c;
// 初始化方式
float a = 0.5; // c语言初始化方式
float b(0.5);  // 构造初始化方式，类对象初始化
float c{-0.7}; // 列表初始化，常用于数组/结构体的初始化
float d={0.7}; // 同上“列表初始化”
```

### 操作符（Operator）

alias： 运算符

::: tip
对于基本类型各种运算符有确定的含义，而对于C++的类和对象这些运算符可能会被重载（参考：todo）使功能有所变化。
:::

+ 赋值运算符： `=`
+ 算数运算符/二元运算符： `+` `-` `*` `/` `%`（模）
+ 位运算符： `&`（与运算） `|`（或运算） `^`（异或运算） `~`（取反运算） `<<` `>>`
+ 复合赋值运算符： `+=` `-=` `*=` `/=` `%=` `>>=` `<<=` `&=` `^=` `|=`
+ 增量/减量运算符： `++` `--` （有“前缀”/“后缀”两种情况）
+ 关系运算符： `==` `!=` `>` `<` `>=` `<=`
+ 逻辑运算符： `!` `&&` `||`
+ 条件运算符/三元运算符： `条件 ? 真结果 : 假结果`

+ 类型转化运算符： `a = (int) b;`

::: info
在编写程序时，经常要用到 `<<` `>>` 的两种重载含义：

+ `<<` —— 插入运算符
+ `>>` —— 取出运算符

例子：

```cpp
#include <iostream>
using namespace std;
int main() {
  int a;
  cout << "请输入：（一个字符）" << endl; // cout为“控制台输出”；<<为插入运算；endl为“换行”
  // 获取一个字符 —— ❗注意：这个方式只能获取一个字符
  cin >> a; // cin为“控制台输入”；>>为取值运算
  cout << "a=" << a << endl;
  // 获取一句话
  const int maxSize = 2535;
  char sentence[maxSize];
  cout<<"请输入：\n"
    "（一句话）"; // 是的，可以这样换行
  cin.getline(sentence, maxSize);
}
```

:::

### 条件判断

if

```cpp
if(条件)
  语句; // 只决定一条语句

if(条件) {
  语句; // 可决定多条语句
} else if(条件) {
} else {
  语句
}
```

switch

```cpp
switch(变量) {
  case 常量:
    语句;
    // break; // 可缺省，则进入下面的case
  case 常量:
    语句;
    break;
  default:
    语句;
    break;
}
```

### 循环

for

```cpp
for(初始化; 条件判断; 更新表达式) {
  语句; // break,continue
}
```

```cpp
for(元素:范围) {
  语句;
}
e.g.
int numbers[] = {1,2,3,4};
for (auto number:numbers) { // auto 新语法，根据上下文自动判断类型
  cout<<number<<endl;
}
for (int i=0;i<sizeof(numbers)/sizeof(int);i++) { // 一般写法
  int number = numbers[i];
  cout<<number<<endl;
}
```

while

```cpp
while(条件判断) {
  语句; // break,continue
}
```

```cpp
do {
  语句;
} while(条件判断);
```

### 数组（Array）

::::: c风格

格式：
`类型 标识符[元素数量];`

初始化：

```cpp
float weights[5] = {0}; // 全部初始化为0
float weights[5] = {};  // 如果缺省，默认也是0
// c语言风格
float weights[] = {1.2，3.1};
float weights[5] = {1.2，3.1}; // 编译器会检查数量：右边少则其他用缺省值；右边多则会报错；
// c++风格
float weights[5]{1.2,3.1}; // c++11
```

访问：（索引从0开始）

```cpp
float weights[5];
weights[0] = 3.5;
weights[5] = 3.5; // ❗注意：编译器不会检查数据越界，因此开发时需要注意
```

@tab 向量（vector）

c++ 中的模板类（标准模板库的[容器类](#id-std-col-vector)）

```cpp
#include <vector>
vector<float> weights = {1.1,2.2,3.3};
// 遍历1
for(int i=0;i<weights.size();i++) {
  float w = weights[i];
}
// 遍历2
for (auto w:weights) {
}
```

:::::

### 字符串

:::: tabs

@tab C风格

字符型数组：

```cpp
// 定义
char hello[] = "hello";
char hello[20] = "hello"; // 默认会在结尾加0字符
char hello[] = {'H',0}; // 这里0表示结束
char hello[] = {'H'};   // ❗注意：语法中允许没有0结束，但这样定义有错误
// 骚操作：可以把结束符0作为循环结束的条件
char hello[]="hello";
for(int i=0;hello[i];i++) { // 0在判断中为False
  char c = hello[i];
}
```

比较：

```cpp
str1 == str2; // 错误
strcmp(str1, str2)==0; // 正确，当两个字符串内容相同返回0
```

@tab c++

定义：

```cpp
#include <string>
using namespace std;
string word1("hello");
string word2("hello");
```

对比：

```cpp
string word1("hello");
string word2("hello");
word1 == word2; // 正确，注意需要类型为string
```

::::

### 函数

函数声明： 说明函数有什么形参（parameter）、返回值

```cpp
返回值类型 函数名(参数类型 [参数名], 参数类型 [参数名2], ...) // 函数头
```

函数定义： 说明函数有什么实参（argument）、返回值、如何实现

```cpp
返回值类型 函数名(参数类型 参数名, 参数类型 参数名2=默认值, ...) // 函数头
{
  // 函数体
}
```

概念：

+ **函数默认值**
+ **函数重载（overloaded function）**
+ **函数递归**

  ::: tabs

  @tab 递归函数

  ```cpp title="斐波那契数列"
  unsigned long fib(unsigned long n) {
    if (n<=1) return 1;
    return fib(n-1)+fib(n-2);
    // 存在两个问题：
    // 1、递归深度过高可能会oom（Out Of Memory）
    // 2、时间复杂度是指数级别o(2^n)
  }
  ```

  @tab 优化方向：缓存结果

  ```cpp title="斐波那契（优化：缓存）"
  unsigned int fibonacci[50] = {0};
  unsigned int fib(unsigned int n) { // 时间复杂度n(n)
    if (n<=1) return 1;
    if (n>=50) return -1; // 太大了。 46 就超过 unsigned int 数值上线
    if (!fibonacci[n]) fibonacci[n] = fib(n-1) + fib(n-2); // 缓存
    return fibonacci[n];
  }
  ```

  @tab 优化方向：动态规划

  ```cpp title="斐波那契（优化动态规划）"
  unsigned int fib(unsigned int n) {
    if (n<=1) return 1;
    int fn2 = 1;
    int fn1 = 1;
    int fn = 0;
    for(int i=2;i<n;i++) {
      fn = fn_2 + fn_1;
      fn_1 = fn_2;
      fn_2 = fn;
    }
    return fn;
  }
  ```

  :::

+ **内联函数（inline function）** —— 为提高函数性能而作的改进。
  内联函数在预处理时会被直接被放入函数中，因此不会有调用普通函数时的状态存储、出栈入栈操作、跳转指令等性能消耗。
  代价是占用更多硬盘、内存资源。
  内联函数一般用在短小但常用的函数中。

  ```cpp
  inline int add(int a, int b) {
    return a + b;
  }
  ```

  ::: info
  内联函数（关键字`inline`）属于建议说明，编译器可以选择忽略的。
  编译器内部也有优化操作，会将普通函数优化为内联函数。
  因此知道这关键字即可。
  :::

  ::: warning
  有几种情况不能作为内联函数：
  1、函数中有复杂控制语句如循环、switch等；
  2、函数中有静态变量；
  3、函数中存在递归调用；（中断错误`Segmentation Fault!`❌）
  :::

### 指针与引用

概念：

+ 引用变量 `int &a`;
+ 常量引用 `const int& b = a;` 无法修改引用地址值
+ 指针 `int* p = {1,2}`
+ 常量指针 `const int* p = &a;` 无法修改指针指向值（如`*p = 3`），可修改指针指向的内容
+ 指针常量 `int* const p = &a;` 可修改指针指向的值，无法指针指向的地址（如`p++`）
+ 指向堆内存

  ```cpp
  char* p1 = new char; // 申请内存
  int* p2 = new int(2); // 申请内存 + 初始化
  char* p3 = new char[3]; // 申请内存（数组）
  char* p4 = new char[3] {1,2,3}; // 申请内存（数组） + 初始化
  // 释放
  delete p1;
  delete p2;
  delete[] p3;
  delete[] p4;
  ```

  ::: tip
  申请创建的堆内存在函数退出后仍然有效。需要调用销毁方法才会释放这块堆内存。如果申请的堆内存在程序中没有释放机制，就会认为程序有“内存泄漏”问题。
  :::

+ 智能指针 —— 为了避免“地址访问错误”、“内存泄漏”等问题，方便内存管理，C++新增“`memory`”辅助内存管理。

  `shared_ptr` 共享指针 自动管理动态分配的内存，在不使用时自动释放分配的内存

  ```cpp
  #include <memory>
  shared_ptr<int> pInt(new int(2));
  cout << *pInt;
  ```
