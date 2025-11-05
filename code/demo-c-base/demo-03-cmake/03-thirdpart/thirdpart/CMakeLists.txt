
message("PROJECT_ROOT_PATH=${PROJECT_ROOT_PATH}")

include(FetchContent)

message("thirdpart:gtest")
# GTest::gtest_main
FetchContent_Declare(
  googletest
  SOURCE_DIR "${PROJECT_ROOT_PATH}/thirdpart/googletest"
)
FetchContent_MakeAvailable(googletest)

message("thirdpart:jsoncpp")
# jsoncpp_lib
FetchContent_Declare(
  jsoncpp
  SOURCE_DIR "${PROJECT_ROOT_PATH}/thirdpart/jsoncpp"
)
FetchContent_MakeAvailable(jsoncpp)

