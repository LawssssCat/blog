#include "Shape.h"

/**
 * 圆形
 */
class Circle : public Shape
{
private:
  float m_r;
protected:
  const float PI = 3.1315926;
public:
  Circle(float r);
  float area() override;
  float perimeter() override;
};