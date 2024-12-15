---
title: Jenkins 介绍
date: 2024-04-18
tag:
  - jenkins
order: 1
---

Jenkins（原名 Hudson）是开源的 CI/CD 工具。

官网： <https://jenkins.io>
插件： <https://plugins.jenkins.io>

Jenkins 是一个持续集成和持续交付工具，可用作简单的 CI 服务器，或者变成任何项目的持续交付中心。
Jeninks 是一个基于 Java 的独立程序，可以立即运行，包含 Windows、Mac OS X 和其他类 Unix 操作系统。
Jenkins 可以通过 web 界面轻松配置，包括及时错误检查和内置帮助。
Jenkins 支持插件扩展。

特性：

- 变更支持： Jenkins 能从代码仓库中获取代码更新列表。
- 永久链接： Jenkins 的 web 链接地址都是永久链接地址。因此，可以在各文档中直接使用。
- 消息通知： Jenkins 可集成 Email/RSS/IM。
- 测试报告： Jenkins 可以用图标等形式提供详细的 Junit/TestNG 测试报告。
- 分布式构建： Jenkins 可以把集成构建等工作分发到多台计算机中完成。
- 文件指纹： Jenkins 会保存每次构建过程中使用的文件信息、构建产生的文件信息。
- 三方插件

<!-- more -->

> 参考：
>
> - ~~https://www.bilibili.com/video/BV1R54y117MD~~ 杂
> - https://www.bilibili.com/video/BV17T4y1m7o2
> - todo https://www.bilibili.com/video/BV1z34y1q738
> - todo https://www.bilibili.com/video/BV1144y1j77n
> - todo https://www.bilibili.com/video/BV1V1421b7CK/ （进阶）

## 概念

软件开发生命周期（Software Development Life Cycle, SDLC）集合了需求分析、设计、开发、测试、部署等过程。

持续集成（Continuous Integration，CI）

持续交付（Continuous Delivery，CD）

持续部署（Continuous Deployment，CD）

## 安装

<https://www.jenkins.io/doc/book/installing/>

### war

Jenkins war 捆绑了 winstone 和 jetty servlet container。所以可以直接运行，启动 web 服务。

参数： <https://www.jenkins.io/doc/book/installing/initial-settings/>

::: details 环境准备

```bash
# userdel jenkins
# groupdel ci
groupadd ci
useradd jenkins -g ci # usermod -G ci jenkins
# passwd jenkins

mkdir -p /data/software
chown -R jenkins:ci /data/software

su jenkins
cd /data/software
wget http://mirrors.jenkins.io/war-stable/lastest/jenkins.war # 2.249.1
# https://mirrors.jenkins.io/war-stable/2.249.1/jenkins.war

$ vim /etc/profile
# JDK
export JAVA_HOME=/usr/local/java/jdk1.8.0_181
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```

:::

```bash
java -jar jenkins.war --httpPort=8888
# nohup java -jar /data/software/jenkins.war --httpPort=8777 --httpsPort=8778 > /data/software/jenkins.log 2>&1 &

# 开放端口
# 如果是云服务器，开放安全组即可
# 如果是虚拟机，则防火墙开放端口
firewall-cmd --zone=public --add-port=8777/tcp --permanent
firewall-cmd --reload
```

```bash
maven clean install -DskipTests
java -jar app.jar
```

### Docker

成熟的部署方式肯定是镜像方式，如：k8s

```bash
# 版本选择例子：

# 最新镜像
docker pull jenkins/jenkins:2.235.5-alpine
docker pull jenkins/jenkins:2.253-centos7
# 推荐
docker pull jenkins/jenkins:2.204.5
docker pull jenkins/jenkins:2.204.5-alpine
```

```bash
# docker pull jenkins/jenkins:lts
# docker run -di --name=jenkins -p 8080:8080 -v /mydata/jenkins_home/:/var/jenkins_home jenkins/jenkins:lts
mkidr -p /data/jenkins && chown -R 1000:1000 /data/jenkins
docker run -itd --name jenkins -p 8080:8080 -p 5000:5000 -u root \
-e JAVA_OPTS=-Duser.timezone=Asia/Shanghai \
-v /data/jenkins/:/var/jenkins_home/ \
--restart always jenkins/jenkins:2.204.5-alpine

# 💡端口 5000 用于 jnlp4 协议连接 agent 节点
```

#### 中文镜像制作

Jenkins 中文社区

```bash
官网:   https://bintray.com/jenkins-zh/generic/jenkins
docker: https://hub.docker.com/r/jenkinszh/jenkins-zh
github: https://github.com/jenkins-zh/jenkins-formulas
```

```dockerfile
FROM jenkins/jenkins:2.204.5-alpine

# 修改源
RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
    echo "http://mirrors.aliyun.com/alpine/latest-stable/community" >> /etc/apk/repositories

# 安装需要的软件，解决时区问题
RUN apk --update add curl bash tzdata && \
    rm -rf /var/cache/apk/*

# 参考中文社区的 Dockerfile
# https://github.com/jenkins-zh/jenkins-formulas/blob/2.190.3/Dockerfile
ENV TZ Asia/Shanghai
ENV JENKINS_UC https://updates.jenkins-zh.cn
ENV JENKINS_UC_DOWNLOAD https://mirrors.tuna.tsinghua.edu.cn/jenkins
ENV JENKINS_OPTS="-Dhudson.model.UpdateCenter.updateCenterUrl=https://updates.jenkins-zh.cn/update-center.json"
ENV JENKINS_OPTS="-Djenkins.install.runSetupWizard=false"
COPY init.groovy /usr/share/jenkins/ref/init.groovy.d/init.groovy
COPY hudson.model.UpdateCenter.xml /usr/share/jenkins/ref/hudson.model.UpdateCenter.xml
COPY mirror-adapter.crt /usr/share/jenkins/ref/mirror-adapter.crt
```

### other

```bash
BUILD_ID=dontKillMe
cd target
ps -ef | grep demo-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print "kill -l5 $2"}' | sh
nohup java -jar demo-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
```

`http://128.0.0.1:8080/restart` 重启

参考：

- <https://gitee.com/zhengqingya/java-developer-document/tree/master/%E7%9F%A5%E8%AF%86%E5%BA%93/%E8%BF%90%E7%BB%B4/Jenkins/01-%E5%AE%89%E8%A3%85>

## 常用设置

### 系统管理

#### 全局工具设置

```bash
# 系统管理/全局工具设置/Maven|JDK|Git|...
```

##### JDK

todo

##### Maven

todo

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
<localRepository>/data/software/repository</localRepository>
<mirrors>
  <mirror>
    <id>aliyun-maven</id>
    <mirrorOf>central</mirrorOf>
    <name>aliyun maven mirror</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  </mirror>
</mirrors>
</settings>
```

### 安全

#### 重置用户密码

常用于：忘记密码

修改 useSecurity 为 false

```xml title="/home/jenkins/.jenkins/config.xml"
<useSecurity>true</useSecurity>
```

::: info
每次重启后，这个值会变回 true
:::

重启 `127.0.0.1:8777/restart`

```python
# 开启用户模式（这样才能改用户密码）
Manage Jenkins/Configure Global Security/安全域/Jenkins' own user database
# 开启登录检查
Manage Jenkins/Configure Global Security/授权策略/Logged-in users can do anything
```

#### 凭证配置

todo `Credentials Binding` 用户凭证插件安装

#### 角色权限管理

`Role-based Authorization Strategy`

### todo

- `Extra CSS` 在文本框内贴上 CSS 样式
- `Git Parameter Plugin` 安装后可以选择按照 `git tag` 或者 `branch` 发布版本
- `NodeJS Plugin` 安装后可以执行 `npm` 指令，打包和发布前端代码
- `SonarQube Scanner for Jenkins` 安装后可以执行 `sonar scanner` 指令，跑 CI 时检查代码质量
- `Maven Integration Plugin` 设置 `maven` 指令的 `classpath` 和指令路径
- `Workspace Cleanup Plugin` 可以指定 `workspace` 最多保留多少个历史命令

### 插件管理

[link](./plugin.md)

## 节点配置

todo
https://www.bilibili.com/video/BV17T4y1m7o2

## 流水线（Pipline）

Pipeline 是一套运行在 Jenkins 上的工作流框架，将原来独立运行于单个或者多个节点的任务连接起来，实现单个任务难以完成的复杂流程编排和可视化工作。

特性：

- 代码化 —— Pipeline 以代码的形式实现，方便编辑、审查和迭代
- 持久 —— Pipeline 计划外重启服务器，可恢复任务
- 可停止 —— Pipeline 可接入交互式输入，以确定是否继续执行 Pipeline
- 可计划 —— Pipeline 支持复杂的持续交付要求。支持 `fork/join`、循环执行、并行执行任务。
- 可扩展 —— Pipeline 插件支持其 DSL 的自定义扩展，以及与其他插件集成的多个选项

::: info

参考：

- Jenkins2 Pipeline Docs - http://zeyangli.github.io/chapter5/1/

:::

### 相关插件

```bash
git 插件：
jenkins/系统管理/节点管理/可选插件/git

pipeline 插件：（安装插件后，创建任务的时候多了 “流水线” 类型）
jenkins/系统管理/节点管理/可选插件/pipeline
```

### 流水线编写

Pipeline 脚本由 Grovvy 语言实现。

::: tip

前置知识：

- [Groovy 基础语法](../dev-java-commonsense/sdk-script-groovy.md)：Groovy 字符串、列表、字典、if 语句、switch 语句、for、while 语句、函数、类、闭包等

:::

支持两种语法：

- `Declarative` 声明式 （推荐）
- ~~`Scripted Pipeline` 脚本式~~

#### 脚本式（Scripted）

```groovy
node('jenkinsagent-154') {
  stage('Preparation') { // for display purpose
    echo 'Hello Scripted Pipeline'
  }
}
```

#### 声明式（Declarative）

```groovy
pipeline {
  // agent any
  agent none
  stages {
    stage('Hello') {
      steps {
        echo 'Hello Declarative Pipeline'
      }
    }
  }
}
```

### Jenkinsfile

Pipeline 有两种创建方法：

- 直接在 Jenkins 的 Web UI 界面中输入脚本
- 通过创建一个 Jenkinsfile 脚本文件放入项目源库（SCM）中

### 共享库（Shared Library）

共享库（shared library）是一些独立的 Groovy 脚本集合。我们可以在运行 Pipeline 的时候获取这些共享库代码。

导入共享库

::: tabs

@tab 新版

```groovy
Library 'your-shared-library'
```

@tab 老版

```groovy
@Library('your-shared-library')
```

:::

—— sharelibrary 基本概念，配置 sharelibrary，编写 Jenkinsfile

使用常用的流水线方法 —— 常用的流水线方法，httpRequest，readJson，readYaml 等

Pipeline 定义 - agent/options | 准备阶段

```groovy
agent {
  node {
    label "master" // 指定运行节点的标签或名称
    customWorkspace "${workspace}" // 指定运行工作目录（可选）
  }
}

options {
  timestamps() // 插件：日志会有时间
  skipDefaultCheckout() // 删除隐式 checkout scm 语句
  disableConcurrentBuilds() // 禁止并行
  timeout(time: 1, unit: 'HOURS') // 流水线超时设置 1h
}
```

Pipeline 定义 - stages | 构建阶段

```groovy
stages {
  stage("GetCode") {
    steps {
      timeout(time:5, unit:"MINUTES") {
        script {
          println("获取代码")
        }
      }
    }
    stage("Build") {
      steps {
        timeout(time:20, unit:"MINUTES") {
          script {
            println("应用打包")
          }
        }
      }
    }
    stage("CodeScan") {
      steps {
        timeout(time:30, unit:"MINUTES") {
          script {
            println("代码扫描")
          }
        }
      }
    }
  }
}
```

Pipeline 定义 - post | 构建后操作

```groovy
post {
  // 总是执行
  always {
    script {
      println("always")
    }
  }
  // 成功后执行
  success {
    script {
      // currentBuild 局部变量
      // description 构建描述
      currentBuild.description = "\n 构建成功！✅"
    }
  }
  // 失败后执行
  failure {
    script {
      currentBuild.description = "\n 构建失败！❌"
    }
  }
  // 取消后执行
  aborted {
    script {
      currentBuild.description = "\n 构建取消！❕"
    }
  }
}
```

## Demo

### 部署 war

```bash
$ weget https://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.66/bin/apache-tomcat-8.5.66.tar.gz
$ tar -zxvf apache-tomcat-8.5.66.tar.gz
$ cd apache-tomcat-8.5.66/bin
$ ./startup.sh
$ tail -222f ../logs/catalina.out # 查看日志，未报错即可
# 开启防火墙
$ firewall-cmd --zone=public --add-port=8080/tcp --permanent
$ firewall-cmd --reload
```

### 部署 jar

:::::: tabs

@tab 部署

```bash title="deploy.sh"
#!/bin/bash

DIR="/data/app"
projectName="maven-test"

server_ips="192.168.1.8"
for server_ip in ${server_ips[@]}; do

echo "$server_ip"

echo "backup"
ssh -Tq -oStrictHostKeyChecking=no admin@${server_ip} <<EOF
mkdir -p $DIR/backup/${projectName}
if [ -f "$DIR/${project}/${projectName}.jar" ]; then
  mv -v $DIR/${project}/${projectName}.jar $DIR/backup/${project}/${projectName}.jar
fi
EOF

echo "download"
scp -q -oStrictHostKeyChecking=no ${WORKSPACE}/target/*.jar admin@${server_ip}:/tmp/${projectName}.jar

echo "deploy"
ssh -q -oStrictHostKeyChecking=no admin@${server_ip} <<EOF
mv -v /tmp/${projectName}.jar $DIR/${projectName}/${projectName}.jar
cd ${DIR}/${projectName}
sh stop.sh
sh start.sh
EOF

done

echo "success"
```

@tab 启动

```bash title="start.sh"
#!/bin/bash
set -e # 错误退出
set -o pipefail

APP_ID=maven-test
APP_DIR="/data/app"

nohup java -Dspring.profiles.active=dev -jar ${APP_DIR}/${APP_ID}/${APP_ID}.jar > release_out.log &
start_ok=false
if [[ $? = 0 ]]; then
  sleep 3
  tail -n 10 release_out.log
  sleep 5
  tail -n 50 release_out.log
fi
aaa=${grep "Started" release_out.log | awk '{print $1}'}
if [[ -n "${aaa}" ]]; then
  echo "Application started ok"
  exit 0
else
  echo "Application started error"
  exit 1
fi
```

@tab 停止

```bash title="stop.sh"
#!/bin/bash

APP_ID=maven-test
ps aux | grep ${APP_ID} | grep -v 'grep' | awk '{print "kill -9 "$2}' | sh
```

::::::

### WebHook

自动触发 Jenkins 构建

步骤：

1. 针对不同代码仓库平台，安装对应插件。如：gitee （一般 github 默认安装了）
1. 内网穿透：frp

```bash
# 钉钉内网穿透
$ git clone --depth=1 https://github.com/open-dingtalk/pierced.git
$ chmod -R 777 pierced
# 运行如下命令
# mydomain 你的二级域名前缀
# 8777 本地监听端口
$ ./pierced/linux/ding -config=./ding.cfg -subdomain=mydomain 8777
```

### 离线、灰度、生产回滚代码

按分支/标签发布

| 依赖插件      | 说明 |
| ------------- | ---- |
| Parameter     |
| Git Parameter |

| 参数                | 说明                                                                     | 值             |
| ------------------- | ------------------------------------------------------------------------ | -------------- |
| x/Git Parameter     | 选择可以发布的分支/标签/其他。参数构建时，会主动拉取仓库相关参数供选择。 | 一般必须默认值 |
| x/Branches to build |                                                                          | `${branch}`    |

todo 主从 Jenkins 节点

本地环境：开发/测试
离线、灰度、生产环境：云服务器

### 通知

```bash
https://oapi.dingtalk.com/rebot/send?access_token=6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd
```

```bash
#!/bin/bash
#-*- coding:utf-8 -*-

tt="BUILD_DATE=$(date +%F-%T)"
message='发布完成\n项目名称: ${PROJECT_NAME}\n项目版本号: ${version}\n操作类型: $type\n触发时间: $tt\n触发人员: $BUILD_USER'

https://oapi.dingtalk.com/rebot/send?access_token=6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd6cecd \
-H 'Content-Type: application/json' \
-d "
{
  \"msgtype\": \"text\",
  \"text\": {
    \"content\": \"$message\"
  }
}
"
```

::: tip
想要获取到构建项目信息，需要在 Jenkins 的构建项目中开启：
构建环境/Set jenkins user build variables
:::

```txt
发布完成
项目名称: tf-teacher-front
项目分支&TAG: t04_20210104_02
检出分支或TAG:
环境: t04
完成时间: 2021-01-04 15:53:22
触发人员: test-lichanjuan
完成状态: 成功
```

蒲公英/fir.im

### SonarQube

官网： <https://sonarqube.org>

Your teammate for **Code Quality and Code Security**.

SonarQube empowers all developers to write cleaner and safer code.

#### 安装

环境准备：

:::::: tabs

@tab 数据库

安装 Postgresql （SonarQube 7.9 以上版本不再支持 mysql）

```bash
$ 安装 docker
$ 配置 docker 国内镜像源
$ docker pull postgres
$ docker run --name mypostgres -d -p 5432:5432 -e POSTGRES_PASSWORD=123456 postgres
$ docker exec -it mypostgres psql -U postgres -d postgres
# 创建数据库
postgres=# create database sonar;
# 设置 sonar 用户名和密码 sonar postgres
postgres=# create user sonar;
postgres=# alter user sonar with password 'postgres';
# 授予 sonar 权限
postgres=# alter role sonar createdb;
postgres=# alter role sonar superuser;
postgres=# alter role sonar createrole;
# 更改 sonar 数据库拥有者（否则 sonarqube 连接失败）
postgres=# alter database sonar owner to sonar;
# 查看数据库
postgres=# \l
# 查看用户
postgres=# \du
# 重启数据库
$ docker exec -it mypostgres systemctl start postgresql-11
```

@tab SonarQube

安装 SonarQube

```bash
# 下载 Community 版本
$ su -
$ mkdir -p /opt/sonar
$ cd /opt/sonar
$ wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-8.9.0.43852.zip

# 解压 sonar 并设置权限
$ yum install unzip
$ unzip sonarqube-8.9.0.43852.zip
$ mv sonarqube-8.9.0.43852 sonarqube
$ useradd sonar
$ chown -R sonar. /opt/sonar

# 更改 sonar 配置
$ su sonar
$ vim /opt/sonar/sonarqube/conf/sonar.properties

sonar.jdbc.username=sonar
sonar.jdbc.password=postgres
sonar.jdbc.url=jdbc:postgresql://47.92.230.42:5432/sonar
# sonar 默认监听 9000 端口
```

@tab JDK

配置 JDK11

```bash
$ tar -zxvf jdk-11_linux-x64_bin.tar.gz
$ cd jdk-11/bin
$ pwd
/opt/sonar/jdk-11/bin

# 修改 sonarqube/conf/wrapper.conf 中的 java 设置
# wrapper.java.command=java
wrapper.java.command=/opt/sonar-jdk-11/bin/java

# 修改环境变量
vim /etc/profile
export ES_JAVA_HOME=/opt/sonar/jdk-11
source /etc/profile
```

::::::

启动 sonar

```bash
$ cd /opt/sonar/sonarqube
$ su sonar ./bin/linux-x86064/sonar.sh start
$ su sonar ./bin/linux-x86064/sonar.sh status
$ su sonar ./bin/linux-x86064/sonar.sh stop
$ tail -f logs/sonar.logs

# 开放防火墙
$ firewall-cmd --zone=public --add-port=9000/tcp --permanent
$ firewall-cmd --reload
```

todo Jenkins 集成 Sonar
