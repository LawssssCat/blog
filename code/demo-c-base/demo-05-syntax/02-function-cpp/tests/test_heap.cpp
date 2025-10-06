#include <gtest/gtest.h>

TEST(test_heap, new_memory) {
  // 申请
  char* p1 = new char; // 申请内存
  int* p2 = new int(2); // 申请内存 + 初始化
  char* p3 = new char[3]; // 申请内存（数组）
  char* p4 = new char[3] {1,2,3}; // 申请内存（数组） + 初始化
  // 释放
  delete p1;
  delete p2;
  delete[] p3;
  delete[] p4;
}
