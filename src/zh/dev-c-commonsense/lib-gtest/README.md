---
title: gtest 使用笔记
---

GoogleTest是C++常用的单元测试框架。[很多开源软件](https://github.com/google/googletest?tab=readme-ov-file#who-is-using-googletest)都是在使用。

使用方式：
[Quickstart: Building with CMake](https://google.github.io/googletest/quickstart-cmake.html)

```bash
# 编译gtest
略
# 编写用例
略，
# enable_test # 开启cmake测试能力
# add_test    # 添加cmake测试用例入口
# #include <gtest/gtest.h> TEST... # 添加用例文件
# 构建
cmake -B build
cd build
make
# 测试
cmake # cmake拉起测试用例的命令，底层调用cmake生成的 make test 指令
make test # cmake生成的拉去测试用例的指令，底层调用可执行文件
./path/to/test_xxxx # 基于gtest_main生成的可执行文件
```

<!-- more -->

参考：

+ 从零搭建一个c/c++工程-将gtest引入到项目中 <https://www.bilibili.com/video/BV1AX4y1J7dh>

Demo：
<RepoLink path="/code/code/demo-c-base/demo-04-cmake/wk/CmakeLists.txt" />

gtest实际上是提供了宏，通过调用宏生成对接框架的测试用例方法。
常用宏有：

+ `TEST(测试组名, 测试用例名)`
+ 断言：
  + `EXCEPT_EQ(actual, except)`
