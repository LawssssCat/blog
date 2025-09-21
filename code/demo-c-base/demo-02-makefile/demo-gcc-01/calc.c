#include <stdio.h>

int add(int a, int b);
int c2i(char* s);

int main(int argc, char* argv[])
{
  int a = c2i(argv[1]);
  int b = c2i(argv[2]);
  printf("%d + %d = %d\n", a, b, add(a, b));
}
