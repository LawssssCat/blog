#!/bin/bash

PATH_SCRIPT="$(cd "$(dirname $0)"; pwd)"
PATH_NGINX="$PATH_SCRIPT/product_nginx"

cd "$PATH_NGINX"

ls
./nginx -c "$PATH_SCRIPT"/conf/nginx.conf -s stop

