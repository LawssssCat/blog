#!/bin/bash

set -e

PATH_CUR="$(cd $(dirname $0); pwd)"
PATH_PRO="$(cd ${PATH_CUR}/..; pwd)"

function handle_download() {
  local thirdpart_home="$1"
  local thirdpart_url="$2"
  local thirdpart_tag="$3"
  local thirdpart_home_abs="$PATH_PRO/thirdpart/$thirdpart_home"
  if [ ! -d "$thirdpart_home_abs" ]; then
    echo "clone $thirdpart_home_abs ..."
    local tmp_dir="/tmp/$thirdpart_home_abs"
    rm -rf "$tmp_dir"
    git clone --depth 1 -b "$thirdpart_tag" -- "$thirdpart_url" "$tmp_dir"
    rm -rf "$tmp_dir/.git"
    mkdir -p "$(dirname "$thirdpart_home_abs")"
    mv "$tmp_dir" "$thirdpart_home_abs" # fix file busy problem
  fi
  echo "clone $thirdpart_home_abs ok"
}

function main() {
  echo ----- download start -----
  handle_download googletest https://github.com/google/googletest.git v1.17.0
  echo ------ download ok ------
}

main
