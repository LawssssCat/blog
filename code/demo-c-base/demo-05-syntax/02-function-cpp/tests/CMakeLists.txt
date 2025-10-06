

add_executable(test_main
  test_sort.cpp)

target_link_libraries(test_main PRIVATE sortlib)

find_package(GTest CONFIG REQUIRED)
target_link_libraries(test_main PRIVATE GTest::gtest GTest::gtest_main GTest::gmock GTest::gmock_main)

# add_test(AllTestsInMain test_main)
include(GoogleTest)
gtest_discover_tests(test_main)

