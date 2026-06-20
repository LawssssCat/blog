#!/bin/bash
# -------------------------------------------------------------------
# run in wsl
# -------------------------------------------------------------------
set -e

PATH_SCRIPT="$(cd "$(dirname $0)"; pwd)"
PATH_PRODUCT="$PATH_SCRIPT/product"

if pgrep -f "kafka.Kafka" > /dev/null; then
    echo "kafka正在运行"
    exit 0
fi

echo "启动Kafka..."
cd ${PATH_PRODUCT}

KRAFT_CONFIG="config/server.properties"
bin/kafka-server-start.sh ${KRAFT_CONFIG}
