---
title: Nginx2Root
---

5个独立入口，均可获取 Nginx Worker 权限：（具体可利用行取决于实际配置和运行环境）

- CVE-2026-123007 —— HTTP/2 流控整数溢出，导致堆越界写
- CVE-2026-123633 —— HTTP/2 请求转发到 upstream，连接回收过程中的 UAF（Use After Free，释放后使用）
- CVE-2026-121810 —— HTTP/3 请求转发到 upstream，QUIC 连接回收过程中的 UAF
- CVE-2026-123634 —— resolver + 请求派生的 proxy_pass + mirror 子请求等特定组合
- CVE-2026-113425 —— Mail Proxy 同时配置 proxy on 与 auth_http

在满足内核条件时可以提权。


## 参考

- 蚁景网安实验室 | NGINX2Root：Nginx曝出5个高危漏洞，攻击链可从远程访问升级到主机Root权限-【网安资讯】 <https://www.bilibili.com/video/BV1mXsb6dEjq>
