#include "fmt/base.h"
#include <iostream>

#include <gtest/gtest.h>

#include <fmt/core.h>

TEST(test_fmt, show_world) {
  std::cout << "hello world ~ " << FMT_VERSION << std::endl;

  // 占位符
  std::string msg = fmt::format("Hello {} ~~~~~~~~", "FMT");
  std::cout << "msg = " << msg << std::endl;

  // 打印
  fmt::println("Hello {} ~~~~~~~", "hahahaha");

  // {} —— 默认格式输出值
  // {:s} —— 字符串
  // {:f} —— 浮点数
  // {:d} —— 十进制整数
  // {:x} —— 十六进制
  fmt::println("name:{1}, age:{0:.5}", "123", "knox");

  // class/struct toString
  // todo
}
