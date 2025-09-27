cmake_minimum_required(VERSION 3.14)
project(simplest_gtest)

add_subdirectory(modules/hello)
add_subdirectory(modules/world)
add_subdirectory(modules/add)

# add_executable(MainProject main.cpp hello/hello.c world/world.c)
add_executable(MainProject main.cpp)

# target_include_directories(MainProject PUBLIC world)
# target_include_directories(MainProject PUBLIC add)
include_directories(modules/hello)
include_directories(modules/world)
include_directories(modules/add)

# 移到modules中
# target_link_libraries(MainProject PUBLIC hellolib) # add_executable hello/hello.c
# target_link_libraries(MainProject PUBLIC worldlib) # add_executable world/world.c
# target_link_libraries(MainProject PUBLIC addlib) # add_executable add/add.c
target_link_libraries(MainProject
PUBLIC
  hellolib # add_executable hello/hello.c
  worldlib # add_executable world/world.c
  addlib # add_executable add/add.c
) 

# 测试
enable_testing() # 开启cmake测试能力。注意：需要放在这里，如果放在test的CMakeLists.txt不生效。原因？原理/为什么必须添加，不自动识别？
add_subdirectory(tests)
