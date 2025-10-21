#include <cstdlib>
#include <gtest/gtest.h>
#include <iostream>
#include <string>

void show(std::string str, int &n) {
  std::cout << str << ":" << &n << ":" << n << std::endl;
}
void xx(int n, int &b) {
  show(__FUNCTION__, b);
  show(__FUNCTION__, n);
  b = n;
  // delete &n;
  show(__FUNCTION__, b);
  show(__FUNCTION__, n);
}
TEST(test_function, hello) {
  int b = random();
  int n = random();
  show(__FUNCTION__, b);
  show(__FUNCTION__, n);
  xx(n, b);
  show(__FUNCTION__, b);
  show(__FUNCTION__, n);
  ASSERT_EQ(b, n);
}
