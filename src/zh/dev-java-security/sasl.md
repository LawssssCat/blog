---
title: SASL认证机制
---

SASL（Simple Authentication and Security Layer）是一种用来扩充C/S模式验证能力的机制。

SASL是一个胶合（glue）库，通过这个库把应用层与形式多样的认证系统整合在一起。
这有点类似于PAM，但是PAM是认证方式，决定什么人可以访问什么服务，而SASL是认证过程，侧重于信任建立过程，这个过程可以调用PAM来建立信任关系。
