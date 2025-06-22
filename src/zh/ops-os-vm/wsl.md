---
title: WSL 介绍
order: 66
---

开启防火墙 （管理员）

```bash
# netsh interface portproxy set v4tov4 listenaddress=0.0.0.0 listenport=8888 connectaddress=$(wsl hostname -I) connectport=8888
$ netsh interface portproxy add v4tov4 listenaddress=localhost listenport=8848 connectaddress=localhost connectport=8848
$ netsh interface portproxy show all

侦听 ipv4:                 连接到 ipv4:

地址            端口        地址            端口
--------------- ----------  --------------- ----------
127.0.0.1       8777        172.17.80.1     8777
localhost       8848        localhost       8848

$ netsh interface portproxy delete v4tov4 listenport=8777 listenaddress=127.0.0.1
```
