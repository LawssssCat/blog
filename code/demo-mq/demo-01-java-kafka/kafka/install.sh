#!/bin/bash
# -------------------------------------------------------------------
# https://kafka.apache.org/community/downloads/
# note:
# run in wsl
# -------------------------------------------------------------------
set -e

PATH_SCRIPT="$(cd "$(dirname $0)"; pwd)"
PATH_PRODUCT="$PATH_SCRIPT/product"

mkdir -pv "$PATH_PRODUCT" && cd $_

if [ ! -f "kafka_2.13-4.3.0.tgz" ]; then
  wget https://www.apache.org/dyn/closer.lua/kafka/4.3.0/kafka_2.13-4.3.0.tgz
fi

if [ ! -d "kafka_2.13-4.3.0" ]; then
  # .tar.gz 和 .tgz 完全等价
  tar -xzf kafka_2.13-4.3.0.tgz
  mv kafka_2.13-4.3.0/* ./
fi

if [ ! -d "kraft-combined-logs" ]; then
  CLUSTER_ID=$(bin/kafka-storage.sh random-uuid)
  echo "生成的 UUID: ${CLUSTER_ID}"
  KRAFT_CONFIG="config/server.properties"
  sed -i "s|log.dirs=.*|log.dirs=./kraft-combined-logs|g" ${KRAFT_CONFIG}
  bin/kafka-storage.sh format --standalone -t ${CLUSTER_ID} -c ${KRAFT_CONFIG}
fi

echo "install ok"
