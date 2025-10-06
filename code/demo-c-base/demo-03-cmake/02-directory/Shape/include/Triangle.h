#pragma once

#include "Shape.h"

class Triangle: Shape {
private:
  float m_a;
  float m_b;
  float m_c;
public:
  Triangle(float a, float b, float c);
  float perimeter() override;
  float area() override;
};