set(LIB_SRC
  ${CMAKE_CURRENT_SOURCE_DIR}/world.cpp
)

add_library(worldlib SHARED ${LIB_SRC})
target_include_directories(worldlib INTERFACE ${CMAKE_CURRENT_SOURCE_DIR}) # 使使用库的代码自动添加include头文件
