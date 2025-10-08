---
title: X11 介绍
order: 10
---

X11（X Window System Version 11）是一种网络透明的窗口系统，主要用于Unix-like操作系统。它采用客户端-服务器模型，允许应用程序（客户端）在远程服务器上运行，而图形界面可以显式在本地计算机上。

X11转发：
通过SSH隧道安全地传输X11图形界面数据的技术，使得远程图形程序可以在本地显式。
通过X11转发，我们可以在没有图形界面的远程Linux操作系统中运行带图形化界面的程序，方便日常的开发维护工作。

X11转发架构：
X Server 是 X11 的服务端，负责将客户端的图形化界面渲染出来；
X Client 是 X11 的客户端，通过 X11 协议将要渲染的图形化界面发送给 X Server，由 X Server 负责渲染出来；
X Client 和 X Server 通过 SSH 通道加密传输。

@startuml
interface "Keyboard"
interface "Mouse"
node "Local（Windows）" as Cl {
  [X Server] as XS
  "Keyboard" <--> XS
  "Mouse"    <--> XS
  portout "SSH Client" as SSHC
  XS <-down-> SSHC : DISPLAY
}
node "Remote（Linux-like）" as Rm {
  port "SSH Server" as SSHS
  [X Client] as XC
  SSHS <-down-> XC : DISPLAY
}
SSHC <--> SSHS
@enduml

## 转发配置

::: tip
很多文章说需要修改sshd服务`/etc/ssh/sshd_config`文件的`X11Forwarding yes`配置。
实际上不需要修改sshd服务端，只需要在ssh客户端连接时加上`-Y`参数即可开启隧道的x11转发功能。
:::

SSH服务端：（Linux-like）

1. 无

SSH客户端：（windows/图形linux-like）

1. 安装x11服务端 `scoop install xming`
1. 启动`xluanch`程序： （这是xming的启动引导，也可以直接运行`xming`命令行，略）
    + 设置Display number 》 勾选“No Access Control”（不安全，应该开启访问控制，并在xming安装目录下的X0.hosts中添加白名单IP）
1. 准备SSH客户端。如：putty/xshell/electerm/...
1. 开启ssh隧道，成功登录。
1. 配置`DISPLAY`环境变量 —— `hostname:display_number.screen_number` e.g. `192.168.10.20:10.0`
    + `hostname` 用于指示 X Server 运行在哪里。例如对于IP为 192.168.10.20 的 Windows X Server 设置值为 `192.168.10.20`
    + `display_number` 用于指示 X Server 实例编号，与服务端口号有关。默认为编号为 `0` 表示 6000 端口。如果为 `10` 表示 6010 端口
    + `screen_number` 用于指示 X Server 将 X Client 的图形化界面渲染到哪个屏幕上。默认为 `0` （一般不需要调整）

todo 开启 xauth —— 一个用于管理 X11 授权的工具。当你使用 SSH 的 X11 转发功能时，SSH 需要 xauth 安全地处理 X11 图形的转发。
