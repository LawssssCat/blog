
#include <gtest/gtest.h>

namespace name
{
  // Demonstrate some basic assertions.
  TEST(HelloTest, xx) {
    // Expect two strings not to be equal.
    EXPECT_STRNE("hello", "world");
  }

  TEST(HelloTest, BasicAssertions) {
    // Expect equality.
    EXPECT_EQ(7 * 6, 42);
  }
} // namespace name


