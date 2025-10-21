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

### 头文件

使头文件只包含一次的操作：

::: tabs

@tab if define

```cpp
#ifndef __SHAPE_H__
#define __SHAPE_H__
// some...
#endif // !__SHAPE_H__
```

@tab pragma once

```cpp
@pragma once
// some...
```

:::

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

### 条件判断（if/else）

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

### 循环（for/while）

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

#### 坑：地址不变问题

```cpp
for f(int i; i<10; i++)
{
    int c=0; // 地址不变！！！
    cout<<c<<&c<<endl;
}
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

### 字符串（String）

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

### 函数（Function）

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

#### 内联函数（inline function） —— 为提高函数性能而作的改进 {id=id-base-function-inline}

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

### 指针（Ptr）与引用（Ref）

指针：指向内存区域地址的变量

```cpp
SubClass obj;
BaseClass* pBase = &obj; // 指针
BaseClass& refBase = ojb; // 引用

obj.greet();     // 子类重写方法
pBase->greet();  // 父类方法
refBase.greet(); // 父类方法
```

```cpp
// 数组的指针
int numbers[] = {1,2,3,4,5,6};
int *ptr = numbers;
cout<<hex<<(unsigned long long)numbers<<endl;
cout<<hex<<(unsigned long long)ptr<<endl;
```

概念：

+ 引用变量 `int &a`;
+ 常量引用 `const int& b = a;` 无法修改引用地址值
+ 指针 `int* p = {1,2}`
+ 常量指针 `const int* p = &a;` 无法修改指针指向值（如`*p = 3`），可修改指针指向的内容
+ 指针常量 `int* const p = &a;` 可修改指针指向的值，无法指针指向的地址（如`p++`）

#### 函数指针

:::::: tip
函数指针常用在回调函数中。 参考[link_回调函数](#id-xx-function-callback)。
下面具体介绍函数指针写法。
::::::

函数存储在内存中，因此有函数的地址。

可以通过“函数名称”获得函数地址：

  ```cpp
double multiply(double a, double b) {
  return a*b;
}
cout<<hex<<(unsigned long long)multiply<<endl; // 地址值：7ff61ade1450
double (*fn_ptr)(double, double) = multiply; // 函数引用fn_ptr
// double (*fn_ptr)(double, double) = &multiply; // 函数引用fn_ptr（写法二）
double res = fn_ptr(1f,2f); // 函数调用
// double res = (*fn_ptr)(1f,2f); // 函数调用（写法二）
// double res = *fn_ptr(1f,2f); // 函数调用（错误：结果是函数调用结果作为地址求值）
cout<<res<<endl; // 函数调用结果：3f
```

格式：

```cpp
返回值 (*函数指针名称)(参数列表);
```

也可以通过`typedef`可以定义函数指针类型的别名：

```cpp
typedef double (*MyFuncTypePtr)(double, double); // 写法一：指针类型定义
// typedef double (MyFuncTypePtr2)(double, double); // 写法二：值类型定义
int main(void) {
  MyFuncTypePtr ptr1 = multiply;
  // MyFuncTypePtr2* ptr2 = multiply; // 写法二
  double res = ptr1(0.4, 1.5);
  // res = ptr2(4,5);
  cout<<res<<endl;
}
```

::: tip
使用`auto`自动类型推断关键字，可以方便定义函数指针：

```cpp
double add(double a, double b) {
  return a+b;
}
int main(void) {
  auto funcPtr = add;
  double res = funcPtr(4.2,1.5);
}
```

坑：`auto`关键字只能用在初始化单个变量时使用，当要定义函数指针数组时则无法使用`auto`关键字

```cpp
double add(double a, double b) {
  return a+b;
}
double multiply(double a, double b) {
  return a*b;
}
typedef double (*MyFuncPtr)(double a, double b);
int main(void) {
  // auto funcPtr[2] = {add,multiply}; // ❌语法错误
  MyFuncPtr funcPtr[2] = {add,multiply};
  double res = funcPtr(4.2,1.5);
}
```

:::

#### 类函数指针

```cpp
// 类成员函数指针
返回类型 (类名::*函数指针名称)(参数列表);
```

```cpp
class DemoClass {
public:
  double add(double a, double b) {return a+b}
  double multiply(double a, double b) {return a*b}
}

int main(void) {
  double (DemoClass::*ptrMemberFunc)(double, double); // 类成员函数指针定义
  ptrMemberFunc = DemoClass::add; // 类成员函数指针赋值
  DemoClass obj;
  double result = (obj.*ptrMemberFunc)(0.5,2.1); // 类成员函数指针调用
}
```

#### 智能指针 {id=id-base-point-smart}

智能指针 —— 为了避免“地址访问错误”、“内存泄漏”等问题，方便内存管理，C++新增“`memory`”辅助内存管理。

::: tip

普通指针的问题：

+ 内存泄漏（Memory Leak）问题

  申请创建的堆内存在函数退出后仍然有效，需要调用销毁方法才会释放这块堆内存。
  如果申请的堆内存在程序中没有释放机制，就会认为程序有“内存泄漏（Memory Leak）”问题。

```cpp
// 指向堆内存的指针
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

+ 内存悬空（Dangling Pointer）问题

  如果指针指向一块已经释放的内存地址区域，那么这个指针就是悬空指针（Dangling Pointer）。
  使用悬空指针会造成不可预料的结果。

+ 野指针（Wild Pointer）问题

  定义了一个指针却未初始化其指向有效的内存区域时，这个指针就是野指针（Wild Pointer）。
  使用野指针访问内存一般会造成"segmentation fault"错误。

使用智能指针正是为了解决上述问题。

:::

类型 | 特点 | 适用场景
--- | --- | ---
`unique_ptr` | 不能同时有两个`unique_ptr`指向同一个地址。 | 适用于普通指针场景，如容器

##### unique_ptr

特点：
不能同时有两个`unique_ptr`指向同一个地址。

模板

```cpp
template <
  class T,
  class Deleter = std::default_delete<T>
> class unique_ptr;
template <
  class T,
  class Deleter
> class unique_ptr<T[], Deleter>; // 针对数组对象的特化版本
```

常用函数

```cpp
T* get()                          // 返回指针
T* operator->();                  // get()
T& operator*();                   // *get()
T* release();                     // 解除管理
void reset(T* newObject);         // 释放原有对象，纳管新指定对象
void swap(unique_ptr<T>& other);  // 与其他指针交换纳管对象
```

Demo

```cpp
unique_ptr<A> ptr1(new A(参数));

unique_ptr<A> ptr = make_unique<A>(参数);
```

##### shared_ptr（共享指针）

  `shared_ptr` 共享指针 自动管理动态分配的内存，在不使用时（内部维护的地址引用计数为零时）自动释放分配的内存

  ```cpp
  #include <memory>
  shared_ptr<int> pInt(new int(2));
  cout << *pInt;
  ```

##### weak_ptr

todo

### 类（Class）

#### 类定义

```cpp
class 类名称 {
  成员变量0; // 默认private
  构造函数():成员变量0(初始化值); // 1
  ~析构函数(); // 2
public:     // 其他类可访问
  成员变量1; // tips:驼峰命名
  成员函数1();
private:    // 仅自己可访问
  成员变量2;
  成员函数2();
protected:  // 继承可访问
  成员变量3;
  成员函数3();
};
```

成员函数可以写在类外面：

```cpp
class Shape {
  protected:
    std::string shapeName;
  public:
    bool draw() {
      return true;
    };
    float area();
}
float Shape::area() {
  return 0.0f;
}
```

访问类成员有如下方法：

+ `Shape::positionX` —— 通过类名
+ `shape.getPositionX()` —— 通过实例
+ `ptr->getPositionX()` —— 通过指针

##### 静态成员（static）

特点：

+ 全局生效，与具体类实例无关
+ 静态访问静态，不能访问实例

```cpp
class Demo {
public:
  void cheer();
private:
  static unsigned int count;
public:
  static void hello();
}

unsigned int Demo::count = 0;
void Demo::hello() {
  // some... // ❗只能访问静态变量
}

Demo::count;
Demo::hello();
```

##### 构造函数

构造函数

```cpp
class Shape{
  public:
    Shape();
    Shape(int x, int y);
}

Shape s1;
Shape s2(1,2);
```

##### 拷贝构造函数

alias: 复制构造函数、浅拷贝和深拷贝问题

alias：转换构造函数（Conversion Constructor）

```cpp
class Shape {
  int x;
  int y;
private:
  int* m_pValue;
public:
  Shape(const Shape& other) { // 拷贝构造函数。如果缺省，编译器会自动生成。
    m_pValue = new int(*other.m_pValue); // 通过重写拷贝构造函数，手动指向一块新的地址
  };
}
Shape s1;
Shape s2 = s1;         // 调用拷贝构造函数【注意❗】
Shape s3(s1);          // 调用拷贝构造函数
Shape s4 = Shape(s1);  // 调用拷贝构造函数
Shape* ps5 = new Shape(s1);  // 调用拷贝构造函数
```

##### 析构函数 `~`

```cpp
class Example {
  private:
    int* pInt;
  public:
    Example() { // 构造函数
      pInt = new int(2);
    }
    // ~Example() { // 析构函数 —— 只能有一个，不能带参数，在类销毁前做操作。如果缺省，编译程序会自动生成一个空的析构函数
    virtual ~Example() { // 如果类可能被继承，必须将其析构函数设置为虚构函数
      delete pInt;
    }
}
```

::: warning
如果类可能被继承，必须将其析构函数设置为虚构函数。
否则内存释放时可能不会触发结构函数。

```cpp
BaseClass* pc = new SubClass();
delete pc; // ❗如果BaseClass析构函数不是virtual函数，则不会调用SubClass析构函数。
```

:::

##### const成员函数

这种函数不允许修改成员变量

```cpp
class ConstExample {
private:
  int m_a;
public:
  void hello();
  int calculate(int a, int b) const;
}
int ConstExample::calculate(int a, int b) const {
  m_a++; // ❌错误，编译报错
  hello(); // ❌错误，编译报错。因为非const函数可能修改成员变量
  return 1;
}
```

##### const对象

const对象无法调用非const函数

##### this指针

`this->hello()`

`(*this).hello()`

链式调用

```cpp
class X {
public:
  X& add(const X& other) {
    return *this; // 返回了自己的引用的赋值对象 —— 即自己
  }
}
X a(),b(),c();
a.add(b).add(c); // 链式调用
```

##### 友元（friend）

类私有或保护的类成员，可以通过“友元（friend）”供外部的函数或者其他类访问。

```cpp
class A {
  int m_value;
friend class B; // 对类友元
friend void increase(A&); // 对函数友元
}
class B {
  void x(A& a) { // 借助友元调用私有/保护成员
    a.m_value++;
  }
}
void increase(A& a) {
  a.m_value++;
}
```

#### 类继承（Polymorphic，多态）

```cpp
class 派生类名称: public 基类名称 {
  // ...
}

// public:
// 这里public表示派生类从基类成员继承的最大限制范围。
// 如果是public，继承的限制范围不变
// 如果是protected，继承的public方法变为protected方法
// 如果是private，继承的public、protected方法变为private方法
```

::: warning

下面几类成员不会被继承

+ 构造函数、析构函数 —— 虽然不继承，但是在子类被创建或销毁时也会被调用
+ 运算符`=()`成员
+ 类的友元

:::

##### 虚函数（virtual）

```cpp
class BaseClass {
public:
  void greet() {}
  virtual void vgreet() {} // 注意
}
class SubClass:public BaseClass {
public:
  void greet() {
    // 子类实现
  }
  void vgreet() {
    // 子类实现
  }
}
```

方法重写和指针/引用的方法调用

```cpp
SubClass obj;
BaseClass* pBase = &obj; // 指针
BaseClass& refBase = ojb; // 引用

obj.greet();     // 子类重写方法
pBase->greet();  // 父类方法
refBase.greet(); // 父类方法
```

虚函数/虚拟方法：如果指针找到该方法，会找子类是否重写了

```cpp
SubClass obj;
BaseClass* pBase = &obj; // 指针
BaseClass& refBase = ojb; // 引用

obj.vgreet();     // 子类重写方法
pBase->vgreet();  // 子类重写方法
refBase.vgreet(); // 子类重写方法
```

##### 纯虚函数、抽象类（Abstract Class）/接口类（Interface Class）

```cpp
class Shape {
  public:
    virtual void draw()=0; // 末尾的“=0”表示纯虚函数。这种函数需要等待被实现。
}
```

##### 重写标识符（override）

::: tip
添加重载标识符可以检查出一些定义和实现不一致的问题，因此代码规范上一般要求添加。
:::

```cpp
class SubClass {
  void greet() override {
    // xxx...
  }
}
```

##### final

防止类/函数继续被重写

```cpp
class SubClass final { // 防止类被继承
  void greet() override final { // 防止方法被重写
    // xxx...
  }
}
```

### 结构体（Struct）

::: tip
**结构体（Struct）和类（Class）的区别？**
等价
:::

```cpp
struct 结构体名称 {
  变量类型 成员变量;
};
```

```cpp
struct Student {
  char name[100];
  char id[10];
  short gender;
  time_t birthday;
};
struct Student sb = {"Tom", "001", 0, 1};
Student sb2 = {"Tom", "001", 0, 1};
Student sb3{"Tom", "001", 0, 1};
Student sb4 = {}; // 空
```

可以在定义时指定结构体变量

```cpp
struct Student {
  char name[100];
  char id[10];
  short gender;
  time_t birthday;
} sb1, sb2 = {"Tom", "001", 0, 1};
```

#### typedef 定义别名

```cpp
typedef struct _Student {
  char name[100];
  char id[10];
  short gender;
  time_t birthday;
} Sb;
Sb sb = {"Tom", "001", 0, 1};
```

#### 序列化（Serialize）

基础变量结构体可以直接序列化、反序列化到文件、网络等

```cpp
typedef struct _Student {
  char name[100];
  char id[10];
  short gender;
  time_t birthday;
} Sb;
Sb sb = {"Tom", "001", 0, 1};

// 写入
FILE* f = fopen(fname, "wb");
fwrite(&sb, sizeof(sb), sizeof(char), f);
fclose(f);

// 读出
Sb sx = {};
FILE* f = fopen(fname, "rb");
fread(&sx, sizeof(sx), sizeof(char), f);
fclose(f);
```

#### 指定占用字节长度

常用在底层驱动、网络通信中。

```cpp
// 通过指定位数，使整个结构体刚好占用 x 位
struct Header {
  unsigned int flag:1;
  unsigned int sig:1;
  unsigned int sn:4;
  unsigned int reserved:2;
  unsigned int crc:8;
}
```

### 联合（union）

联合（union）在同一时间只能使用一种类型。
联合（union）内存中所占大小由成员类型最大的决定。

```cpp
union MyUnionType {
  unsigned long l;    // 4byte
  unsigned char c[8]; // 8byte --- 最大，决定了联合的大小
  struct {            // 4byte
    unsigned char i;
    unsigned char o;
    unsigned short v;
  } s;
}
```

```cpp
MyUnionType data {};
data.l = 0x3EUL; // 被下面语句覆盖
data.s = {'f','s', 12}; // 覆盖上一条语句
```

### 枚举（enum）

```cpp
enum ColorType {
  Red=1,
  Blue=2,
  Yellow, // 3，取值由上一个加一
  Green=6
};
```

加上`class`关键字，可以避免枚举名称冲突

```cpp
enum class ColorType {
  Red,
  Blue,
  Yellow,
  Green
};
enum class ColorType2 {
  Red,
  Blue,
  Yellow,
  Green
};
ColorType color;
color = ColorType::Red;
```

### 类型转换（Type Casting）

包括：

+ 显式转换
+ 隐式转换

#### 隐式转换

基础类型

```cpp
int a = 2;
float b = a; // 隐式转换

double c = 3.5e39;
b = c; // 隐式转换，但有问题（精度降低）：值超过类型最大值，最终b存储为“无穷大”
```

类继承关系：

略

复制构造函数/转换构造函数：

```cpp
class ClassA {};
class ClassB {
  public ClassB (const Class& a) {}
}

ClassA a;
ClassB b = a;
```

#### 显式转换

```cpp
int a = 2;
float b = (float)a;
```

显式转换的话，编译器不会校验是否有转换函数，这会导致运行问题。
避免上述问题，有以下几种显式引用的方法：

+ `static_cast <转换类型> (表达式)` —— 静态转换，只在编译时有检查，在运行时不做检查。要求转换前后两个类型有继承关系或者转换函数。
+ `dynamic_cast <转换类型> (表达式)` —— 动态转换，在编译、运行两个阶段分别有检查

  + 编译阶段：检查基类类是否支持多态，即是否存在虚函数（`virtual`），否则语法检查阶段报错
  + 运行阶段：
    + 对于指针：检查派生类是否包含基类的**所有成员**，否则派生类指针引用为空（`nullptr`）

      ```cpp
      SubClass *ptr = dynamic_cast<BaseClass *>(&obj);
      if (!ptr) cout<<"无法将指针转换，因为派生类没有包含基类的所有成员";
      ```

    + 对于引用：如果没法转换，会抛出`bad_cast`异常

      ```cpp
      BaseClass bas;
      try {
        SubClass sub = dynamic_cast<SubClass>(bas);
      } catch(bad_cast& e) {
        // ...
      }
      ```

  ::: info
  多态类型转换需要RTTI（Run Time Type Information，运行时类型信息）。
  RTTI信息在编译时生成，通过xx选项控制（默认开启）。
  :::

+ `reinterpret_cast <转换类型> (表达式)` —— 重解释转换，不进行检查，只负责将一个类型的指针转换为另一个类型的指针。

  ```cpp
  // long address = reinterpret_cast<long>(&other); // 编译报错，提示精度丢失，因为地址为longlong类型
  long long address = reinterpret_cast<long long>(&other);
  ```

  ::: info
  应用场景：
  1、与内存和硬件的直接交互的底层程序和接口程序；
  2、与操作系统组件进行交互，需要精确数据格式；
  3、处理网络或多媒体数据的应用程序；
  :::

+ `const_cast <转换类型> (表达式)` —— 常量转换

  ```cpp
  int Hello(char* str) { // 函数声明参数是非常量的
    // ...
  }
  const char* str = "world!";
  hello(const_cast<char*>(str)); // 由于需要调用函数，所以需要转换。
  ```

  ::: warning
  将常量指针转换为非常量指针，在转换后指针指向的地址仍然是不可修改的，否则运行报错。
  :::

### 运算符重载

```cpp
返回类型 operator<<(参数1, 参数2)
// tips：返回值和参数1类型相同的话，可以实现链式调用
cout << a << b << endl; // 链式调用
```

可以重载的运算符：

+ 一元运算符
  `!`
  `++` （包括：前缀、后缀） `--` （包括：前缀、后缀）
  `*` （解引用）
  `&` （取地址）
  `~`
+ 二元运算符
  `+` `-` `*` `/` `%`
  `<` `>` `=` `!=` `>=` `<=` `==`
  `<<` `>>` `&` `^` `|`
  `&&` `||`
  `+=` `-=` `*=` `/=` `%=` `|=`
+ 其他
  `=` 赋值运算符（有默认实现：数值拷贝，调用拷贝构造函数）
  `()` 函数调用运算符
  `->` 间接引用运算符
  `[]` 下标运算符
+ 不能重载的运算符
  `.` 成员运算符
  `*` 函数指针成员运算符
  `::` 域解析运算符
  `? =` 三元运算符
  `sizeof`
  `typeid`

#### 一元运算符重载

例子：为`vector`提供`<<`运算重载，以便打印容器内容

```cpp
#include <iostream>
#include <vector>
int main(void) {
  std::vector<int> numbers(1,3,5,7,9);
  std::cout<<numbers<<std::endl;
}
ostream& operator<<(ostream& o, const vector<int>& numbers) {
  o<<"[";
  unsigned int last = numbers.size()-1;
  for(int i=0;i<last;i++) o<<numbers[i]<<",";
  o<<numbers[last]<<"]";
  return o;
}
```

作为成员方法重载

```cpp
class Complex {
private:
  float r;
  float i;
public:
  Complex(float real, float imaginary):r(real),i(imaginary){};
  Complex operator+(const Complex& other) const {
    return Complex(this->r+other.r,this->+other.i);
  }
}
```

前缀运算符、后缀运算符

```cpp
class ExampleClass {
private:
  int m_a;
  // 前缀增量运算符 ++a
  ExampleClass& operator++() {
    m_a++;
    return *this;
  }
  // 后缀增量运算符 a++
  ExampleClass operator++(int) {
    ExampleClass temp = *this; // 克隆
    operator++();
    return temp;
  }
}
```

#### 二元运算符重载

```cpp
class Complex {
public:
  float r;
  float i;
public:
  Complex(float real, float imaginary):r(real),i(imaginary){};
  // c = a + b;
  Complex operator+(const Complex& other) const {
    return Complex(this->r+other.r,this->i+other.i);
  }
  // c = a + 1f;
  Complex operator+(float r) const {
    return Complex(this->r+r,this->i);
  }
  // c = 1f + a;
  friend Complex operator+(float a, const Complex& b) { // 通过友元，将扩展方法给到fload类型
    return b+a;
  }
}
```

#### 函数调用运算符`()`重载

```cpp
返回类型 operator()(参数列表)
```

e.g.

```cpp
// 一次函数： y = kx + b = fn(x)
struct LinearFunction {
  double k; // 斜率
  double b; // 截距
  double operator()(double x) const {
    return k*x + b;
  }
}
```

#### 间接引用运算符`->`重载

```cpp
返回指针或者引用 operator->()
```

::: info
[智能指针](#id-base-point-smart)就是通过使用“间接引用运算符”重载实现的。
:::

```cpp
struct Complex {
  float r,i;
}
class LocalPtr {
private:
  Complex* m_ptr;
public:
  LocalPtr(Complex* p):m_ptr(p){} // 构造函数
  ~LocalPtr() { // 析构函数
    if(m_ptr) delete m_ptr; // 释放指针引用
  }
  Complex* operator->() { // 重载“间接引用运算符”
    return m_ptr;
  }
  LocalPtr& operator=(const LocalPtr&)=delete;
  LocalPtr(const LocalPtr&)=delete;
}
int main(void) {
  LocalPtr pComplex(new Complex({0.1,0,5}));
  pComplex->i++;
}
```

#### 下标运算符`[]`重载

让对象成员可以像数组一样通过下标访问

```cpp
变量类型 operator[](参数)
```

```cpp
class Array {
private:
  vector<float> numbers;
public:
  Array(vector<float> n):numbers(n){}; // 构造函数
  float operator[] (int i) {
    int size = numbers.size();
    assert(i<size&&i>-size);
    if (i<0) return numbers[size+i];
    return numbers[i];
  }
}
int main() {
  Array arr(vector<float>{1,2,3,4,5,6,7,8,9});
  std::cout<<arr[1]<<std::endl;
  std::cout<<arr[-1]<<std::endl;
}
```

#### 类型转换运算符

```cpp
operator 转换类型();
```

e.g.

```cpp
class Complex {
public:
  float r;
  float i;
public:
  Complex(float real, float imaginary):r(real),i(imaginary){};
  operator float() const {
    return r;
  }
}
int main() {
  Complex a(0.1, 2);
  std::cout<<float(a)<<std::endl;
}
```

### 宏（Macro）

```cpp
#define 标识符 替换表达式
```

::: tip
宏的标识符一般使用“全大写、`_`符号分隔”。
当然，这不是必须的。
:::

#### 宏常量【不推荐】

```cpp
#define MAX_SIZE 1000
```

::: warning

很多库使用宏定义常量。
但这样定义会有很多问题：重名覆盖、难以溯源、等
因此，很多现代的规范要求用静态常量替代这种宏定义常量。

```cpp
static const int MAX_SIZE=999;
```

:::

#### 条件编译

```cpp
#define LARGE_ARRAY

#undef MAXSIZE
#ifdef LARGE_ARRAY
  #define MAXSIZE 60
#else
  #define MAXSIZE 20
#endif
```

#### 宏函数

```cpp
#define 标识符(参数列表) 替换表达式
```

```cpp
// #define MUL(a,b) a*b // bug:MUL(3,1+1) => 3*1+1
#define MUL(a,b) (a)*(b)
```

::: tip
如果没有特殊需求（如rust的内存检查），可以使用[内联函数](#id-base-function-inline)替换宏函数。

```cpp
inline int mul(int a, int b) {return a*b;}
```

优势：

1. 不容易出错
1. 容易调试

:::

#### `#`和`##`

`#` 会将表达式作为字符串变量替换。
e.g.

```cpp
#define PRINT(a) cout<<#a<<" = "<<(a)<<endl;
float a = 3.0;
PRINT(a*2+3); // 输出：a*2+3 = 9
```

`##` 用于连接两个标识符。
e.g.

```cpp
#define MEMBER(type,a) type m_##a
struct Demo {
  MEMBER(int, a);
  MEMBER(float, b);
}
/* 替换结果：
struct Demo {
int m_a;
float m_b;
}
*/
```

#### 换行

```cpp
#define PROPERTY(Type,number) \
  private: \
    Type m_##member; \
  public: \
    const Type& get##member() const{return m_##member;} \
    void set##member(Type m){m_##member = m;}
```

#### 可变参的宏函数

```cpp
#define LOG(o, ...) fprintf(o, "[%s:%d]",__FILE__,__LINE__); \
                    fprintf(o,__VA__ARGS__);
```

#### 常用宏

标识符 | 类型 | 说明
--- | --- | ---
`__LINE__` | int | 源文件行号
`__FILE__` | string | 源文件名
`__DATE__` | string | 编译日期
`__TIME__` | string | 编译时间
`__cplusplus` | int | 编译器版本号，e.g.199711

#### 模板（Template）

::: tip
Java中的泛型。
:::

##### 函数模板

```cpp
template<class 标识符1, class 标识符2, ...> 函数声明;
template<typename 标识符1, typename 标识符2, ...> 函数声明; // 与class等价，只是typename规范的出现时间比class晚。
```

e.g.

```cpp
template<class T>
void swap(T& a, T&b) { // 标准库有更优的实现，这里只做例子
  T temp = a;
  a = b;
  b = temp;
}
int main(void) {
  int a = 10, b = 13;
  // swap<int>(a,b);
  swap(a,b); // <int>可省略，自动推断
  float c = 3f;
  swap<float>(a,c); // 当参数类型不一致，无法自动推断，需要显示声明模板参数类型
  return 0;
}
```

::: tip
优先级上，手动实现的函数比函数模板生成的函数优先级要高。
也就是说：
1、允许标识符一样参数一样的直接函数声明和函数模板生成的函数同时存在；
2、如果同时存在，使用直接函数声明；
:::

###### 隐式实例化（Implicit Instantiation）、显式实例化（Explicit Instantiation）

函数模板默认只有在被调用时才会根据参数类型生成具体函数。

如下面函数模板被指定两种类型的入参，就会生成这两个入参类型对应的具体函数：

```cpp
template<class T> T max(T a, T b) {
  return a>b?a:b;
}
int main() {
  int a = 10, b = 13;
  float c = 1f;
  max(a,b); // 生成： int max(int a, int b) {...}
  max<float>(a,c); // 生成： float max(float a, float b) {...}
}
```

也可以提前显式实例化：

```cpp
template float max<float>(float a, float b);
```

###### 显式特化

`template<> void f<int>(int)`

可以对特殊的类型`T`做不同的实现

##### 类模板

```cpp
template<typename T>
class Vector3 {
private:
  T m_vec[3]; // 成员属性
public:
  Vector3(T v1, T v2, T v3) { // 构造方法
    m_vec[0] = v1;
    m_vec[1] = v2;
    m_vec[2] = v3;
  }
  T getMax(); // 成员函数（定义）
}
template<typename T>
T Vector<T>::getMax() { // 成员函数（实现）
  T temp = m_vec[0]>m_vec[1]?m_vec[0]:m_vec[1];
  return temp>m_vec[2]?temp:m_vec[2];
}

Vector3<int> vec1(1,2,3);         // 初始化
Vector3<float> vec2(1.1,2.1,3.1); // 初始化
```

::: info
支持默认值写法： `template<typename T=char> ....`。
调用： `Vector3<>` = `Vector3<char>`
:::

##### 类模板特化、偏特化/部分特化（Partial Specialization）

::: tabs

@tab 一般

```cpp
template<typename T>
class Vector3 {
private:
  T m_vec[3]; // 成员属性
public:
  Vector3(T v1, T v2, T v3) { // 构造方法
    m_vec[0] = v1;
    m_vec[1] = v2;
    m_vec[2] = v3;
  }
  T getMax(); // 成员函数（定义）
}
template<typename T>
T Vector<T>::getMax() { // 成员函数（实现）
  T temp = m_vec[0]>m_vec[1]?m_vec[0]:m_vec[1];
  return temp>m_vec[2]?temp:m_vec[2];
}
```

@tab 特化

```cpp
template<>
class Vector3<char> {
private:
  char m_vec[4]; // 不同
public:
  Vector3(char v1, char v2, char v3) {
    m_vec[0] = v1;
    m_vec[1] = v2;
    m_vec[2] = v3;
    m_vec[3] = 0; // 不同
  }
  const char* asString() const {
    return m_vec;
  }
}
```

:::

##### 非类型参数（None-type Parameter）

::: info
坑：非类型取值不能是变量。（编译器编译过程无法识别）
:::

e.g.

```cpp
template<typename T, int N>
class Vector {
private:
  T m_values[N];
public:
  Vector(T values[N]) {
    for(int i=0;i<n;i++) {
      m_values[i] = values[i];
    }
  }
  T getMax() {
    T maxValue = m_values[0];
    for(T v:m_values) maxValue = maxValue>v?maxValue:v;
    return maxValue;
  }
}

float values[5] = {1.0f,-0.3f,0.7f,0.8f,1.2f};
const int n = 5; // 必须是常量，不能是变量。是变量则编译器报错
Vector<float,n> vector5(values);
cout<<vector5.getMax();
```

##### 模板类型参数

::: tip
这种定义方式在tls库/工具库里经常见到，要理解作用。
:::

```cpp
// <template <template 模板参数类型> typename 参数名>
template<template <typename T> typename 参数名>
class 类名 {
  // ...
}
```

e.g.

```cpp
template<template <typename> typename Container, typename T> // 定义两个模板类型：Container和T。其中Container类型是模板类类型
class Wrapper {
private:
  Container<T> m_values;
public:
  Wrapper(const Container<T>& o):m_values(o){}
  void print() {
    for(auto v:m_values) cout<<v<<endl;
  }
}

int main() {
  // 1
  vector<int> ls = {1,23,4,5,6,7};
  Wrapper<vector, int> example(ls);
  example.print();
  // 2
  set<int> ss = {1,2,3,4,5,5,1};
  Wrapper<set,int> example2(ss);
  example2.print();
}
```

### 命名空间（namespace）

处理并行开发中的重名问题

```cpp
namespace 标识符 {
  // 类、对象、变量、函数等等都可以放在这里
}
```

#### 限定名（Qualified Name）、using

```cpp
namespace MyNamespace {
  int foo(int a, int b) {
    return a + b;
  }
}

// 限定名（Qualified Name） MyNamespace::foo
MyNamespace::foo(0,1);

// 通过 using 引入命名空间的全部内容
using namespace MyNamespace
foo(0,1);
// 或者 using 特定标识符
using namespace MyNamespace::foo
foo(0,1);
```

using 作用范围

```cpp
int func(void) {
  using namespace MyNamespace // 作用范围是方法内部
}
```

::: tip
对于较大的文件，建议用尽量少的作用范围，以避免标识符冲突问题
:::

#### 默认命名空间（Global Namespace，全局命名空间）

```cpp
void foo() {
  cout<<"Global Foo"<<endl;
}
namespace MyNamespace {
  void foo() {
    cout<<"MyNamespace Foo"<<endl;
  }
}

using MyNamespace::foo;
int main(void) {
  foo();   // MyNamespace Foo
  ::foo(); // Global Foo
}
```

### 异常（Exception）

```cpp
try {
  // ...代码
  throw exception; // 异常
} catch(Type e) {
  // ...代码
}
```

e.g.

```cpp
float divide(float a, float b) {
  if (b==0) // bug float!=0
    throw runtime_err("Divided by Zero!");
  return a/b;
}
int main(void) {
  try {
    int c = devide(3.1,0);
    cout<<"c=."<<c<<endl;
  } catch(runtime_error& e) {
    cout<<"exception:"<<e.what()<<endl;
  }
  return 0;
}
```

#### throw

c++可以将各种变量、对象、指针作为异常抛出。

:::::: tabs

@tab string

```cpp
int main(void) {
  try {
    throw "这是一个字符串异常";
  } catch(const char* e) {
    cout<<e<<endl;
  }
}
```

@tab int

```cpp
int main(void) {
  try {
    throw 2;
  } catch(int e) {
    cout<<e<<endl;
  }
}
```

@tab struct

```cpp
struct my_exception {
  string mess;
  unsigned int errorno;
}
int main(void) {
  try {
    throw my_exception("My exception");
  } catch(my_exception e) {
    cout<<e.mess<<":"<<e.errorno<<endl;
  }
}
```

@tab 函数指针

```cpp
string exception_func() {
  return "这是一个异常";
}
int main(void) {
  try {
    throw exception_func;
  } catch (string (*e)()) {
    cout<<e()<<endl;
  }
}
```

::::::

虽然可以使用自定义的异常类型，但是一般使用std提供的异常类 `std::exception`。

```cpp
class my_exception:public exception {
private:
  unsigned int error_code;
  string reason;
public:
  my_exception(const string& message, int code):reason(message),error_code(code);
  virtual const char* what() const noexcept override {
    return reason.c_str();
  }
}
int main(void) {
  try {
    throw my_exception("自定义异常", 1000);
  } catch(my_exception& e) {
    const<<e.what()<<endl;
  }
  return 0;
}
```

#### catch

```cpp
int main(void) {
  try {
    // 代码。。。。。。。。。
  } catch(runtime_error& e) {
    // 运行时异常
  } catch(my_exception& e) {
    // 自定义异常
  } catch(exception& e) {
    // std通用异常（一般正规异常都会继承这个类，当然只是约定俗成，所以会有后面的catch(...)）
  } catch(...) {
    // 其他未知的异常（一般对于未知异常应该继续往外抛出让能处理的处理，而非私自捕获又不处理，导致问题被隐藏而非解决或被告知）
    throw; // 要抛出这种未知异常，直接写throw即可，
  }
  return 0;
}
```

#### 资源回收问题

当遇到异常，资源回收是一个问题：

```cpp
class MyResource {
private:
  string m_name;
public:
  MyResource(const char* name):m_name(name) {cout<<"MyResource Construct"<<m_name<<endl;}
  virtual ~MyResource() {cout<<"MyResource Destruct"<<m_name<<endl;}
}
void doSomething() {
  MyResource res = new MyResource("[doSomething]"); // 创建在堆中，需要手动释放
  throw runtime_error("xxx");
  delete *pRes; // 由于上一行代码抛出异常，导致释放语句未被执行，导致内存泄漏
}
int main() {
  try {
    MyResource res("[main]"); // 创建在栈中，会自动释放
    doSomething();
  } catch(runtime_error& e) {
    cout<<e.what()<<endl;
  }
}
```

针对这种问题，可以通过“智能指针”解决

```cpp
void doSomething() {
  shared_ptr<MyResource> pRes(new MyResource("[doSomething]")); // 智能指针：当异常出现，智能指针会被销毁，在之前智能指针会调用MyResource的析构函数
  throw runtime_error("xxx");
  delete *pRes;
}
```

::: tip
上述资源管理方式称为“[RAII（Resource Acquisition Is Initialization，资源获取即初始化）](#id-cpp-raii)”。
这种方式在现在C++编程中应用广泛。
:::

## 进阶玩法

### 回调函数 {id=id-xx-function-callback}

回调函数可以有如下多种形式：

::: tabs

@tab 函数指针形式

```cpp
bool compare(float a, float b) {
  return a < b;
}
main(void) {
  vector<float> numbers{0.1,3,0.0,-3.4,7,100,-100};
  sort(numbers.begin(),numbers.end(),compare);
  for (auto number:numbers)
    cout<<number<<",";
}
```

@tab 重载类函数形式

这种方式更加灵活

```cpp
struct Compare {
  bool compare(float a, float b) {
    return a < b;
  }
}
main(void) {
  vector<float> numbers{0.1,3,0.0,-3.4,7,100,-100};
  sort(numbers.begin(),numbers.end(),Compare());
  for (auto number:numbers)
    cout<<number<<",";
}
```

@tab lambda形式

todo

:::

### RAII（Resource Acquisition Is Initialization，资源获取即初始化） {id=id-cpp-raii}

todo <https://www.bilibili.com/video/BV1Fg411M7ZS/>

## 标准库

### 容器

alias： 集合

C++标准库里的所谓“容器”是用来存储“对象”的。

C++标准库里的容器使用“类模板”实现，可以支持多种类型的元素。

常用容器：

+ 序列容器（Sequence Container） —— 可按顺序读取其中元素
  + array
  + vector
  + deque
  + list
+ 关联容器（Associative Container） —— 通过键值映射来访问元素
  + map
  + set

::: tip
选择方式：

+ 如果需要高效的随机存取，而不在乎插入、删除的效率 —— 使用 vector
+ 如果需要高效的随机存取，且需要高效在**两端**插入、删除数据 —— 使用 deque
+ 如果不关心随机存取效率，而需要高效在**任意位置**插入、删除数据 —— 使用list

:::

#### 数组（array）

array是固定大小的数组。
与c风格数组不同的是，array数组提供复制、迭代等更多的方法。

`template<typename T, std::size_t N>std::array`

```cpp
TEST(test_container, array_iterator) {
  const int n = 5;
  std::array<float, n> numbers{1,2,3,4,5};
  for (auto v : numbers) {
    std::cout<<v<<" ";
  }
}
```

#### 向量（vector） {id=id-std-col-vector}

动态数组，内存连续，支持随机访问、高效末尾输入/删除

```cpp
#include <vector>
template <class T, class Alloc = allocator<T>> class vector;
```

##### 初始化

```cpp
// 等价：
vector<int> numbers = {1,2,3,4,5};
vector<int> numbers{1,2,3,4,5};
vector<int> numbers({1,2,3,4,5});
```

::: tip
vector有构造函数`vector(std::initializer_list<int> __l, const std::allocator<int> &__a = std::vector<int>::allocator_type())`。
而`vector<int> numbers{1,2,3,4,5}`写法中的数组会被转换为`__l`的值，所以这种写法等价于`vector<int> numbers({1,2,3,4,5})`写法。
:::

```cpp
const int size = 5;
float value = 3.0;
vector<float> values(size, value);
// 等价
vector<float> values{3f,3f,3f,3f,3f};
```

##### 元素访问

```cpp
vector<float> values{3f,3f,3f,3f,3f};

// 下标（不做越界检查，直接终止进程：Signal received: SIGABRT）
numbers[1]++;

// at方法，越界抛出异常
try {
  numbers.at(10) = 2000;
} catch(out_of_range& e) {
  // ...
}
```

##### 迭代器 （`vector<T>::iterator/begin/end/rbegin/rend`）

```cpp
vector<int> numbers {1,2,3,4,5};
vector<int>::iterator it;
for(it = numbers.begin();it!=numbers.end();++it) {
  cout << *it << ",";
}

// 逆向
vector<int>::iterator it;
for(it = numbers.end()-1;it>=numbers.begin();--it) {
  cout << *it << ",";
}

// 逆向（写法二：反向迭代器）
vector<int>::reverse_iterator it;
for(it = numbers.rbegin();it!=numbers.rend();++it) {
  cout << *it << ",";
}
```

###### 迭代器失效问题

迭代器在[增加/删除](#id-std-container-vector-insert)后需要更新，否则结果会错误（甚至导致死循环）。

```cpp
#include <vector>
#include <iostream>
using namespace std;
int main(void) {
  vector<int> numbers{1,2,3,4,5,6,7,8,9};
  auto it = numbers.begin();
  cout<<"插入前：*it="<<*it<<endl; // 1
  auto it2 = numbers.insert(it, 99);
  cout<<"插入后：*it="<<*it<<endl; // 666666666666666666 （随机乱码）
  cout<<"插入后：*it2="<<*it2<<endl; // 99
  return 0;
}
```

##### 插入（`insert push_back emplace`）、移除（`erase pop_back`）、提前保留n大小空间（`reserve(std::size_t n)`） {id=id-std-container-vector-insert}

```cpp
TEST(test_container, use_emplace) {
  class Complex {
  protected:
    double m_i;
    double m_r;
  public:
    Complex(double i, double r) : m_i(i), m_r(r) {
      std::cout << "构造函数 " << (unsigned long long) this << std::endl;
    }
    Complex(const Complex& other) : m_i(other.m_i), m_r(other.m_r) {
      std::cout << "拷贝构造函数 " << (unsigned long long) this << std::endl;
    }
    ~Complex() {
      std::cout << "解构函数 " << (unsigned long long) this << std::endl;
    }
  };
  std::vector<Complex> values;
  values.reserve(999); // 预分配内存，避免扩容
  std::cout << "==== emplace " << values.size() << std::endl;
  values.emplace( // "安放"
    values.end(), // 插入的位置
    0.1, 0.2 // 构造函数参数
  );
  // 输出：
  // 构造函数 a
  std::cout << "==== insert " << values.size() << std::endl;
  values.insert( // "插入"
    values.end(),
    Complex(0.2, 0.4)
  );
  // 输出：
  // 构造函数 a
  // 拷贝构造函数 c
  // 解构函数 a
  std::cout << "==== end " << values.size() << std::endl;
  // 解构函数 a
  // 解构函数 c
}
```

#### 双端队列（deque）

动态数组，连续数组结构，支持随机访问、高效首尾插入/删除

::: info
deque提供两级数组结构：第一级类似vector存储数据；另一级维护容器的首位地址。
:::

`template <class T, class Alloc = allocator<T>> class deque;`

```cpp
#include <iostream>
#include <deque>
using namespace std;
int main(void) {
  deque<float> dq{1,2,3,4,5};
  dq.push_front(0.2); // 队列头加数据
  dq.push_back(0.3); // 队列尾加数据
}
```

#### 链表（list）

动态数组，双向链表结构（非连续存储结构），支持高效首位，不支持随机访问

`template <class T,class Alloc = allocator<T>> class list;`

```cpp
std::list<int> ls {1,2,3,4,5};
show(&ls); // list[1,2,3,4,5]
auto it = ls.begin();
it++; // 支持正向
std::cout << *it << std::endl; // 2
auto it2 = ls.insert(it, -1); // 插入，返回insert指针
std::cout << *it << std::endl; // 2
std::cout << *it2 << std::endl; // -1
show(&ls); // list[1,-1,2,3,4,5]
it2++;
std::cout << *it2 << std::endl; // 2
auto it3 = ls.erase(it2); // 删除，返回next指针
std::cout << *it3 << std::endl; // 3
it3--; // 支持逆向
std::cout << *it3 << std::endl; // -1
show(&ls); // list[-1,3,4,5]
```

#### 集合（set/unordered_set）

集合中的值都是唯一的。

##### set

和map一样数据结构是红黑树（tree、bucket）实现的key值。

```cpp
template<
  class Key,
  class Compare = std::less<Key>,
  class Allocator = std::allocator<Key>
> class set;
```

#### multiset

todo

#### 键值对（map/unordered_map）

##### map

按Key排序升序排序

红黑树（平衡二叉树）

搜索、增加/删除 时间复杂度 `O(logN)`

```cpp
template<
  class Key, // 键
  class T,   // 值
  class Compare = std::less<key>, // 比较元素的类
  class Allocator = std::allocator<std::pair<const Key, T>> // 内存分配类
> class map;
```

Demo

```cpp
TEST(test_container, map_print) {
  std::map<std::string, float> prices{
    {"苹果", 5.5},
    {"香蕉", 6.9},
    {"葡萄", 8.5},
  };
  {
    // 插入
    prices["火龙果"] = 9.6;
  }
  {
    // 插入（方式二）
    auto res = prices.insert(std::make_pair("桔子", 3.0));
    auto insert_key = res.first->first;
    auto insert_value = res.first->second;
    auto insert_success = res.second;
    std::cout << "[insert] res:key=" << insert_key << ",value=" << insert_value << ",success=" << insert_success << std::endl;
  }
  // 遍历
  for (const auto& p : prices)
    std::cout << "[show] " << p.first << ":" << p.second << std::endl;
  // 删除
  auto erase_num = prices.erase("苹果");
  std::cout << "[delete] 删除数量:" << erase_num << std::endl;
  // 查找
  {
    // 查找固定值 find
    auto it = prices.find("香蕉");
    if (it != prices.end())
      std::cout << "[find] key=" << it->first << ",value=" << it->second << std::endl;
  }
  {
    // 查找范围 upper_bound/lower_bound
    // key1 , key2 , ... keyn , upper , other , ...
    // [ ------ found ------- ]  上限  [ ignore....... ]
    auto upper = prices.upper_bound("火龙果"); // 上限
    for(auto it = prices.begin(); it!=upper; it++)
      std::cout << "[upper] key=" << it->first << ",value=" << it->second << std::endl;
  }
  {
    // 查找范围 equal_range
    // key1 , key2 , ... keyn , range , other , ...
    // [ ---------- left ----------- ] [ ------ right ------ ]
    // [ ------ left ------- ] middle  [ ------ right ------ ]
    auto range = prices.equal_range("火龙果"); // 上限
    for(auto it = prices.begin(); it!=range.first; it++)
      std::cout << "[range-left] key=" << it->first << ",value=" << it->second << std::endl;
    std::cout << "[range-middle] key=" << range.first->first << ",value=" << range.first->second << std::endl;
    for(auto it = range.second; it!=prices.end(); it++)
      std::cout << "[range-right] key=" << it->first << ",value=" << it->second << std::endl;
  }
}
```

##### multimap

与map不同的是一个key可以有多个value

```cpp
template<
  class Key,
  class T,
  class Compare = std:less<Key>,
  class Allocator = std::allocator<std::pair<const Key, T>>
> class multimap;
```

```cpp
TEST(test_container, multimap_print) {
  std::multimap<float, std::string> prices{
    {5.5f, "苹果"},
    {6.9f, "香蕉"},
    {8.5f, "葡萄"},
    {3.5f, "桔子🍋"},
  };
  prices.insert(std::make_pair(3.5f, "橙子🍊"));
  auto r = prices.equal_range(3.5f);
  for (auto it = r.first; it!=r.second; it++) {
    std::cout << "[equal_range] key=" << it->first << ",value=" << it->second << std::endl;
  }
}
```

## 函数（function）

在STL标准库中，提供了一些函数包装的模板，这些模板可以对函数或者调用的对象进行包装，方便其他函数调用

### std::function

`std::function`是一个通用的多态函数封装器，它将一个可调用的对象（比如函数指针、函数对象、Lambda函数等）进行封装，方便在后续的代码中调用。

#### 对外声明

```cpp
template<typename _Signature>
  class function;
```

具体声明

```cpp
template<class R, class... Args>
  class function<R(Args...)>
  // R operator()(Args... args)
```

```cpp
function<R(Args...)> fname = target;
```

demo

```cpp
double multiply(double a, double b) {
  return a*b;
}
TEST(test_std_function, demo_normal_function) {
  // 普通函数
  std::function<double(double,double)> func1 = multiply;
  double res = func1(1.1, 2.3);
  std::cout << "[demo normal function] " << res << std::endl;
}

struct Linear {
  float k_, b_;
  Linear(float k, float b) : k_(k), b_(b) {}
  float f(float x) {
    return k_ * x + b_;
  }
};
TEST(test_std_function, demo_class_function) {
  Linear l(1.2, 2.3);

  // 成员函数
  std::function<float(Linear&, float)> mf = &Linear::f;
  float res = mf(l, 5);
  std::cout << "[demo class function] " << res << std::endl;

  // 成员属性
  std::function<float(Linear&)> k = &Linear::k_;
  std::cout << "[demo class property] " << k(l) << std::endl;
}
```

#### 类型擦除模式

只要可转换的可以忽略类型差异

```cpp
int add(int a, int b) {
  return a+b;
}
struct Substruct {
  float operator()(float a, float b) {
    return a-b;
  }
};
TEST(test_std_function, demo_type_remove) {
  std::map<char, std::function<double(double,double)>> calculator {
    {'+', add},
    {'-', Substruct()},
    {'*', [](int a, int b) -> int {return a*b;}}, // 类型擦除
  };
  std::cout << "[demo type_remove] " << calculator['+'](12.6, 3.9) << std::endl; // 15
}
```

### std::mem_fn

这个模板可以进一步简化类方法的调用。

```cpp
template<class M, class T>
/* unspecified */ mem_fn(M T::* pm) noexcept;
```

Demo

```cpp
struct Foo {
  float w;
  float calculate(float a, float b) {return 2*a+w*b;}
  Foo& operator+=(float a) {
    w+=a;
    return *this;
  }
};
TEST(test_std_function, demo_mem_fn) {
  Foo f{1.0};
  // 类函数
  auto memfn = std::mem_fn(&Foo::calculate); // 直接用auto接受类型
  float res = memfn(f, 2.1, 3.2);
  std::cout << "[demo mem_fn] calc=" << res << std::endl;
  // 类函数 function （对比）
  std::function<float(Foo&, float, float)> memfn4diff = &Foo::calculate;
  float res4diff = memfn4diff(f, 2.1, 3.2);
  std::cout << "[demo mem_fn4diff] calc=" << res4diff << std::endl;
  ASSERT_EQ(res, res4diff);
  // 类操作
  auto op_add_assign = std::mem_fn(&Foo::operator+=);
  op_add_assign(f, 2.0); // 1.0 + 2.0 = 3.0
  std::cout << "[demo mem_fn] w=" << f.w << std::endl;
}
```

### std::bind/std::cref

```cpp
template<class F, class... Args>
  bind(F&& f, Args&&... args);
```

`std::bind`是个函数模板，它用来生成一个函数调用的转发包装器（也就是一个函数对象）。
调用包装器时就相当于调用它所包装的函数或者对象f

`std::cref`是在使用`std::bind`函数模板是绑定参数变量的函数。（全称"Content Reference"）

```cpp
int sum(int a, int b, int c) {
  return a+b+c;
}
TEST(test_std_function, demo_bind) {
  int res1;
  {
    // 默认参数
    auto f = std::bind(sum, 1, 2, 3);
    res1 = f();
    std::cout << "[bind] res=" << res1 << std::endl;
  }
  int res2;
  {
    // 预置参数
    auto f = std::bind(sum, 1, std::placeholders::_1, 3);
    res2 = f(2);
    std::cout << "[bind] res=" << res2 << std::endl;
  }
  ASSERT_EQ(res1, res2);
  int res3;
  {
    // 变量参数
    int n = 22;
    auto f = std::bind(sum, 1, 2, std::cref(n)); // 注意：不能直接传入n变量
    n = 3;
    res3 = f();
    std::cout << "[bind] res=" << res3 << std::endl;
  }
  ASSERT_EQ(res1, res3);
}
```
