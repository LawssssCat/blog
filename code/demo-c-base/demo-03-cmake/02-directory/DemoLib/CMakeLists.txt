set(MSG_ID "demolib")


add_library(DemoLib INTERFACE) # INTERFACE表示这是接口库，这种库不会编译生成二进制库，但是可以设置包含目录、依赖项以及其他需求
# set(CPPHTTPLIB_OPENSSL_SUPPORT ON)
target_compile_definitions(DemoLib INTERFACE CPPHTTPLIB_OPENSSL_SUPPORT)


# 通过这个命令包含GNU_INSTALL_DIRS模块。
# 该模块定义了标准的安装目录。
# 参考：https://cmake.org/cmake/help/latest/module/GNUInstallDirs.html
# include(GNUInstallDirs) # 该模块根据GNU编码标准定义了安装目录变量，并提供了计算与安装相关的绝对路径的命令。
# /usr/include/c++/15/cstdlib:83:15: fatal error: stdlib.h: No such file or directory


# 给目标库添加包含路径
target_include_directories(DemoLib
  INTERFACE
    # 使用生成式表达式，为构建和安装设置不同的包含路径
    # $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include> # 本地构建，使用当前include目录
    # $<INSTALL_INTERFACE:${CMAKE_INSTALL_INCLUDEDIR}> # 安装，使用安装的包含路径
    include
)
message("[${MSG_ID}] BUILD_INTERFACE = ${BUILD_INTERFACE}")
message("[${MSG_ID}] INSTALL_INTERFACE = ${INSTALL_INTERFACE}")
message("[${MSG_ID}] CMAKE_CURRENT_SOURCE_DIR = ${CMAKE_CURRENT_SOURCE_DIR}")
message("[${MSG_ID}] CMAKE_INSTALL_INCLUDEDIR = ${CMAKE_INSTALL_INCLUDEDIR}")


# 依赖：OpenSSL
# message("[${MSG_ID}] fetch OpenSSL")
# include(FetchContent)
# FetchContent_Declare(openssl
#   # GIT_REPOSITORY https://github.com/openssl/openssl
#   # GIT_TAG        master # c1eeb9406b6142148f267594197d853403d10208 # openssl-3.5.4
#   URL https://github.com/openssl/openssl/releases/download/openssl-3.6.0/openssl-3.6.0.tar.gz
#   # FIND_PACKAGE_ARGS NAMES OpenSSL
#   OVERRIDE_FIND_PACKAGE
# )
# message("[${MSG_ID}] active OpenSSL")
# FetchContent_MakeAvailable(openssl)
message("[${MSG_ID}] find OpenSSL")
# find_package(OpenSSL REQUIRED) # 在使用https时需要用到OpenSSL
find_package(OpenSSL)
# find_package(OpenSSL COMPONENTS SSL)
if(NOT OPENSSL_FOUND)
  message(SEND_ERROR "[${MSG_ID}] OpenSSL not found")
endif()
message("[${MSG_ID}] OPENSSL_FOUND = ${OPENSSL_FOUND}")
target_link_libraries(DemoLib INTERFACE OpenSSL::SSL OpenSSL::Crypto)
# target_link_libraries(DemoLib INTERFACE SSL Crypto)
# include_directories(${OPENSSL_INCLUDE_DIR})
# if(WIN32)
#   target_link_libraries(DemoLib INTERFACE wsock32 ws2_32 crypto32)
# endif()
