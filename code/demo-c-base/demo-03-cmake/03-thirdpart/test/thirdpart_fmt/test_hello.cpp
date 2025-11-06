#include "fmt/base.h"
#include "fmt/format.h"
#include <iostream>

#include <gtest/gtest.h>

#include <fmt/core.h>
#include <locale>

// 参考：
// https://awesometop.cn/posts/0b20b1ca03b24e60ac6bbb9f6400c79f

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
  fmt::println("name:{1}, age:{0:.5}", "123456", "knox");
  fmt::println("price:{0:.5f}", 22.f);

  auto s_no = fmt::format("{:L}", 1234567890);
  auto s_en = fmt::format(std::locale("en_US.UTF-8"), "{:L}", 1234567890);
  fmt::println("normal:{}", s_no); // 1234567890
  fmt::println("locale:{}", s_en); // 1,234,567,890

  // class/struct toString
  // todo
}
