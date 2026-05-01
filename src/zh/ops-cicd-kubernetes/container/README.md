---
title: 容器（container）
---

## 容器管理

- [containerd](./containerd.md)
- [docker](./docker.md)
- [podman](./podman.md)

基础能力：

- 命名空间（Namespaces） —— 控制你能看到什么。隔离进程、网络等。
- 控制组（Cgroups） —— 控制你能使用多少。分配CPU和内存资源。

## scratch （空镜像） （首个镜像）

scratch是Docker提供的一个特殊的空镜像，不包含任何文件系统、shell、包管理器或其他任何内容。
scratch大小为0字节，是一切镜像的起点。

> 由于scratch是完全空白的镜像，因此不能额执行任何命令，只能使用COPY/ADD/ENV/WORKDIR/CMD等不需要执行系统命令的指令。

```dockerfile title="custom-ubuntu-scratch"
# 第一阶段：下载和解压阶段
FROM alpine:latest AS builder
# 安装必要工具
RUN apk add --no-cache wget
# 下载Ubuntu base rootfs并解压
RUN wget -O /ubuntu-base.tar.gz \
    https://cdimage.ubuntu.com/ubuntu-base/releases/20.04/release/ubuntu-base-20.04.5-base-amd64.tar.gz
RUN mkdir /rootfs && \
    tar -xzf /ubuntu-base.tar.gz -C /rootfs

# 第二阶段：构建最终镜像
FROM scratch
# 从构建阶段复制已解压的文件系统
COPY --from=builder /rootfs /
# 设置环境变量
ENV PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
# 设置工作目录
WORKDIR /root
# 默认命令
CMD ["/bin/bash"]
```

```bash
cd scratch_sample
docker build -t custom-ubuntu-scratch .
# 测试
docker run -it --rm custom-ubuntu-scratch
cat /etc/os-release
ls -la /
```

## nsenter （调试容器工具）

解决问题：
如调试容器网络，容器内缺少工具、权限。

```bash
CONTAINER_PID=$(docker inspect --format '{{.State.Pid}}')
sudo nsenter -t $CONTAINER_PID -n -p -m /bin/bash

# 常用网络调试命令
ip addr show    # 查看容器内虚拟网卡和IP地址
ip route show   # 查看容器内路由表、默认网关
iptables -L -n
netstat -tlupn
tcpdump -i etho0 # 抓包
```

参考：

- <https://www.bilibili.com/video/BV1MfQkBeEXR/>
