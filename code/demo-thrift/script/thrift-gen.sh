#!/bin/bash

SCRIPT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=$(cd $SCRIPT_PATH/../; pwd)

thrift --gen java -o ${PROJECT_PATH}/target/  ${PROJECT_PATH}/user.thrift

mv -vf ${PROJECT_PATH}/target/gen-java/* ${PROJECT_PATH}/thrift-api/src/main/java/
