#!/bin/bash

# -Xmx100m

java -version

# 配置中心
nohup java -jar xx-config-1.0-SNAPSHOT.jar > ./log/run-config.log &

# 注册中心
nohup java -jar xx-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=eureka8701 > ./log/run-eureka8701.log &
nohup java -jar xx-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=eureka8702 > ./log/run-eureka8702.log &
nohup java -jar xx-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=eureka8703 > ./log/run-eureka8703.log &

# 接口调用看板
# nohup java -jar xx-hystrix-1.0-SNAPSHOT.jar > ./log/run-hystrix.log &

nohup java -jar xx-goods-1.0-SNAPSHOT.jar --server.port=9101 --eureka.instance.instance-id=xx-goods-01 > ./log/run-goods.log &
nohup java -jar xx-goods-1.0-SNAPSHOT.jar --server.port=9102 --eureka.instance.instance-id=xx-goods-02 > ./log/run-goods.log &
nohup java -jar xx-portal-1.0-SNAPSHOT.jar > ./log/run-portal.log &
