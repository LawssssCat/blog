---
title: Kubernetes 系列
tag:
  - SRC
---

介绍 Kubernetes 的[生态](https://www.cncf.io)、基本功能、架构、组件。

<!-- more -->

> 文档：
>
> - 官方文档 —— <https://kubernetes.io/zh-cn/docs/home/>

<Catalog />

## 前提

### 产生背景：微服务治理

为了解决传统单体架构存在的诸多问题（如：单点故障、无法扩容、需全线停机以更新局部功能），厂商倾向于把传统单体架构服务按功能/按业务拆分成诸多微服务逐个治理，从而缓解单体架构带来的问题。

但是拆分成多个微服务后，要管理的服务增多，对服务的管理成本增加。
主要有以下问题：

1. **微服务间通信问题** —— 微服务间通讯需考虑 I/O、线程调度模型、序列化方式、多语言支持、服务治理等问题。基于 REST API/RPC/MQ 等协议，业界有 Dubbo/Dubbox、Motan、Thrift、Grpc 等解决方案。

   | RPC 对比 | Dubbo/Dubbox | Motan | Thrift | Grpc |
   | -------- | ----------- | ------- | ----- | ------ |
   | 开发团队 | Dubbo - 阿里 <br> Dubbox - 当当 | 新浪微博 | apache | google |
   | 开发语言 | Java      | Java    | 跨语言    | 跨语言   |
   | 服务治理 | ✅        | ✅     | ❌      | ❌    |
   | 多种序列化 | ✅      | ✅     | 只支持 thrift | 只支持 protobuf |
   | 多种注册中心 | ✅    | ✅     | ❌      | ❌    |
   | 管理中心   | ✅     | ✅      | ❌      | ❌     |
   | 跨语言通信 | ❌      | ❌     | ✅      | ✅      |
   | 通信架构 | ![image.png](https://s2.loli.net/2024/03/06/yr5gho7T3i18bks.png) | ![image.png](https://s2.loli.net/2024/03/06/YANgHPkIZVjpoMz.png) | ![image.png](https://s2.loli.net/2024/03/06/25ZunBshdkGP7V9.png) | ![image.png](https://s2.loli.net/2024/03/06/8rgYWh3QyUMpfoc.png) |

1. **微服务发现问题** —— 有如下解决方案：

   - 传统服务配置 —— 需要运维人员手动配置

     ![image.png](https://s2.loli.net/2024/03/06/8GRUtZn7J93apfC.png)

   - 客户端发现 —— 访问多个 ip，ip 后的服务器直接提供服务

     ![image.png](https://s2.loli.net/2024/03/06/5mMdiRu2sWTGweO.png)

   - 服务端发现 —— 访问一个 ip，ip 后的服务器通过正向代理方式提供服务

     ![image.png](https://s2.loli.net/2024/03/06/w9WSpPJIlG6AmZN.png)

1. **服务部署/更新/扩容问题** —— 需要准备代码、准备制品、准备服务器、修改配置，分配端口、运维部署应用（手动/脚本/自动化）、配置反向代理、...诸多繁琐的步骤

为此，提供服务编排能力的产品应运而生：

- **Mesos** —— Mesos是一个分布式调度系统内核，早于Docker产生。Mesos作为资源管理器，从DC/OS（数据中心操作系统）的角度提供资源视图。Mesos工作模式为主从结构，主节点分配任务，从节点上的Executor负责任务的执行，通过Zookeeper给主节点提供服务注册、服务发现能力，通过Framework Marathon提供容器调度能力。
- **Docker Swarm** —— Docker Swarm是一个由Docker团队开发的调度框架。Swarm由多个代理（Agent）组成，把这些代理称为节点（Node）。这些节点在启动Docker Daemon时会打开端口、API提供Docker Swarm远程调用。
- **Kubernetes** —— Kubernetes借鉴了Google的Borg框架优缺点而形成。提出很多实用的新概念，目前（2025年12月30日）k8s是业界绝对的标准。

### 容器层

- [docker](./docker.md)
- [podman](./podman.md)
- containerd

## 实验环境

K8S官方有推荐几种快速搭建实验环境的工具： <https://kubernetes.io/docs/tasks/tools/>

- [minikube](#id-laboratory-minikube)
- [kind](#kind-idid-laboratory-kind)
- [k3d](#k3d-idid-laboratory-k3d)
- k3s

### minikube {id=id-laboratory-minikube}

[link_minikube](./minikube.md)

安装步骤：
<https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download>

### Kind {id=id-laboratory-kind}

https://www.lixueduan.com/posts/kubernetes/15-kind-kubernetes-in-docker/

### k3d {id=id-laboratory-k3d}

https://coding.gs/2024/04/03/k3d/getting-started-with-k3d/

## 架构

分层架构：

- 生态系统
- 接口层
- 管理层
- 应用层
- 核心层

相关组件

- 控制面板组件
  - etcd
- kube-apiserver —— 提供所有内部和外部的 API 请求操作的唯一入口。同时也负责整个集群的认证、授权、访问控制、服务发现等等能力。
- kube-controller-manager —— 负责维护整个 Kubernetes 集群的状态，比如多副本创建、滚动更新等。
  - cloud-controller-manager
- kube-scheduler —— 监听未调度的 Pod，按照预定的调度策略绑定到满足条件的节点上。
- 节点组件
  - kubelet
  - kube-proxy
  - container runtime
- 附加组件
  - kube-dns
  - Ingress Controller
  - Heapster
  - Dashboard
  - Federation
  - Fluentd-elasticsearch

## 工具

### Helm

helm是kubernetes的包管理工具，可以将应用程序打包成一个可复用的单元，方便在集群中部署和管理。
helm的核心是chart，chart是应用程序的打包文件，包含应用程序的配置、部署文件和依赖关系。

[link_helm](./helm.md)
