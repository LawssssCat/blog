---
title: Arthas（阿尔萨斯）笔记
order: 99
---

Arthas是Alibaba开源的Java程序线上诊断工具。
Arthas可以通过全局视角实时查看应用load、内存、gc、线程的状态信息，可以在不修改应用代码的情况下，对业务问题进行诊断，包括查看方法调用的出入参、异常，监测方法执行耗时，类加载信息等。

官网：
<https://arthas.aliyun.com/doc/web-console.html>

参考：

+ 介绍 <https://www.xiehai.zone/arthas/>
+ 源码分析+基本使用 <https://www.bilibili.com/video/BV1hV4y1c7Rx>

<!-- more -->

## 安装 & 卸载

参考：
<https://arthas.aliyun.com/en/doc/download.html>

```bash
# 在线安装
curl -O https://alibaba.github.io/arthas/arthas-boot.jar
java -jar arthas-boot.jar
# 离线安装
todo https://maven.aliyun.com/repository/public/com/taobao/arthas/arthas-packaging/3.1.7/arthas-packaging-3.1.7-bin.zip
unzip -d arthas arthas-packaging-3.1.7-bin.zip

# 卸载
rm -rf ~/.arthas/
rm -rf ~/logs/arthas/
```

```bash
# demo
curl -O https://alibaba.github.io/arthas/arthas-demo.jar
java -jar arthas-demo.jar
# attach
java -jar arthas-boot.jar
# 问题：Connect to telnet server error: 127.0.0.1 3658
# 参考：<https://arthas.aliyun.com/doc/expert/user-question-history13522.html>
# 解决：指定其他端口
java -jar arthas-boot.jar --telnet-port 9998 # 端口占用时命令
java -jar arthas-boot.jar --telnet-port 9998 --http-port 9999 --target-ip 0.0.0.0
```

## 参数

| 命令选项 | 描述 |
| ------------- | ------------------ |
| 安装下载 | |
| \-\-disabled-commands \<commands> | 禁用一些命令逗号分隔 |
| \-\-repo-mirror \<repo> | maven仓库，仓库可为`maven`、`aliyun`或者http地址 |
| \-\-user-version \<version> | 使用特定的Arthas版本，版本号可从versions中选择 |
| \-\-user-http | 强制使用http方式下载，默认使用https方式下载 |
| \-\-versions | 列出本地和远程可用的Arthas版本 |
| \-\-arthas-home \<path> | Arthas目录 |
| 运行监听 | |
| \-\-attach-only | 仅attach目标进程，不做连接，attach成功后就结束 |
| \-\-target-ip | telnet监听ip，默认为`127.0.0.1` |
| \-\-telnet-port \<number> | Arthas监听端口，默认`3658`，`0`则为随机端口 |
| \-\-http-port \<number> | Arthas http监听端口，默认`8563`，`0`则为随机端口 |
| \-\-tunnel-server \<url> | Tunnel服务url地址 |
| \-\-agent-id \<id> | 注册到Tunnel服务的代理id |
| \-\-session-timeout \<number> | 会话超时秒数，默认`1800`秒 |
| \-\-stat-url \<url> | 统计url地址，[参考](https://github.com/alibaba/arthas/blob/master/tunnel-server/src/main/java/com/alibaba/arthas/tunnel/server/app/web/StatController.java)实现 |
| \-\-select \<value> | 通过类名称或jar名称选择进程 |
| \-\-username \<name> | 授权用户名，默认`arthas` |
| \-\-password \<password> | 授权密码 |
| -f, \-\-batch-file \<file> | 指定脚本执行，脚本建议以`as`结尾 |
| -c, \-\-command \<commands> | attach后直接执行命令，多个命令使用`;`分隔 |
| \<pid> | 根据进程号选择进程 |
| 显示交互 | |
| \-\-app-name \<name> | 应用名称 |
| \-\-height \<number> | Arthas客户端终端高度 |
| \-\-width \<number> | Arthas客户端终端宽度 |
| -v, \-\-verbose | 打印debug信息 |
| -h, \-\-help | 帮助 |

## 配置

参考：
<https://arthas.aliyun.com/en/doc/arthas-properties.html>

名称： arthas.properties

位置：

+ `~/.arthas/lib/3.x.x/arthas/`

todo

## 命令

参考：
<https://arthas.aliyun.com/en/doc/commands.html>

```bash
[INFO] arthas-client connect 127.0.0.1 9998
  ,---.  ,------. ,--------.,--.  ,--.  ,---.   ,---.  
 /  O  \ |  .--. ''--.  .--'|  '--'  | /  O  \ '   .-' 
|  .-.  ||  '--'.'   |  |   |  .--.  ||  .-.  |`.  `-. 
|  | |  ||  |\  \    |  |   |  |  |  ||  | |  |.-'    |
`--' `--'`--' '--'   `--'   `--'  `--'`--' `--'`-----' 

wiki         https://arthas.aliyun.com/doc
tutorials    https://arthas.aliyun.com/doc/arthas-tutorials.html
version      4.1.7
main_class   arthas-demo.jar
pid          12005
start_time   2026-03-08 22:32:32.157
current_time 2026-03-08 22:36:53.863
```

### dashboard 仪表盘

```bash
# 线程（Thread）
ID    NAME                                  GROUP              PRIORITY     STATE       %CPU         DELTA_TIME  TIME         INTERRUPTED DAEMON       
53    Timer-for-arthas-dashboard-8b2b82cb-1 system             5            RUNNABLE    0.16         0.007       0:0.169      false       true
1     main                                  main               5            TIMED_WAITI 0.05         0.002       0:5.349      false       false        
51    arthas-NettyHttpTelnetBootstrap-3-15  system             5            RUNNABLE    0.02         0.001       0:0.127      false       true
21    Common-Cleaner                        InnocuousThreadGro 8            TIMED_WAITI 0.0          0.000       0:0.050      false       true
2     Reference Handler                     system             10           RUNNABLE    0.0          0.000       0:0.002      false       true
3     Finalizer                             system             8            WAITING     0.0          0.000       0:0.000      false       true
# JVM情况
Memory                           used      total      max        usage      GC                                                                         
heap                             28M       54M        3908M      0.74%      gc.g1_young_generation.count          4                                    
g1_eden_space                    16M       24M        -1         66.67%     gc.g1_young_generation.time(ms)       99
g1_old_gen                       11M       28M        3908M      0.28%      gc.g1_old_generation.count            0                                    
g1_survivor_space                1M        2M         -1         92.62%     gc.g1_old_generation.time(ms)         0
nonheap                          39M       43M        -1         92.13%     
codeheap_'non-nmethods'          1M        3M         7M         17.69%
metaspace                        28M       28M        -1         99.20%
```

### thread 线程

```bash
[arthas@12005]$ thread 1
"main" Id=1 TIMED_WAITING
    at java.base@17.0.18/java.lang.Thread.sleep(Native Method)
    at java.base@17.0.18/java.lang.Thread.sleep(Thread.java:344)
    at java.base@17.0.18/java.util.concurrent.TimeUnit.sleep(TimeUnit.java:446)
    at app//demo.MathGame.main(MathGame.java:17)
[arthas@12005]$ thread 1 | grep 'main('
    at app//demo.MathGame.main(MathGame.java:17)
```

### jad 反编译

```bash
[arthas@12005]$ jad demo.MathGame

ClassLoader:                                                                                                                                           
+-jdk.internal.loader.ClassLoaders$AppClassLoader@30946e09
  +-jdk.internal.loader.ClassLoaders$PlatformClassLoader@255854ef

Location:                                                                                                                                              
/tmp/me/arthas-demo.jar

       /*
        * Decompiled with CFR.
        */
       package demo;

       import java.util.ArrayList;
       import java.util.List;
       import java.util.Random;
       import java.util.concurrent.TimeUnit;

       public class MathGame {
           private static Random random = new Random();
           public int illegalArgumentCount = 0;

           public static void main(String[] args) throws InterruptedException {
               MathGame game = new MathGame();
               while (true) {
/*16*/             game.run();
/*17*/             TimeUnit.SECONDS.sleep(1L);
               }
           }

           public void run() throws InterruptedException {
               try {
/*23*/             int number = random.nextInt() / 10000;
/*24*/             List<Integer> primeFactors = this.primeFactors(number);
/*25*/             MathGame.print(number, primeFactors);
               }
               catch (Exception e) {
/*28*/             System.out.println(String.format("illegalArgumentCount:%3d, ", this.illegalArgumentCount) + e.getMessage());
               }
           }

           public static void print(int number, List<Integer> primeFactors) {
               StringBuffer sb = new StringBuffer(number + "=");
/*34*/         for (int factor : primeFactors) {
/*35*/             sb.append(factor).append('*');
               }
/*37*/         if (sb.charAt(sb.length() - 1) == '*') {
/*38*/             sb.deleteCharAt(sb.length() - 1);
               }
/*40*/         System.out.println(sb);
           }

           public List<Integer> primeFactors(int number) {
/*44*/         if (number < 2) {
/*45*/             ++this.illegalArgumentCount;
                   throw new IllegalArgumentException("number is: " + number + ", need >= 2");
               }
               ArrayList<Integer> result = new ArrayList<Integer>();
/*50*/         int i = 2;
/*51*/         while (i <= number) {
/*52*/             if (number % i == 0) {
/*53*/                 result.add(i);
/*54*/                 number /= i;
/*55*/                 i = 2;
                       continue;
                   }
/*57*/             ++i;
               }
/*61*/         return result;
           }
       }

Affect(row-cnt:1) cost in 1222 ms.
```

### watch

查看函数返回值

```bash
[arthas@12005]$ watch demo.MathGame primeFactors returnObj
Press Q or Ctrl+C to abort.
Affect(class count: 1 , method count: 1) cost in 425 ms, listenerId: 1
method=demo.MathGame.primeFactors location=AtExceptionExit
ts=2026-03-10 01:10:46.730; [cost=4.490965ms] result=null
method=demo.MathGame.primeFactors location=AtExit
ts=2026-03-10 01:10:47.736; [cost=0.106589ms] result=@ArrayList[
    @Integer[2],
    @Integer[2],
    @Integer[2],
    @Integer[2],
    @Integer[2],
    @Integer[2],
    @Integer[3],
    @Integer[3],
    @Integer[5],
    @Integer[7],
    @Integer[7],
]
```

### trace

略

### quit/exit & stop

quit和exit会退出会话，但arthas还会在后台运行，端口会占用，下次连接能直接连上。

stop可以完全退出arthas。

### todo

todo 监听地址修改 for web

## 源码分析

```bash
git clone git@github.com:alibaba/arthas.git
cd arthas
git checkout -b arthas-all-3.6.8
# 注释arthas-vmtool组件，否则编译报错
mvn clean package -TskipTests
```

目录：

```bash
bin/ —— 脚本
lib/
core/ —— arthas-core
common/ —— arthas-common
boot/ —— arthas-boot —— 入口 com.taobao.arthas.boot.Bootstrap#main
client/ —— arthas-client
agent/ —— arthas-agent
arthas-agent-attach/
arthas-spring-boot-starter/
memorycompiler/ —— arthas-memorycompiler
site/ —— arthas-site
spy/ —— arthas-spy
tunnel-common/ —— arthas-tunnel-common
tunnel-client/ —— arthas-tunnel-client
tunnel-server/ —— arthas-tunnel-server
testcase/ arthas-testcase
tutorials/
# async-profiler/
# math-game/ —— demo
web-ui/
```

### 网络通信架构（netty）

todo

### 组件关系

todo

### 命令实现流程

todo client -> server

### 命令实现原理

todo trace/thread/dashboard处理方式

## use-case

别人使用arthas一些好的实践，帮助增加定位问题思路。
