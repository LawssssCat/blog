---
title: Linux 笔记
order: 1
---

+ Centos
+ Debian
+ [ubuntu](./ubuntu/introduction.md)
+ suse
+ Fedora
+ RHEL
+ arch
+ Linux Mint
+ Kail
+ tails

+ [openwrt](./openwrt/README.md)

新闻：

+ Linux-Console.net <https://cn.linux-console.net/>

文件系统：

+ d `mkdir a`
+ p `mkfifo b` 管道
+ s `nc -lU c`
+ c `ll /dev/video0` 字设备（Character Devices），如摄像头、鼠标、键盘、... 参考 <https://doc.embedfire.com/linux/h618/driver/zh/latest/linux_driver/base/character_device/character_device.html>
+ b `ll /dev/dm-0` 块设备（Block），如硬盘、USB、...等无法直接字节写入写出，需要通过文件系统操作的设备。可以通过`lsblk`查看块设备情况。
