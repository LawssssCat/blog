---
title: 世界树
---

规定：

- 标题
  - 标题2 = 领域
  - 标题3 = 大类
  - 标题4 = 小类

## 计算机

操作系统: [link](./ops-os-linux/README.md)

### 系统管理

#### 传输数据、文件上传/下载

工具：

- ~~IDM —— 支持：http。 商业软件~~
- bittorrent  —— 支持：bt。
  - 原生
  - 封装
    - qBittorrent
      - todo VueTorrent 界面
- aria2 —— 支持：bt、http
  - 原生
  - 封装
    - Motrix
- Gopped —— try?

BT网站：

- ~~<https://btdig.com/index.html>~~
- <https://www.btsearch.net/>

视频平台视频下载：

- yt-dlp <https://minuo.org/yt-dlp-complete-guide-2024>
  - 使用例子

    ```bash
    # 查看格式
    yt-dlp -F https://www.youtube.com/watch?v=7SH4irC_xMs
    # 下载
    yt-dlp -f "bv+ba" --merge-output-format mp4 https://www.bilibili.com/video/BV1ei4y1h7ZP/
    yt-dlp -f "bv+ba/ba" https://www.bilibili.com/video/BV1My4y1672a/
    # 使用 Cookie
    # 方式一：通过浏览器 https://www.appinn.com/yt-dlp-cookies-from-browser/
    # ❗注意：需要读取 Cookies，需要先关闭相关浏览器，否则可能无权限打开
    # C:\Users\14134\AppData\Local\Google\Chrome\User Data\Default\Network\Cookies
    yt-dlp.exe --cookies-from-browser chrome -F https://www.bilibili.com/video/xxxx
    yt-dlp.exe --cookies-from-browser chrome:"C:\Users\scavi\AppData\Local\Google\Chrome\User Data\Default" -F https://www.bilibili.com/video/xxxx
    # 方式二：单独文件
    yt-dlp --cookies-from-browser chrome --cookies 2.txt
    ```

#### 网络互通

VPN：跨局域网安全传输数据

- WireGuard
  - GUI
    - WGDashboard —— WireGuard VPN 仪表盘

内网穿透：暴露局域网服务到公网

- frp
- ngrok
- [rathole](https://github.com/rathole-org/rathole)
- Portr —— 暴露本地htp、tcp、websocket至公网。基于SSH转发确保连接稳定安全，支持多协议隧道（http、tcp、websocket），支持管理后台、支持监控

#### 系统监控

服务监控

- dashy <https://github.com/Lissy93/dashy>
- zabbix
- 普罗米修斯
- gradxxxx
- Pinpoint - APM监控工具
  - todo https://www.bilibili.com/video/BV1Ri4y1X7UC/
- Skywalking - APM管理工具
  - todo https://www.bilibili.com/video/BV1Tq4y127Yi/

网络监控

- networkmanager

### 软件开发

#### 软件和硬件中间层

操作系统内核

- Linux Kernel
  - 内存模型

程序启动：

- ATF(arm-trusted-firmware)
- das u-boot —— 嵌入式系统中的引导过程和环境

基础库

- glibc —— c语言基础库
- MUSL —— glibc 竞品

#### 开发环境管理

- [mise](https://github.com/jdx/mise)
- [dotenv](https://github.com/motdotla/dotenv)

#### 黑盒测试工具

压力测试：

- jmeter
- burpsite

浏览器自动化

- selenium

#### 白盒测试工具

代码质量检查：

- Sonar
  - <https://www.bilibili.com/video/BV1rb4y1n77K/>
- archunit @analyzeclasses @archtest —— Java代码架构检查
- <https://github.com/diffplug/spotless>
- <https://github.com/find-sec-bugs/find-sec-bugs> （插件）

接口测试：

- [rest-assured](https://github.com/rest-assured/rest-assured) Java DSL rest-assured

单元测试框架：

- <https://github.com/spockframework/spock>

#### CI/CD

- jenkins
  - [Jenkins Job DSL Plugin](https://github.com/jenkinsci/job-dsl-plugin)

#### 分布式资源管理

虚拟机管理：

- vagrant

容器管理：

- containerd —— 容器管理引擎
- slim —— 镜像缩小

集群管理：

- Kubernetes

微服务管理：

- Spring Cloud
- ServiceComb 微服务快速搭建框架 | 国产化 Spring Cloud

#### JavaScript原生

资讯整理

- JavaScript 中文网 <www.javascriptcn.com>

#### JAVA原生

[link_整理](./dev-java-commonsense/README.md)

调试工具

- jps/jstack/...
- jd-gui <https://github.com/java-decompiler/jd-gui> —— 反编译

即时编译

- Java VisualVM - jvisualvm
  - todo 查看进程/线程资源使用情况
- groovy

#### 文字处理

regex

- todo regex 可视化

json

- JsonCrack（json 可视化）

工具集合

- CyberChef（网络字符串处理）

#### 音视频处理

处理引擎

- ffmepg

#### 命令行界面开发

终端

- mosh —— ssh连接优化，如放置连接终端掉线

参数交互

- [picocli](https://github.com/remkop/picocli) —— java库，实现命令行交互中的参数/打印处理。支持颜色、自动补全、子命令等功能。
- apache cli
- ~~[alibaba/cli](https://github.com/alibaba/cli) —— java库，在arthas中有使用~~

流处理

- jline —— java库，实现命令行交互中的流式功能

#### Web服务

API提供

- spring web
- todo spring flux —— nio

API消费

- httpclient
  - todo https://www.bilibili.com/video/BV1W54y1s7BZ/

权限认证

- RBAC
  - sa-token
    - todo api 参数签名 https://www.bilibili.com/video/BV17oeKeZEHo/

#### CRUD

常用工具

- apache commons系列
- guava
  - todo https://www.bilibili.com/video/BV1Lv411P7Ua/
  - todo https://www.bilibili.com/video/BV1RmpUeyEgW/
- ~~hutool —— 被收购~~
- jodd
- spring utils

数据库引擎

- PostgreSQL PL/Java —— PostgreSQL 的存储过程、触发器和函数

数据库操作

- jdbc
  - druid
    - todo 监控 druid https://www.bilibili.com/video/BV1ih411n7Ps/
- mybatis
  - pagehelper
  - basetypehandler jsonarray
  - 监控
    - dolphie
  - 性能测试
    - TPCC

#### 流量分发

流量代理：

- nginx

虚拟交换机：

- open vswitch —— 虚拟交换机
- dpdk —— 流量包处理

#### 语法分析

- <https://hanlp.hankcs.com/en/demos/con.html> —— yyds
- <https://hanlp.hankcs.com/>
- <https://github.com/baidu/DDParser>

#### 逆向分析

todo <https://www.bilibili.com/video/BV1FP411B7b7/>

#### 大数据分析

大数据仓库/分析引擎

- Hadoop
  - MapReduce 数据集并行运算
- Spark —— 流批处理
- flink —— 流批处理
  - todo Flink SQL双流Join原理及使用详解 <https://www.bilibili.com/video/BV1QD4y1h7CJ/>

全文检索

- Lucene
- elasticsearch —— elk中的e <https://www.cnblogs.com/zsql/p/13164414.html>
  - 可视化
    - elkjs <https://github.com/kieler/elkjs>
      - <https://github.com/eclipse/sprotty>

数据采集

- flume 日志采集

#### 低代码

- ~~JECloud 小心商业坑~~

### 系统信息安全

#### 安全通信

操作系统：

- tails（The Amnesic Incognito Live System，无痕匿名自启动系统） <https://tails.net/>

网络服务：

- 服务列表 <https://zh.wikipedia.org/zh-cn/%E5%8C%BF%E5%90%8D%E6%9C%8D%E5%8A%A1%E5%88%97%E8%A1%A8>
- 资讯 <https://wikileaks.org/>
- 资讯 <https://www.couragefound.org/>
- 交易 <https://bitcoin.org/>
- 网站访问/浏览器 <https://www.torproject.org/>

IP 检测、DNS 泄漏、WebRTC 测试：

- <https://browserleaks.com/>
  - 检查IP劫持： <https://browserleaks.com/ip>
  - 检查DNS信息泄露： <https://browserleaks.com/dns>
- <https://little-drummer.github.io/IPTest/>

#### 安全监测

- wazuh

#### 安全测试

钓鱼

- gophish

#### 安全资讯

- cisp
- freebuf 安全门户网站

### 人工智能

#### 论文

- 重新思考开源生成式人工智能：“开放清洗”与《欧盟人工智能法案》 <https://pure.mpg.de/rest/items/item_3588217_2/component/file_3588218/content>
  - 跟踪指令调优LLM的开放性 <https://opening-up-chatgpt.github.io/>

#### 计算引擎

人工智能计算框架

- MindSpore（昇思）

#### 交互界面

agent:

- OpenClaw
- ClaudeCode
- OpenCode

chat:

- chatgpt
- [lobehub](https://github.com/lobehub/lobehub)

### 多媒体

#### 视频播放

播放器

- kazumi 看番
- mpv 播放器
- [wiliwili](https://github.com/xfangfang/wiliwili) —— 播放器 for 手柄（可在PC全平台、PSVita、PS4 、Xbox 和 Nintendo Switch上播放）

#### 视频编辑

屏幕录制：

- obs
- [cap](https://github.com/CapSoftware/Cap)
- [streamcap](https://github.com/ihmily/StreamCap)

#### 平面图片编辑

PPT：

- Slidev
  - demo: github.com/sxzz/talks

屏幕截图：

- ~~snipaste~~（好用，但...）/~~snagit~~/~~pixpin~~（好用，但...）

#### 系统交互提示

键盘/鼠标输入显示：

- carnac
- [Keyviz](https://github.com/mulaRahul/keyviz)

语音识别：

- <https://github.com/cold-land/airinputlan>

#### 网站访问

广告拦截：

- uBlock
- VT4Browsers —— 下载链接病毒扫描

流量代理：

- SwitchyOmega —— 代理切换

网站保存：

- xxx ？？？ —— 页面（单页面）离线保存
- GoFullPage - Full Page Screen Capture —— 全屏截图

文字办公

- google 文档 —— office 在线编辑

文本翻译

- ~~划词翻译 —— 浏览器插件~~

#### 邮件通信

邮件服务

- ewomail

#### NAS

选购：

- <https://www.youtube.com/watch?v=AfS8BVMb5K0>

#### 论坛/bbs（Bulletin Board System，电子公告板）

- 维基 <https://zh.wikipedia.org/zh-cn/BBS>
- 4chan <https://www.4chan.org/>
- 8kun <https://8kun.top/index.html>
- 2channel
  - 2ch.sc
  - 5ch.net
- 批踢踢 <https://www.ptt.cc/bbs/hotboards.html>

#### 实时通讯

- discord

#### 文件分享

- [catbox](https://catbox.moe/)
- ~~sm.ms~~ —— 商业化？

## 经济

### 金融

todo 投资
