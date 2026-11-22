---
title: 安全事件：Java4Shell
---

漏洞：
- CVE-2026-114938 Pre-Auth RCE
  评分
  - CVSS 4.0 = 9.2
  - Oracle CPU = 8.1

条件：
- OpenJDK 17.0.6 及以上所有更新版本（JDK 17 / 21 / 25 / 27），以及下游发行版（Oracle JDK / Eclipse Temurin / Amazon Corretto 等）
- 支持 AVX-512 VBMI 指令集的 x86-64 处理器，如 Intel Ice Lake 及更新代际、AMD Zen 4 及更新平台

攻击链路：
- 畸形 Base64 网络全球
- 身份认证拦截器 Bypassed （未授权穿越）
- Hotspot JVM core 汇编内存 堆缓冲区溢出
  - `java.util.Base64.Decoder`
  - 触发基于针对 x86-64 架构的原生优化： AVX-512 VBMI 指令集的 “向量寄存器硬件加速机制” 高速解码 Base64
  - 🐞安全团队 Calif 发现：解码器处理末尾带有畸形填充符号的 Base64 字符串时，尾端分片输出长度计算会出现偏差。
    导致向量掩码存储指令突破目标数组边界，引发 CWE-122 堆缓冲区溢出，向相邻内存覆写约 62 ~ 63 字节数据。
    —— 修复： `[BUG] Tail Output Length Calculation Mismatch`
      - 常规测试会破坏对象头部元数据导致GC崩溃
      - 但是配合特定堆内存排布，可以挟持控制流，升级为 “Pre-Auth RCE（预认证远程代码执行）”

影响：
- 大量Base64解码场景会先解码再认证：
  - HTTP Basic 凭据
  - JWT 令牌
  - SAML 报文
- Pre-Auth RCE
  - Keycloak （开源身份认证与访问管理）
  - PingFederate （企业级联邦身份认证服务器）
  - Atlassian Jira （企业级研发项目与缺陷追踪平台）
  - Confluence （企业核心知识库与文档协作系统）
  - Jenkins （CI/CD持续集成与自动化构建中枢）
  - JFrog Artifactory （二进制制品库与供应链分发核心）
- Post-Auth RCE
  - Apache Kafka （分布式事件流与中间件消息集群）
  - Apache Solr （全文检索与索引分析平台）
  - Elasticsearch （分布式日志检索与可观测性引擎）

## 排查方式

todo

## 规避方式

升级运行环境

1. 升级 JDK 版本

修改启动参数

1. 禁用硬件加速的 Base64 内部函数： `java -XX:-UseBASE64Intrinsics -jar xxx.jar`
1. 限制JVM使用AVX2指令集： `java -XX:UseAVX=2 -jar xxx.jar`

## 参考

- 蚁景网安实验室 | Java4Shell：OpenJDK曝高危远程代码执行漏洞CVE-2026-114938-【网安资讯】 <https://www.bilibili.com/video/BV1R3sU6xEg1>
