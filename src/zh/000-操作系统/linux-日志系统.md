---
title: Linux日志系统
---

journalctl —— 检索 systemd journald 产生的日志，比如内核、驱动、服务等

存储：
/var/log/journal

```bash
man journalctl

# 查看内核日志
journalctl -k

# 查看指定服务日志
journalctl -u NetworkManager

# 查看错误日志
journalctl -p err

# 查看本次启动的日志
journalctl -b
journalctl -b 1 # 查看上次启动

# 按时间筛选
journalctl --since "2026-11-26 22:00:00"
journalctl --since "1 hour ago"
journalctl --since "3:00" --until "4:00"

# 查看空间占用
journalctl --disk-usage
# 删除超过30天的日志
journalctl --vacuum-time=30d
# 只保留最近10个文件
journalctl --vacuum-files=10
# 可以将已产生的日志rotate（轮换）到磁盘，否则当前系统读写中的日志不会被纳入清理
journalctl --rotate
```
