set(TARGET test_main)

add_executable(${TARGET}
  test_hello.cpp
)

target_link_libraries(${TARGET} GTest::gtest_main)

# add_test(AllTestsInMain test_main)
include(GoogleTest)
gtest_discover_tests(test_main)
