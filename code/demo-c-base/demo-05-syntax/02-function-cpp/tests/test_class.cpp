#include <iostream>
#include <vector>

#include <gtest/gtest.h>

#include "Singleton.h"

// class:构造方法、拷贝构造方法、析构方法
static int n = 0;
class Shape {
public:
  Shape(int nn) { // 构造函数
    n = nn;
  }
  Shape(Shape& x) { // 拷贝构造函数
    n++;
  }
  ~Shape() { // 析构函数
    std::cout<<"free"<<std::endl;
    n = -1;
  }
};
TEST(test_class, construct_copy) {
  Shape p(0);
  Shape p2 = p;
  ASSERT_EQ(n, 1); // 因为调用了一次“拷贝构造函数”
  // delete &p2;
  // ASSERT_EQ(n, -1); // 因为调用了一次“析构构造函数”
}


// class:函数继承、属性初始化（语法糖）
class SubShape : private Shape {
public:
  int m_xx;
  SubShape()
  :Shape(0) // 调用父类构造方法
  ,m_xx(99) // 初始化属性
  {}
};
TEST(test_class, sub_class) {
  SubShape p;
  SubShape p2 = p;
  ASSERT_EQ(n, 1); // 因为调用了一次“拷贝构造函数”
  ASSERT_EQ(p.m_xx, 99);
}


// 虚函数
class BaseClass {
public:
  int greet() {
    return 1;
  }
  virtual int vgreet() { // 虚拟方法：如果指针找到该方法，会找子类是否重写了
    return 1;
  }
};
class SubClass:public BaseClass {
public:
  int greet() {
    return 2;
  }
  int vgreet() {
    return 2;
  }
};
TEST(test_class, virtual_method) {
  SubClass sb;
  BaseClass* pbb = &sb; // 指针
  BaseClass& refbb = sb; // 引用
  // 普通方法
  ASSERT_EQ(sb.greet(), 2);
  ASSERT_EQ(pbb->greet(), 1);  // ❗调用了“父类方法”
  ASSERT_EQ(refbb.greet(), 1); // ❗调用了“父类方法”
  // 虚拟方法
  ASSERT_EQ(sb.vgreet(), 2);
  ASSERT_EQ(pbb->vgreet(), 2);
  ASSERT_EQ(refbb.vgreet(), 2);
}

// 单例模式
TEST(test_class, single_getInstance) {
  Singleton* s1 = Singleton::getInstance();
  Singleton* s2 = Singleton::getInstance();
  ASSERT_EQ(s1, s2);
}

// 运算符重载
ostream& operator<<(ostream& o, const vector<int>& numbers) {
  o<<"[";
  unsigned int last = numbers.size()-1;
  for(int i=0;i<last;i++) o<<numbers[i]<<",";
  o<<numbers[last]<<"]";
  return o;
}
TEST(test_class, operation_overload) {
  std::vector<int> numbers = {1,2,3,4,5};
  // 写法1
  std::cout << numbers << std::endl;
  // 写法2
  operator<<(std::cout, numbers);
}


// 函数引用
static int x = 0;
class MyClass {
public:
  MyClass(int num) {
    n = num;
    std::cout << "init MyClass, x=" << n << std::endl;
  }
  void hello() {
    std::cout << "hello, x=" << n << std::endl;
  }
private:
  int n;
};
void show(MyClass* x) {
  std::cout << "address:" << (unsigned long long) x << std::endl;
  x->hello();
}
TEST(test_class, class_reference) {
  std::cout << "========= new class" << std::endl;
  // MyClass a = new MyClass(); // No viable conversion from 'MyClass *' to 'MyClass' (fix available)clang(typecheck_nonviable_condition)
  // MyClass& a = new MyClass(); // Non-const lvalue reference to type 'MyClass' cannot bind to a temporary of type 'MyClass *'clang(lvalue_reference_bind_to_temporary)
  MyClass* a = new MyClass(++x);
  show(a);
  std::cout << "========= 空指针" << std::endl;
  MyClass* b;
  show(b);
  MyClass* b1;
  show(b1);
  // MyClass* b3 = nullptr;
  // show(b3); // Signal received: SIGSEGV
  std::cout << "========= 栈" << std::endl;
  MyClass c(++x);
  show(&c);
}

