#include <stdio.h>
#include "add.hpp"

int main()
{
  int a = 10;
  int b = 3;
  int res = add(a, b);
  printf("a + b = %d\n", res);
  return 0;
}