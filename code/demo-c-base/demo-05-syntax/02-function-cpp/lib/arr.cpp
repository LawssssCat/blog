#include <iostream>
#include <sstream>

#include "arr.h"

void arr_print(int* arr, int n) {
  std::cout << "Arrays:[";
  for (int i=0; i<n; i++) {
    std::cout << arr[i];
    if (i<n-1) {
      std::cout << ",";
    }
  }
  std::cout << "]" << std::endl;
}

std::string arr_toString(int* arr, int n) {
  std::ostringstream oss;
  oss << "[";
  for (int i=0; i<n; i++) {
    oss << arr[i];
    if (i<n-1) {
      oss << ",";
    }
  }
  oss << "]";
  return oss.str();
}
