---
title: vagrant 介绍
order: 66
---

Vagrant提供虚拟化技术编排能力，配合Virtual Box，可以通过简单的配置脚本能快速搭建起虚拟机

e.g.

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64" # 系统
  config.vm.provider :virtualbox do |vbox| # 规格
    vbox.cpus = 2
    vbox.memory = 2048
  end
  config.vm.disk :disk, size: "40GB" # 存储
end
```

<!-- more -->

资料：

+ 文档|<https://www.yuque.com/wukong-zorrm/xmk0v0> \
  视频|<https://www.bilibili.com/video/BV1me411f7sU> ⭐⭐⭐
+ 视频|<https://www.bilibili.com/video/BV15t4y167zX> ⭐⭐⭐
+ 视频|<https://www.bilibili.com/video/BV1ZG411h7tq> todo
+ 视频|<https://www.bilibili.com/video/BV1F5411e7EL>

::: tip

**问题：Vagrant含义？**

Vagrant —— Vagrant is a tool for building and distributing development environments.
流浪汉 —— 流浪汉无依无靠，走哪都一样。我们的程序也是一样的，需要在哪运行都一样，避免“在我电脑上是好的，怎么到你那就不能用”的问题。

:::

::: tip

**问题：有了容器技术，为什么还要虚拟机？**

像搭建Redis集群，因为xxxxx的原因，通过容器技术搭建会有问题。
像搭建k8s集群，虽然有minikube、k3d、kind等容器化环境，但是对于学习入门，用虚拟机会少碰到问题。
像大数据，如apache Ambari、hadoop等一般不放在容器中

:::

::: tip

**问题：Vagrant和Docker的关系？**

Docker晚于Vagrant，Docker设计上借鉴Vagrant。
他们有如下关系可以对应：

&nbsp; | Docker | Vagrant
--- | --- | ---
编排 | docker compose | vagrant
引擎 | docker | hypervisor(Virtual Box/VMware/Hyper-V)
定义 | image | box
实例 | container | virtual machine

:::

## 安装

Vagrant 2.4.0
<https://developer.hashicorp.com/vagrant/downloads>

VirtualBox 7.0.12
<https://www.virtualbox.org/wiki/Downloads>

Vagrant提供虚拟化技术编排能力，但不提供虚拟化的功能。虚拟化需要借助其他虚拟化引擎，例如Virtual Box、VMware、Hyper-V等，Vagrant也可以使用Docker。

::: warning

Windows用户如果安装了Docker Desktop，为避免出现问题，
需要先卸载Docker Desktop，在安装Vagrant和VirtualBox后，在重新安装Docker Desktop。

:::

::: tip

**问题：为什么使用Virtual Box？**

1. Vagrant对Virtual Box支持旧，支持好，功能丰富
1. Virtual Box可以在Windows、Linux、Mac（Intel）下提供一致的功能体验

:::

## 使用、概念

基础命令

```bash
# 拉取Box（从Vagrant Cloud拉取），生成vagrantfile配置文件
vagrant init
vagrant init hashicorp/bionic64
# 启动虚拟机 （读取当前目录下Vagrantfile文件配置；启动后可以到virtualbox上看启动情况）
vagrant up
vagrant status
vagrant reload # 更新配置，使更改生效
vagrant reload --provision
vagrant halt # 关闭虚拟机
vagrant destroy # 删除虚拟机
vagrant suspend # 休眠
vagrant resume # 唤醒
# 连接虚拟机
vagrant ssh
vagrant ssh <name>
vagrant ssh-config

vagrant box list
```

### Box概念

使用 `vagrant init hashicorp/bionic64` 会从 Vagrant Cloud 上拉取文件作为 Box 资源。

Box 资源是压缩包（tar/tar.gz/zip），其结构如下：

+ VM Image —— 虚拟机镜像（`.ofa`）和磁盘文件（`.vmdk`）
+ metadata.json —— 包含数据如：虚拟化平台（virtualbox/vmware）、架构（x64/ARM）
+ info.json —— 描述信息
+ Vagrantfile —— 内置配置文件

### Box选型

问题：如何从 [Vagrant Cloud](https://portal.cloud.hashicorp.com/vagrant/discover) 上选择我们需要的 Box 资源？

像Ubuntu、Alamlinux、Fedaro、Rocky等，官方都会发布支持Virtual Box的Box文件，其他平台的Box大多由第三方维护。

知名的第三方维护组织有[generic](https://app.vagrantup.com/generic)和[bento](https://app.vagrantup.com/bento)，几乎所有的Linux发行版本，都可以在上述两个组织下找到。其中bento的一些box来源于generic组织。

<!--

```bash
vagrant init bento/almalinux-8
```

-->

```ruby title="Vagrantfile"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
end
```

::: details

```py
# 【（首次初始化并）启动虚拟机】
$ vagrant up
Bringing machine 'default' up with 'virtualbox' provider...
# 1. 下载
==> default: Box 'ubuntu/jammy64' could not be found. Attempting to find and install...
    default: Box Provider: virtualbox
    default: Box Version: >= 0
==> default: Loading metadata for box 'ubuntu/jammy64'
    default: URL: https://vagrantcloud.com/api/v2/vagrant/ubuntu/jammy64
==> default: Adding box 'ubuntu/jammy64' (v20241002.0.0) for provider: virtualbox
    default: Downloading: https://vagrantcloud.com/ubuntu/boxes/jammy64/versions/20241002.0.0/providers/virtualbox/unknown/vagrant.box
    default:
==> default: Successfully added box 'ubuntu/jammy64' (v20241002.0.0) for 'virtualbox'!
==> default: Importing base box 'ubuntu/jammy64'...
# 2. 网络设置： 默认配置nat模式网卡
==> default: Matching MAC address for NAT networking...
==> default: Checking if box 'ubuntu/jammy64' version '20241002.0.0' is up to date...
==> default: Setting the name of the VM: vagrant_default_1750522687819_5098
Vagrant is currently configured to create VirtualBox synced folders with
the `SharedFoldersEnableSymlinksCreate` option enabled. If the Vagrant
guest is not trusted, you may want to disable this option. For more
information on this option, please refer to the VirtualBox manual:

  https://www.virtualbox.org/manual/ch04.html#sharedfolders

This option can be disabled globally with an environment variable:

  VAGRANT_DISABLE_VBOXSYMLINKCREATE=1

or on a per folder basis within the Vagrantfile:

  config.vm.synced_folder '/host/path', '/guest/path', SharedFoldersEnableSymlinksCreate: false
==> default: Clearing any previously set network interfaces...
==> default: Preparing network interfaces based on configuration...
    default: Adapter 1: nat
# 3. 设置端口转发： 虚拟机22端口 <=> 宿主2222端口
==> default: Forwarding ports...
    default: 22 (guest) => 2222 (host) (adapter 1)
==> default: Running 'pre-boot' VM customizations...
==> default: Booting VM...
==> default: Waiting for machine to boot. This may take a few minutes...
# 4. 设置ssh用户、密钥对
    default: SSH address: 127.0.0.1:2222
    default: SSH username: vagrant
    default: SSH auth method: private key
    default: Warning: Connection reset. Retrying...
    default: Warning: Connection aborted. Retrying...
    default:
    # 生成新的 ssh 密钥对
    # 否则可以使用 ssh vagrant@localhost -p 2222 命令登入虚拟机，这被认为是不安全的。
    default: Vagrant insecure key detected. Vagrant will automatically replace
    default: this with a newly generated keypair for better security.
    default:
    default: Inserting generated public key within guest...
    default: Removing insecure key from the guest if it's present...
    default: Key inserted! Disconnecting and reconnecting using new SSH key...
# 5. 启动成功
==> default: Machine booted and ready!
==> default: Checking for guest additions in VM...
    default: The guest additions on this VM do not match the installed version of
    default: VirtualBox! In most cases this is fine, but in rare cases it can
    default: prevent things such as shared folders from working properly. If you see
    default: shared folder errors, please make sure the guest additions within the
    default: virtual machine match the version of VirtualBox you have installed on
    default: your host and reload your VM.
    default:
    default: Guest Additions Version: 6.0.0 r127566
    default: VirtualBox Version: 7.1
# 6. 将当前目录，以共享目录形式，挂载到虚拟机/vagrant目录
==> default: Mounting shared folders...
    default: C:/my/temp/vagrant => /vagrant

# tips：上述设置，可以进入virtual box设置进行查看

# 【进入虚拟机】
$ vagrant ssh
vagrant@ubuntu-jammy:~$ pwd
/home/vagrant
vagrant@ubuntu-jammy:~$ ll /vagrant/
total 5
drwxrwxrwx  1 vagrant vagrant    0 Jun 21 16:15 ./
drwxr-xr-x 20 root    root    4096 Jun 21 16:18 ../
drwxrwxrwx  1 vagrant vagrant    0 Jun 21 16:15 .vagrant/
-rwxrwxrwx  1 vagrant vagrant   75 Jun 21 16:14 Vagrantfile*
vagrant@ubuntu-jammy:~$ whoami
vagrant
```

:::

### Vagrantfile

Vagrantfile是用于配置和管理虚拟机配置选项的文件，例如管理虚拟机的操作系统、网络设置、共享文件夹等。

Vagrantfile使用ruby语法。

当我们运行 `vagrant init` 命令时，会在 **当前目录** 下生成 Vagrantfile 配置文件。
默认配置如下：

```ruby title="Vagrantfile"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
end
```

#### 镜像源配置

Vagrant Cloud 不像 Docker Hub 有完整的镜像站。
但是常用的 Linux 系统都会发布 Box 文件，可以从 linux 发行版的国内镜像站下载 Box 文件。（比如 Ubuntu 清华源）

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
  config.vm.box_url = "https://mirrors.tuna.tsinghua.edu.cn/ubuntu-cloud-images/jammy/20230929/jammy-server-cloudimg-amd64-vagrant.box"
end
```

也可以从指定本地离线box源

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
  config.vm.box_url = "file://tmp/ubuntu-2204.box"
end
```

#### 环境变量配置

```ruby title="Vagrant"
ENV["LC_ALL"] = "en_US.UTF-8"

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
end
```

#### 端口转发配置

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
  config.vm.network "forwarded_port", guest: 80, host: 8080
end
```

::: tip
端口转发可以使外部访问虚拟机内部服务，但是不适合搭建集群。
搭建集群需要设置“共有网络”（见网络篇）。
:::

#### Provider和Provision

Provider 是 “虚拟化提供程序” 负载虚拟化配置CPU、内存、硬盘、网络等。
Provider 可选：

+ Virtual Box
+ vmware
+ hyper-V
+ docker

Provision 是 “配置管理器” 负载环境配置、软件安装。
Provision 可选：

+ shell
+ ansible
+ ProgressChef
+ puppet
+ salt

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"

  # 规格配置
  config.vm.provider :virtualbox do |vbox|
    vbox.name = "ubuntu-22.04"
    vbox.cpus = 2
    vbox.memory = 2048
    # 设置显示模式。 参考：https://www.ugnas.com/tutorial-detail/id-51.html
    vbox.customize ['modifyvm', :id, '--graphicscontroller', 'vmsvga'] # 模拟 VMware SVGA 图形设备。这种图形控制器与传统 VBoxVGA 选项相比，提高了性能和 3D 支持。
  end

  # 修改hostname，更新/etc/hosts
  config.vm.hostname = "ubuntu-1"
  # 设置硬盘配置
  config.vm.disk :disk, size: "40GB", primary: true # 设置系统盘容量
  config.vm.disk :disk, size: "20GB", name: "extra_disk" # 新建一个硬盘
end
```

::: tip

如果要使用VMware配置虚拟机：

1. 需要安装 Vagrant VMware Utility 和 vagrant VMware provider plugin 插件
1. 修改 Vagrantfile 文件的 provider 选项

```ruby
config.vm.provider "vmware_desktop" do |v|
  v.vmx["memsize"] = "1024"
  v.vmx["numvcpus"] = "2"
end
```

:::

##### 例子：通过Provision开启ssh密码登录

vagrant用户默认密码是vagrant。这是不安全的，因此vagrant默认不启动ssh密码登录。

通过provision可以修改密码且开启ssh密码登录。

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
  # 修改vagrant用户密码
  config.vm.provision "shell", inline: "echo vagrant:Abc123 | sudo chpasswd"
  # 启用ssh密码认证
  config.vm.provision "shell", path: "bootstrap.sh"
end
```

```bash title="bootstrap.sh"
#!/bin/bash

echo "===> Enable ssh password authentication"
sed -i 's/^[# ]*PasswordAuthentication .*/PasswordAuthentication yes/' /etc/ssh/sshd_config
echo "PasswordAuthentication yes" > /etc/ssh/sshd_config.d/1-ssh-open-passauth.conf
systemctl reload sshd
```

::: tip

provision 配置只在第一次使用 `vagrant up` 创建环境时执行。
如果虚拟机已经创建，需要显式调用：

```bash
vagrant up --provision
vagrant reload --provision
```

或者手动执行（常用于调试）

```bash
vagrant provision
```

:::

### 网络配置

::: tip

默认网络设置：

Vagrant总是把第一个网卡设置成“网络地址转换(NAT)”模式（Network Address Translation）。
这种模式可以向外访问网络，但是主机和虚拟机、虚拟机和虚拟机之间无法相互识别（因为todo）。

为了实现虚拟机之间相互访问，可以将网卡设置改为“NAT网络”模式（NAT network/NAT service）。

&nbsp; | 适用场景 | 说明
--- | --- | ---
网络地址转换(NAT) <br>（Network Address Translation） | 虚拟机访问外部资源（如更新、下载软件），但无需对外提供服务。 | &nbsp;
桥接网卡 <br>（Bridged Adapter） | 虚拟机需要与局域网中的其他设备（如服务器、打印机）通信，或者虚拟机需要被局域网中其他设备访问。 | &nbsp;
todo | ... | &nbsp;

:::

Vagrant 网络：

1. **NAT（alias：端口转发）（默认）** —— 虚拟机可以上网，外部网络不可见虚拟机。这种模式下外部访问虚拟机服务需要通过端口转发。

  ```ruby
  config.vm.network "forwarded_port", guest: 80, host: 8080
  ```

  ::: info
  vagrant 总是把第一个网卡设置成 “NAT” 模式，不可修改。
  :::

1. **私有网络** —— 宿主机、虚拟机之间可以相互访问

  ```ruby
  # 这个IP需要在VirtualBox的网段范围内
  config.vm.network "private_network", ip: "192.168.56.20"
  # 可以使用dhcp方式，自动配置地址
  # config.vm.network "private_network", type: "dhcp"
  ```

  ::: tip
  有了私有网络，就可以通过IP访问虚拟机了！

  ```bash
  ssh vagrant@192.168.56.20 -i .vagrant/machines/default/virtualbox/private_key
  ```

  :::

  ::: info
  VirtualBox默认将`192.168.56.0/21`网段分配给私有网络。
  相关命令：

  ```bash
  # 查看网段
  VBoxMessage list hostonlynets
  ```

  :::

1. **公有网络** —— 虚拟机可以上网，外部网络可见虚拟机

  公有网络在VirtualBox中，使用bridge网络实现。

  ```ruby
  config.vm.network "public_network", ip: "10.150.36.191", bridge: "en4: USB-eth"
  ```

  网卡相关命令

  ```bash
  # 查看网卡
  VBoxManage list bridgedifs
  # 创建网卡
  VBoxManage natnetwork add --netname natnet1 --network “192.168.15.0/24” --enable
  VBoxManage natnetwork modify --netname natnet1 --dhcp on
  VBoxManage natnetwork add --netname natnet1 --network “192.168.15.0/24” --enable --dhcp on
  VBoxManage natnetwork start --netname natnet1
  # 更多
  https://www.laoma.cc/archives/1537.html
  ```

### 文件同步

```ruby
# todo
config.vm.define "node-1" do |host|
  host.vm.box = "ubuntu/jammy64"
  host.vm.synced_folder ".", "/vagrant", type: "smb"
end
```

::: info

坑：**虚拟机系统没有安装`VBoxGuestAdditions.iso`里的`vagrant-vbguest`，导致无法使用`vboxsf`文件系统**

具体报错如下：

```bash
Vagrant was unable to mount VirtualBox shared folders. This is usually
because the filesystem "vboxsf" is not available. This filesystem is
made available via the VirtualBox Guest Additions and kernel module.
Please verify that these guest additions are properly installed in the
guest. This is not a bug in Vagrant and is usually caused by a faulty
Vagrant box. For context, the command attempted was:

mount -t vboxsf -o uid=1000,gid=1000,_netdev vagrant1 /vagrant1

The error output from the command was:

mount: unknown filesystem type 'vboxsf'
```

解决防范如下：

1. 安装缺失软件
1. 使用vagrant文件同步插件，如
1. 更改虚拟机系统，其他系统可能默认安装文件同步所需的软件

坑：**hyper-v无法直接同步文件，需要借助第三方软件，如smb**

适配脚本如下：

```ruby
host_list = [
  {
    :name => "host1",
    :box => "centos/7"
  }
]

Vagrant.configure("2") do |config|

  config.vm.provider "hyperv" do |v|
    config.vm.synced_folder ".", "/vagrant", type: "smb"
  end
  config.vm.provider "virtualbox" do |v|
    config.vm.synced_folder ".", "/vagrant", type: "virtualbox"
  end

  host_list.each do |item|
    config.vm.define item[:name] do |host|
      host.vm.box = item[:box]
    end
  end

end
```

:::

## 目录说明

项目目录：存储虚拟机信息

```bash
./.vagrant
todo
```

用户目录：存储box信息

```bash
~/.vagrant.d/
todo
```

## Box打包、上传

配置好的box可以重新打包，后面就能直接用了。
更可以将box上传VagrantCloud网站，后面就能直接下载了。

```bash
# 打包命令
vagrant package # 在当前目录生成package.box文件
# 重新导入
vagrant box add newBoxName=01 package.box
vagrant box list
# vagrant init newBoxName-01 # 使用
# 上传命令
todo
```

::: warning

打包前需要先删除网络配置，否则重新使用box会有问题：

```bash
sudo rm -rf /etc/udev/rules.d/70-persistent-net.rules
```

:::

todo 待整理
<https://www.bilibili.com/video/BV1SK4y1S7SR>

```bash
vagrant plugin install vagrant-vbguest --plugin-version 0.21

vagrant package --base vagrant-virtualbox_default_1610_48693 # 生成package.box文件
vagrant box add --name centos-vim --provider=virtualbox ./package.box
```

todo 发布

## 案例：批量创建虚拟机

### 一、创建虚拟机，并指定IP地址

::: tabs

@tab 默认配置

```ruby title="Vagrant"
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"

  (1..3).each do |i|
    config.vm.define "node-#{i}" do |node|
      node.vm.provision "shell", inline: "echo hello from node #{i}"
    end
  end
end
```

@tab 指定配置

```ruby title="Vagrant"
vm_list = [
  { # hash map
    "name" => "node-1",
    "cpu" => "2",
    "mem" => "2048",
    "ip_addr" => "192.168.56.10"
  },
  {
    "name" => "node-2",
    "cpu" => "1",
    "mem" => "1024",
    "ip_addr" => "192.168.56.11"
  },
  {
    "name" => "node-3",
    "cpu" => "1",
    "mem" => "1024",
    "ip_addr" => "192.168.56.12"
  },
]

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"

  vm_list.each do |item|
    config.vm.define item["name"] do |node|

      node.vm.provider "virtualbox" do |vbox|
        vbox.name = item["name"] # 虚拟机名词
        vbox.memory = item["mem"] # 内存
        vbox.cpus = item["cpu"] # CPU
      end
      # 设置hostname —— /etc/sysconfig/network
      node.vm.hostname = item["name"]
      # 设置IP —— 设置网络后，主机-虚拟机、虚拟机-虚拟机之间才可以通信
      node.vm.network "private_network", ip: item["ip_addr"]

    end
  end
end
```

:::

创建后通过ssh命令连接

```bash
ssh vagrant@192.168.56.10 -i .vagrant/machines/node-1/virtualbox/private_key
ssh vagrant@192.168.56.11 -i .vagrant/machines/node-2/virtualbox/private_key
ssh vagrant@192.168.56.12 -i .vagrant/machines/node-3/virtualbox/private_key
```

### 二、虚拟机互通

这时候在虚拟机内部可以通过ip互通

但我们一般需要设置host互通：

```bash
$ vim /etc/hosts
192.168.60.11 node-1
192.168.60.12 node-2
192.168.60.13 node-3
```

### 三、免密登录（跳板机）

集群间的文件传输或者跨节点登录等，都需要进行密码的验证，每次输入密码很麻烦，可以设置为免密码登录。

生成公钥

```bash
$ ssh-keygen -t rsa
Generating public/private rsa key pair.
Enter file in which to save the key (/home/vagrant/.ssh/id_rsa):
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
Your identification has been saved in /home/vagrant/.ssh/id_rsa
Your public key has been saved in /home/vagrant/.ssh/id_rsa.pub
The key fingerprint is:
SHA256:Sx3LHxNOvKHf9A3pR40dlKTU5wtRT6CK89gBltVhuss vagrant@node-1
The key's randomart image is:
+---[RSA 3072]----+
|          ..oo==o|
|         o +ooo+o|
|        + o *..oo|
|       . = O = ++|
|        S O = * =|
|       . B = * =.|
|        o E o o +|
|               . |
|                 |
+----[SHA256]-----+
```

将公钥拷贝到其他机器（包含自身）

```bash
$ ssh-copy-id node-1
$ ssh-copy-id node-2
$ ssh-copy-id node-3
# e.g.
$ ssh-copy-id node-1 # 遇到的问题：Permission denied (publickey,gssapi-keyex,gssapi-with-mic). 因为没有指定秘钥，右没有开启密码登录，所以没有登录方式，所以报错
/usr/bin/ssh-copy-id: INFO: Source of key(s) to be installed: "/home/vagrant/.ssh/id_rsa.pub"
The authenticity of host 'node-1 (127.0.2.1)' can't be established.
ED25519 key fingerprint is SHA256:hrgKADcxz0ExinW1LFgSDWz8u9ar5V1R84elql5hvic.
This key is not known by any other names
Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
/usr/bin/ssh-copy-id: INFO: attempting to log in with the new key(s), to filter out any that are already installed
/usr/bin/ssh-copy-id: INFO: 1 key(s) remain to be installed -- if you are prompted now it is to install the new keys
vagrant@node-1: Permission denied (publickey).
$ sudo vim /etc/ssh/sshd_config # 开启密码登录，默认密码vagrant
PasswordAuthentication yes
$ sudo systemctl restart sshd
$ ssh-copy-id vagrant@node-1
/usr/bin/ssh-copy-id: INFO: Source of key(s) to be installed: "/home/vagrant/.ssh/id_rsa.pub"
/usr/bin/ssh-copy-id: INFO: attempting to log in with the new key(s), to filter out any that are already installed
/usr/bin/ssh-copy-id: INFO: 1 key(s) remain to be installed -- if you are prompted now it is to install the new keys
vagrant@node-1's password:

Number of key(s) added: 1

Now try logging into the machine, with:   "ssh 'vagrant@node-1'"
and check to make sure that only the key(s) you wanted were added.
```

### todo 一键完成脚本

todo

## todo 案例：创建k3s集群

集群规划

主机名 | IP地址 | 类型
--- | --- | ---
k3s-server | 192.168.56.10 | Control Plane 控制平面
k3s-agent1 | 192.168.56.11 | 工作节点
k3s-agent2 | 192.168.56.12 | 工作节点

```ruby title="Vagrant"
vm_list = [
  { # hash map
    "name" => "node-1",
    "cpu" => "2",
    "mem" => "2048",
    "ip_addr" => "192.168.56.10"
  },
  {
    "name" => "node-2",
    "cpu" => "1",
    "mem" => "1024",
    "ip_addr" => "192.168.56.11"
  },
  {
    "name" => "node-3",
    "cpu" => "1",
    "mem" => "1024",
    "ip_addr" => "192.168.56.12"
  },
]

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"

  vm_list.each do |item|
    config.vm.define item["name"] do |node|

      node.vm.provider "virtualbox" do |vbox|
        vbox.name = item["name"] # 虚拟机名词
        vbox.memory = item["mem"] # 内存
        vbox.cpus = item["cpu"] # CPU
      end
      # 设置hostname
      node.vm.hostname = item["name"]
      # 设置IP
      node.vm.network "private_network", ip: item["ip_addr"]

      # 执行shell脚本
      node.vm.provision "shell" do |script|
        script.path = "k3s-install.sh" # 脚本路径
        script.args = [item["name"]] # 传递参数
      end

    end
  end
end
```

```bash title="k3s-install.sh"
echo "===> k3s cluster settings:"
TOKEN="NEIwQ0IxMEUtNTA2MS00RE"
API_SERVER="192.168.56.10"
FLANNEL_IFACE="enp0s8"
echo "   TOKEN: ${TOKEN}"
echo "   API_SERVER: ${API_SERVER}"
echo "   FLANNEL_IFACE: ${FLANNEL_IFACE}"

echo "===> disable firewall"
ufw disable

if [ "$1" == "k3s-server" ]; then
  echo "===> install $1"
  curl -sfL "https://rancher-mirror.rancher.cn/k3s/k3s-install.sh" | \
    INSTALL_K3S_MIRROR="cn" \
    K3S_TOKEN="${TOKEN}" \
    sh -s - server --flannel-iface ${FLANNEL_IFACE}
else
  echo "===> install $1"
  curl -sfL "https://rancher-mirror.rancher.cn/k3s/k3s-install.sh" | \
    INSTALL_K3S_MIRROR="cn" \
    K3S_URL="https://${API_SERVER}:6443" \
    K3S_TOKEN="${TOKEN}" \
    sh -s - --flannel-iface ${FLANNEL_IFACE}
fi
```

::: info

**Flannel网络**：

K3s内置了flannel作为默认的网络插件（CNI）。
flannel默认是选择第一个网卡进行网络通信的，但是由于vagrant虚拟机的第一个网卡总是nat模式，虚拟机之间无法相互访问。
因此，在安装k3s时，需要使用`--flannel-iface`将第二个网卡enp0s8设置为默认网络。

:::

创建后通过ssh命令连接

```bash
ssh vagrant@192.168.56.10 -i .vagrant/machines/node-1/virtualbox/private_key
ssh vagrant@192.168.56.11 -i .vagrant/machines/node-2/virtualbox/private_key
ssh vagrant@192.168.56.12 -i .vagrant/machines/node-3/virtualbox/private_key
```

查看节点状态

```bash
$ ssh vagrant@192.168.56.10 -i .vagrant/machines/node-1/virtualbox/private_key
# 节点状态
$ sudo kubectl get node
# pod状态
$ sudo kubectl get pod -A
```

::: warning

处理：“failed to get CA certs:” 问题

todo
<https://www.cnblogs.com/jmbkeyes/p/13140559.html>
<https://dev59.com/5FEG5IYBdhLWcg3wct5F>
<https://github.com/k3s-io/k3s/issues/2852>
<https://github.com/rancher/rke2/issues/5651>

:::

## 插件

```bash
vagrant plugin list
```

### 插件：vagrant-hostmanager

管理虚拟机中的 `/etc/hosts` 文件，便于维护集群间通信信息

Github： <https://github.com/devopsgroup-io/vagrant-hostmanager>

```bash
# 1 安装
vagrant plugin install vagrant-hostmanager
# 2 配置
略
# 3 生效
vagrant up # 在up或者destroy时生效
vagrant hostmanager # 手动生效
```

### 插件：Parallels

todo

### 插件：Vagrant Manager

todo
