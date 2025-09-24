add_library(Shape)
target_sources(Shape
  PRIVATE
    src/Circle.cpp
    src/Triangle.cpp
    src/Rectangle.cpp
)
target_include_directories(Shape
  PUBLIC # 依赖该库的目标都会包含这些头文件
    include
)