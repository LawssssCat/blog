#!/bin/bash

PATH_SCRIPT="$(cd "$(dirname $0)"; pwd)"
PATH_NGINX="$PATH_SCRIPT/product_nginx"

mkdir -pv $PATH_NGINX; cd $_;

if [ ! -f "nginx-1.30.3.zip" ]; then
  wget https://nginx.org/download/nginx-1.30.3.zip
fi
if [ ! -d "nginx-1.30.3" ]; then
  unzip nginx-1.30.3.zip
  mv nginx-1.30.3/* ./
fi

echo "install ok"
