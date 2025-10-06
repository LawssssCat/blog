set(MSG_ID "test")


# googletest
include(FetchContent) # 引入函数
FetchContent_Declare( # https://github.com/google/googletest
  googletest
  URL https://github.com/google/googletest/archive/refs/tags/v1.17.0.zip
)
# For Windows: ...
# todo
FetchContent_MakeAvailable(googletest)


# DemoLib
add_executable(test_DemoLib
  test_DemoLib.cpp
)
target_link_libraries(test_DemoLib
PRIVATE
  GTest::gtest_main
  GTest::gtest
  DemoLib
)


# test case
include(GoogleTest) # 引入gtest_discover_tests函数
gtest_discover_tests(test_DemoLib) # 自动发现可执行程序中的用例


message("[${MSG_ID}] ready ok")
