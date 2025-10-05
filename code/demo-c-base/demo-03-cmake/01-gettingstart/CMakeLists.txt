# 最小要求CMake版本
# cmake_minimum_required(VERSION major.minor[.patch])
cmake_minimum_required(VERSION 3.10)

# 项目信息
# project(项目名
#   [VERSION 版本号]
#   [LANGUAGES 编程语言 ...] # 不填写，默认 LANGUAGES C CXX
# )
project(Helloworld VERSION 0.1
DESCRIPTION "Demo Project of Complex Number"
LANGUAGES CXX)

# add_executable(目标名称 源文件1 [源文件2...])
# add_executable(helloworld hello.cpp)
# add_executable(demo main.cpp complex.cpp)
add_executable(demo # 将该目标的依赖通过target_sources方法声明
  main.cpp
  engine/hello.cpp
)
# 对demo的属性描述
# 其他属性有：
# - INCLUDE_DIRECTORIES
# - COMPILE_DEFINITIONS
# - COMPILE_OPTIONS
# - LINK_LIBRARIES
# - LINK_OPTIONS
# - LINK_FLAGS
# - STATIC_LIBRARY_OPTIONS
# - STATIC_LIBRARY_FLAGS
# - SOURCES
target_include_directories(demo
  PUBLIC
    ${CMAKE_CURRENT_SOURCE_DIR}/engine
)

add_subdirectory(lib)
# add_subdirectory(engine)
# add_subdirectory(portal)

# 构造静态库
# g++ -c complex.cpp -o complex.o
# g++ main.cpp complex.o -o demo
# ar rcs libcomplex.a complex.o # 制作静态库
# ar -t libcomplex.a # 查看静态库内容
# g++ main.cpp -L . -l complex -o demo # 链接静态文件，生成可执行程序。通过在当前目录（.）查找库文件（libcomplex.a）的方式
target_link_libraries(demo
  PUBLIC
    complex # libcomplex.a
)
