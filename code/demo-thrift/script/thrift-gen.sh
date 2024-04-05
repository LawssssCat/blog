#!/bin/bash

SCRIPT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=$(cd $SCRIPT_PATH/../; pwd)

function copy_file() {
  local src="$1"
  local dst="$2"
  mkdir -pv ${dst%/*}
  cp -avf $src $dst
}

# java
thrift --gen java -o ${PROJECT_PATH}/target/ ${PROJECT_PATH}/user.thrift
copy_file "${PROJECT_PATH}/target/gen-java/*" "${PROJECT_PATH}/thrift-api/src/main/java/"

# python
thrift --gen py -o ${PROJECT_PATH}/target/ ${PROJECT_PATH}/user.thrift
copy_file "${PROJECT_PATH}/target/gen-py/*" "${PROJECT_PATH}/thrift-client-python/thrift_api/"
