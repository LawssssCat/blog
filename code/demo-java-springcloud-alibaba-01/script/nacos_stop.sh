#!/bin/bash

set -ex

CURRENT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=${CURRENT_PATH}/../
TARGET_PATH=${PROJECT_PATH}/build/nacos/

chmod +x ${TARGET_PATH}/nacos/bin/shutdown.sh && ${TARGET_PATH}/nacos/bin/shutdown.sh
