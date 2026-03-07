---
title: WSL 介绍
order: 66
---

官网：
<https://learn.microsoft.com/zh-cn/windows/wsl/>

基础知识：
<https://learn.microsoft.com/zh-cn/windows/wsl/#related-videos>

共享路径位置：
`\\wsl$`

<!-- more -->

## 架构版本

参考：
<https://www.youtube.com/watch?v=PaEcQmgEz78>

wsl1.0： （已废弃）
不正真运行linux内核，而是通过windows内核来模拟linux系统，wsl1.0作为其中的翻译层。
因此，会出现兼容性问题，如无法运行docker，并且翻译工作量巨大无法持续。

```bash
linux发行版：kali/ubuntu/suse/...
↑
↓
wsl1.0：翻译层
↑
↓
windows NT 内核
```

wsl2.0：
基于Hypervisor的虚拟化平台。
支持docker、显卡直通（`nvidia-smi`）、WSLG（利用RDP远程桌面协议让WSL中的UI界面在Windows上显示）、远程桌面控制（`apt install kali-win-hex; hex --esm --ip -sound`）。
使用要求开启CPU虚拟化、开启名为“适用于 Linux 的 Windows 子系统”和“虚拟机平台”的Windows功能

```bash
windows用户空间
↑
↓
windows内核
↑
↓
Hypervisor虚拟化平台
↑
↓
linux内核
↑
↓
linux用户空间
```

## 安装子系统

```bash
# 安装适用于 Linux 的 Windows 子系统分发版。
wsl --install
wsl --install --web-download # 更稳定：从 Internet 而不是 Microsoft Store 下载分发版。

# 查看可安装的发行版
wsl --list --online

# 查看已安装的发行版
wsl -l -v
wsl -s Ubuntu # 设置默认发行版

# 导出/导入已安装的发行版
wsl --export Ubuntu ubuntu.tar
wsl --import Ubuntu2 ubuntu.tar
wsl --import Ubuntu2 C:\\my\\wsl\\Ubuntu2 ubuntu.tar
wsl --unregister Ubuntu # 卸载发行版

# 管理发行版安装路径
wsl --manage Ubuntu2 --resize 300MB # 设置大小
wsl --manage Ubuntu2 --move C:\my\wsl\Ubuntu3 # 移动路径
```

## 高级配置

配置文件：

+ `.wslconfig` —— windows配置，对全部子系统生效
+ `wsl.conf` —— 子系统linux内部配置，只在该子系统中生效

```bash title='/etc/wsl.conf'
[boot]
systemd=true # 启动systemd支持
```

```bash title='%USERPROFILE%/.wslconfig'
[wsl2]
networkingMode=mirrored
```

## 发行版制作

todo <https://learn.microsoft.com/zh-cn/windows/wsl/build-custom-distro>

## 其他

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
