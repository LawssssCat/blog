---
title: Linux初始化系统（init system）
---

首个在用户空间启动的程序。

流程：

1. power on
2. kernel loads —— linus
3. first userspace program (INIT) —— PID 1 【初始化系统】
  + brings up everything else

作用：

- manage system services —— 如 ssh、nginx、...
  - starting
  - stopping
  - reloading
  - monitoring
  - starting at boot （开机启动）
- daemons

实现：

- SystemD （/usr/lib/systemd/systemd） —— on most major ditrsos（发行版操作系统，distributions operation system）

## SystemD

```bash
sudo systemctl list-units --type=service --state=running

# 可以看到如：
# NetworkManager
# SSH
# Tailscale —— 一款基于 WireGuard 协议的零配置虚拟专用网络（Mesh VPN）工具，用来将分布在不同地方的设备安全地连成一个虚拟的“局域网”。
# Docker
```

## 参考

- 超通俗SystemD科普 | INIT / Systemctl / Unit 文件
  - 英文 | Zeroes&Ones | https://www.youtube.com/watch?v=uQnw-pijI6w
  - 中文 | 赛博门外憨 | Linux必备！超通俗SystemD科普 | INIT / Systemctl / Unit 文件 | https://www.bilibili.com/video/BV13Kah6pEE8/
