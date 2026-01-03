# README

启动虚拟机

```bash
vagrant up
```

安装minikube

```bash
# vi ~/.bashrc
nc -zv 192.168.10.1 10808
export http_proxy="http://192.168.10.1:10808"
export https_proxy="http://192.168.10.1:10808"
export no_proxy='a.test.com,127.0.0.1,2.2.2.2,192.168.0.0/16,172.0.0.0/8,10.0.0.0/8'

# https://podman.org.cn/docs/installation
# sudo dnf -y install podman
# sudo dnf -y install podman-machine

# https://docs.docker.com/engine/install/fedora/
sudo dnf remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
sudo dnf config-manager addrepo --from-repofile https://download.docker.com/linux/fedora/docker-ce.repo
sudo dnf install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable --now docker
sudo docker run --rm hello-world
# bash <(wget -qO- https://xuanyuan.cloud/docker.sh)

# curl -LO https://github.com/kubernetes/minikube/releases/latest/download/minikube-linux-amd64
curl -LO https://github.com/kubernetes/minikube/releases/download/v1.26.1/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
```

启动k8s环境（通过minikube使用docker驱动）<https://www.cnblogs.com/davyyy/p/12531032.html>

```bash
# minikube start
# minikube start -n 3

# --vm-driver 如果不写会自动检测，可选值 virtualbox, vmwarefusion, hyperv, vmware
# --image-mirror-country 需要使用的镜像镜像的国家/地区代码。留空以使用全球代码。对于中国大陆用户，请将其设置为 cn（将自动使用阿里云服务来支持minikube的环境配置）。
# --image-repository 用来拉取 Kubernetes 集群所需镜像的仓库
# --iso-url 下载 minikube 虚机安装所需的 iso 文件
# --registry-mirror docker registry 的镜像源，集群安装后拉取镜像加速用，可以使用其它加速器地址
# minikube start --vm-driver=virtualbox --image-mirror-country=cn --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers --iso-url=https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/iso/minikube-v1.7.3.iso --registry-mirror=https://reg-mirror.qiniu.com
# minikube start --image-mirror-country=cn --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers
minikube start --image-mirror-country=cn
# ❌  Exiting due to DRV_AS_ROOT: The "docker" driver should not be used with root privileges.
minikube start --force --driver=docker
minikube start -n 3 --force --driver=docker

# vi ~/.bashrc
alias kubectl="minikube kubectl -- " # 命令别名
source <(kubectl completion bash) # 命令补全
```

minikube常用命令

```bash
minikube node list
minikube ssh -n minikube

# 启动ingress插件
# minikube addons enable ingress 启动错误排查
# https://www.cnblogs.com/wangiqngpei557/p/15365861.html
minikube addons enable ingress
```

k8s常用命令

```bash
kubectl get svc
kubectl expose pod learning-k8s-demo-pod --type=NodePort --port=8899 --target-port=80

# 同命名空间 ${service-name}
# 不同命名空间 ${service-name}.${namespace}
# 全限定域名 ${service-name}.${namespace}.svc.cluster.local

kubectl get pod

minikube service  learning-k8s-demo-pod-service
```

网络平面整理

```bash
# vagrant平面
192.168.56.60
192.168.49.1 # 节点平面网关

# 节点平面
--- core
192.168.49.2
172.17.0.1 # 容器平面网关（小网） DOWN
10.244.0.1 # 容器平面网关（大网） UP
--- m02
192.168.49.3
192.168.49.4
10.244.1.1 # 容器平面网关（大网） UP
--- m03
192.168.49.4
10.244.2.1 # 容器平面网关（大网） UP

# 容器平面
# 172.17.0.3
# 172.17.0.5
10.244.2.2

# cluster-ip
10.96.0.1
10.99.100.2
```

helm安装

```bash
sudo yum install -y openssl git
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-4
chmod 700 get_helm.sh
./get_helm.sh

# 命令补全
source <(helm completion bash)
```

helm常用命令

```bash
helm list
helm install # helm install test-chart my-chart/
helm uninstall
helm history
helm create
helm package
```
