// #include <iostream>
#pragma once

#include <vector>

// bubble
void sort_bubble(std::vector<int> &col) {
  // std::cout << "ok~" << std::endl;
  for(int k = col.size(); k > 1; k--) { // 注意1：从1开始，减少多余循环
    for(int i = 0; i < k - 1; i++) { // 注意1：到k-1结束，否则坐标越位
      int a = col[i];
      int b = col[i+1];
      if (a > b) {
        col[i] = b;
        col[i+1] = a;
      }
    }
  }
}

// bubble
void sort_bubble_2(std::vector<int> &col) {
  // std::cout << "ok~" << std::endl;
  for(int k = col.size(); k > 1;) { // 注意1：从1开始，减少多余循环；注意2：没有k--
    int m = 0; // 注意2
    for(int i = 0; i < k - 1; i++) { // 注意1：到k-1结束，否则坐标越位
      int a = col[i];
      int b = col[i+1];
      if (a > b) {
        col[i] = b;
        col[i+1] = a;
        m = i + 1; // 注意2
      }
    }
    k = m; // 注意2
  }
}
