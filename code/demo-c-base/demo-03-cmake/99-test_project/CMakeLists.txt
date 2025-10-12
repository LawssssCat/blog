cmake_minimum_required(VERSION 3.10.0)
project(X1 VERSION 0.1.0 LANGUAGES C CXX)

add_executable(X1 main.cpp)

set("MY_SSS" 666)
message("[${CMAKE_CURRENT_SOURCE_DIR}] [${PROJECT_NAME}] MY_SSS = ${MY_SSS}")
add_subdirectory(proj0)
add_subdirectory(proj2)
message("[${CMAKE_CURRENT_SOURCE_DIR}] [${PROJECT_NAME}] MY_SSS = ${MY_SSS}")
