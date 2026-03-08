#!/bin/bash

set -ex

CURRENT_PATH=$(cd $(dirname $0); pwd)
PROJECT_PATH=${CURRENT_PATH}/../

cd ${PROJECT_PATH}/service-stock/target
nohup java -jar service-stock-1.0-SNAPSHOT.jar &

cd ${PROJECT_PATH}/order-stock/target
nohup java -jar service-order-1.0-SNAPSHOT.jar &
