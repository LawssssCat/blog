cmake_minimum_required(VERSION 3.10.0)
project(demo-thirdpart VERSION 0.1.0 LANGUAGES C CXX)

set(TARGET demo-thirdpart)
set(PROJECT_ROOT_PATH ${CMAKE_CURRENT_SOURCE_DIR})

# 生成clangd提示
option(ENABLE_CLANGD_DATABASE '开启compile_command.json生成，引导clangd给出提示' ON)
if(ENABLE_CLANGD_DATABASE)
  message("enable clangd 'compile_command.json' generate")
  # 构建时生成compile_commands.json（重要）
  set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
  file(WRITE ${CMAKE_CURRENT_SOURCE_DIR}/.clangd
  "CompileFlags:
    CompilationDatabase: ${CMAKE_CURRENT_BINARY_DIR}/")
endif()

add_executable(${TARGET} main.cpp)

add_subdirectory(thirdpart)

include(CTest)
enable_testing()
add_subdirectory(test)
