#!/bin/bash

SCRIPT_PATH="$(cd $(dirname $0); pwd)"
THIRD_PATH="$SCRIPT_PATH/thirdpart"

mkdir -pv "$THIRD_PATH"
cd "$THIRD_PATH"