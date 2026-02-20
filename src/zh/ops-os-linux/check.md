---
title: Linux 系统安全排查
order: 6
---

## 检查开放端口

```bash
# 查看开放端口
$ ss -tlupn # 旧系统用 netstat
Netid      State       Recv-Q      Send-Q                  Local Address:Port              Peer Address:Port      Process
udp        UNCONN      0           0                       127.0.0.53%lo:53                     0.0.0.0:*
udp        UNCONN      0           0                    10.0.2.15%enp0s3:68                     0.0.0.0:*
tcp        LISTEN      0           4096                    127.0.0.53%lo:53                     0.0.0.0:*
tcp        LISTEN      0           128                           0.0.0.0:22                     0.0.0.0:*
tcp        LISTEN      0           50                                  *:2181                         *:*          users:(("java",pid=15705,fd=19))      
tcp        LISTEN      0           128                              [::]:22                        [::]:*
tcp        LISTEN      0           50                                  *:37889                        *:*          users:(("java",pid=15705,fd=15))      
tcp        LISTEN      0           50                 [::ffff:127.0.2.1]:3888                         *:*          users:(("java",pid=15705,fd=20)

# 查看开放这些端口的进程
$ sudo lsof -i:53
COMMAND     PID            USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
systemd-r 11201 systemd-resolve   13u  IPv4  64700      0t0  UDP localhost:domain 
systemd-r 11201 systemd-resolve   14u  IPv4  64701      0t0  TCP localhost:domain (LISTEN)

# 结论：
# 22 ssh服务
# 53 dns服务
# 86 dhcp服务
# 2181,37889,3888 zookeeper服务
```

## ACL（Access ...）

`getfacl` 查看后面有 `+` 标识

`setfacl`

`lsattr`

`chattr` —— e.g. `chattr +i xxfile` 使xxfile不可删除
