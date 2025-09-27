

set(MSG_ID "test")
set(PROEJCT_ROOT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../")
set(GTEST_SOURCE_DIR "${PROEJCT_ROOT_DIR}/thirdpart/googletest")

#####################################33
# 源码引入
#####################################33
# 错误：When specifying an out-of-tree source a binary directory must be explicitly specified.
# add_subdirectory(${CMAKE_HOME_DIRECTORY}/thirdpart/googletest)
add_subdirectory(${GTEST_SOURCE_DIR} googletest)
set(BUILD_GMOCK OFF)

add_executable(test_common test_common.cpp)
target_include_directories(test_common PRIVATE ${GTEST_SOURCE_DIR})
target_link_libraries(test_common
PRIVATE
  gtest gtest_main)
add_test(NAME test_common COMMAND test_common) # 添加cmake测试用例标志

add_executable(test_add test_add.cpp)
target_include_directories(test_add PRIVATE ${GTEST_SOURCE_DIR})
target_include_directories(test_add PRIVATE ${PROEJCT_ROOT_DIR}/modules/add)
target_link_libraries(test_add
PRIVATE
  addlib
  gtest gtest_main)
add_test(NAME test_add COMMAND test_add)


#####################################33
# 下载测试框架
#####################################33
# GoogleTest requires at least C++17
# set(CMAKE_CXX_STANDARD 17)
# set(CMAKE_CXX_STANDARD_REQUIRED ON)

# include(FetchContent) # 引入函数
# FetchContent_Declare( # https://github.com/google/googletest
#   googletest
#   URL https://github.com/google/googletest/archive/refs/tags/v1.17.0.zip
# )
# For Windows: Prevent overriding the parent project's compiler/linker settings
# set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
# FetchContent_MakeAvailable(googletest)


# 添加测试用例
# add_subdirectory(tests EXCLUDE_FROM_ALL)
# add_executable(RunTest testadd.cpp testcommon.cpp ${CMAKE_HOME_DIRECTORY}/add.cpp)
# message("[${MSG_ID}] CMAKE_HOME_DIRECTORY = ${CMAKE_HOME_DIRECTORY}")
# target_include_directories(RunTest PRIVATE ${CMAKE_HOME_DIRECTORY})
# target_link_libraries(RunTest
#   gtest_main
#   gtest
# )
# target_link_directories(RunTest PRIVATE ${CMAKE_HOME_DIRECTORY})
# include(GoogleTest) # 引入函数
# gtest_discover_tests(RunTest) # 自动发现可执行程序中的用例
