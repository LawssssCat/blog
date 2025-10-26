#include <cstddef>
#include <memory>
#include <utility>
#include <ostream>
#include <vector>

#include <gtest/gtest.h>

// =====================================
// unique_ptr
// =====================================

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
  Rectangle* r = new Rectangle(a, b);
  std::unique_ptr<Rectangle> pDemo(r);
  // std::unique_ptr<Rectangle> pDemo2(r); // 不能两个unique_ptr指针指向同一个地址！坑：编译不报错，运行报错！
  pDemo->show();
  ASSERT_EQ(pDemo->area(), 12);
}

TEST(test_pointer, demo_unique_ptr_null) {
  std::unique_ptr<Rectangle> pDemo(nullptr);
  int n = 0;
  if (!pDemo) {
    n = 1;
    std::cout<<"--- nullptr"<<std::endl;
  }
  ASSERT_EQ(n, 1);
  ASSERT_EQ(pDemo, nullptr);
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
  std::vector<std::shared_ptr<double>> todo; // 智能指针：释放范围
  // std::vector<double*> todo; // 智能指针：释放范围
  std::vector<std::unique_ptr<Rectangle>> col;
  int n = 10;
  for(int i=0; i<n; i++) {
    // 问题代码
    // double a = 1;
    // double b = i;
    // auto x = new Rectangle(a, b);
    // [问题解决方式1]：new新double，手动维护释放
    // double* a = new double(1); // 智能指针：自动释放
    // double* b = new double(i);
    // todo.push_back(a); // 智能指针：避免被释放
    // todo.push_back(b);
    // auto x = new Rectangle(*a, *b);
    // [问题解决方式2]：new新double，智能指针维护释放
    std::shared_ptr<double> a = std::shared_ptr<double>(new double(1)); // 智能指针：自动释放
    std::shared_ptr<double> b = std::shared_ptr<double>(new double(i));
    todo.push_back(a); // 智能指针：避免被释放
    todo.push_back(b);
    auto x = new Rectangle(*a, *b);
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
  // for(auto it = todo.begin(); it != todo.end(); ++it) {
  //   auto x = *it;
  //   ASSERT_NO_THROW([x]() {delete x;}()); // 释放
  //   // ASSERT_ANY_THROW([x]() {delete x;}()); // 重复释放问题
  // }
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
  std::cout<<"-----"<<std::endl;
  A* a = new A();
  std::cout<<"-----"<<std::endl;
  A* a2 = new A();
  std::cout<<"-----"<<std::endl;
  B* b = new B(*a, *a2);
  // test
  std::cout<<"======="<<std::endl;
  std::cout<<"B.A:"<<(unsigned long long)&(b->m_a)<<std::endl;
  std::cout<<"_.A:"<<(unsigned long long)a<<std::endl;
  std::cout<<"B.A2:"<<(unsigned long long)&(b->m_a2)<<std::endl;
  std::cout<<"_.A2:"<<(unsigned long long)a2<<std::endl;
  ASSERT_NE(&(b->m_a), a); // 创建新的
  ASSERT_EQ(&(b->m_a2), a2); // 没有创建新的，只引用地址
  // test
  std::cout<<"======="<<std::endl;
  delete b;
  ASSERT_EQ(x_a, 1);
  ASSERT_EQ(x_b, 1);
  std::cout<<"======="<<std::endl;
  delete a; // 只要有外部引用，就不会被自动销毁
  ASSERT_EQ(x_a, 2);
  ASSERT_EQ(x_b, 1);
  std::cout<<"======="<<std::endl;
  delete a2;
  ASSERT_EQ(x_a, 3);
  ASSERT_EQ(x_b, 1);
}

// =====================================
// shared_ptr
// =====================================

class Alpha {
    int x = 1;
  public:
    void show() {
      std::cout<<"hello:"<<std::endl;
    }
    void showX() {
      std::cout<<"hello:"<<x<<std::endl;
    }
};
class Base {
  public:
    virtual ~Base(){}
};
class Derive: public Base{};
TEST(test_pointer, demo_shared_ptr) {
  std::shared_ptr<Base> sp1(new Derive());
  // dynamic
  {
    std::shared_ptr<Derive> sp2 = std::dynamic_pointer_cast<Derive>(sp1);
    std::shared_ptr<Base> sp3 = std::dynamic_pointer_cast<Derive>(sp1);
    std::shared_ptr<Base> sp4 = std::dynamic_pointer_cast<Base>(sp1);
    std::shared_ptr<Alpha> sp5 = std::dynamic_pointer_cast<Alpha>(sp1); // 转换成功！
    sp5->show(); // 虽然调用成功了，但是不能这样转！
    // sp5->showX(); // 由于找不到内部属性，所以会调用失败！
  }
  // static
  {
    std::shared_ptr<Derive> sp2 = std::static_pointer_cast<Derive>(sp1);
    std::shared_ptr<Base> sp3 = std::static_pointer_cast<Derive>(sp1);
    std::shared_ptr<Base> sp4 = std::static_pointer_cast<Base>(sp1);
    // std::shared_ptr<Alpha> sp6 = std::static_pointer_cast<Alpha>(sp1); // note: in instantiation of function template specialization 'std::static_pointer_cast<Alpha, Base>' requested here
  }
}

class Axx {
public:
  std::shared_ptr<Axx> xx;
};
TEST(test_pointer, demo_shared_ptr_memory_leak_recur) {
  std::shared_ptr<Axx> xx(new Axx());
  xx->xx = xx;
  ASSERT_EQ(xx->xx, xx->xx->xx); // 自己引用自己
  ASSERT_EQ(xx.use_count(), 2); // 不是1，不是无限，是2
  std::cout<<"----"<<std::endl;
  // delete xx.get(); // 循环依赖，需要手动释放。但也会使得指针引用释放失败，所以不能在这里释放。需要结合weak_ptr使用
  std::cout<<"----"<<std::endl;
}

class Aaa {};
TEST(test_pointer, demo_shared_ptr_memory_leak_recur_weak_ptr) {
  std::shared_ptr<Aaa> sp1 = std::make_shared<Aaa>();
  ASSERT_EQ(sp1.use_count(), 1);
  std::weak_ptr<Aaa> wp1(sp1); // 不改变引用数量
  ASSERT_EQ(sp1.use_count(), 1);
  std::weak_ptr<Aaa> wp2;
  ASSERT_EQ(sp1.use_count(), 1);
  wp2 = sp1;
  ASSERT_EQ(sp1.use_count(), 1);
  std::cout<<"use count:"<<wp2.use_count()<<std::endl;
}

// =====================================
// weak_ptr
// =====================================
TEST(test_pointer, demo_weak_ptr) {
  class Ax {
  public:
    int &x;
    Ax(int &a): x(a) {};
    ~Ax() {
      x = 666;
    }
  };
  std::weak_ptr<Ax> w_p1;
  int n = 10;
  {
    std::shared_ptr<Ax> s_p1(new Ax(n));
    std::shared_ptr<Ax> s_p2 = s_p1;

    w_p1 = s_p2; // 可以直接赋值
    ASSERT_EQ(w_p1.use_count(), 2); // 引用数 = 两个共享指针
  }
  ASSERT_EQ(w_p1.use_count(), 0); // 引用数 = 0
  ASSERT_TRUE(w_p1.expired()); // 没有引用，所以释放了
  ASSERT_EQ(n, 666);
}

TEST(test_pointer, demo_weak_ptr2) {
  class Rectangle {
  double m_a;
  double m_b;
  public:
    Rectangle(double a, double b): m_a(a), m_b(b) {}
  };
  std::weak_ptr<Rectangle> w_p1;
  {
    std::shared_ptr<Rectangle> s_p1(new Rectangle(3.5, 4.1));
    std::shared_ptr<Rectangle> s_p2 = s_p1;
    w_p1 = s_p2;
    // std::cout << "w_p1:" << w_p1 << std::endl;
    ASSERT_EQ(w_p1.use_count(), 2);
    // 获取控制权
    // 方式一
    // std::shared_ptr<Rectangle> s_p3(w_p1); // 可以，但会抛出异常当引用为零，需要改为下面的lock方式
    // 方式二
    std::shared_ptr<Rectangle> s_p3 = w_p1.lock(); // lock是线程安全的原子操作（atomic operation），且不会抛出异常当引用为零
    std::cout << "s_p3:" << s_p3 << std::endl; // shared_ptr重载了插入操作符，会打印里封装的指针
    // ASSERT_LT(s_p3, 0);
    ASSERT_EQ(w_p1.use_count(), 3);
  }
  ASSERT_EQ(w_p1.use_count(), 0);
  std::shared_ptr<Rectangle> s_p3 = w_p1.lock(); // 释放了，无法再获取控制权，返回的是一个空指针
  std::cout << "s_p3:" << s_p3 << std::endl;
  // ASSERT_EQ(w_p1, 0);
  ASSERT_EQ(w_p1.use_count(), 0);
}
