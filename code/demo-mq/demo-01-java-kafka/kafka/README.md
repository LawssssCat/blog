
kafka安装：
<https://blog.csdn.net/KeyandL/article/details/148518493>
~~<https://www.cnblogs.com/ycfenxi/p/19200689>~~

```text
bin/windows/ - Windows批处理脚本
config/ - 配置文件
libs/ - 依赖库
logs/ - 日志文件（启动后生成）

# 修改config/server.properties
process.roles=broker,controller                  # 表示此节点，既是broker又可以当controller
node.id=1                                        # 节点id，不重名即可
controller.quorum.voters=1@localhost:9093        # controller竞争者，也就是controller将从它们之中诞生(这里的kafka1是刚刚设置的本机的域名解析，或者直接写localhost也行)
listeners=PLAINTEXT://:9092,CONTROLLER://:9093   # 监听地址(也就是客户端连接时访问的地址)
advertised.listeners=PLAINTEXT://localhost:9092
controller.listener.names=CONTROLLER
log.dirs=./kafka-logs                            # kafka数据存放地址
```

## 测试

```shell
# 创建topic
$ product/bin/kafka-topics.sh --create --topic kafka-test --partitions 1 --replication-factor 1 --bootstrap-server 127.0.0.1:9092
Created topic kafka-test.

# 生产消息
$ product/bin/kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic kafka-test
> hello
> hello
Ctrl + C

# 消费消息
$ product/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --from-beginning --topic kafka-test
The consumer rebalance protocol (KIP-848) is production-ready! Set group.protocol=consumer to try it out. See https://kafka.apache.org/documentation/#consumer_rebalance_protocol
hello
hello
Ctrl + C
Processed a total of 2 messages

# 查看头信息
$ product/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --from-beginning --topic Topic_2_Request \
  --from-beginning \
  --property print.headers=true \
  --property print.timestamp=true \
  --property print.key=true
$ product/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --from-beginning --topic Topic_2_Response \
  --from-beginning \
  --property print.headers=true \
  --property print.timestamp=true \
  --property print.key=true
```

## windows访问wsl的kafka服务

+ 开启localhost转发（必须）
+ 开启镜像网络

```shell
# .wslconfig
[wsl2]
localhostForwarding=true          # 是否启用 localhost 转发
 
[experimental]
networkingMode=mirrored           # 开启镜像网络
```
