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
add_executable(demo) # 将该目标的依赖通过target_sources方法声明

# add_subdirectory(sdk)
# add_subdirectory(engine)
# add_subdirectory(portal)

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
target_sources(demo
  PRIVATE
    main.cpp
    complex.cpp
    complex.h # 可有可无（cpp中必然有引用该头文件），加上便于IDE显式辅助信息
)
