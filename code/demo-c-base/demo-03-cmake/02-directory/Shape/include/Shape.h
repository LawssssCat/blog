#pragma once

#include <cmath>
using namespace std;

/**
 * 形状
 */
class Shape
{
public:
  /**
   * 面积
   */
  virtual float area() = 0;
  /**
   * 周长
   */
  virtual float perimeter() = 0;
  virtual ~Shape() {};
};