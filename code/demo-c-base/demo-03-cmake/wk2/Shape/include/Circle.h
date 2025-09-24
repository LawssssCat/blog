#include "Shape.h"

#define PI 3.1315926

/**
 * 圆形
 */
class Circle : public Shape
{
private:
  float m_r;
public:
  Circle(float r);
  float area() override;
  float perimeter() override;
};