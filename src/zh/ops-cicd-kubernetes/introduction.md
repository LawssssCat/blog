---
title: Kubernetes 介绍
date: 2024-04-04
tag:
  - kubernetes
order: 1
---

介绍 Kubernetes 的产生环境、基本功能、架构、组件。

<!-- more -->

文档：

- https://kubernetes.io/zh-cn/docs/home/

## Kubernetes 产生背景：微服务治理

为了解决传统单体架构存在的诸多问题（如：单点故障、无法扩容、需全线停机以更新局部功能），厂商倾向于把传统单体架构服务按功能/按业务拆分成诸多微服务逐个治理，从而缓解单体架构带来的问题。

但是拆分成多个微服务后，要管理的服务增多，对服务的管理成本增加。
主要有以下问题：

1. **微服务间通信问题** —— 微服务间通讯需考虑 I/O、线程调度模型、序列化方式、多语言支持、服务治理等问题。基于 REST API/RPC/MQ 等协议，业界有 Dubbo/Dubbox、Motan、Thrift、Grpc 等解决方案。

   | RPC 对比     | Dubbo/Dubbox                                                     | Motan                                                            | Thrift                                                           | Grpc                                                             |
   | ------------ | ---------------------------------------------------------------- | ---------------------------------------------------------------- | ---------------------------------------------------------------- | ---------------------------------------------------------------- |
   | 开发团队     | Dubbo - 阿里 <br> Dubbox - 当当                                  | 新浪微博                                                         | apache                                                           | google                                                           |
   | 开发语言     | Java                                                             | Java                                                             | 跨语言                                                           | 跨语言                                                           |
   | 服务治理     | ✅                                                               | ✅                                                               | ❌                                                               | ❌                                                               |
   | 多种序列化   | ✅                                                               | ✅                                                               | 只支持 thrift                                                    | 只支持 protobuf                                                  |
   | 多种注册中心 | ✅                                                               | ✅                                                               | ❌                                                               | ❌                                                               |
   | 管理中心     | ✅                                                               | ✅                                                               | ❌                                                               | ❌                                                               |
   | 跨语言通信   | ❌                                                               | ❌                                                               | ✅                                                               | ✅                                                               |
   | ~~整理性能~~ | 3                                                                | 4                                                                | 5                                                                | 3                                                                |
   | 通信架构     | ![image.png](https://s2.loli.net/2024/03/06/yr5gho7T3i18bks.png) | ![image.png](https://s2.loli.net/2024/03/06/YANgHPkIZVjpoMz.png) | ![image.png](https://s2.loli.net/2024/03/06/25ZunBshdkGP7V9.png) | ![image.png](https://s2.loli.net/2024/03/06/8rgYWh3QyUMpfoc.png) |

1. **微服务发现问题** —— 有如下解决方案：

   - 传统服务配置 —— 需要运维人员手动配置

     ![image.png](https://s2.loli.net/2024/03/06/8GRUtZn7J93apfC.png)

   - 客户端发现 —— 访问多个 ip，ip 后的服务器直接提供服务

     ![image.png](https://s2.loli.net/2024/03/06/5mMdiRu2sWTGweO.png)

   - 服务端发现 —— 访问一个 ip，ip 后的服务器通过正向代理方式提供服务

     ![image.png](https://s2.loli.net/2024/03/06/w9WSpPJIlG6AmZN.png)

1. **服务部署/更新/扩容问题** —— 需要准备代码、准备制品、准备服务器、修改配置，分配端口、运维部署应用（手动/脚本/自动化）、配置反向代理、...诸多繁琐的步骤

为此，提供服务编排能力的产品应运而生：

Mesos、Docker Swarm、Kubernetes
