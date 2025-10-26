#include <iostream>
#include <list>
#include <map>
#include <ostream>
#include <set>
#include <stdexcept>
#include <string>
#include <utility>
#include <vector>

#include <gtest/gtest.h>

// ###################################
// 向量（vector）
// ###################################

TEST(test_container, disable_vector_get_index) {
  std::vector<float> values{1.0, 2.0, 3.0, 4.0, 5.0};
  try {
    // std::cout << "index:" << values[99] << std::endl; // Test has ended unexpectedly: Signal received: SIGABRT
  } catch (...) {
    std::cout << "out of index []" << std::endl;
  }

  std::cout << "ok" << std::endl;
}

TEST(test_container, vector_get_at) {
  std::vector<float> values{1.0, 2.0, 3.0, 4.0, 5.0};

  try {
    values.at(99) = 22;
  } catch(std::out_of_range& e) {
    std::cout << "out of index at. " << e.what() << std::endl;
  }

  std::cout << "ok" << std::endl;
}

TEST(test_container, vector_use_emplace) {
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

// ###################################
// 数组（array）
// ###################################
TEST(test_container, array_iterator) {
  const int n = 5;
  std::array<float, n> numbers{1,2,3,4,5};
  for (auto v : numbers) {
    std::cout<<v<<" ";
  }
}

// ###################################
// 双向链表（list）
// ###################################
void show(std::list<int>* ls) {
  std::cout<<"list[";
  std::list<int>::iterator it;
  for (it = ls->begin(); it != ls->end();) {
    std::cout<<*it;
    ++it;
    if (it != ls->end()) {
      std::cout<<",";
    }
  }
  std::cout<<"]"<<std::endl;
}
TEST(test_container, list_iterator) {
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
}

// ###################################
// 键值对（map）
// ###################################
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
}

// ###################################
// 键值对（multimap）
// ###################################
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

// ###################################
// 集合（set）
// ###################################
TEST(test_container, set_print) {
  std::set<std::string> prices{"苹果", "香蕉", "葡萄", "桔子"};
  prices.insert("梨"); // 增加
  prices.erase("苹果"); // 删除
  for (auto p : prices)
    std::cout << "[set] value=" << p << std::endl;
}
