add_library(mylib
  arr.cpp
  sort.cpp
)

target_include_directories(mylib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
