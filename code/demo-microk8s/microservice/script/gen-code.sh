#!/bin/bash

SCRIPT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=$(cd $SCRIPT_PATH/../; pwd)

function copy_file() {
  local src="$1"
  local dst="$2"
  mkdir -pv ${dst%/*}
  cp -avf $src $dst
}

# message
thrift --gen py   -out ${PROJECT_PATH}/message-thrift-python-service/thrift_api/ ${PROJECT_PATH}/script/thrift/message.thrift
thrift --gen java -out ${PROJECT_PATH}/message-thrift-java-api/src/main/java/    ${PROJECT_PATH}/script/thrift/message.thrift

# user
thrift --gen java -out ${PROJECT_PATH}/user-thrift-java-api/src/main/java/ ${PROJECT_PATH}/script/thrift/user.thrift
