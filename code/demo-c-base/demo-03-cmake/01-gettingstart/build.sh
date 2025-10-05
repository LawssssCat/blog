#!/bin/bash

SCRIPT_PATH=$(cd "$(dirname $0)";pwd)

# 生成
# mkdir -pv $SCRIPT_PATH/build
# cd $SCRIPT_PATH/build
# cmake ..
cmake -B "build"
# cmake -B "build-Ninja" -G "Unix Makefiles"

# 构建
cmake --build "build"
# cmake --build "build" --clean-first --verbose # 打印详细日志
# /usr/bin/cmake --build /home/vagrant/wk2/build --config Debug --target all -j 4 -- # CMake Tools 识别当前路径生成的命令
