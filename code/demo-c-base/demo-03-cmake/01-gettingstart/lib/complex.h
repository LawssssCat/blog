#include <ostream>

#define COMPLEX_H
#ifdef COMPLEX_H

/**
 * 复数类
 */
class Complex {
public:
  Complex(double real, double imag);
  Complex operator+(const Complex& other) const;
  Complex operator-(const Complex& other) const;
  Complex operator*(const Complex& other) const;
  Complex operator/(const Complex& other) const;
  void print() const;
  friend std::ostream& operator << (std::ostream& os, const Complex& c);
private:
  double real;
  double imag;
};

#endif
