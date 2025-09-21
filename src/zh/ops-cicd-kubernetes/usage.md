---
title: Kubernetes 基本使用
date: 2024-04-04
tag:
  - kubernetes
order: 2
---

Kubernetes 采用 Pod 和 Label 服务消除编排物理或虚拟计算、网络和存储等基础设施的负担。

<!-- more -->

Kubernetes 的控制面三大组件

- kube-apiserver —— 提供所有内部和外部的 API 请求操作的唯一入口。同时也负责整个集群的认证、授权、访问控制、服务发现等等能力。
- kube-controller-manager —— 负责维护整个 Kubernetes 集群的状态，比如多副本创建、滚动更新等。
- kube-scheduler —— 监听未调度的 Pod，按照预定的调度策略绑定到满足条件的节点上。

