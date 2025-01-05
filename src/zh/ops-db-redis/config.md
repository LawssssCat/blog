---
title: Redis 配置和部署策略
date: 2024-04-08
tag:
  - redis
order: 99
---

## 配置

完整配置在源码目录 redis.conf 文件中：

::: details 常用配置注解（5.0.8）

```bash title="redis-5.0.8/redis.conf"
# Redis configuration file example.
#
# Note that in order to read the configuration file, Redis must be
# started with the file path as first argument:
#
# ./redis-server /path/to/redis.conf

# Note on units: when memory size is needed, it is possible to specify
# it in the usual form of 1k 5GB 4M and so forth:
#
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.

################################## INCLUDES ###################################
# 指定包含其他的配置文件，可以在同一主机上多个Redis实例之间使用同一份配置文件
# 而同时各个实例又拥有自己的特定配置文件
#
# include /path/to/local.conf
# include /path/to/other.conf

################################## MODULES #####################################
# 略

################################## MODULES #####################################
# 绑定的主机地址
# bind 127.0.0.1 #限制redis只能本地访问
bind 127.0.0.1
# protected-mode no #默认yes，开启保护模式，限制为本地访问

# 端口号
port 6379

# 客户端连接超时
timeout 0

# redis默认不是以守护进程方式（后台方式）进行
# yes 更换为守护进程开启💡改为yes会使配置文件方式启动redis失败💡
# Redis采用的是单进程多线程的模式。
# 在守护进程模式下（daemonize yes），redis会在后台运行，并将进程pid号写入至redis.conf选项pidfile设置的文件中，此时redis将一直运行，除非手动kill将该进程关闭。
# 而当不是守护进程时（daemonize no），开启进程的当前界面将进入redis命令行界面，exit强制退出、或者关闭连接工具（putty，xshell等）都会导致redis进程退出。
daemonize no

# 当redis以守护进程方式运行，redis默认把pid写入pidfile指定的文件
pidfile /var/run/redis_6379.pid

# 日志级别
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
loglevel notice

# 日志记录方式，默认为标准输出
# 如果配置为守护京进程方式运行，而这里有配置标准输出，则日志将被发送给/dev/null
# logfile ""
logfile stdout

# 设置数据库的数量
# 默认数据库为0，可以使用 SELECT <dbid> 命令在连接上指定数据库id
# （后面会讲为什么是16个，每个库有什么用）
databases 16

################################ SNAPSHOTTING  ################################
# 指定在多长时间内，有多少次更新操作，就会将数据同步到数据文件，可以多个条件配合
# save <seconds> <changes>
# 默认分表表示为：
# 900秒内有1个更改，300秒内有10个更改，60秒内有10000个更改
save 900 1
save 300 10
save 60 10000

# 指定存储至本地数据库时是否压缩数据，默认为yes
# redis采用LZF（压缩算法）
# 如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
rdbcompression yes

# 指定本地数据库文件名，默认为dump.rdb
dbfilename dump.rdb

# 指定本地数据库存放目录
dir ./

################################# REPLICATION #################################
# 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
#
# Master-Replica replication. Use replicaof to make a Redis instance a copy of
# another Redis server. A few things to understand ASAP about Redis replication.
#
#   +------------------+      +---------------+
#   |      Master      | ---> |    Replica    |
#   | (receive writes) |      |  (exact copy) |
#   +------------------+      +---------------+
#
# 1) Redis replication is asynchronous, but you can configure a master to
#    stop accepting writes if it appears to be not connected with at least
#    a given number of replicas.
# 2) Redis replicas are able to perform a partial resynchronization with the
#    master if the replication link is lost for a relatively small amount of
#    time. You may want to configure the replication backlog size (see the next
#    sections of this file) with a sensible value depending on your needs.
# 3) Replication is automatic and does not need user intervention. After a
#    network partition replicas automatically try to reconnect to masters
#    and resynchronize with them.
#
# replicaof <masterip> <masterport>

# 当master服务设置了密码保护时，slav服务连接master的密码
#
# If the master is password protected (using the "requirepass" configuration
# directive below) it is possible to tell the replica to authenticate before
# starting the replication synchronization process, otherwise the master will
# refuse the replica request.
#
# masterauth <master-password>

################################## SECURITY ###################################
# 设置Redis连接密码，如果设置了连接密码，客户端在连接Redis时，需要通过 AUTH <password>命令提供密码
# 默认关闭
#
# requirepass foobared

################################### CLIENTS ####################################
# 设置同一时间最大客户端连接数
# 默认无限制，Redis可以同时打开客户端连接数为Redis进程可以打开的最大文件描述符数
# 如果设置maxclients 0，表示不做限制。
# 当客户端连接数达到限制时，Redis会关闭新的连接并向客户端返回max number of clients reached 错误信息
#
# maxclients 10000

############################## MEMORY MANAGEMENT ################################
# 指定Redis最大内存限
# Redis在启动时会把数据加载到内存中，达到最大内存后，Redis会先尝试清除已到期或即将到期的Key。
# 当此方法处理后，仍然到达最大内存的设置，将无法再进行写入操作，但仍然可以进行读取操作。
# Redis新的vm机制，会把key存放到内存，value会存放到swap区
# 默认值官方没说
#
# maxmemory <bytes>

# 指定内存维护策略（下面马上讲）
#
# MAXMEMORY POLICY: how Redis will select what to remove when maxmemory
# is reached. You can select among five behaviors:
#
# volatile-lru -> Evict using approximated LRU among the keys with an expire set.
# allkeys-lru -> Evict any key using approximated LRU.
# volatile-lfu -> Evict using approximated LFU among the keys with an expire set.
# allkeys-lfu -> Evict any key using approximated LFU.
# volatile-random -> Remove a random key among the ones with an expire set.
# allkeys-random -> Remove a random key, any key.
# volatile-ttl -> Remove the key with the nearest expire time (minor TTL)
# noeviction -> Don't evict anything, just return an error on write operations.
#
# LRU means Least Recently Used
# LFU means Least Frequently Used
#
# Both LRU, LFU and volatile-ttl are implemented using approximated
# randomized algorithms.
#
# Note: with any of the above policies, Redis will return an error on write
#       operations, when there are no suitable keys for eviction.
#
#       At the date of writing these commands are: set setnx setex append
#       incr decr rpush lpush rpushx lpushx linsert lset rpoplpush sadd
#       sinter sinterstore sunion sunionstore sdiff sdiffstore zadd zincrby
#       zunionstore zinterstore hset hsetnx hmset hincrby incrby decrby
#       getset mset msetnx exec sort
#
# The default is:
#
# maxmemory-policy noeviction

############################# LAZY FREEING ####################################
# 略

############################## APPEND ONLY MODE ###############################
# 指定是否每次更新后进行日志记录
# Redis在默认情况下是异步的把数据库写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。
# 因为，redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。
# 默认为 no
appendonly no

# 指定更新日志名，默认为appendonly.aof
# The name of the append only file (default: "appendonly.aof")
appendfilename "appendonly.aof"

# 指定更新日志条件，共有3个可选值：
# no：表示等操作系统进行数据缓存同步到磁盘（快）
# always：表示每次更新操作后手动调用fsync()将数据写到磁盘（慢，安全）
# everysec：表示每秒同步一次（折中方案，默认值）
# 请选择：
# appendfsync always
appendfsync everysec
# appendfsync no

################################ LUA SCRIPTING  ###############################
# 略

################################ REDIS CLUSTER  ###############################
# 略

################################## SLOW LOG ###################################
# 略

################################ LATENCY MONITOR ##############################
# 略

############################### ADVANCED CONFIG ###############################
# 指定在超过一定的数量或者最大的元素超过某一临界值时，采用一种特殊的哈希算法
hash-max-ziplist-entries 512
hash-max-ziplist-value 64

# 指定是否激活重置哈希，默认为开启
# （后面再介绍Redis的哈希算法）
activerehashing yes

########################### ACTIVE DEFRAGMENTATION #######################
# 略
```

:::

重点看几个配置：（5.0.8）

- **bind 127.0.0.1** ── 注释掉这部分，这是限制 redis 只能本地访问
- **protected-mode no** ── 默认 yes，开启保护模式，限制为本地访问
- **daemonize no** ── 默认 no，改为 yes 意为以守护进程方式启动，可后台运行，除非 kill 进程（改为 yes 会使配置文件方式启动 redis 失败）
- **databases 16** ── 数据库个数（可选）
- **dir ./** ── 输入本地 redis 数据库存放文件夹（可选）
- **appendonly yes** ── redis 持久化（可选）

## 内存维护策略

todo 由头

动态数据删除（LRU 算法、LFU 算法）

> - 对于在内存中但又不用的数据块（内存块）叫做 LRU（`Least Recently Used`，最近最久未使用算法）
> - 通过内存管理的一种页面置换算法，操作系统会判断哪些数据属于 LRU 而将其移出内存而腾出统建来加载另外的数据

1. **volatile-lru**：设定的超时时间的数据中，删除最不常用的数据
2. **allkeys-lru**：查询所有的 key 中最近最不常用的数据进行删除，==这是应用最为广泛的策略==
3. volatile-random：在已经设定了超时的数据中随机删除
4. allkeys-random：查询所有的 key，之后随机删除
5. volatile-ttl：查询全部设置定超时时间的数据之后，排序，将马上要过期的数据进行删除。
6. ==noeviction：如果设置为该属性，则不会进行删除操作，如果内存溢出，则报错返回==。（默认）
7. volatile-lfu：从所有配置了过期时间的键中驱逐使用的平率最少的键
   > LFU(`Least Frequently Used` ,最近最少使用算法)也是一种常见的缓存算法
8. allkeys-lfu：从所有键中驱逐使用频率最少的键

> lfu 是 4.0 后新增策略

## 持久化策略

持久化 - redis 数据存储在内存中，持久化方案有两种：

- 定时快照（snapshot）：
  定时将数据写入磁盘，每次均是全部读写，代价非常高
- 基于语句追加（aof）：
  只往数据库变化的数据，可能导致 追加的 log 过大。
  而且追加方式是所有操作重新执行一遍，回复速度慢

**dump.rdb**

redis 数据存在内存中，也会定时将数据进行持久化

dump.rdb 里面存储的就是持久化的信息（里面有我们上面 set 的信息）

## 高可用策略

todo

- lua 脚本

## 待整理

Redis 有内置复制、Lua 脚本、LRU 收回、事务以及不同级别磁盘持久化功能，同时通过 Redis Sentinel 提高可用性、通过 Redis Cluster 提供自动分区功能。

todo
