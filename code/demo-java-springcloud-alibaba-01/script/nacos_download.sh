#!/bin/bash

set -ex

CURRENT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=${CURRENT_PATH}/../
TARGET_PATH=${PROJECT_PATH}/build/nacos/

NACOS_VERSION="2.2.0"
NACOS_DOWNLOAD_URL="https://github.com/alibaba/nacos/releases/download/${NACOS_VERSION}/nacos-server-${NACOS_VERSION}.zip"

func_check() {
  echo "94ee599775e4b760dce7d8adfdf55573  ${TARGET_PATH}/nacos-server-${NACOS_VERSION}.zip" > ${TARGET_PATH}/nacos-server.md5
  md5sum -c ${TARGET_PATH}/nacos-server.md5
}

if [ ! -f "${TARGET_PATH}/nacos-server-${NACOS_VERSION}.zip" ]; then
  mkdir -pv ${TARGET_PATH}
  wget -P ${TARGET_PATH} ${NACOS_DOWNLOAD_URL}
fi

func_check || {
  echo "Please delete: ${TARGET_PATH}/nacos-server-${NACOS_VERSION}.zip"
  exit 1;
}

cd ${TARGET_PATH}
unzip "nacos-server-${NACOS_VERSION}.zip"