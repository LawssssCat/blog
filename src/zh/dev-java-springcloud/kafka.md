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

- 实时数据处理
- 流处理
- 消息队列
- 事件驱动架构

## JMS，Java Message Service

消息结构

- 消息头
- 消息属性
- 消息主体内容

消息交互模型

- JMS Producer
- JMS Provider （alias MQ 软件）
- JMS Consumer

消息交互模型实现

- P2P（点对点）模型 —— 一个消息只能被一个消费者消费，然后供应商给生产者反馈（feedback）
- PS（消费订阅）模型 —— 消费者通过订阅主题（Topic）消费信息，即一个消息可被多个消费者消费，然后供应商给生产者反馈（feedback）
  - 该模式的供应商：RabbitMQ/ActiveMQ/RocketMQ/Kafka/..

## Kafka 概念

名词

- record —— 消息
- Broker —— 消息队列
- Topic —— 订阅标识
- Producer —— 消息生产者
- Consumer —— 消息消费者
- .log —— 消息持久化文件
- offset —— 消息位置标记，e.g. 0,1,2,... （从零开始）
