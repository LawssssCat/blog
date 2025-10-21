#include <functional>
#include <iostream>

#include <gtest/gtest.h>
#include <map>

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
  // 类函数 mem_fn
  auto memfn = std::mem_fn(&Foo::calculate); // 直接用auto接收值
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
