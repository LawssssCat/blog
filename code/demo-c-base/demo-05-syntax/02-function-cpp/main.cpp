// #include <stdio.h>
#include <iostream>

#include <fmt/base.h>
#include <fmt/core.h>

int main()
{
  // printf("fmt:%d\n", FMT_VERSION);
  std::cout << "fmt:" << FMT_VERSION << std::endl;
  fmt::print("Hello World!\n");
  return 0;
}
