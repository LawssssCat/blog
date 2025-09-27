set(LIB_SRC
  ${CMAKE_CURRENT_SOURCE_DIR}/hello.cpp
)

add_library(hellolib SHARED ${LIB_SRC})
target_include_directories(hellolib INTERFACE ${CMAKE_CURRENT_SOURCE_DIR}) # 使使用库的代码自动添加include头文件
