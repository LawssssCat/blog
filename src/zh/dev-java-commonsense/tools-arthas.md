---
title: Arthas（阿尔萨斯）笔记
order: 99
---

Arthas是Alibaba开源的Java程序线上诊断工具。
Arthas可以通过全局视角实时查看应用load、内存、gc、线程的状态信息，可以在不修改应用代码的情况下，对业务问题进行诊断，包括查看方法调用的出入参、异常，监测方法执行耗时，类加载信息等。

官网：
<https://arthas.aliyun.com/doc/web-console.html>

参考：

+ <https://www.xiehai.zone/arthas/>

<!-- more -->

## 安装 & 卸载

参考：
<https://arthas.aliyun.com/en/doc/download.html>

```bash
# 在线安装
curl -O https://alibaba.github.io/arthas/arthas-boot.jar
java -jar arthas-boot.jar
# 离线安装
todo https://maven.aliyun.com/repository/public/com/taobao/arthas/arthas-packaging/3.1.7/arthas-packaging-3.1.7-bin.zip
unzip -d arthas arthas-packaging-3.1.7-bin.zip

# 卸载
rm -rf ~/.arthas/
rm -rf ~/logs/arthas/
```

```bash
# demo
curl -O https://alibaba.github.io/arthas/arthas-demo.jar
java -jar arthas-demo.jar
# attach
java -jar arthas-boot.jar
java -jar arthas-boot.jar --telnet-port 9998 # 端口占用时命令
java -jar arthas-boot.jar --telnet-port 9998 --http-port 9999 --target-ip 127.0.0.1
```

## 参数

| 命令选项 | 描述 |
| ------------- | ------------------ |
| 安装下载 | |
| \-\-disabled-commands \<commands> | 禁用一些命令逗号分隔 |
| \-\-repo-mirror \<repo> | maven仓库，仓库可为`maven`、`aliyun`或者http地址 |
| \-\-user-version \<version> | 使用特定的Arthas版本，版本号可从versions中选择 |
| \-\-user-http | 强制使用http方式下载，默认使用https方式下载 |
| \-\-versions | 列出本地和远程可用的Arthas版本 |
| \-\-arthas-home \<path> | Arthas目录 |
| 运行监听 | |
| \-\-attach-only | 仅attach目标进程，不做连接，attach成功后就结束 |
| \-\-target-ip | telnet监听ip，默认为`127.0.0.1` |
| \-\-telnet-port \<number> | Arthas监听端口，默认`3658`，`0`则为随机端口 |
| \-\-http-port \<number> | Arthas http监听端口，默认`8563`，`0`则为随机端口 |
| \-\-tunnel-server \<url> | Tunnel服务url地址 |
| \-\-agent-id \<id> | 注册到Tunnel服务的代理id |
| \-\-session-timeout \<number> | 会话超时秒数，默认`1800`秒 |
| \-\-stat-url \<url> | 统计url地址，[参考](https://github.com/alibaba/arthas/blob/master/tunnel-server/src/main/java/com/alibaba/arthas/tunnel/server/app/web/StatController.java)实现 |
| \-\-select \<value> | 通过类名称或jar名称选择进程 |
| \-\-username \<name> | 授权用户名，默认`arthas` |
| \-\-password \<password> | 授权密码 |
| -f, \-\-batch-file \<file> | 指定脚本执行，脚本建议以`as`结尾 |
| -c, \-\-command \<commands> | attach后直接执行命令，多个命令使用`;`分隔 |
| \<pid> | 根据进程号选择进程 |
| 显示交互 | |
| \-\-app-name \<name> | 应用名称 |
| \-\-height \<number> | Arthas客户端终端高度 |
| \-\-width \<number> | Arthas客户端终端宽度 |
| -v, \-\-verbose | 打印debug信息 |
| -h, \-\-help | 帮助 |

## 配置

参考：
<https://arthas.aliyun.com/en/doc/arthas-properties.html>

名称： arthas.properties

位置：

+ `~/.arthas/lib/3.x.x/arthas/`

todo

## 命令

参考：
<https://arthas.aliyun.com/en/doc/commands.html>

### dashboard 仪表盘

略
