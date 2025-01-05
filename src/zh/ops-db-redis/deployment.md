---
title: Redis 安装、部署
date: 2025-01-04
tag:
  - redis
order: 10
---

## 编译

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

## Docker

```bash
docker pull redis:latest
docker run -itd --name redis-test -p 6379:6379 redis
docker exec -it redis-test /bin/bash
```

## K8S

`podman kube play xxx.yaml`

```yaml
apiVersion: apps/v1
kind: Pod
metadata:
  name: ruoyi-pod
spec:
  containers:
    - name: mysql
      image: docker.io/library/mysql:5.7.35
      # imagePullPolicy: IfNotPresent
      # command:
      # args:
      ports:
        - containerPort: 3306
          hostPort: 23306
          # port: 30080             # 服务访问端口，集群内部访问的端口
          # targetPort: 3306          # pod控制器中定义的端口（应用访问的端口）
          # nodePort: 23306           # NodePort，外部客户端访问的端口
      volumeMounts:
        - name: mysql-data
          mountPath: /var/lib/mysql
        - name: mysql-logs
          mountPath: /var/lib/logs
        # - name: mysql-conf
        #   mountPath: /etc/mysql/mysql.conf.d/mysql.cnf
      env:
        - name: MYSQL_ROOT_PASSWORD
          value: 123456
        - name: MYSQL_USER
          value: azi
        - name: MYSQL_PASSWORD
          value: 123mysql
    - name: redis
      image: docker.io/library/redis:7.2.6
      command:
        - "redis-server"
        - "/usr/local/etc/redis/redis.conf"
      ports:
        - containerPort: 6379
          hostPort: 26379
      volumeMounts:
        - name: redis-data
          mountPath: /data
        - name: redis-logs
          mountPath: /logs
        - name: redis-config
          mountPath: /usr/local/etc/redis/
          readonly: true

  volumes:
    - name: mysql-data
      persistentVolumeClaim:
        claimName: ry-mysql-data
    - name: mysql-logs
      persistentVolumeClaim:
        claimName: ry-mysql-logs
    - name: redis-data
      persistentVolumeClaim:
        claimName: ry-redis-data
    - name: redis-logs
      persistentVolumeClaim:
        claimName: ry-redis-logs
    - name: redis-config
      configMap:
        name: ry-config-map
        items:
          - key: redis-config
            path: redis.conf

---
apiVersion: v1
kind: ConfigMap
metadata:
  name: ry-config-map
data:
  redis-config: |-
    port 6379
    bind 0.0.0.0
    appendonly yes
    save 60 1000
    maxmemory 512mb
    maxmemory-policy allkeys-lru
```
