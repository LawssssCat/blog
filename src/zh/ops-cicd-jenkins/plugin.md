---
title: Jenkins 插件管理
date: 2024-07-22
tag:
  - jenkins
order: 2
---

<https://updates.jenkins-ci.org/download/plugins/>

## 常用插件

### 插件：国内源

::: tip

Jenkins 的所有插件安装后需要重启才能生效。

:::

国内源：

```bash
Manage Jenkins/Manage Plugin/Advanced/Update Site/
https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json
```

修改服务器配置

```bash
# 进入 jenkins 安装目录
# e.g. /home/jenkins/.jenkins/updates/default.json
# 替换 updates.jenkins-ci.org/download 为 mirrors.tuna.tsinghua.edu.cn/jenkins
# 替换 www.google.com 为 www.baidu.com
sed -i 's@http://updates.jenkinsci.org/download@https://mirrors.tuna.tsinghua.edu.cn/jenkins@g' default.json
sed -i 's@http://www.google.com@https://www.baidu.com@g' default.json
```

### 插件：参数化构建

todo

## plugin-installation-manager-tool

Jenkins Plugin Installation Manager Tool 是一个用于管理 Jenkins 插件的工具，它可以帮助你在 Jenkins 中进行插件的安装、升级和管理。

```bash
mkdir -p plugin-management-cli/plugins
cd plugin-management-cli
wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/plugin-management-parent-pom-1.0.2/jenkins-plugin-manager-1.0.2.jar
# 安装插件
java -jar jenkins-plugin-manager-1.0.2.jar --war /usr/share/jenkins/jenkins.war --plugin-download-directory plugins --plugins blueocean:1.1.15

# sudo chown -R jenkins:ci ./plugins
# sudo cp -R ./plugins/* /var/lib/jenkins/plugins

# sudo systemctl restart jenkins
```

e.g.

```bash
# 2.249.1
java -jar ~/jenkins/plugin-management-cli/jenkins-plugin-manager-1.0.2.jar --war ~/jenkins/jenkins.war --plugin-download-directory ~/.jenkins/plugins --plugins credentials-binding:1.27.1
java -jar ~/jenkins/plugin-management-cli/jenkins-plugin-manager-1.0.2.jar --war ~/jenkins/jenkins.war --plugin-download-directory ~/.jenkins/plugins --plugins credentials-binding:1.27.1
java -jar ~/jenkins/plugin-management-cli/jenkins-plugin-manager-1.0.2.jar --war ~/jenkins/jenkins.war --plugin-download-directory ~/.jenkins/plugins --plugins credentials-binding:1.27.1
```
