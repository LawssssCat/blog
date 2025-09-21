# C/C++基础测试

用vagrant起虚拟机，在Centos中搭建开发环境

1、启动虚拟机

```bash
vagrant up
```

2、配置登录信息

```bash
$ su -
vagrant

# 开放会话管理功能
$ vi /etc/ssh/sshd_config
PasswordAuthentication yes
AllowAgentForwarding yes
AllowTcpForwarding yes
$ systemctl restart sshd.service

# 生成登录秘钥
$ ssh-keygen
$ ssh-copy-id localhost
vagrant
$ 考出来，配进去 ~/.ssh/config
Host vagrant-cppdev
  HostName 192.168.56.20
  User vagrant
  IdentityFile .ssh/id_ed25519
```

编译环境（参考：<https://www.cnblogs.com/lenmom/p/9193388.html>）

```bash
# 镜像源
sudo sed -e 's|^metalink=|#metalink=|g' \
    -e 's|^#baseurl=http://download.example/pub/fedora/linux|baseurl=https://mirrors.tuna.tsinghua.edu.cn/fedora|g' \
    -i.bak \
    /etc/yum.repos.d/fedora.repo \
    /etc/yum.repos.d/fedora-updates.repo
sudo yum makecache
```
