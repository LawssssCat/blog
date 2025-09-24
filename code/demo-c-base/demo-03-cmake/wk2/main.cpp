#include <iostream>
#include <string.h>

#include "config.h"

#include "Circle.h"
#include "Rectangle.h"
#include "Triangle.h"

void usage(char *argv0)
{
  std::cout << "Usage: " << std::endl;
  std::cout << "\t" << argv0 << " c <radius>" << std::endl;
  std::cout << "\t" << argv0 << " r <widtd> <length>" << std::endl;
  std::cout << "\t" << argv0 << " t <a> <b> <c>" << std::endl;
}
int main(int argc, char *argv[])
{
  std::cout << "Version: " << MYPROJECT_VERSION_MAJOR << "." << MYPROJECT_VERSION_MINOR << std::endl;
  if (argc == 3 && strcmp(argv[1], "c") == 0)
  {
    Circle c(atof(argv[2]));
    std::cout << "Circle area = " << c.area() << std::endl;
    std::cout << "Circle perimeter = " << c.perimeter() << std::endl;
    return 0;
  }
  else if (argc == 4 && strcmp(argv[1], "r") == 0)
  {
    Rectangle r(atof(argv[2]), atof(argv[3]));
    std::cout << "Rectangle area = " << r.area() << std::endl;
    std::cout << "Rectangle perimeter = " << r.perimeter() << std::endl;
    return 0;
  }
  else if (argc == 5 && strcmp(argv[1], "t") == 0)
  {
    Triangle t(atof(argv[2]), atof(argv[3]), atof(argv[4]));
    std::cout << "Triangle area = " << t.area() << std::endl;
    std::cout << "Triangle perimeter = " << t.perimeter() << std::endl;
    return 0;
  }

  usage(argv[0]);
  return 0;
}
