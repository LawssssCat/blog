set(LIB_SRC
  ${CMAKE_CURRENT_SOURCE_DIR}/add.cpp
)

add_library(addlib SHARED ${LIB_SRC})
target_include_directories(addlib INTERFACE ${CMAKE_CURRENT_SOURCE_DIR}) # 使使用库的代码自动添加include头文件
