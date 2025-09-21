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
