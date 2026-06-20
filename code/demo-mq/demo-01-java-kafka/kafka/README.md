
测试

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
```
