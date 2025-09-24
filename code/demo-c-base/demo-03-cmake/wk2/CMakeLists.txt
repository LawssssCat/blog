cmake_minimum_required(VERSION 3.31.6)
project(myProject VERSION 1.2.3 LANGUAGES CXX)
set(CMAKE_CXX_STANDARD 20)

add_executable(DemoApp main.cpp)
add_subdirectory(Shape) # 添加到项目中，构建时就会读取其中的CMakeLists.txt文件
target_link_libraries(DemoApp Shape)
