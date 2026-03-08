---
title: SUSE 介绍
order: 1
---

openSUSE版本：

+ openSUSE-Tumbleweed （tumbleweed 风滚草） —— 滚动更新。紧密跟随openSUSE开发时间表的滚动发布版本。
+ openSUSE-Leap （leap 跳跃） —— 稳定更新。按照固定的时间表发布一个进过稳定测试的版本。

openSUSE镜像源：

+ <https://mirrors.tuna.tsinghua.edu.cn/help/opensuse/>

```bash
# leap
sudo zypper mr -da # 禁用官方软件源
sudo zypper ar -p 98 -cfg 'https://mirrors.tuna.tsinghua.edu.cn/opensuse/distribution/leap/$releasever/repo/oss/' mirror-oss
sudo zypper ar -p 98 -cfg 'https://mirrors.tuna.tsinghua.edu.cn/opensuse/distribution/leap/$releasever/repo/non-oss/' mirror-non-oss
sudo zypper ar -p 98 -cfg 'https://mirrors.tuna.tsinghua.edu.cn/opensuse/update/leap/$releasever/oss/' mirror-update
sudo zypper ar -p 98 -cfg 'https://mirrors.tuna.tsinghua.edu.cn/opensuse/update/leap/$releasever/non-oss/' mirror-update-non-oss
zypper lr # 检查软件源状态，并使用 zypper mr -d 禁用多余的软件源。
sudo zypper ref # 刷新软件源
```

zypper用法：

```bash
zypper [全局选项]  [命令选项] [参数]

zypper lr # 仓库
zypper addrepo -c SLES12-SP3-Updates
zypper removerepo nVidia-Driver-SLE12-SP3
zypper ref # 刷新软件源
zypper clean # 清除 zypper 本地缓存

zypper search telnet
zypper install telnet
zypper remove telnet

zypper info rsync # 查看
zypper ve rsync # 检查包完整性

zypper se -i # 列出已安装的包
zypper lu # 列出可用包更新
zypper update rsync # 更新

zypper lp # 列出补丁
zypper patch SUSE-SLE-Module-Containers-12-2018-273 # 安装补丁
```
