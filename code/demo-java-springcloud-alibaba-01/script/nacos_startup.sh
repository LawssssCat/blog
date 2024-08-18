#!/bin/bash

set -ex

CURRENT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=${CURRENT_PATH}/../
TARGET_PATH=${PROJECT_PATH}/build/nacos/

if [ -z "${JAVA_HOME}" ] && [ -d "/usr/lib/jvm/jre/" ]; then # fedora
export JAVA_HOME="/usr/lib/jvm/jre/"
fi
chmod +x ${TARGET_PATH}/nacos/bin/startup.sh && ${TARGET_PATH}/nacos/bin/startup.sh -m standalone

# tail
tail -n 1000 -F ${TARGET_PATH}/nacos/logs/start.out
