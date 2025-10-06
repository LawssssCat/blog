#include <gtest/gtest.h>
#include <stdexcept>

#include "arr.h"

// 引用变量
TEST(syntax_function, valuable_reference) {
  int a = 1;
  int& b = a; // 引用变量，地址一样
  b++;
  ASSERT_EQ(a, 2);
  ASSERT_EQ(b, 2);
  ASSERT_EQ(b, a);
  b = 66;
  ASSERT_EQ(a, 66);
}

// 指针
TEST(syntax_function, address_reference_value) {
  int a = 1;
  int *p = &a; // 取地址
  const int *cp = &a; // 常量指针，不可修改
  // ASSERT_THROW([&cp]() {
  //   cp += 1;
  // }, std::runtime_error);
}

// 指针
TEST(syntax_function, address_reference_array) {
  int numbers[] = {1,2,3};
  int n1 = sizeof(numbers) / sizeof(int);
  int* p = numbers; // 取地址

  // 操作1：修改值
  p[1] = 1;
  p[2] = 1;
  ASSERT_EQ(arr_toString(numbers, n1), "[1,1,1]");
  // int exp[] = {1,1,1};
  // ASSERT_EQ(numbers, exp);

  // 操作2：取值
  int *p2 = p+1;
  p2[0] = 199;
  ASSERT_EQ(arr_toString(numbers, n1), "[1,199,1]");

  // 操作3：越界
  // ASSERT_THROW([p]() {
  //   int temp = (*p+999);
  // }, std::runtime_error);

  // 操作4：不同类型的自增，进度不一样
  int *px1 = numbers;
  char *px2 = (char*)px1;
  px1++; // 4字节++
  px2++; // 1字节++
  ASSERT_GE((long long)px1, (long long)px2);
  px2++;
  px2++;
  px2++;
  ASSERT_EQ((long long)px1, (long long)px2);
}

int my_add(int &a) {
  a++;
  return a;
}

TEST(syntax_function, argument_index) {
  int a = 0;
  int b = my_add(a);
  ASSERT_EQ(a, 1);
  ASSERT_EQ(b, 1);
  ASSERT_NE(&a, &b); // 值相同，但是地址不同
}
