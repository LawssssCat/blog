---
title: Flume
order: 10
---

Flume是分布式的、可靠的、高可用的服务，用于有效地采集、聚合和移动大量日志数据。

## 版本

+ Flume OG（Original Generation） —— 初始版本。该版本有代码臃肿、核心组件设计不合理、核心配置不标准等问题。
+ Flume NG（Next Generation） —— 2011年启动的重构版本（重构核心组件、核心配置、代码架构）。

## 架构

Flume运行的核心是Agent。Flume以Agent为最小的独立运行单位，它是一个完整的数据采集工具。

Flume Agent 包含三个核心组件，分别是：

+ source组件 —— 与数据源进行交互，采集数据，封装成event（事件，对应的是一条记录，有结构：header=键值对、body=采集到的数据内容）（一般一行日志一个event）
+ channel组件 —— 将source传输的event进行缓存，然后传输给sink
+ sink组件 —— 将接收到的event下沉到存储系统或者是下一个Agent的source组件中
+ 其他组件
  + selector —— 将event分发到不同的channel
  + interceptor —— 拦截、修改event

```txt
数据源： kafka消息队列、磁盘上的数据、http/web数据

flume | ----------- agent ----------- 
flume | 
flume | source： kafka实现类、磁盘实现类、http实现类
flume | ~~~~~~~~ selector ~~~~~~~~
flume | ↓
flume | ↓ event
flume | ↓
flume | ======== interceptor ========
flume | channel： memory、file
flume | ↓
flume | ↓ event
flume | ↓
flume | ======== interceptor ========
flume | sink：kafka实现类、磁盘实现类、http实现类
flume | 
flume | --------------------------------- 

输出： kafka消息队列、hdfs、hbase、...
```

数据模型：

::: tabs

@tab 基础数据流

```txt
Source --> Flume Agent --> HD
```

@tab 多Agent串行

```txt
Source --> Flume Agent --AVRO/RPC--> Flume Agent --> HD
```

@tab 多Agent汇聚

```txt
Source --> Flume Agent --> |
                           | --> Flume Agent (Consolidation) --> HD
Source --> Flume Agent --> |
```

:::

## 配置

### 核心组件组装方式

```ini
# list the sources, sinks and channels for agent
<Agent>.sources = <Source1> <Source2>
<Agent>.sinks = <Sink1> <Sink2>
<Agent>.channels = <Channel1> <Channel2>

# set channel for source
<Agent>.sources.<Source1>.channels = <Channel1> <Channel2> ...
# set channel for sink
<Agent>.sinks.<Sink1>.channel = <Channel1>
```

e.g.

```ini
# Name the components on this agent  声明变量名称
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source  配置数据源
a1.sources.r1.type = netcat
a1.sources.r1.bind = localhost
a1.sources.r1.port = 2666

# Describe the sink  配置接收器（存储源）
a1.sinks.k1.type = logger

# Use a channel which buffers events in memory 配置管道参数
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel  组装
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
```

### 常用的核心组件实现

+ Source
  + org.apache.flume.source.kafka.kafkaSource —— Kafka source
  + http
  + exec
  + syslogtcp —— Syslog TCP source
  + syslogudp —— Syslog UDP source
  + thrift —— Thrift source
  + jms —— JMS source
  + avro —— Avro source
  + spooldir —— Spooling directory source
+ Channel
  + memory —— Memory channel
  + jdbc —— JDBC Channel
  + org.apache.flume.channel.kafka.kafkaChannel —— Kafka channel
  + file —— File Channel
+ Sink
  + hdfs —— HDFS sink
  + logger —— Logger sink
  + org.apache.flume.sink.kafka.kafkaSink —— Kafka sink
  + avro —— Avro sink
  + hive —— HIVE sink

## 目录

```bash
.
├── bin/
│   ├── flume-ng
│   ├── flume-ng.cmd
│   └── flume-ng.ps1
├── lib/
├── conf/
│   ├── flume-conf.properties.template
│   ├── flume-env.ps1.template
│   ├── flume-env.sh.template
│   └── log4j.properties
├── tools/
├── docs/
├── LICENSE
└── README.md
```

## 安装

### 本体安装

安装要求：

+ JDK8+
+ 内存空间 —— 用于源、通道、接收器使用
+ 磁盘空间 —— 用于通道、接收器使用
+ 目录权限 —— agent使用的目录读写权限

#### 安装方式一：压缩包安装

```bash
tar -zxvf apache-flume-1.8.0-bin.tar.gz -C /usr/local
cd /usr/local
mv apache-flume-1.8.0-bin flume
```

```bash
$ vim /etc/profile
export FLUME_HOME=/usr/local/flume
export PATH=$FLUME_HOME/bin:$PATH
$ source /etc/profile
$ flume version
Flume 1.8.0
```

```bash
$ cp conf/flume-env.sh.template conf/flume-env.sh
$ vim conf/flume-env.sh
# export JAVA_HOME=/usr/lib/jvm/java=8-oracle
export JAVA_HOME=/usr/local/jdk # 自定义
```

## 拦截器

todo
