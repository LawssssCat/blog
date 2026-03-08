---
title: zookeeper 介绍
order: 66
---

Zookeeper是一个分布式协调服务的开源框架。主要用来解决分布式集群中系统的一致性问题，例如怎样避免同时操作同一数据造成脏读问题。

+ ZooKeeper本质上是一个分布式的小文件存储系统。提供基于类似文件系统的目录树方式的数据存储，并且可以对树种的节点进行有效管理。

+ ZooKeeper提供给客户端**监控**存储在zk内部数据的功能，从而可以达到基于数据的集群管理的功能（**分布式服务管理平台**）。常用于管理apache的各种技术服务，如Hadoop（大象）、Hive（蜜蜂）、Pig（猪）等。
当然也能实现诸如：
统一命名服务（dubbo）、分布式配置管理（solor）的配置集中管理、分布式消息队列（sub/pub）、分布式锁、分布式协调等功能。

<!-- more -->

## 应用场景

zookeeper提供的服务包括：
统一命名服务、统一配置管理、统一集群管理、服务器节点动态上下线、负载均衡等。

### 统一配置管理

ZNode `/Config` 的数据变更会通知所有客户端

### 服务器节点动态上下线

分布式服务管理平台关心：

+ 接收服务的上下线
+ 接收观察者（客户端）的注册
+ 观察服务上下限变化，一旦服务列表发生变化，Zookeeper负责将变化通知相关观察者

### 统一命名服务

“正向代理”

### 负载均衡

“反向代理”

## 系统架构

### 集群架构

zookeeper有两种部署模式：单机模式、集群模式

+ 单机模式 —— 当配置文件中不包含`server....=....`配置时为单机模式，否则为集群模式
+ 集群模式 —— 集群模式下至少要有两个节点且需要有半数节点的服务存活，否则客户端无法操作数据

zookeeper的集群有两类角色：

+ 一个leader —— 任务为“事务请求的唯一处理者（为了确保事务一致性）”。其中有事务如：写数据、更新数据、创建节点、删除节点、...
+ 零到多个follower/observer —— 任务为“处理读请求、转发事务请求”

::: tip
具体那些节点是leader/follower，这些细节在下面“一致性算法”中会详细阐述。
:::

### 一致性算法

todo

#### 服务端：选举机制

在Zookeeper集群中，leader不是配置出来的，是通过集群内部的选举机制产生的。

选举机制：

1. 节点1启动，节点1状态为LOOKING状态。此时集群只有一个节点，集群不会响应任何报文
1. 节点2启动，节点2状态为LOOKING状态。节点1、节点2在同一集群且集群无leader，选取id大的为leader，节点2为leader、节点1为follower
1. 节点3启动，节点2状态为LOOKING状态。节点3加入上述集群，集群中已有leader，则节点3为follower
1. 节点4启动，同节点3...

#### ZAB一致性协议

ZAB（Zookeeper Atomic Broadcast，ZK原子广播）是Zookeeper中用来保证分布式事务最终一致性的协议。

::: info

ZAB、RAFT等众多分布式一致性算法都是对paxos算法的（简化）实现。
其中，ZAB协议是为分布式协调服务Zookeeper专门设计的一种支持崩溃恢复和原子广播协议。

:::

ZAB协议要求：

1. **广播消息**
    1. 对于客户端发送的写请求（事务请求），均由leader接收。
    1. Leader将请求包装为“事务”，给该事务分配一个全局递增的、唯一的ID，称为事务ID（ZXID）
    1. Leader将事务以“提议（Proposal）”的形式广播到follower节点。
    1. Leader如果收到**半数**的Follower反馈ACK，则执行commit操作（先自己提交，再通知Follower提交）

    @startuml
    node "Leader 1"
    node "Follower 2"
    node "Follower 3"
    node "Client 1"

    [Client 1] -up-> [Follower 2] : 1. update request /znode
    [Follower 2] -right-> [Leader 1] : 2. redirect
    [Leader 1] -left-> [Follower 2] : 3. proposal
    [Leader 1] -right-> [Follower 3] : 3. proposal
    [Follower 2] -right-> [Leader 1] : 4. ACK
    [Follower 3] -left-> [Leader 1] : 4. ACK
    [Leader 1] -left-> [Follower 2] : 5. commit
    [Leader 1] -right-> [Follower 3] : 5. commit
    @enduml

1. **Leader奔溃问题**
    正如上面提到的，当leader宕机后，有一套高效可靠的leader选举算法选出新的leader。
    新leader需要解决如下问题：
    + ZAB协议确保那些已经在leader提交的事务最终会被所有服务器提交
    + ZAB协议确保那些仅在leader提出/复制的事务最终会被所有服务器丢弃

#### 客户端：观察者机制（Watcher）

Zookeeper使用Watcher机制实现分布式数据的发布和订阅功能，从而实现分布式的通知功能：
Zookeeper允许客户端向服务端注册一个Watcher监听，当服务端的一些指定事件出发了这个Watcher，那么Zookeeper会向指定客户端发送一个事件通知。

相关进程/线程

+ 客户端线程
+ 客户端WatcherManager
+ Zookeeper服务器

工作流程：

+ 客户端向zookeeper服务器注册的同时，会将Watcher对象存储在客户端的WatcherManager当中
+ 当Zookeeper服务器触发Watcher事件后，会向客户端发送通知
+ 客户端线程从WatcherManager中取出对应的Watcher对象来执行回调逻辑

@startuml
node "Zookeeper Server"
node "Micro Service" {
  [Zookeeper Client]
  [Zookeeper WatcherManager]
}
[Zookeeper Client] -up-> [Zookeeper Server] : 1. register
[Zookeeper Client] -right-> [Zookeeper WatcherManager] : 1. Watcher Object
[Zookeeper Client] <-down- [Zookeeper Server] : 2. notify
[Zookeeper Client] <-left- [Zookeeper WatcherManager] : 3. Watcher Object
@enduml

### 数据模型

ZooKeeper以树形节点（ZNode，ZookeeperNode）的形式进行数据（元数据，MetaData）存储。

e.g.

```bash
/ —— 根节点
/Znode01
/Znode01/a
/Znode01/b
/Znode02/a
```

ZNode节点数据为描述数据（data about data），描述数据的属性，如存储位置、历史数据、资源查找、文件记录等，最大存储1MB

#### 节点类型（ZNode Type）

Zookeeper节点类型：

+ **持久性节点（Persistent）** —— 最常见的一种节点，被创建后会一直存在服务器（客户端与zookeeper断开连接后，该节点依旧存在），直到被主动清除
  + **顺序的持久性节点（Sequential-Persistent）** —— 有顺序的持久节点（在创建节点时，会在节点名后面加上一个数字后缀，来表示其顺序，由父节点维护，例如：Znode001，Znode002，....）
+ **临时性节点（Ephemeral）** —— 它的生命周期和客户端会话绑定在一起，客户端关闭该节点会被清理掉。【注意：临时节点不能创建子节点】
  + **顺序的临时性节点（Sequential-Ephemeral）** —— 有序的临时节点

#### 事务（ZXID）

在Zookeeper中，和事务有关的操作有：数据节点的创建/删除、数据节点内容的更新等。对于每一个事务请求，Zookeeper都会为其分配一个全局唯一的事务ID（ZXID，通常是64位自增长数字）。每个XID对应一次更新操作，这些ZXID可以简介识别出Zookeeper处理这些操作请求的全局顺序。

::: tip

事务（Transaction）用于保护系统数据的ACID特性：

+ 原子性（Atomic）
+ 一致性（Consistency）
+ 隔离性（Isolation）
+ 持久性（Durability）

:::

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

## 安装启动

Apache Zookeeper 发布版本：
<https://zookeeper.apache.org/releases.html>

Apache Zookeeper 版本归档：
<http://archive.apache.org/dist/zookeeper/>

+ apache-zookeeper-3.6.0.tar.gz —— 源码。需要使用maven安装依赖包。 运行 `mvn clean install` 和 `mvn javadoc:aggregate` 命令
+ apache-zookeeper-3.6.0-bin.tar.gz —— 制品。自带依赖包

测试代码仓：
<RepoLink path="/code/demo-zookeeper/demo-01-baseusage/" />

### 单机模式（JAR，STANDALONE）

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

### 集群模式（JAR，CLUSTER）

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

#### todo 易用性封装

todo
