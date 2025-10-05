add_library(complex # 注意：生成的库名可能不同。linux静态库名libxxx.a、MSVC静态库静态库名xxx.lib
  complex.cpp
)
target_include_directories(complex
PUBLIC
  ${CMAKE_CURRENT_SOURCE_DIR}
)