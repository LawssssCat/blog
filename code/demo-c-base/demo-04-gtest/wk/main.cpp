#include <iostream>

// #include "hello/hello.h"
#include "hello.h" // 没有说明在哪个目录，引入时需要用-I参数指定include目录
#include "world.h" // 没有说明在哪个目录，引入时需要用-I参数指定include目录
#include "add.h"


int main() {
  std::cout << "3 + 4 = " << add1(3, 4) << std::endl;
  Hello();
  World();
  return 0;
}