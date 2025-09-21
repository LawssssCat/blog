---
title: Redis 安装、部署
date: 2025-01-04
tag:
  - redis
order: 10
---

## 编译（compile）

```bash
# 编译环境（c语言）
yum -y install gcc automake autoconf libtool make

# 源码下载
wget http://download.redis.io/releases/redis-5.0.8.tar.gz
tar xzvf redis-5.0.8.tar.gz -C /opt
# x 解压
# z 支持gzip解压文件
# v 显示信息
# f 指定压缩文件
# -C 解压到指定目录

cd /opt/redis-5.0.8
# 编译
make
# 指定安装地址
make PREFIX=/usr/local/redis install

# 安装目录中的工具
/usr/local/redis/bin/redis-cli # redis 的客户端
/usr/local/redis/bin/redis-server # redis 的服务端
```

```bash
# =========== 后台启动 =========
/usr/local/redis/bin/redis-server # 默认配置启动
/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf # 指定配置启动
nohup /usr/local/redis/bin/redis-server > /dev/redis/logs & # 后台启动 （或者在 redis.conf 中配置 daemonize yes）
jobs -l # 查看后台任务
tail -f /dev/redis/logs # 查看日志
# 防火墙开端口
firewall-cmd --zone=public --add-port=6379/tcp --permanent
firewall-cmd --reload

# =========== 客户端连接 =========
# redis-cli -h IP地址 -p 端口 # 默认IP本机 端口 6379
/usr/local/redis/bin/redis-cli
# /usr/local/redis/bin/redis-cli -h 127.0.0.1 -p 6379

# 检测是否服务端启动
[mp0a@fedora redis-bin]$ ./bin/redis-cli
127.0.0.1:6379> ping
PONG

# =========== 关闭服务端 =========
# 方式1：（不推荐，会丢失数据）
ps -ef | grep -i redis
kill -9 PID
# 方式2：
.//redis-cli # 客户端连接进去
127.0.0.1:6379> shutdown # 输入关闭命令
```

## 单服务（single）

### Docker

```bash
docker pull redis:latest
docker run -itd --name redis-test -p 6379:6379 redis
docker exec -it redis-test /bin/bash
```

### Podman Pod（伪K8S）

`podman kube play xxx.yaml`

```yaml
<!-- @include: ./deploy-podman-pod.single.compose.yaml -->
```

### K8S

参考：

+ todo https://www.oryoy.com/news/shi-yong-kubernetes-k8s-bu-shu-redis-dan-ji-ban-wan-zheng-an-zhuang-zhi-nan.html

## 主从（master/slave）

todo

## 哨兵（sentinel）


### Podman Pod（伪K8S）

`podman kube play xxx.yaml`

todo

```yaml
<!-- @include: ./deploy-podman-pod.sentinel.compose.yaml -->
```

## 集群（cluster）

todo
