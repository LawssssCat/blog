cmake_minimum_required(VERSION 3.31.6)
project(myProject # <project_name>
  VERSION 1.2.3   # <VERSION_MAJOR>.<VERSION_MINOR>.x
  LANGUAGES CXX
)
set(CMAKE_CXX_STANDARD 20)

add_executable(DemoApp main.cpp)
add_subdirectory(Shape) # 添加到项目中，构建时就会读取其中的CMakeLists.txt文件
target_link_libraries(DemoApp Shape)

configure_file(
  "config.h.in" # 配置文件的模板文件，包含"@<project_name>_VERSION_MAJOR@"、"@<project_name>_VERSION_MINOR@"占位符
  "config.h"    # 配置文件的输出文件，输出时会根据项目配置替换占位符的值
)
target_include_directories(DemoApp
  PRIVATE "${PROJECT_BINARY_DIR}"
)
