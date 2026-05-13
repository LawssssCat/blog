---
title: zookeeper 介绍
---

Zookeeper是一个分布式协调服务的开源框架。主要用来解决分布式集群中系统的一致性问题。

<!-- more -->

## 基本概念

> 参考：
>
> + [wangzhiwubigdata/God-Of-BigData - Zookeeper简介及核心概念](https://github.com/wangzhiwubigdata/God-Of-BigData/blob/master/%E5%A4%A7%E6%95%B0%E6%8D%AE%E6%A1%86%E6%9E%B6%E5%AD%A6%E4%B9%A0/Zookeeper%E7%AE%80%E4%BB%8B%E5%8F%8A%E6%A0%B8%E5%BF%83%E6%A6%82%E5%BF%B5.md)

## 一致性

> 参考：
>
> + [bilibili - 动画讲解：ZAB(Zookeeper Atomic Broadcast)算法原理](https://www.bilibili.com/video/BV1WN411c7xh)

一致性取舍：
CP —— 保证一致性（Consistency）、分区容错性（Partition Tolerance），不保证可用性（Availability）

一致性算法：
ZAB（Zookeeper Atomic Broadcast，ZK原子广播）是Zookeeper中用来保证分布式事务最终一致性的协议。

该一致性算法需要理解的事件：

+ 选举：todo
+ todo

## 维护

### 配置文件

```bash
/opt/zookeeper/conf$ cat zoo.cfg
# Zookeeper服务端与客户端心跳时间
# The number of milliseconds of each tick
tickTime=2000
# 集群中的Follower与Leader之间最大容忍心跳
# The number of ticks that the initial
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
#dataDir=/tmp/zookeeper
dataDir=/opt/zookeeper/zkdata
dataLogDir=/opt/zookeeper/zklog
# the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

## Metrics Providers
#
# https://prometheus.io Metrics Exporter
#metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
#metricsProvider.httpPort=7000
#metricsProvider.exportJvmInfo=true
```

### 安装启动

Apache Zookeeper 发布版本：
<https://zookeeper.apache.org/releases.html>

Apache Zookeeper 版本归档：
<http://archive.apache.org/dist/zookeeper/>

+ apache-zookeeper-3.6.0.tar.gz —— 源码。需要使用maven安装依赖包。 运行 `mvn clean install` 和 `mvn javadoc:aggregate` 命令
+ apache-zookeeper-3.6.0-bin.tar.gz —— 制品。自带依赖包

测试代码仓：
<RepoLink path="/code/demo-zookeeper/demo-01-baseusage/" />

#### 单机模式（JAR，STANDALONE）

安装

```bash
tar -zxvf apache-zookeeper-3.6.0-bin.tar.gz
mv apache-zookeeper-3.6.0-bin /opt/zookeeper
# 修改配置
mkdir /opt/zookeeper/data
mkdir /opt/zookeeper/log
cd /opt/zookeeper/conf
cp zoo_sample.cfg zoo.cfg
vim /opt/zookeeper/conf/zoo.cfg
# 数据/日志存储位置
# dataDir=/opt/zookeeper/data
# dataLogDir=/opt/zookeeper/log
```

启动

```bash
/opt/zookeeper$ ./bin/zkServer.sh start
/usr/bin/java
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
/opt/zookeeper$ ./bin/zkServer.sh status
/usr/bin/java
ZooKeeper JMX enabled by default
Using config: /opt/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: standalone
/opt/zookeeper$ jps
8662 QuorumPeerMain # zookeeper进程
8732 Jps
```

进入zookeeper

```bash
/opt/zookeeper/bin$ ./zkCli.sh
```

#### 集群模式（JAR，CLUSTER）

<https://zookeeper.apache.org/releases.html>

zookeeper-3.4.14.tar.gz `/opt/xx/software`

```bash
$ tar -zxvf zookeeper-3.4.14.tar.gz -C ../servers/
# 目录
$ mkdir -p /opt/xx/servers/zookeeper-3.4.14/data
$ mkdir -p /opt/xx/servers/zookeeper-3.4.14/data/logs
# 配置
$ cd /opt/xx/servers/zookeeper-3.4.14/conf
$ mv zoo_sample.cfg zoo.cfg
$ vim zoo.cfg

# 数据
dataDir=/opt/xx/servers/zookeeper-3.4.14/data
dataLogDir=/opt/xx/servers/zookeeper-3.4.14/data/logs
# 集群
## server.服务器ID=服务器IP地址:通信断开:投票端口
server.1=linux121:2888:3888
server.2=linux122:2888:3888
server.3=linux123:2888:3888
#自动清理事务日志和快照文件（小时）
autopurge.purgeInterval=1

# 设置服务器ID for 选举主节点
$ echo 1 > /opt/xx/servers/zookeeper-3.4.14/data/myid
```

分发：

```bash
$ cat /vagrant/zk_rsync.sh # 同步脚本
#!/bin/bash
# todo ansible 实现
# set -ex
set -e

path="$1"
echo "PATH: $path"

ip=$(ip addr show | grep -E 'inet [0-9]' | awk '{print $2}' | awk -F '/' '{print $1}' | grep '192.168.56')
echo "IP: $ip"

# 待分发地址
ips=(
'192.168.56.10'
'192.168.56.11'
'192.168.56.12'
)

# 需要先配置免密登录~
for ip in ${ips[@]}; do
  echo "====> $ip"
  rsync -azvh --rsync-path="sudo rsync" --mkpath $path vagrant@$ip:$path;
done

$ bash zk_rsync.sh /opt/xx/servers/zookeeper-3.4.14/

# 更新id
$ ssh vagrant@192.168.56.11 'echo 2 > /opt/xx/servers/zookeeper-3.4.14/data/myid'
$ ssh vagrant@192.168.56.12 'echo 3 > /opt/xx/servers/zookeeper-3.4.14/data/myid'

# 启动
$ bash /opt/xx/servers/zookeeper-3.4.14/bin/zkServer.sh start
$ ssh vagrant@192.168.56.11 bash /opt/xx/servers/zookeeper-3.4.14/bin/zkServer.sh start
$ ssh vagrant@192.168.56.12 bash /opt/xx/servers/zookeeper-3.4.14/bin/zkServer.sh start
# 检查
# 如果失败了，检查权限、环境（jre）
$ bash /opt/xx/servers/zookeeper-3.4.14/bin/zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /opt/xx/servers/zookeeper-3.4.14/bin/../conf/zoo.cfg
Mode: follower

# node-1 follower
# node-2 leader
# node-3 follower
```

集群管理：

```bash
#!/bin/sh
echo "start zookeeper server..."
if(($#==0));then
echo "no params";
exit;
fi
hosts="192.168.56.10 192.168.56.11 192.168.56.12"

for host in $hosts; do
ssh $host "source /etc/profile; bash /opt/xx/servers/zookeeper-3.4.14/bin/zkServer.sh $1"
done
```

## 客户端访问

### zkCli

```bash
# 进入zookeeper安装目录执行下面命令进入客户端
# bin/zkCli.sh -server ip:port # 连接指定zookeeper服务器
$ bin/zkCli.sh                 # 连接本地zookeeper服务器
[zk: localhost:2181(CONNECTED) 2] help # 查看全部命令
ZooKeeper -server host:port cmd args
        stat path [watch]
        set path data [version]            # 改
        ls path [watch]
        delquota [-n|-b] path
        ls2 path [watch]
        setAcl path acl
        setquota -n|-b val path
        history
        redo cmdno
        printwatches on|off
        delete path [version]
        sync path
        listquota path
        rmr path                           # 删
        get path [watch]                   # 查
        create [-s] [-e] path data acl     # 增 -s顺序 -e临时
        addauth scheme auth
        quit
        getAcl path
        close
        connect host:port

# 增删改查
[zk: localhost:2181(CONNECTED) 3] create -s /zk-test 123   # 增：新增顺序+持久节点。tips：需要包含内容，否则不会创建，也不会提示创建失败（坑）
Created /zk-test0000000000 # 顺序节点，名字后自动添加编号
[zk: localhost:2181(CONNECTED) 4] create /zk-test 123      # 增：新增持久节点
Created /zk-test
[zk: localhost:2181(CONNECTED) 0] create -e /zk-temp x     # 增：新增临时节点。tips：临时节点需要有内容，否则“Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3”报错
Created /zk-temp
[zk: localhost:2181(CONNECTED) 17] create /11/22/33 44     # 增：节点需要一个个创建
Node does not exist: /11/22/33
[zk: localhost:2181(CONNECTED) 1] ls /zookeeper            # 查：查看根路径子节点信息
[quota]
[zk: localhost:2181(CONNECTED) 0] get /zookeeper           # 查：获取根路径节点状态信息
cZxid = 0x0                              # 即 Create ZXID 缩写，表示节点被创建时的事务ID
ctime = Thu Jan 01 00:00:00 UTC 1970     # 即 Create Time 缩写
mZxid = 0x0                              # 即 Modified ZXID 缩写，表示节点最后一次被修改时的事务ID
mtime = Thu Jan 01 00:00:00 UTC 1970     # 即 Modified Time 缩写
pZxid = 0x0                              # 表示该节点的子节点列表最后一次被修改的事务ID（只有子节点列表变更时才会更新pZid，子节点内容变更不会更新）
cversion = -1                            # 表示子节点的版本号
dataVersion = 0                          # 表示内容的版本号
aclVersion = 0                           # 表示acl版本
ephemeralOwner = 0x0                     # 表示创建该临时节点时的会话sessionID，如果是持久性节点值为0
dataLength = 0                           # 表示数据长度
numChildren = 1                          # 表示直系子节点数
[zk: localhost:2181(CONNECTED) 1] set /xx aa               # 改：需要节点存在
Node does not exist: /xx
[zk: localhost:2181(CONNECTED) 2] set /zk-temp 666         # 改：
cZxid = 0x200000009
ctime = Sat Sep 13 17:15:05 UTC 2025
mZxid = 0x20000000d                      # 序号变化
mtime = Sat Sep 13 17:22:05 UTC 2025
pZxid = 0x200000009
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x1000a5d8de20003
dataLength = 3
numChildren = 0
[zk: localhost:2181(CONNECTED) 3] delete /zk-test          # 删：tips：如果节点有子节点，需要先删除子节点
```

### Java Client

测试代码仓：
<RepoLink path="/code/demo-zookeeper/demo-02-javaclient/" />

#### 原生接口

增删改查代码：

```java
package org.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        // 创建会话
        Watcher watcher = event -> LOGGER.info("event = {}", event);
        String connectStr = "192.168.56.10";
        int sessionTimeout = 60000;
        ZooKeeper zooKeeper = new ZooKeeper(connectStr, sessionTimeout, watcher);
        LOGGER.info("zooKeeper.getSessionId() = {}", zooKeeper.getSessionId());

        // 创建节点
        try {
            String str = zooKeeper.create(
                    "/java-client",
                    "hello".getBytes(),
                    // permission就是一个int表示的位码，每个位表示一个对应的操作的允许状态
                    // 类似unix的文件权限，不同的是共有5种操作：CREATE、READ、WRITE、DELETE、ADMIN（对应更改ACL的权限）
                    // OPEN_ACL_UNSAFE 创建开放节点，允许任意操作
                    // READ_ACL_UNSAFE 创建只读节点
                    // CREATOR_ALL_ACL 创建者才有全部权限
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT
            );
            LOGGER.info("created result = {}", str);
        } catch (KeeperException.NodeExistsException e) {
            LOGGER.warn("node exist"); // 节点已存在会报错
        }

        // 获取节点
        Stat statGet = new Stat();
        int versionGet = 0;
        try {
            byte[] data = zooKeeper.getData("/java-client", false, statGet);
            versionGet = statGet.getVersion();
            LOGGER.info("version = {}, data = {}", versionGet, new String(data));
        } catch (KeeperException.NoNodeException e) {
            LOGGER.warn("no node", e); // 节点不存在会报错
        }

        // 修改节点
        // try {
        //     // 指定版本，可能会报错，如果该版本已经被使用
        //     Stat statSet = zooKeeper.setData("/java-client", "hello-1".getBytes(), 0);
        //     LOGGER.info("modify stat = {}", statSet);
        // } catch (KeeperException.BadVersionException e) {
        //     LOGGER.error("bad version", e); // 节点版本已存在会报错
        // }
        int versionSet = versionGet + 1;
        try {
            // 根据获取的结果指定版本，可能会报错，当有其他客户端在get-set间隔中完成更新
            Stat statSet = zooKeeper.setData("/java-client", ("hello-" + versionSet).getBytes(), versionGet);
            LOGGER.info("modify stat = {}", statSet);
        } catch (KeeperException.BadVersionException e) {
            LOGGER.error("bad version", e); // 节点版本已存在会报错
        }

        // 删除节点
        try {
            zooKeeper.delete("/java-client", versionSet);
            LOGGER.info("delete ok");
        } catch (KeeperException.BadVersionException e) {
            LOGGER.error("bad version", e); // 节点版本已存在会报错
        }
    }
}
```
