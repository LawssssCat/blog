#!/bin/bash

PATH_SCRIPT="$(cd "$(dirname $0)"; pwd)"
PATH_NGINX="$PATH_SCRIPT/product_nginx"

cd "$PATH_NGINX"

ls
./nginx -c "$PATH_SCRIPT"/conf/nginx.conf -s stop &
./nginx -c "$PATH_SCRIPT"/conf/nginx.conf &

tail -Fn 999 logs/access.log nohup.out
