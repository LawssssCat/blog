---
title: Kafka 介绍
date: 2024-11-12
tag:
  - kafka
order: 66
---

kafka 是 Scala 和 Java 开发的分布式消息发布和订阅系统的 “消息中间件”。

特点：

- 高吞吐（high throughput）
- 高可靠（permanent storage）
- 高可用（high availability）
- 伸缩（scalable）

应用场景：

- 大数据场景分布式数据交换（实现功能：实时数据处理/流处理/事件驱动架构）
  组件：
  - Flume 生产数据
  - kafka 消息中间件
  - Spark/Flink（分布式计算引擎） 消费数据

## JMS，Java Message Service 模型

>
> 说明：线程间通信、进程间通信、分布式数据交换
>
> - 线程间通信：JVM/Thread/堆/栈
> - 线程间通信：Socket/文件
> - 分布式数据交换：消息中间件（消息缓冲区/JMS，Java Message Service）
>

消息结构

- 消息头
- 消息属性
- 消息主体内容

消息交互模型

- JMS Producer
- JMS Provider （alias MQ 软件）
- JMS Consumer

消息交互模型的实现分类

- **P2P（点对点）模型** —— 一个消息生产者、一个消息、一个消费者消费，消息消费后生产者能收到反馈（feedback）
- **PS（发布订阅）模型** —— 消费者通过订阅主题（Topic）消费信息，即一个消息可被多个消费者消费，然后供应商给生产者反馈（feedback）
  - 该模式的供应商：RabbitMQ/ActiveMQ/RocketMQ/Kafka/..

### Kafka 消息模型

通用模型 | Kafka模型
--- | ---
![image.png](https://s2.loli.net/2025/06/21/L8D4zEVh3Tq7H1G.png) | ![image.png](https://s2.loli.net/2025/06/21/QW5FhqziukNedTJ.png)

## Kafka 概念

名词

- record —— 消息
- Broker —— 消息队列
- Topic —— 订阅标识
- Producer —— 消息生产者
- Consumer —— 消息消费者
- .log —— 消息持久化文件
- offset —— 消息位置标记，e.g. 0,1,2,... （从零开始）
