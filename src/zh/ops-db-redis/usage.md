---
title: Redis 基本用法
date: 2024-04-08
tag:
  - redis
order: 20
---

## 常见命令

```bash
########################
# 检查连通性
127.0.0.1:6379> ping
pong
127.0.0.1:6379> echo a
"a"

########################
# 选择数据库（设计多个数据库为了数据库安全和备份）
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> move a 1 # 将当前数据库中的 a 转移到编号为 2 的数据库
(integer) 1 # 移动成功
127.0.0.1:6379> move a 2
(integer) 0 # 移动失败：没有定义a的值
127.0.0.1:6379> flushdb  # 清空当前数据库
127.0.0.1:6379> flushall # 清空所有数据库

127.0.0.1:6379> dbsize # 查看数据库的 key 数量
(integer) 17
127.0.0.1:6379> info # 查看数据库信息
.....非常多
127.0.0.1:6379> config get * # 实时传存储收到的请求，返回相关的配置

########################
# 设值/取值/删除 （有效期）
# ❗不同数据类型，其 get / set 命令均不同❗

# 如：字符串
set key1 value1
get key1
# 如：哈希表（H开头）
hset key1 field1 value1
hget key1 field1
# 如：链表（L开头）
# 如：集合（S开头）
# 如：有序集合（Z开头）
# ...

# type key # 获取数据类型

127.0.0.1:6379> del b # 删除b的值
(integer) 0
127.0.0.1:6379> exists a # 判断a是否存在值。1=存在，0=不存在
(integer) 1

# 取键名
127.0.0.1:6379> keys * # 查询所有键名
1) "a"
# keys h?llo => hello, hallo, hxllo, ...
# keys h*llo => hello, hllo, hxxxxxxxxllo, ....
# keys h[ae]llo => hello, hallo
127.0.0.1:6379> keys a # 查询a键名
1) "a"
127.0.0.1:6379> randomkey # 随机返回一个键名
"a"
127.0.0.1:6379> rename a b # 重新设置键名
OK

########################
# 存活时间（TTL, time to live）
# 设置值+有效时间
127.0.0.1:6379> expire a 1 # 设置有效时间
(integer) 1
127.0.0.1:6379> pexpire a 1000 # 以毫秒（milliseconds）为单位
(integer) 1
# pexireat key milliseconds-timestamp # 设定过期时间戳
127.0.0.1:6379> persist a # 设置有效时间-1（设置为一直时间不过期）
(integer) 0

# 取有效时间
# 永久存活/没设置存活时间 —— -1
# 过期/不存在 —— -2
# 存活时间 —— 正数
127.0.0.1:6379> ttl a
(integer) -1 # -1 表示永久有效
127.0.0.1:6379> ttl a
(integer) 8 # 表示还有8秒过期
127.0.0.1:6379> ttl *
(integer) -2 # -2 表示没有找到/已过期
127.0.0.1:6379> ttl b
(integer) -2
# pttl key # 返回毫秒
```

## 规范

命名规范：

```bash
user:id:name
如
user:1:zhangshang
user:2:lisi

sys_config:sys.account.captchaEnabled
sys_dict:sys_oper_type
sys_dict:sys_notice_status
sys_config:sys.index.sideTheme
sys_config:sys.login.blackIPList
sys_dict:sys_job_status
sys_dict:sys_user_sex
sys_dict:sys_yes_no
```

## 数据类型

Redis 支持丰富数据类型：

- **string**（字符串）
- **hash**（哈希）
- **list**（列表）
- **set**（集合）
- **zset**（sorted set，有序集合）
- 位图
- hyperloglogs
- 等（新版会增加类型）

### 字符串（string）

::: warning

- 最大存储 512MB
- 二进制安全，可以包含任何数据：
  - 比如序列化的对象进行存储
  - 比如一张图片进行二进制存储
  - 比如简单的字符串，数值等等

:::

```bash
127.0.0.1:6379> set a 1
OK
127.0.0.1:6379> mset a 1 b 2 # 批量设置
OK
127.0.0.1:6379> setex a 10 1 # 设置a的值为1，且有效时间10s only for 字符串
OK

# 计算
127.0.0.1:6379> setnx a 1 # （NX = not exists）设置如果不存在
(integer) 0 # 设置成功1，设置不成功1

# SETRANGE KEY offset value     # 替换字符串
# SETBIT KEY offset value       # 设置指定 key 对应偏移量上的 bit 值，value 只能是1或者0
# GETSET KEY value              # 设置KEY上新的值value，并返回旧的值。❗当key不存在时，依然存入新的值，并返回nil
# INCR KEY                      # 自增 ❗如果KEY不存在，那么结果为 1 （KEY会先被初始化为 0，然后马上自增为 1）
# INCRBY KEY increment          # 指定增量值
# DECR KEY                      # 自减 ❗如果KEY不存在，那么结果为 -1 （KEY会先被初始化为 0，然后马上自减为 -1）
# DECRBY KEY decrement          # 指定减量值
# APPEND KEY VALUE              # 字符串拼接（追加至末尾）

127.0.0.1:6379> get a # 查询a的值
"1"
127.0.0.1:6379> get b
(nil) # 没查到/已过期

# GET K1 K2 K3 ... 批量读
# GETRANGE KEY offset end # 字符量截取
# GETBIT KEY offset # 对KEY所存储字符串，获取指定的偏移量上的位（bit）
# GETSET KEY value # 设置KEY上新的值value，并返回旧的值。❗当key不存在时，依然存入新的值，并返回nil
# STRLEN KEY # 返回KEY所存储的字符串的长度
```

### 哈希表（Hash）

H 开头

```bash
127.0.0.1:6379> hset h1 name haha
(integer) 1
127.0.0.1:6379> hset h1 age 18
(integer) 1
127.0.0.1:6379> hget h1 name
"haha"
127.0.0.1:6379> hget h1 age
"18"

# HSETNX KEY FIELD value          # 只有在字段FIELD不存在时，设置值
# HINCRBY KEY FIELD increment     # 为KEY对象（整数类型的）FIELD属性设置自增量
# HINCRBYFLOAT KEY FIELD increment # 为KEY对象（浮点类型的）FIELD属性设置自增量

# 批量存取
127.0.0.1:6379> hmset h1 a 1 b 2
(integer) 2
127.0.0.1:6379> hmget h1 a b
1) "1"
2) "2"

# HEXISTS KEY FIELD # 查看KEY对象的FIELD是否存在
# HGETALL KEY        # 获取KEY中全部属性值
# HKEYS KEY          # 获取KEY对象的所有属性名
# HLEN KEY           # 获取KEY对象属性的个数
# HDEL KEY F1 F2 ... # 删除一个或多个KEY对象的属性
```

### 链表（list）

L 开头

**特点：**

- 有序
- 可重复

```bash
127.0.0.1:6379> lpush a 0 # 栈顶插入
(integer) 1
127.0.0.1:6379> rpush a x # 栈底插入
(integer) 2
127.0.0.1:6379> lpush a 1 2 3 4 # 批量插入
(integer) 6 # 长度/位置
# lpop key # 弹出栈顶（最后push的值）
# rpop key # 弹出栈底（最先push的值）
# blpop key timeout # 阻塞，等待有值后才返回，timeout超时时间❗如果为0，则一直阻塞直到有返回值
# brpop key timeout

127.0.0.1:6379> lrange a 0 100 # ❗倒序返回
1) "5"
2) "4"
3) "3"
4) "2"
5) "1"
5) "x"
6) "0"

# lset key index newValue                 # 覆盖新值
# linsert key before|after value newValue # 找到值，插入
# lrem key count value
# ltrim key start end
```

### 集合（set）

S 开头

**特点**：

- 元素无序（不记录插入顺序）
- 唯一

```bash
127.0.0.1:6379> SADD runoobkey redis
(integer) 1
127.0.0.1:6379> SADD runoobkey mongodb
(integer) 1
127.0.0.1:6379> SADD runoobkey mysql
(integer) 1
127.0.0.1:6379> SADD runoobkey mysql
(integer) 0
127.0.0.1:6379> scard runoobkey # 获取集合的成员数
(integer) 4
127.0.0.1:6379> SMEMBERS runoobkey
1) "mysql"
2) "mongodb"
3) "redis"

# SISMEMBER KEY member      # 判断 member 元素是否是集合 KEY 的成员
# SRANDMEMBER key [count]   # 返回集合中一个或多个随机数

# SREM KEY member1 member2 ...    # 移除集合中一个或多个成员
# SPOP KEY [count]                # 移除并返回集合中的一个或多个随机元素
# SMOVE source destination member # 将 member 元素从 source 集合移动到 destination 集合

差集：
# SDIFF key1 key2 ...                   # 返回给定所有集合的差集（左侧）
# SDIFFSTORE destination key1 key2 ...  # 返回给定所有集合的差集，并存储在 destination 中

交集：
# SINTER key1 key2 ...                  # 返回给定所有集合的交集(共有数据)

并集：
# SUNION key1 key2 ...                  # 返回所有给定集合的并集
# SUNIONSTORE destination key1 key2 ... # 所有给定集合的并集存储在 destination 集合中
```

### 有序集合（zset/sorted set）

Z 开头

**特点**：

- 元素有序
- 唯一

**命令**

```bash
赋值：
	ZADD KEY score1 member1 score2 member2 ....
	向有序集合添加一个或多个成员
	或更新已存在成员的分数

取值：
	ZCARD KEY
	获取有序集合的成员数

	ZCOUNT KEY min max
	计算在有序集合中，指定取件分数的成员数

	ZRANK KEY member
	返回有序集合中，指定成员的索引

	ZRANGE KEY start stop [WITHSCORES]
	通过索引区间，返回有序集合指定区间内的成员
	（低到高）

	ZREVRANGE KEY start stop [WITHSCORES]
	通过索引区间，返回有序集合指定区间内的成员
	（高到低）

	ZRANGEBYSCORE KEY min max [WITHSOCRES] [LIMIT]
	通过分数，返回有序集合指定区间内的成员
	（低到高）

	ZREVRANGEBYSCORE KEY max min [WITHSCORES]
	同上
	（高到低）

删除：
	DEL KEY
	移除集合

	ZREM KEY member member ...
	移除有序集合中一个或多个成员

	ZREMRANGEBYSCORE KEY start stop
	移除有序集合中给定的排名区间的所有成员
	（第一名是0）
	（低到高排序）

	ZRERANGEBYSCORE KEY min max
	移除有序集合中，给定的分数区间的所有成员

	ZINCBY KEY increment member
	增加member元素的分数increment，返回值是更改后的分数
```

### Bitmaps 位统计

存储需求：存储位的状态

应用场景：统计有/无两种状态的数据

- 某电影一周内是否有没点播
- 某账号一年内是否有被使用

```bash
# SETBIT KEY offset value     # 设置指定KEY对应偏移量上的bit值，value只能是1或者0
# GETBIT KEY offset           # 获取指定KEY对应偏移量上的bit值
# bitcount KEY [start end]    # 统计指定KEY中1的数量

# 对指定的KEY按位进行交、并、非、异或操作，并将结果保存到destKey中
bitop op destkey key1 key2 ...
# op 可以是：
# and 交
# not 非
# or 并
# xor 异或

e.g.
   0101 0011
or 1101 1001
=  1101 1001
```

### HyperLogLog 基数统计

HyperLogLog 是用来做基数统计的，运用了 loglog 算法。

基数：是数据集去重后元素个数

e.g.

```
{1,3,5,7,5,8} => 基数集合：{1,3,5,7,8} => 基数：5
{1,1,1,7,1,1} => 基数集合：{1,7}       => 基数：2
```

基数估算算法（loglog 算法）：（[推导过程](https://www.jianshu.com/p/55defda6dcd2)）

HyperLogLog 与 bitmap 的区别是：

- bitmap 保存统计数据，统计上限收数据上限限制，统计结果准确
- HyperLogLog 不保存统计数据，最大占用 12k （2^64^，最大值不随统计数量变化），但实际可统计数据量远大于 12k，但核心是基数估算算法，最终数值存在一定误差

**应用场景：** 基本与 bitmap 相同，但一般在被统计数据量大于 12k 的情况下，且不需要有精确结果的情况下使用。

- 统计注册 IP 数
- 统计每日访问 IP 数
- 统计页面实时 UV 数
- 统计在线用户数
- 统计用户每天搜索不同词条的个数
- 统计真实文章阅读数
- ...

```bash
PFADD KEY element element ...
	添加指定元素到 HyperLogLog 中

PFCOUNT key1 key2 ...
	返回给定 HyperLogLog 的基数估算值

PFMERGE destkey sourcekey sourcekey ...
	将多个 HyperLogLog 合并为一个 HyperLogLog
```

### GEO

GEO（Geostationary Earth Orbit，静止地球轨道），即经度纬度

**基本操作**

```bash
geoadd  key longitude latitude member  [longitude latitude member  ...]
	添加坐标点

geopos key member [member ... ]
	获取坐标点

geodist key member1 member2 [unit]
	计算坐标点距离
```

```bash
georaidus key longitude latitude radius m|km|ft|mi [withcoord] [withdist] [withhash] [count count]
	根据坐标求范围内的数据

georadiusbymember key member radius m|km|ft|mi [withcoord] [withdist] [withhash] [count count]
	根据点求范围内数据

geohash key member [member...]
	获取指定点对应的坐标hash值
```

## 事务

redis 事务四大命令：

1.  MULTI ── 开启事务
1.  EXEC ── 提交
1.  DISCARD ── 回滚
1.  WATCH ── “乐观锁”，监视一个（或者多个）KEY：
    `watch` 需要在 `multi` 前设置。
    如果在 `watch` 到 `exec` 命令的过程中，被监控的 key 值发生改变，那么事务中执行的全部命令都将不生效，`exec` 命令返回 `nil` 结果。

    e.g.

    ```bash
    session_01> watch account:a
    OK
    session_02> set account:a 10
    OK
    session_01> multi
    OK
    session_01> incr account:a
    QUEUED
    session_01> exec
    (nil)
    ```

1.  UNWATCH ── 对所有 KEY 的监视

Redis 事务两点需要注意：

- **事务队列** —— 从 `multi` 命令开始，到 `exec` 命令开始后，Redis 才会将一个事务中的所有命令序列化，然后按顺序执行，执行中不会被其他命令插入。
- **异常处理** —— 收到 `EXEC 命令` 后进入事务执行，事务中任意命令执行失败，其余的命令依然被执行。 （语法错误除外）

应用场景：

- 秒杀 —— 保证一组命令在执行的过程中不被其他命令插入

## 消息发布、订阅

```bash
# 订阅“log”管道
subscribe log
# 发布消息给“log”管道
publish log hello
```

::: tip

发布订阅功能对比：

| &nbsp;           | redis                              | rabbitmq                                 |
| ---------------- | ---------------------------------- | ---------------------------------------- |
| 可靠性：确认机制 | 无，只是将消息依次发送给每个订阅者 | 有                                       |
| 可靠性：持久化   | 无，没有正对消息的持久化           | 有                                       |
| 监控             | 无                                 | 有，可以在内置后台看到所有队列的详细情况 |

:::
