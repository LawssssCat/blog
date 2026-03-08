---
title: Docker 介绍
order: 66
---

## 安装

参考：

+ <https://www.cnblogs.com/Liyuting/p/17022739.html>

Docker从17.03版本之后分为CE（Community Edition: 社区版）和EE（Enterprise Edition: 企业版）。相对于社区版本，企业版本强调安全性，但需付费使用。这里我们使用社区版本即可。

```bash
# 查看docker安装情况
systemctl status docker
```

:::::: tabs

@tab 自动化安装脚本

```bash
# 官方
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
# 国内daocloud
curl -sSL https://get.daocloud.io/docker | sh
```

@tab 手动安装

手动安装Docker分三步：卸载、设置仓库、安装。

第一步，卸载历史版本。这一步是可选的，如果之前安装过旧版本的Docker，可以使用如下命令进行卸载：

```bash
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine \
                  docker-ce
```

第二步，设置仓库。新主机上首次安装Docker Engine-Community之前，需要设置Docker仓库。此后可从仓库安装和更新Docker。

```bash
$ sudo yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
# 阿里云：http:**//mirrors.aliyun.com/docker-ce/linux/centos/**docker-ce.repo
# 清华源：https:**//mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/**docker-ce.repo
```

第三步，安装Docker。执行一下命令，安装最新版本的 Docker Engine-Community 和 containerd。

```bash
# docker-ce为社区免费版本
sudo yum install -y docker-ce docker-ce-cli containerd.io
```

::::::

## 镜像

搜索可用镜像：
<https://docker.aityp.com/>

::: tip

拉取镜像时如果网络不通，可以考虑：1、使用镜像源；2、网络代理；

`vim /etc/docker/daemon.json; systemctl docker restart`

```json
{
  "proxies": {
    "http-proxy": "http://172.17.0.1:7890",
    "https-proxy": "http://172.17.0.1:7890",
    "no-proxy": "*.test.example.com,.example.org,127.0.0.0/8"
  },
  "registry-mirrors": ["https://registry.docker-cn.com"]
}
```

`docker run -it --env HTTP_PROXY="http://172.17.0.1:7890" --env HTTPS_PROXY="http://172.17.0.1:7890" ubuntu:latest env`

`docker build --build-arg HTTP_PROXY="http://172.17.0.1:7890" --build-arg HTTPS_PROXY="http://172.17.0.1:7890"`

参考：

+ <https://linux.do/t/topic/531821>

:::

## 网络

容器间 link 通信

```bash
docker run -id --name=rocky02 --link rocky01 rockylink:9.0
```

不同主机下的容器互联 Overlay网络（逻辑网络） for swarm集群/k8s集群
Overlay网络需要一个注册中心来保存每个节点信息（如：主机名、IP地址等）。现在比较流行的有Consul、Nacos、ZooKeeper等

```bash
# 启动consul服务
$ docker run -d -p 8500:8500 -h consul --name consul --restart always progrium/consul -server -bootstrap
# 访问web界面
$ curl http://192.18.0.110:8500

# 节点注册
$ vim /usr/lib/systemd/system/docker.service
# ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock -H tcp://0.0.0.0:2376 --cluster-store=consul://192.168.0.110:8500 --cluster-advertise=192.168.0.110:2376
$ systemctl daemon-reload
$ systemctl restart docker

# 创建overlay网络
$ docker network create -d overlay myovernet
$ docker network create -d overlay myovernet --subnet 172.16.1.0/24 --gateway 172.16.1.254 # 可选：指定网络、网关

# 验证荣期间跨节点通信
$ docker run -d --name=nginx --net=myovernet nginx:1.20.2
$ docker run -d --name=mysql --net=myovernet -e MYSQL_ROOT_PASS=123321 nginx:1.20.2
```

## Docker-Compose 安装

参考：

+ <https://www.cnblogs.com/yihuyuan/p/18773255>

```bash
# 从 GitHub 下载最新版 Docker Compose 的二进制文件：
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
# 使用 FastGit 镜像
# sudo curl -L "https://hub.fgit.cf/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
# 或使用 ghproxy 代理
# sudo curl -L "https://ghproxy.com/https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 赋予执行权限
sudo chmod +x /usr/local/bin/docker-compose
# （可选）创建符号链接
# 将二进制文件链接到 /usr/bin 目录，方便全局调用：
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
# 验证安装
docker-compose --version
```
