---
title: UML 使用
date: 2024-09-19
order: 1
---

UML（Unified Modeling Language，统一建模语言）

## 标准

标准：

- [OMG(Object Management Group，国际对象管理协会) UML(Unified Modeling Language) Specification](https://www.omg.org/spec/UML/2.5.1/About-UML/)
  - alias
    - UML 2.0
    - UML 2.x standard compliant
  - 证书
    - ~~[OCSMP(OMG Certified Systems Modeling Professional)](https://www.omg.org/certification/sysml/)~~ 评估认证人员使用 SysML 进行系统建模能力的认证 （价格高到飞起 350 刀/次 至少考 4 次。且国内基本没用？猜的）
    - 软考，不是主要考 uml，国内相对 ocsmp 有用？
- sysml http://production.omgsysml.org/spec/

UML 图分类

- 结构图（Structual Diagrams）
  - 类图（Class Diagram）
  - 包图（Package Diagram）
  - 组合结构图/复合结构图（Composite Structure Diagram）
  - 对象图/实例图（Object Diagram）
  - 组件图（Component Diagram）
  - 部署图（Deployment Diagram）
  - 剖面图（Profile Diagram）
- 行为图（Behavioral Diagrams）
  - 活动图（Activity Diagram）
  - 用例图（Use Case Diagram）
  - 状态机图（State Machine Diagram）
  - 交互图（Interaction Diagram）
    - 序列图/顺序图（Sequence Diagram）
    - 时序图/时间图（Timing Diagram）
    - 通信图（Communication Diagram）
    - 交互概念图（Interactive Overview Diagram）

### 用例图（Use Case Diagram）

概念

- 用例
  - 系统的一个特征，完成一个业务目标
  - 区分需求的必要性、价值性、完整性
- 参与者
  - 在系统外部和系统交互的人/设备/系统

关系

- 包含 include
- 扩展 extend

### 活动图（Activity Diagram）/流程图（Flowchart Diagram）/BPMN

参考：

- https://www.bilibili.com/video/BV1MY411c7BN/
- https://www.bilibili.com/video/BV1zi4y187Q2?p=2
- https://sparxsystems.com/enterprise_architect_user_guide/17.0/modeling_languages/interruptibleactivityregion.html

区别

- 流程图 —— ASME（美国机械工程师学会） 提出，ISO 5807:1985
- 活动图 —— OMG 组织管理规范 ISO/IEC 19505-1:2012
  - 活动图是流程图的扩展
  - 活动图属于 UML， **偏向架构设计** （整个 UML 不止能描述业务流程，还能描述需求和用例等）
- BPMN（Business Process Modeling Notation，业务流程建模标记法） —— BPMI 机构管理（2005 年并入 OMG）
  - BPMN 用于描述具体业务流程，有特定的使用场景： **在开发工作流引擎时，一般用 BPMN 作为业务流程定义的输入**

概念

- 泳道 —— 表示不同的活动主体（人），包含各种活动
- 节点
  - 动作（Action）
  - 传递性节点（Call Activity Node） —— 代表子系统
  - 事物（Object Node） —— 活动中的信息主体
  - 事件
    - 发送事件 —— 左尖
    - 接收事件 —— 右尖
    - 时间事件（Time Event，作为初始节点时为 Repeating Time Event） —— 沙漏
- 线条
  - 事件
  - 分叉/汇合 —— 并行/动作间没有必然先后顺序要求
  - 警卫条件（Guard Condition）
  - 中断事件（Interrupting Event）
- 扩充区（Expansion Region）
- 结构化活动 —— 定义循环

### 类图（Class Diagram）

参考：

- https://www.bilibili.com/video/BV1KL4y1F7go/
- https://www.bilibili.com/video/BV1zi4y187Q2?p=3
- https://docs.staruml.io/working-with-uml-diagrams/class-diagram
- https://www.cnblogs.com/coolstream/p/9572846.html

用于描述类的内部结构以及与其他类的关系

类

- 名称
- 属性/字段/成员变量 `[可见性][/]名称[:类型名][多重性][=默认值][{特性字符串}]`
- 操作/行为/成员方法 `[可见性]名称([参数列表]):[返回值][{特性字符串}]`
- 约束（OCL，Object Constraint Language） —— e.g. `self.name -> notEmpty()`

常用的关系 see [link](https://docs.staruml.io/working-with-uml-diagrams/class-diagram#relationship)

- association 关联关系，关系弱 `-----` e.g. 森林与老虎 —— 成员变量
  - 关联类（association class） e.g. 人 —— 参与者（关联类） —— 会议
- aggregation 聚合关系，关系强 `----<>` e.g. 雁群与雁 —— 成员变量
- composition 组合/复合/合成关系，关系最强 `----<+>` e.g. 人与头 —— 成员变量
- dependency 依赖关系 `....>` —— 形式变量、局部变量
- generalization 泛化/继承 `....|>` —— extends
- realization 特化/实现 `----|>` —— implements

> **属性还是关系？** 属性也能用 “关联”/“组合” 关系表示，用于突出被关系者的内部细节

### 对象图（Object Diagram）

参考：

- https://docs.staruml.io/working-with-uml-diagrams/object-diagram
- https://www.visual-paradigm.com/tw/guide/uml-unified-modeling-language/what-is-object-diagram/

类图运行时的一个快照

### 包图（Package Diagram）

参考：

- https://docs.staruml.io/working-with-uml-diagrams/package-diagram
- https://www.uml-diagrams.org/examples/java-servlet-30-api-package-diagram-example.html?context=pkg-examples

宏观展示 “特性”/“领域” 间关系

### 组合结构图（Composite Structure Diagram）

参考：

- https://www.cnblogs.com/coolstream/p/9573438.html
- https://docs.staruml.io/working-with-uml-diagrams/composite-structure-diagram
- https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-composite-structure-diagram/
- https://www.ibm.com/docs/zh/dmrt/9.5?topic=diagrams-composite-structure

UML 2.0 新规，类图的扩展，描绘类内部属性的组合结构

与类图相似，复合结构图用于显示类与类之间的关系。
与类图不同，复合结构图可以显示类的内部结构和这个结构所实现的协作。

元素

- 类 class —— 包含多个部件，每个部件视作其一个属性（attribute）
- 部件 part —— 类内部的属性/内部类
- 端口 port —— 对外暴露的接口
- 协作 collaboration —— 包含多个部件
- collaboration use

关系

- Connector
- Role Binding Connector

### 组件图（Component Diagram）

参考：

- https://www.bilibili.com/video/BV1BT4y167rd/
- https://docs.staruml.io/v/v3/working-with-diagrams/component-diagram
- https://www.uml-diagrams.org/component-diagrams.html
- https://www.cnblogs.com/coolstream/p/9572868.html

组件图显示组件、提供的和所需的接口、端口、 以及他们之间的关系。
这种类型的图表用于 **基于组件的开发 （CBD, Component-Based Development）** 描述具有 **面向服务的体系结构 （SOA, Service-Oriented Architecture）** 的系统。

> 组件 vs 对象
>
> 组件和对象都是为了提高 “重用性” 和 “可替换性”。
>
> - 对象： 类、继承、多态。关注逻辑关系 —— 不同类是怎么关联起来的。
> - 组件： 接口、服务、关注点分离。关注具体实现层面的关系 —— 不同接口怎么组装在一起。

UML 组件图的主要元素

- 组件 —— 组件（Component）是定义了良好接口的、可重用的、可替代的物理实现单元，它一般表示实际存在的、物理的物件。程序源代码、可执行文件、子系统、一个脚本、动态链接库（DLL）、ActiveX 控件都可以成为系统中的组件。组件隐藏了内部实现的细节，仅通过接口提供服务。
- 接口 —— 组件中的接口主要分为两类：提供接口（Provided Interface）和需求接口（Required Interface）。
- 端口 —— 端口用于描述组件或类与它的环境、与其它类、与其它组件或内部部件的交互点。这个交互点一般是组件或类的一个属性，默认情况下，端口具有公共可见性。
- 连接器

### 部署图（Deployment Diagram）

参考：

- https://www.bilibili.com/video/BV1zi4y187Q2?p=9
- https://docs.staruml.io/v/v3/working-with-diagrams/deployment-diagram
- https://www.cnblogs.com/coolstream/p/9572870.html
- https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-deployment-diagram/

部署图也称配置图，用来显示系统中硬件和软件的**物理架构**。
从中可以了解到软件和硬件组件之间的物理拓扑、连接关系以及处理节点的分布情况。

部署图主要元素

- 物件(Artifact)：UML 部署图中的物件时软件开发过程中的产物，包括需求文档、源代码、库文件、可执行程序、库文件、用户手册等。
- 节点(Node)：
  - 执行环境节点（executionEnvironment） —— 物件部署的目标位置。如硬件设备或运行在硬件设备上的软件系统。
  - 设备节点（device） —— 不用于部署物件，但是参与节点间的通信
- 连接(Association)：节点或物件之间的连线。见部署图主要关系部分

部署图主要关系

- 依赖：节点的物件之间存在相互调用的弱关系
- 关联：节点间的通信方式。跟具体的协议有关，比如 TCP/IP, ftp, http, soap, web service 等

### 4+1 View Model

todo https://www.bilibili.com/video/BV1Ya41157SU

todo https://www.bilibili.com/video/BV1ogxTezERp/

- Scenarios —— 用例图（Use Case Diagram）
- Logical View —— 类图（Class Diagram）
- Development View —— 组件图（Component Diagram）
- Process View —— 组件图（Component Diagram）
- Physical View —— 部署图（Deployment Diagram）

### 状态机图（State Machine Diagram）

参考：

- https://www.bilibili.com/video/BV1YR4y1A7Mr/

成员

- 状态
  - 初态/终态 —— 伪状态（不是真正的状态），用于表示状态转移的最初开始和最终结束方向
    - 初态 —— 有且只有一个
    - 终态 —— 0 ~ n 个
  - 简单状态
  - 领域（Region）
    - 子状态
  - 历史状态
    - 深历史 —— 记录所有深度子状态
    - 浅历史 —— 记录当前深度子状态
- 状态转移 —— `源状态 --> 触发事件 [监护条件] / 动作 --> 目标状态`
- 条件
- 并发

### 序列图（Sequence Diagram）

参考：

- https://www.cnblogs.com/coolstream/p/9572860.html
- https://docs.staruml.io/v/v3/working-with-diagrams/sequence-diagram

时序图表示参与者与对象之间、对象与对象之间的动态交互过程及时序关系。

时序图详细而直观地展示了对象随时间变化的状态、调用关系和消息时序，时序图中的主要元素有：

- 参与者/交互者(Actor) —— 参与系统的角色，可以是人、物或其它系统
- 门（Gate） —— 消息通过门进入或者离开交互或交互片段 | 用来定义一部分的交互
- 对象(Object)
- 生命线(Lifetime)
- 控制焦点(Focus of Control)
- 消息(Message)
  - 无触发消息/无接收消息 —— 不关心消息的发送者/接收者。一般用于起始/结束消息
  - 同步消息
  - 重入消息
  - 异步消息
- 激活(Activation)
- 约束(Constraint)
- 组合片段(Combined Fragments) —— 描述交互执行的条件与方式

  - alt(Alternative) —— 备用多个片段：与 if...else...或 switch 对应
  - opt(Optional) —— 可选：可能发生也可能不发生的片段
  - par(Parallel) —— 并行：并行发生
  - `loop [value]` —— 循环：与 for 或 foreach 对应，`[value]` 表示循环次数
  - region —— 关键区域：片段只能有一个线程一次执行它
  - neg —— 否定：片段显示无效的交互
  - ref —— 参考：指在另一个图上定义的交互。绘制框架以覆盖交互中涉及的生命线。可以定义参数和返回值。
  - sd —— 序列图：用于包围整个序列图

- 连续（continuation）

### 通信图（Communication Diagram）

参考：

- https://www.bilibili.com/video/BV1zi4y187Q2?p=5

顺序图 vs 通信图

- 通信图和顺序图可以等价互换
- 通信图牺牲了顺序图上的直观性
- 通信图增强了布局和关联上的直观性

要点：

- 通信图每个消息必须要有序号，表示消息的出现顺序！

### 时序图（Timing Diagram）

参考：

- https://www.bilibili.com/video/BV1zi4y187Q2?p=6

## 工具

画图

- [Enterprise Architect](https://sparxsystems.com/) —— 强大，收费 | todo https://space.bilibili.com/74123713/channel/series
- Rational Rose 2007 / Rational Rose 7.0 —— 完善，IBM 收费
- draw.io —— 开源答案、二次开发答案
- ~~[StarUML](./staruml.md) —— 小，免费~~ （不好用）

码图（适合简单场景、可管理变更）

- plantUML

other

- todo Astah | uml 画图工具，强大 but 收费

- todo Cameo Systems Modeler | <https://sysmltools.com/reviews/cameo-systems-modeler-sysml/>

### 工具间导入导出

不同工具绘制的 UML 图，可以通过 XMI 在系统间数据交换

XMI 是 OMG 组织定义的 UML 图表达格式

todo 格式分析

## 信息源

todo 软件工程

- todo https://www.bilibili.com/video/BV1o8411i7Pe/
- todo https://www.bilibili.com/video/BV18y4y1h7e7/

todo https://www.bilibili.com/video/BV17C4y1e7E4/

参考：

- [UML 系列视频教程](https://www.bilibili.com/video/BV1MY411c7BN/) （⭐⭐⭐⭐⭐） —— 入门概念
- [软件建模-统一建模语言 UML Tutorial (2.0)](https://www.bilibili.com/video/BV1zi4y187Q2/) —— 另一种描述，比较睡意/自然？
- [sparxsystems | Unified Modeling Language (UML)](https://sparxsystems.com/enterprise_architect_user_guide/17.0/modeling_languages/whatisuml.html) —— 字典
- todo https://www.uml-diagrams.org/class-diagrams-overview.html
- todo https://docs.staruml.io/user-guide/basic-concepts
- todo https://www.visual-paradigm.com/cn/guide/uml-unified-modeling-language/what-is-uml/
- todo https://www.zhihu.com/people/OOThinking
