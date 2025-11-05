set(TARGET test_main)

add_executable(${TARGET}
  test_hello.cpp
  thirdpart_jsoncpp/test_hello.cpp
  thirdpart_fmt/test_hello.cpp
)

target_link_libraries(${TARGET} GTest::gtest_main)
target_link_libraries(${TARGET} jsoncpp_lib)
target_link_libraries(${TARGET} fmt::fmt)

# add_test(AllTestsInMain test_main)
include(GoogleTest)
gtest_discover_tests(test_main)
