---
title: Git
date: 2025-02-03
order: 20
---

参考：

+ 《完全学会Git, GitHub, Git Server的24堂课》


```bash
git init # 创建 .git 目录
gitk # 可视化

#####################
# 三区操作（工作/暂存/提交/远程）
#####################

git status
# git config --global core.editor notepad

git add poem.txt
# git rm --cached poem.txt # ❗不带 --cached 会将文件删除
git mv poem.txt xxx.txt # 改名

# stash
git stash list
git stash save
# git stash pop
git stash apply

# branch

# merge
# --no-ff —— no fast-forward merge
# git config --global merge.tool kdiff3
# git config --global mergetool.kdiff3.cmd "'D:/Program Files/KDiff3/kdiff3.exe' \"\$BASE\" \"\$LOCAL\" \"\$REMOTE\" -o \"\$MERGED\""
# git config --global mergetool.prompt false
# git config --global mergetool.kdiff3.trustExitCode true
# git config --global mergetool.keepBackup false

# cherry-pick

# rebase

# checkout
git checkout commitId 文件1 文件2 ...

# reset
# --hard
# 

# commit
git commit -m "xxx" --author="name <eamil>"

# revert

# clone/pull/push
git clone "远程Git文档库路径" "本地Git文档库路径" # 本地
git push origin --set-upstream 分支名称
# git config --global push.default origin # 默认推送分支
git config -l | grep 分支名称
# remote.origin.url="远程Git文档库地址"
# remote.origin.fetch=+refs/heads/*:refs/remotes/分支名称/*
# branch.master.remote=origin

#####################
# 文件信息
#####################

# diff
git diff
git difftool # KDiff3/VimMerge
# e.g. KDiff3
# git config --global diff.tool kdiff3
# git config --global difftool.kdiff3.cmd "'D:/Program Files/KDiff3/kdiff3.exe' \"\$LOCAL\" \"\$REMOTE\"" 
# git config --global difftool.prompt false

# find
git grep '要找的字符串' commitId
git grep -e '要找的字符串' -e '要找的字符串' commitId # or
git grep -e '要找的字符串' --and -e '要找的字符串' commitId # and
git grep -e '要找的字符串' --and \( -e '要找的字符串' -e '要找的字符串' \) commitId # and + or
git blame -L 1,2 .\pnpm-lock.yaml # 文件最后修改人

# 文件最后修改人
git blame .\pnpm-lock.yaml
git blame -L 1,2 .\pnpm-lock.yaml # 指定行

# log
git log [args...] 文件1 文件2 ... 
# --author=
# --after=
# --before=

# --numstat
# --shortstat
# --oneline

# --graph 节点图
# --decorate 标识分支名称

git log --graph --oneline --all --decorate
git log --pretty=tformat: --numstat

# shotlog
git shortlog
# --numbered -n 提交次序排序
# --summary -s 忽略说明

# ls-files
git ls-files
# git ls-files | xargs wc -l
# git ls-files | xargs cat | wc -l

# reflog —— 查看全部分支（包括被丢弃的分支）
```

## 配置文件

优先级

+ `.git/config`
+ `~/.gitconfig`
+ `%git_home%/etc/gitconfig`

```bash
git config -l # （全部优先级）显示当前目录、用户空间、安装目录配置
git config --global -l # 只显示用户空间、安装目录配置
git config --system -l # 只显示安装目录配置
```

常用配置

```bash
git config --global user.name '操作者名称'
git config --global user.email '操作者邮箱'
# git config --global --unset user.email 
```

## .gitignore

格式

## 统计

todo GitStatus —— Python 开发

## GUI

todo ~~Git GUI~~

todo ~~SmartGit~~

todo ~~SourceTree~~

todo TortoiseGit

todo GitHub for Windows

## Git Server

### 远程仓库结构

GitHub 存储的用户仓库称之为 “远程仓库”，这种仓库以 Bare 类型的 Git 文档库形式存储。

::: info
所谓 Bare 类型的文档库就是将 .git 文件夹的内容放到工作目录中。
由于远程仓库只需要存储“已提交”的记录，所以用 Bare 类型的文件库旧可以专门管理提交的文件版本。
:::

```bash
git init --bare 名称
# .
# ├── HEAD
# ├── config
# ├── description
# ├── hooks
# │   ├── applypatch-msg.sample
# │   ├── commit-msg.sample
# │   ├── fsmonitor-watchman.sample
# │   ├── post-update.sample
# │   ├── pre-applypatch.sample
# │   ├── pre-commit.sample
# │   ├── pre-merge-commit.sample
# │   ├── pre-push.sample
# │   ├── pre-rebase.sample
# │   ├── pre-receive.sample
# │   ├── prepare-commit-msg.sample
# │   ├── push-to-checkout.sample
# │   ├── sendemail-validate.sample
# │   └── update.sample
# ├── info
# │   └── exclude
# ├── objects
# │   ├── info
# │   └── pack
# └── refs
#     ├── heads
#     └── tags

# 从“远程Git文档库”复制出“本地Git文档库”
git clone "远程Git文档库路径" "本地Git文档库路径" # 本地
git clone //计算机名称/"远程Git文档库路径" "本地Git文档库路径" # 局域网
git clone http://Web域名或者IP/"远程Git文档库路径" "本地Git文档库路径" # Web服务器
git clone Git账号@SSH服务器地址:"远程Git文档库路径" "本地Git文档库路径" # SSH服务器

# 从“远程Git文档库”复制出“远程Git文档库”
git clone --bare "远程Git文档库路径" "远程Git文档库路径"

git remote -l
git remote add "分支名称" "远程Git文档库路径"
git remote update
```

### Git Daemon

Git Daemon 是 Git 内建的网络访问服务。
使用 Git Daemon 不需要按拳皇其他程序，只需要使用 Git 命令就可以启动专用的通信协议 Git Protocol 和 9418 端口。（缺点：没有授权的管理和控制机制）

```bash
git daemon --export-all --port=9418 --verbose # 开放当前文件夹中的所有Git文档库
# git coone git://域名或者IP地址/GitRemoteRepo/Game1 # 开呗当前目录/GitRemoteRepo/Game1
git daemon --export-all --base-path='/d/GitRemoteRepo'
# git coone git://域名或者IP地址/Game1 # 开呗当前目录/GitRemoteRepo/Game1
```

### HTTP 服务

使用 HTTP 访问 Git Server 步骤：

一、需要在服务器安装 Git 程序。（Git 程序没有区分服务器版本或者客户端版本）

二、启动 Web Server 服务。

> 这里使用 Apache 服务器： <https://www.apachelounge.com/download/>

```xml title="httpd.conf"
# 代码仓位置
<Directory "C:/GitRemoteRepo">
  Options Indexes FollowSymLinks
  AllowOverride None
  Order allow,deny
  Allow from all
</Directory>

# Git程序安装位置
<Directory "C:/Program Files (X86)/Git/libexec/git-core">
  Options Indexes FollowSymLinks
  AllowOverride None
  Order allow,deny
  Allow from all
</Directory>

  SetEnv GIT_REPOJECT_ROOT C:/GitRemoteRepo
  SetEnv GIT_HTTP_EXPORT_ALL
  ScriptAliasMatch "(?x)^/git/( \
    .*/(HEAD | \
    info/refs | \
    objects/(info/[^/]+ | [0-9a-f]{2}/[0-9a-f]{38} | pack/pack-[0-9a-f]{40}\.(pack|idx)) | \
    git-(upload|receive)-pack \
  ))$" "C:/Program Files (x86)/Git/libexec/git-core/git-http-backend.exec/$1"

# 当访问/git路径时，需要使用指定的账号文件htpasswd验证账号和密码
<Localtion /git>
  AuthType Basic
  AuthName "GIT Repository"
  AuthUserFile "C:/GitRemoteRepo/htpasswd"
  Require valid-user
</Localtion>
```

三、使用 Apache 工具程序创建账号文件

```bash
htpasswd -cmb C:/GitRemoteRepo/htpasswd 账号名称 密码
```

四、启动

```bash
httpd # 启动

cd C:/GitRemoteRepo
git init --bare xxx 

# 拉取
cd /tmp
git clone http://127.0.0.1/git/xxx
```

### HTTPS 服务

步骤：

1. 创建证书
1. 修改 Apache 的 `httpd.conf` 文件
1. 修改 Apache 的 `httpd-ssl.conf` 文件

```bash
openssl genrsa -des3 -out server.key 2048 -config $httpd_home/conf/openssl.cnf # 对称密码
openssl req -new -key server.key -out server.csr -config $httpd_home/conf/openssl.cnf # 证书创建请求
openssl x509 -req -days 3650 -in server.csr -signkey server.key -out server.crt # 证书
mv server.key server.key.old
openxsl rsa -in server.key.old -out server.key # 非对称私钥（不用每次启动httpd时输入密码）
mv server.key $httpd_home/conf/
mv server.crt $httpd_home/conf/

# 修改 Apache 的 httpd.conf 配置文件，去掉下面的"#"符号
#LoadModule socache_shmcb_module modules/mod_socache_shmcb.so
#LoadModule ssl_module modules/mod_ssl.so
#Include conf/extra/httpd-ssl.conf

# 重启httpd服务


# 拉取
git -c http.sslVerify=false clone https://域名或者IP地址/git/xxx
# git config http.sslVerify false
```

### SSH 服务

> ~~这里 Windows 使用 Copssh。~~ 用 linux 启动 ssh server 服务。

todo
