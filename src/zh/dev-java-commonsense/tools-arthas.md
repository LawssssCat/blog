---
title: Arthas（阿尔萨斯）笔记
order: 99
---

Arthas是Alibaba开源的Java程序线上诊断工具。
Arthas可以通过全局视角实时查看应用load、内存、gc、线程的状态信息，可以在不修改应用代码的情况下，对业务问题进行诊断，包括查看方法调用的出入参、异常，监测方法执行耗时，类加载信息等。

官网：
<https://arthas.aliyun.com/doc/web-console.html>

命令列表：
<https://arthas.aliyun.com/doc/commands.html>

参考：

+ 介绍 <https://www.xiehai.zone/arthas/>
+ 源码分析+基本使用 <https://www.bilibili.com/video/BV1hV4y1c7Rx>
+ telnet协议 <https://datatracker.ietf.org/doc/html/rfc729>

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

## 配置 & 默认文件位置

参考：
<https://arthas.aliyun.com/en/doc/arthas-properties.html>

名称： arthas.properties

位置：

+ `~/.arthas/lib/3.x.x/arthas/`

日志：

+ agent日志
  + `~/logs/arthas/arthas.log`
  + `/tmp/logs/arthas/arthas.log`

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

通过远程监听进入debug模式：

```bash
版本：
arthas-all-3.6.8
wget https://maven.aliyun.com/repository/public/com/taobao/arthas/arthas-packaging/3.6.8/arthas-packaging-3.6.8-bin.zip
mkdir arthas-packaging-3.6.8-bin
cd arthas-packaging-3.6.8-bin
unzip ../arthas-packaging-3.6.8-bin.zip

# 程序启动
# suspend=y —— 等待idea客户端接入再进入main方法
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 arthas-boot.jar

# 调试窗口（附加到远程JVM）
断点：
com.taobao.arthas.boot.Bootstrap#main
```

### 环境变量处理

```bash
properties:os.name
properties:os.arch
MSYSTEM —— e.g. MINGW
SHELL —— e.g. /bin/bash


JAVA_HOME
properties:java.home —— java家目录
properties:java.specification.version —— java版本

JAVA_TOOL_OPTIONS


ARTHAS_LIB_DIR
properties:user.home —— 用户家目录
properties:user.home + .arthas/lib
properties:java.io.tmpdir + .arthas/lib
```

logger:
arthas-packaging-3.6.8-bin/logback.xml
~/logs/arthas/arthas.log

类加载关系：

+ BootStrapClassloader
  + PlatFormClassloader
    + SystemClassloader
    + ArthasClassloader

### 命令行参数处理

三方件：

+ com.taobao.middleware.cli —— 参数解析
+ jline —— 命令行交互

启动流程命令依赖：

```bash
arthas-boot.jar —— 入口，下载/调用其他jar包工具
arthas-client.jar —— 客户端
arthas-core.jar
arthas-agent.jar
jdk/bin/jps
jdk/lib/tools.jar —— java -Xbootclasspath/a:tools.jar
```

#### arthas-boot.jar

命令行入口，可以通过这个jar包调用client、core、agent包功能

```bash title="arthas-boot.jar"
e.g.
java -jar arthas-boot.jar -v --select demo

--help 略
--verbose 略

--versions
        local ARTHAS_LIB_DIR/{version}
        remote https://arthas.aliyun.com/api/versions
--use-version down to ARTHAS_LIB_DIR/{use-version}/
--arthasHome
        1. is directory
        1. contains "arthas-core.jar"
        1. contains "arthas-agent.jar"
        1. contains "arthas-spy.jar"
--repo-mirror 略

--pid Target pid
--select select target process by classname or JARfilename
--target-ip   The target jvm listen ip, default 127.0.0.1
--telnet-port The target jvm listen telnet port, default 3658
--http-port   The target jvm listen http port, default 8563
--session-timeout The session timeout seconds, default 1800 (30min)

--app-name The app name
--username The username
--password The password

--stat-url The report stat url
--tunnel-server The tunnel server url
--agent-id The agent id register to tunnel server

--attach-only Attach target process only, do not connect
```

#### arthas-client.jar

client包，提供连接telnet功能

```bash title="arthas-client.jar"
e.g.
java -jar arthas-client.jar -c session --execution-timeout 20000 127.0.0.1 3658

--help 略

-c,--command Command to execute, multiple commands separated by ;

--execution-timeout

-b,--batch-file The batch file to execute

-w,--width 交互宽度
-h,--height 交互高度
```

#### arthas-core.jar

core包，提供附加（attach）agent功能

```bash title="arthas-core.jar"
java -jar arthas-core.jar \
-pid 1580 \
-target-ip 127.0.0.1 \
-telnet-port 3658 \ \
-http-port 8563 \
-core arthas-core.jar \
-agent arthas-agent.jar
```

进入arthas-core调试需要手动添加`-agentlib`命令行执行

```bash
ARTHAS_HOME=$(cd .; pwd)
java -jar \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 \
arthas-core.jar \
-pid $(jps|grep demo|awk '{print $1}') \
-target-ip 127.0.0.1 \
-telnet-port 3658 \ \
-http-port 8563 \
-core ${ARTHAS_HOME}/arthas-core.jar \
-agent ${ARTHAS_HOME}/arthas-agent.jar
```

```java title="com.taobao.arthas.core.Arthas#attachAgent"
VirtualMachine virtualMachine = VirtualMachine.attach("" + configure.getJavaPid());
String arthasAgentPath = configure.getArthasAgent();
configure.setArthasAgent(encodeArg(
    // ***/arthas/arthas-packaging-3.6.8-bin/arthas-agent.jar
    arthasAgentPath));
configure.setArthasCore(encodeArg(
    // ***/arthas/arthas-packaging-3.6.8-bin/arthas-core.jar
    configure.getArthasCore()));
virtualMachine.loadAgent(arthasAgentPath,
    // ***%2Farthas%2Farthas-packaging-3.6.8-bin%2Farthas-core.jar;;javaPid=256;telnetPort=3658;ip=127.0.0.1;
    // arthasAgent=***%2Farthas%2Farthas-packaging-3.6.8-bin%2Farthas-agent.jar;
    // arthasCore=***%2Farthas%2Farthas-packaging-3.6.8-bin%2Farthas-core.jar;
    configure.getArthasCore() + ";" + configure.toString());
```

#### arthas-agent.jar

agent包，通过pid将agent附加到指定java程序中（参考），调用premain/agentmain方法将telnet监听启动供client连接。

```bash
# 1、直接给 demo 添加远程调试监听
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
    -jar arthas-demo.jar

# 2、idea连接远程监听
# 3、添加agent附加 （随便方法）
java -jar arthas-boot.jar
```

```xml title="agent/pom.xml"
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>single</goal>
                        </goals>
                        <phase>package</phase>
                        <configuration>
                            <descriptorRefs>
                                <descriptorRef>jar-with-dependencies</descriptorRef>
                            </descriptorRefs>
                            <archive>
                                <manifestEntries>
                                    <Premain-Class>com.taobao.arthas.agent334.AgentBootstrap</Premain-Class>
                                    <Agent-Class>com.taobao.arthas.agent334.AgentBootstrap</Agent-Class>
                                    <Can-Redefine-Classes>true</Can-Redefine-Classes>
                                    <Can-Retransform-Classes>true</Can-Retransform-Classes>
                                    <Specification-Title>${project.name}</Specification-Title>
                                    <Specification-Version>${project.version}</Specification-Version>
                                    <Implementation-Title>${project.name}</Implementation-Title>
                                    <Implementation-Version>${project.version}</Implementation-Version>
                                </manifestEntries>
                            </archive>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

```java
    public static void premain(String args, Instrumentation inst) {
        main(args, inst);
    }
    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
    }
    private static synchronized void main(String args, final Instrumentation inst) {
        // 尝试判断arthas是否已在运行，如果是的话，直接就退出
        try {
            Class.forName("java.arthas.SpyAPI"); // 加载不到会抛异常
            if (SpyAPI.isInited()) {
                ps.println("Arthas server already stared, skip attach.");
                ps.flush();
                return;
            }
        } catch (Throwable e) {
            // ignore
        }
        // ...
    }
```

##### 类加载流程（启动服务监听流程）

```bash title="arthas-agent.jar"
com.taobao.arthas.agent334.AgentBootstrap#premain
    java.arthas.SpyAPI
    com.taobao.arthas.core.server.ArthasBootstrap
        static
            com.taobao.arthas.core.env.PropertySource
            com.taobao.arthas.core.env.MapPropertySource
            com.taobao.arthas.core.env.ArthasEnvironment
            com.taobao.arthas.core.shell.history.impl.HistoryManagerImpl
            com.taobao.arthas.core.security.SecurityAuthenticatorImpl
            com.taobao.arthas.core.shell.ShellServer
            com.taobao.arthas.core.shell.term.TermServer
            com.taobao.arthas.core.shell.handlers.BindHandler
            com.taobao.arthas.core.shell.system.JobController
            com.taobao.arthas.core.shell.session.SessionManager
            com.alibaba.arthas.tunnel.client.TunnelClient
            com.taobao.arthas.core.advisor.TransformerManager
            com.taobao.arthas.core.command.view.ResultViewResolver
            com.taobao.arthas.core.shell.term.impl.http.api.HttpApiHandler
            com.taobao.arthas.core.config.Configure
            com.taobao.arthas.core.util.affect.EnhancerAffect
        com.taobao.arthas.core.server.ArthasBootstrap#getInstance
            initFastjson
            initSpy
                instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(spyJarFile));
            initArthasEnvironment
            outputPath.mkdirs();
            loggerContext = LogUtil.initLogger(arthasEnvironment);
            enhanceClassLoader
            initBeans
                # 注册命令的展示
                com.taobao.arthas.core.command.view.ResultViewResolver#initResultViews
                    registerView(RowAffectView.class);
                    registerView(StatusView.class);
                    registerView(VersionView.class);
                    registerView(MessageView.class);
                    registerView(HelpView.class);
                    //registerView(HistoryView.class);
                    registerView(EchoView.class);
                    registerView(CatView.class);
                    registerView(Base64View.class);
                    registerView(OptionsView.class);
                    registerView(SystemPropertyView.class);
                    registerView(SystemEnvView.class);
                    registerView(PwdView.class);
                    registerView(VMOptionView.class);
                    registerView(SessionView.class);
                    registerView(ResetView.class);
                    registerView(ShutdownView.class);
                    registerView(ClassLoaderView.class);
                    registerView(DumpClassView.class);
                    registerView(GetStaticView.class);
                    registerView(JadView.class);
                    registerView(MemoryCompilerView.class);
                    registerView(OgnlView.class);
                    registerView(RedefineView.class);
                    registerView(RetransformView.class);
                    registerView(SearchClassView.class);
                    registerView(SearchMethodView.class);
                    registerView(LoggerView.class);
                    registerView(DashboardView.class);
                    registerView(JvmView.class);
                    registerView(MemoryView.class);
                    registerView(MBeanView.class);
                    registerView(PerfCounterView.class);
                    registerView(ThreadView.class);
                    registerView(ProfilerView.class);
                    registerView(EnhancerView.class);
                    registerView(MonitorView.class);
                    registerView(StackView.class);
                    registerView(TimeTunnelView.class);
                    registerView(TraceView.class);
                    registerView(WatchView.class);
                    registerView(VmToolView.class);
                    registerView(JFRView.class);
            bind
                tunnelClient = new TunnelClient();
                httpSessionManager = new HttpSessionManager(); # com.taobao.arthas.core.shell.term.impl.http.session.HttpSessionManager#HttpSessionManager
                shellServer = new ShellServerImpl(options); # com.taobao.arthas.core.shell.impl.ShellServerImpl#ShellServerImpl
                # 注册命令的处理
                builtinCommands = new BuiltinCommandPack(disabledCommands); # com.taobao.arthas.core.command.BuiltinCommandPack#initCommands
                    commandClassList.add(HelpCommand.class);
                    commandClassList.add(AuthCommand.class);
                    commandClassList.add(KeymapCommand.class);
                    commandClassList.add(SearchClassCommand.class);
                    commandClassList.add(SearchMethodCommand.class);
                    commandClassList.add(ClassLoaderCommand.class);
                    commandClassList.add(JadCommand.class);
                    commandClassList.add(GetStaticCommand.class);
                    commandClassList.add(MonitorCommand.class);
                    commandClassList.add(StackCommand.class);
                    commandClassList.add(ThreadCommand.class);
                    commandClassList.add(TraceCommand.class);
                    commandClassList.add(WatchCommand.class);
                    commandClassList.add(TimeTunnelCommand.class);
                    commandClassList.add(JvmCommand.class);
                    commandClassList.add(MemoryCommand.class);
                    commandClassList.add(PerfCounterCommand.class);
                    commandClassList.add(OgnlCommand.class);
                    commandClassList.add(MemoryCompilerCommand.class);
                    commandClassList.add(RedefineCommand.class);
                    commandClassList.add(RetransformCommand.class);
                    commandClassList.add(DashboardCommand.class);
                    commandClassList.add(DumpClassCommand.class);
                    commandClassList.add(HeapDumpCommand.class);
                    commandClassList.add(JulyCommand.class);
                    commandClassList.add(ThanksCommand.class);
                    commandClassList.add(OptionsCommand.class);
                    commandClassList.add(ClsCommand.class);
                    commandClassList.add(ResetCommand.class);
                    commandClassList.add(VersionCommand.class);
                    commandClassList.add(SessionCommand.class);
                    commandClassList.add(SystemPropertyCommand.class);
                    commandClassList.add(SystemEnvCommand.class);
                    commandClassList.add(VMOptionCommand.class);
                    commandClassList.add(LoggerCommand.class);
                    commandClassList.add(HistoryCommand.class);
                    commandClassList.add(CatCommand.class);
                    commandClassList.add(Base64Command.class);
                    commandClassList.add(EchoCommand.class);
                    commandClassList.add(PwdCommand.class);
                    commandClassList.add(MBeanCommand.class);
                    commandClassList.add(GrepCommand.class);
                    commandClassList.add(TeeCommand.class);
                    commandClassList.add(ProfilerCommand.class);
                    commandClassList.add(VmToolCommand.class);
                    commandClassList.add(StopCommand.class);
                # 3658 telnet
                shellServer.registerTermServer(new HttpTelnetTermServer(configure.getIp(), configure.getTelnetPort(), options.getConnectionTimeout(), workerGroup, httpSessionManager));
                # 8563 http
                shellServer.registerTermServer(new HttpTermServer(configure.getIp(), configure.getHttpPort(), options.getConnectionTimeout(), workerGroup, httpSessionManager));
                # listen com.taobao.arthas.core.shell.impl.ShellServerImpl#listen
                for shellServer.registerCommandResolver(resolver); # builtinCommands
                shellServer.listen(new BindHandler(isBindRef)); # com.taobao.arthas.core.shell.handlers.BindHandler#BindHandler
                    # 启动telnet监听
                    # both suport http/telnet
                    # 3658 com.taobao.arthas.core.shell.term.impl.httptelnet.HttpTelnetTermServer
                    termServer.termHandler(new TermServerTermHandler(this));
                    termServer.listen(handler); # com.taobao.arthas.core.shell.handlers.server.TermServerListenHandler
                        bootstrap = new NettyHttpTelnetTtyBootstrap(workerGroup, httpSessionManager).setHost(hostIp).setPort(port);
                        bootstrap.start
                            # 省略termd框架处理，直接到回调函数：建立连接，得到conn对象
                            termHandler.handle(new TermImpl(Helper.loadKeymap(), conn)); # TermServerTermHandler
                                shellServer.handleTerm(term);
                                    ShellImpl session = createShell(term);
                                        Session session = new SessionImpl();
                                    session.init(); # ShellImpl
                                        term.interruptHandler(new InterruptHandler(this));
                                        term.suspendHandler(new SuspendHandler(this));
                                        term.closeHandler(new CloseHandler(this));
                                        term.write(welcome + "\n");
                                    sessions.put(session.id, session); # Put after init so the close handler on the connection is set
                                    session.readline(); # Now readline
                                        term.readline # TermImpl
                                            readline.readline # io.termd.core.readline.Readline
                    # 启动websocket监听
                    # 8563 com.taobao.arthas.core.shell.term.impl.HttpTermServer
                    termServer.termHandler(new TermServerTermHandler(this));
                    termServer.listen(handler); # com.taobao.arthas.core.shell.handlers.server.TermServerListenHandler
                        bootstrap = new NettyWebsocketTtyBootstrap(workerGroup, httpSessionManager).setHost(hostIp).setPort(port);
                        bootstrap.start
                            略，同上
                sessionManager = new SessionManagerImpl(options, shellServer.getCommandManager(), shellServer.getJobController());
                httpApiHandler = new HttpApiHandler(historyManager, sessionManager);
                SpyAPI.init();
```

##### 命令行交互流程

基于如下命令分析：
`sysprop | grep "java" >> test.txt`

断点：
com.taobao.arthas.core.shell.handlers.shell.ShellLineHandler#handle

> todo 理解token处理逻辑
> com.taobao.arthas.core.shell.cli.CliTokens#tokenize

```bash
com.taobao.arthas.core.shell.handlers.shell.ShellLineHandler#handle
    CliTokens.tokenize(line);
    Job job = createJob(tokens);
        job = shell.createJob(tokens); # com.taobao.arthas.core.shell.impl.ShellImpl
            job = jobController.createJob(
                commandManager, # new InternalCommandManager(resolvers); —— 包含十几个命令
                args, # tokens
                session, # 会话
                new ShellJobHandler(this), 
                term, # 连接
                null
            ); # com.taobao.arthas.core.shell.system.impl.GlobalJobControllerImpl
                job = super.createJob() # com.taobao.arthas.core.shell.system.impl.JobControllerImpl
                    Process process = createProcess(session, tokens, commandManager, jobId, term, resultDistributor); # com.taobao.arthas.core.shell.system.Process
                        Command command = commandManager.getCommand(token.value());
                            # com.taobao.arthas.core.command.BuiltinCommandPack#initCommands
                            CommandResolver
                                command = getCommand(resolver, commandName);
                                    name.equals(command.name())
                            BuiltinCommandResolver
                        return createCommandProcess(command, tokens, jobId, term, resultDistributor);
                            ProcessOutput ProcessOutput = new ProcessOutput(stdoutHandlerChain, cacheLocation, term);
                            ProcessImpl process = new ProcessImpl(command, remaining, command.processHandler(), ProcessOutput, resultDistributor);
                            process.setTty(term);
                    JobImpl job = new JobImpl(jobId, this, process, line.toString(), runInBackground, session, jobHandler);
                    jobs.put(jobId, job);
                    return job
                JobTimeoutTask jobTimeoutTask = new JobTimeoutTask(job);
                ArthasBootstrap.getInstance().getScheduledExecutorService().schedule(jobTimeoutTask, jobTimeoutInSecond, TimeUnit.SECONDS);
                jobTimeoutTaskMap.put(job.id(), jobTimeoutTask);
                job.setTimeoutDate(timeoutDate);
    job.run();
        run # com.taobao.arthas.core.shell.system.impl.JobImpl
            # com.taobao.arthas.core.shell.system.impl.ProcessImpl
            process.setSession(this.session);
            process.run(foreground);
                processStatus = ExecStatus.RUNNING;
                # 封装process
                process = new CommandProcessImpl(this, tty); # com.taobao.arthas.core.shell.system.impl.ProcessImpl.CommandProcessImpl
                resultDistributor = new TermResultDistributorImpl(
                    process, 
                    ArthasBootstrap.getInstance().getResultViewResolver(); # 包含initBeans时注册的ViewResolver集合
                );
                cl = commandContext.cli().parse(args2);
                process.setArgs2(args2);
                process.setCommandLine(cl);
                # 执行process
                Runnable task = new CommandProcessTask(process);
                ArthasBootstrap.getInstance().execute(task);
                    handler.handle(process); # Handler<CommandProcess>
                        handler.handle(process);      # com.taobao.arthas.core.shell.command.impl.AnnotatedCommandImpl.ProcessHandler
                            process(process)  # com.taobao.arthas.core.shell.command.impl.AnnotatedCommandImpl
                                AnnotatedCommand instance = clazz.newInstance();
                                instance.process(process);
                                    # 具体Command的实现，如com.taobao.arthas.core.command.basic1000.SystemPropertyCommand
                                    process.appendResult(new SystemPropertyModel(System.getProperties()));
                                        System.getProperties(); # JDK函数
                                        new SystemPropertyModel; # 包装数据 MVC模式 ResultModel
                                        ProcessImpl.this.appendResult
                                            resultDistributor.appendResult # TermResultDistributorImpl
                                                # 拿View处理器
                                                getResultView
                                                    resultViewMap.get(model.getClass())
                                                # 调用View处理器
                                                resultView.draw(commandProcess, model)
                                                    # 具体Command对应的ResultView实现，如SystemPropertyView
                                                    renderKeyValueTable
                                                        # 因为是控制台输出，所以这里大量调用text-ui的内容（taobao封装的又一个库，处理字符窜格式化）
                                                        TableElement table = new TableElement(1, 4).leftCellPadding(1).rightCellPadding(1);
                                                        RenderUtil.render
                                                            略
```

#### arthas-spy.jar

略，仅作标识作用

#### termd框架

```bash
termd —— Arthas's terminal implementation is based on termd, an open source library for writing terminal applications in Java.
版本：
1.1.7.12
git checkout -b termd-core-1.1.7.12
例子：
examples.events.SshEventsExample
+ ssh 127.0.0.1 -p 4000 -o HostkeyAlgorithms=+ssh-dss
examples.events.TelnetEventsExample
examples.events.WebsocketEventsExample
```

### 网络通信架构（netty）

+ telnet 3658
    启动telnet服务入口

+ http 8563
    启动websocket服务入口

### 组件关系

todo ResultModel
todo ResultView

todo Subject

todo ShellServer
    todo Shell —— session
        todo Term —— The terminal. （extend Tty —— Provide interactions with the Shell TTY.）
            todo TtyConnection
                extends TelnetTtyConnection
            todo Readline
                todo Interaction
        todo Session
        todo JobController
        todo Job
        todo InternalCommandManager
    todo TermServer —— A server for terminal based applications.
        impl
            todo TelnetTermServer
            todo HttpTermServer
            todo HttpTelnetTermServer
                todo NettyHttpTelnetTtyBootstrap
                    todo NettyHttpTelnetBootstrap
                        ServerBootstrap —— netty
                            todo
                            todo TtyConnection # io.termd.core.telnet.TelnetTtyConnection
                                todo TtyEventDecoder
        todo Session
            todo Job
        todo Readline
            todo Interaction
        handler
            todo echoHandler
            todo stdinHandler
            todo stdoutHandlerChain
            todo SignalHandler
                interruptHandler
                suspendHandler
todo CommandResolver
    todo BuiltinCommandResolver
    todo BuiltinCommandPack
todo AnnotatedCommand
todo Command
    todo AnnotatedCommandImpl
todo Tty
    todo CommandProcess
todo Handler
    todo ProcessHandler

### 命令实现流程

todo client -> server

### 命令实现原理

todo trace/thread/dashboard处理方式

## use-case

别人使用arthas一些好的实践，帮助增加定位问题思路。
