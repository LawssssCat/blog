#include <any>
#include <memory>
#include <utility>
#include <ostream>
#include <vector>

#include <gtest/gtest.h>

class Rectangle {
public:
  Rectangle(double &w, double &h):width(w),height(h){
    show("create");
  }
  ~Rectangle(){
    show("free");
    width = 0;
    height = 0;
  }
  double area() {
    return width*height;
  }
  void show(std::string flag) {
    std::cout<<"rectangle:"<<flag<<":"<<(unsigned long long)this<< ":" <<width<<"x"<<height<<"="<<area()<<std::endl;
  }
  void show() {
    show("area");
  }
private:
  double& width;  // 引用
  double& height; // 引用
};

TEST(test_pointer, demo_unique_ptr) {
  double a = 3;
  double b = 4;
  std::unique_ptr<Rectangle> pDemo(new Rectangle(a, b));
  pDemo->show();
  ASSERT_EQ(pDemo->area(), 12);
}

TEST(test_pointer, demo_unique_ptr_free) {
  double a = 3;
  double b = 4;
  std::unique_ptr<Rectangle> pDemo(new Rectangle(a, b));
  pDemo = nullptr; // 释放
  ASSERT_EQ(a, 0); // 由于析构方法
  ASSERT_EQ(b, 0);
}

TEST(test_pointer, demo_unique_ptr_move) {
  // mock
  double a = 3;
  double b = 4;
  std::unique_ptr<Rectangle> pDemo1(new Rectangle(a, b));
  double a2 = 1;
  double b2 = 1;
  std::unique_ptr<Rectangle> pDemo2(new Rectangle(a2, b2));
  pDemo1->show();
  pDemo2->show();
  // test
  // pDemo1 = pDemo2; // Overload resolution selected deleted operator '='clang(ovl_deleted_oper)
  pDemo1 = std::move(pDemo2); // 对象1释放
  ASSERT_EQ(pDemo2, nullptr); // 地址2为空
  ASSERT_EQ(a, 0); // 由于析构方法
  ASSERT_EQ(b, 0);
  std::cout << "move end ..." << std::endl;
  pDemo1->show();
  // std::cout << "rectangle:area=" << pDemo1->area() << std::endl;
  // std::cout << "rectangle:area=" << pDemo2->area() << std::endl;
  ASSERT_EQ(a2, 1);
  ASSERT_EQ(b2, 1);
}

TEST(test_pointer, demo_unique_ptr_reset) {
  double a = 3;
  double b = 4;
  std::unique_ptr<Rectangle> pDemo1(new Rectangle(a, b));
  // pDemo1 = pDemo2; // Overload resolution selected deleted operator '='clang(ovl_deleted_oper)
  pDemo1.reset(new Rectangle(a, b)); // 释放
  ASSERT_EQ(a, 0); // 由于析构方法
  ASSERT_EQ(b, 0);
}

TEST(test_pointer, demo_scene_01) {
  // std::vector<std::shared_ptr<double>> todo; // 智能指针：释放范围
  std::vector<double*> todo; // 智能指针：释放范围
  std::vector<std::unique_ptr<Rectangle>> col;
  int n = 10;
  for(int i=0; i<n; i++) {
    // 问题代码
    // double a = 1;
    // double b = i;
    // auto x = new Rectangle(a, b);
    // [问题解决方式1]：new新double，手动维护释放
    double* a = new double(1); // 智能指针：自动释放
    double* b = new double(i);
    todo.push_back(a); // 智能指针：避免被释放
    todo.push_back(b);
    auto x = new Rectangle(*a, *b);
    // [问题解决方式2]：new新double，智能指针维护释放
    // std::shared_ptr<double> a = std::shared_ptr<double>(new double(1)); // 智能指针：自动释放
    // std::shared_ptr<double> b = std::shared_ptr<double>(new double(i));
    // todo.push_back(a); // 智能指针：避免被释放
    // todo.push_back(b);
    // auto x = new Rectangle(*a, *b);
    // [问题解决方式3]：todo
    // auto fn = [](double a, double b) {
    //   return new Rectangle(a, b);
    // };
    // auto x = fn(1, i);
    // 放入
    // std::cout << "item:area:" << (unsigned long long)x << ":" << x->area() << std::endl;
    col.push_back(std::unique_ptr<Rectangle>(x));
  }
  // foreach
  for(auto it = col.begin(); it != col.end(); ++it) {
    (*it)->show();
    // std::cout << "item:area:" << (unsigned long long)(it->get()) << ":" << (*it)->area() << std::endl;
  }
  // free
  for(auto it = todo.begin(); it != todo.end(); ++it) {
    auto x = *it;
    ASSERT_NO_THROW([x]() {delete x;}()); // 释放
    // ASSERT_ANY_THROW([x]() {delete x;}()); // 重复释放问题
  }
}

static int x_a = 0;
static int x_b = 0;
class A {
public:
  A() { // 创建
    std::cout<<"create:A:"<<(unsigned long long)this<<std::endl;
  }
  ~A() { // 销毁
    std::cout<<"free:A:"<<(unsigned long long)this<<std::endl;
    x_a += 1;
  }
  A(const A& a) { // 复制
    std::cout<<"copy:A:"<<(unsigned long long)this<<std::endl;
  }
};
class B {
public:
  A m_a;
  A& m_a2;
  B(A& a, A& b): m_a(a), m_a2(b) {
    std::cout<<"create:B:"<<(unsigned long long)this<<std::endl;
  }
  ~B() {
    std::cout<<"free:B:"<<(unsigned long long)this<<std::endl;
    x_b += 1;
  }
};
TEST(test_pointer, class_free) {
  A* a = new A();
  A* a2 = new A();
  B* b = new B(*a, *a2);
  // test
  std::cout<<"B.A:"<<(unsigned long long)&(b->m_a)<<std::endl;
  std::cout<<"_.A:"<<(unsigned long long)a<<std::endl;
  std::cout<<"B.A2:"<<(unsigned long long)&(b->m_a2)<<std::endl;
  std::cout<<"_.A2:"<<(unsigned long long)a2<<std::endl;
  ASSERT_NE(&(b->m_a), a); // 创建新的
  ASSERT_EQ(&(b->m_a2), a2); // 没有创建新的，只引用地址
  // test
  delete b;
  ASSERT_EQ(x_a, 1);
  ASSERT_EQ(x_b, 1);
  delete a; // 只要有外部引用，就不会被自动销毁
  ASSERT_EQ(x_a, 2);
  ASSERT_EQ(x_b, 1);
  delete a2;
  ASSERT_EQ(x_a, 3);
  ASSERT_EQ(x_b, 1);
}
