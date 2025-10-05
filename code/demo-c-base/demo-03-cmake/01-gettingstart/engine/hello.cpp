#include <iostream>

#include <hello.h>

#include "complex.h"

void sayHello() {
  std::cout << "Hello, World! engine" << std::endl;

  Complex a(1, 2);
  Complex b(3, 4);
  Complex c = a + b;
  std::cout << "a + b = " << (a + b) << std::endl;
  std::cout << "a - b = " << (a - b) << std::endl;
  std::cout << "a * b = " << (a * b) << std::endl;
  std::cout << "a / b = " << (a / b) << std::endl;
}
