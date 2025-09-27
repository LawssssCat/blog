
#include <gtest/gtest.h>

#include "add.h"

TEST(AddTest, xx1)
{
  int a = 1;
  int b = 2;
  EXPECT_EQ(add1(a, b), 4);
}

TEST(AddTest, xx2)
{
  EXPECT_EQ(add1(1, 2), 4);
}
