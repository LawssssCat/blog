#include <vector>

#include <gtest/gtest.h>

#include "sfn.h"

void do_sort(const char* name, void (*fp)(std::vector<int> &c), std::vector<int> col, std::vector<int> exp) {
  fp(col);
  ASSERT_EQ(col, exp) << "fail for " << name;
}

void fn_sort(std::vector<int> col, std::vector<int> exp) {
  do_sort("func_bubble",   sort_bubble, col, exp);
  do_sort("func_bubble_2", sort_bubble_2, col, exp);
}

TEST(SortLib, is_sorted) {
  std::vector<int> exp = {1,2,3,4,5};
  std::vector<int> col = {1,2,3,4,5};
  fn_sort(col, exp);
}

TEST(SortLib, revert) {
  std::vector<int> exp = {1,2,3,4,5};
  std::vector<int> col = {5,4,3,2,1};
  fn_sort(col, exp);
}

TEST(SortLib, middle) {
  std::vector<int> exp = {1,2,3,4,5};
  std::vector<int> col = {1,2,4,3,5};
  fn_sort(col, exp);
}

TEST(SortLib, start) {
  std::vector<int> exp = {1,2,3,4,5};
  std::vector<int> col = {2,1,3,4,5};
  fn_sort(col, exp);
}

TEST(SortLib, end) {
  std::vector<int> exp = {1,2,3,4,5};
  std::vector<int> col = {1,5,3,4,2};
  fn_sort(col, exp);
}

TEST(SortLib, one) {
  std::vector<int> exp = {1};
  std::vector<int> col = {1};
  fn_sort(col, exp);
}

TEST(SortLib, two) {
  std::vector<int> exp = {-1,2};
  std::vector<int> col = {-1,2};
  fn_sort(col, exp);
}

TEST(SortLib, same) {
  std::vector<int> exp = {0,0,1};
  std::vector<int> col = {0,1,0};
  fn_sort(col, exp);
}
