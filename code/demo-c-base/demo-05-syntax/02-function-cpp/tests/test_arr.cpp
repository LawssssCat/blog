#include <string>

#include <gtest/gtest.h>

#include "arr.h"

TEST(my_lib, array_print) {
  int arr[] = {1,2,3,5};
  int n = sizeof(arr) / sizeof(arr[0]);
  arr_print(arr, n);
}

TEST(my_lib, arr_toString) {
  int arr[] = {1,2,3,5};
  int n = sizeof(arr) / sizeof(arr[0]);

  std::string result = arr_toString(arr, n);
  ASSERT_EQ(result, "[1,2,3,5]");
}
