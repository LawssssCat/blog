---
title: helm 介绍
---

helm是kubernetes的包管理工具，可以将应用程序打包成一个可复用的单元，方便在集群中部署和管理。
helm的核心是chart，chart是应用程序的打包文件，包含应用程序的配置、部署文件和依赖关系。

特点：

+ **标准化与可重复性**：任何环境（开发、测试、生产）的部署都可以通过同一套Chart和不同的配置文件来完成，保证环境的一致性和部署的可重复性。一个`helm upgrade`命令取代数十个`kubectl apply`/`kubectl exec`命令
+ **版本控制（支持安装、升级、回滚）**：Helm Chart本身可以像代码一样被版本化，并存储在Chart仓库（如[Artifact Hub](https://artifacthub.io/)或者私有仓库）中。CI/CD流水线可以准确地拉去指定版本的Chart进行部署或回滚
+ **环境隔离与配置管理**：Helm允许为不同的环境使用不同的values.yaml文件。例如可以通过`-f values-production.yaml`参数指定生产环境配置文件，该文件包含了生产环境的数据库密码、资源限制（CPU/Memory）和副本数等。

## 示例：使用Helm构建MySQL主从集群

目的：说明helm的定位

需要的设置：

+ **创建Secret**：1、用于存储root密码；2、存储复制专用用户replicator的密码
+ **创建ConfigMap**：1、用于存储自定义的my.cnf配置
+ **创建持久化存储（PVC）**：1、为1个主节点和2个从节点分别定义并创建3个PersistentVolumeClaim，以确保数据持久化
+ **创建Headless Service**：1、为了让StatefulsSet中的Pod拥有稳定且唯一的网络标识
+ **创建主节点StatefulSet**：1、编写一个复杂的StatefulSet文件来定义节点。需要正确地挂载PVC、将Secret中的密码注入为环境变量、配置容器端口等
+ **创建从节点StatefulSet**：1、编写一个复杂的StatefulSet文件来定义节点。需要确保从节点能通过正确的网络地址找到主节点
+ **创建Service**：1、指向主节点用于写操作；2、指向所有节点用于读操作
+ **手动执行主从配置**
  1. 等待Pod启动
  1. 使用`kubectl exec`进入主节点Pod
  1. 登录MySQL，手动执行SQL命令`CREATE USER 'replicator'@'%' IDENTIFIED BY '...';`和`GRANT REPLICATION SLAVE ON *.* TO 'replicator'@'%';`
  1. 使用`kubectl exec`进入每个从节点Pod
  1. 登录MySQL，手动执行`CHANGE MASTER TO ....`命令，将主库地址、用户名、密码等信息填入
  1. 登录MySQL，执行`START SLAVE;`

通过helm可以将上述复杂的手动设置简化为几行命令：

1. 添加Bitnami的Chart仓库

  ```bash
  helm repo add bitnami https://charts.bitnami.com/bitnami
  helm repo update
  ```

1. 一键部署MySQL主从集群

  ```bash
  helm install my-mysql-cluster bitnami/mysql \
    --namespace mysql-cluster \
    --create-namespace \
    --set architecture=replication \
    --set secondary.replicaCount=2 \
    --set auth.rootPassword='root' \
    --set primary.service.type=NodePort \
    --set primary.service.nodePorts.mysql=30306
  ```

## 安装

<https://helm.sh/zh/docs/intro/install/>

```bash
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-4
chmod 700 get_helm.sh
./get_helm.sh
# 命令补全
source <(helm completion bash)
```

## 基本使用

char包层级结构

```bash
# 可通过命令创建： helm create my-chart
my-chart/   # Chart名称
├── Chart.yaml           # 【必须】Chart元信息，包含Chart的名称、版本、描述等信息
├── values.yaml          # 【必须】默认配置值，定义所有可供用户自定义的参数及其默认值，可以通过 `-f` 或者 `--set` 来指定或修改值
├── charts/              # 【可选】依赖的子Chart
├── templates/           # 【必须】模板文件目录，存放k8s资源的模板文件。里面充满 `{{.Values.xxxx}}` 的占位符（使用go模板语法），当执行 `helm install` 是，Helm引擎会读取values.yaml中的值，然后渲染到占位符里，生成完整的、k8s可用的资源模板清单。可通过 `helm template --debug my-chart` 命令调试，具体参考调试章节
│   ├── _helpers.tpl     # 可重用的模板片段库。用于定义一些可以在多个模板文件中重复使用的“函数”，保持代码整洁
│   ├── deployment.yaml  # 部署应用的核心文件
│   ├── service.yaml     # 服务发现文件
│   ├── ingress.yaml     # 入口路由文件
│   ├── configmap.yaml   # 配置文件
│   ├── secret.yaml      # 敏感信息文件
│   └── tests/           # 测试文件目录
│       └── test-connection.yaml
└── README.md            # 【推荐】使用说明
```

常用命令

```bash
helm list # helm ls
helm install # helm install test-chart my-chart/
helm uninstall # 从secret/confgmap中存取操作记录
helm history
helm create # 创建目录
helm package # 打包成tgz文件，便于传输
```

```yaml title="Chart.yaml"
apiVersion: v2 # 接口版本，根据helm支持的版本决定
name: my-chart # chart名称，自定义
version: 0.1.0 # chart版本，自定义
appVersion: "1.16.0" # 应用版本，自定义
```

### 模板语法

Helm 提供了超过 60 个可用的函数。其中一些是由 Go 模板语言 本身定义的。大多数其他函数都是 Sprig 模板库 的一部分。

+ 模板函数和管道 <https://helm.kubernetes.ac.cn/docs/chart_template_guide/functions_and_pipelines/>
+ sprig <https://masterminds.github.io/sprig/>

### 调试

<https://helm.kubernetes.ac.cn/docs/chart_template_guide/debugging/>
