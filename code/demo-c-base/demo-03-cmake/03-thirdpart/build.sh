#!/bin/bash

set -e

ARG_OPTION="$1"

PATH_CUR="$(cd $(dirname $0); pwd)"
PATH_PRO="$(cd ${PATH_CUR}; pwd)"


case $ARG_OPTION in
  download)
    bash "$PATH_PRO/script/handle_download.sh"
    ;;
  *)
    echo "Error"
    ;;
esac
